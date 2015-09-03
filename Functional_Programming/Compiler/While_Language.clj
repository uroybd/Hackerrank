(def varatom (atom {}))

(defn state-parser
  "Parse a string and make vector of strings"
  [string]
  (flatten (map #(clojure.string/split % #";")
                (map clojure.string/trim
                     (clojure.string/split string #":=")))))

(defmacro state [key val]
  `(swap! varatom #(assoc % ~key ~val)))

(defn spliter [string x]
  (let [splited (if (re-find
                     (cond (= x "+") #"\+"
                           (= x "*") #"\*"
                           (= x "/") #"\/"
                           :else (re-pattern x))
                     string)
                  (mapv
                   identity
                   (map clojure.string/trim
                        (clojure.string/split
                         string
                         (cond (= x "+") #"\+"
                               (= x "*") #"\*"
                               (= x "/") #"\/"
                               :else (re-pattern x)))))
                  string)]
    (if (= (type []) (type splited))
      (flatten [x splited])
      string)))

(defn checker [input fil]
  (if (seq? input)
    (map #(spliter % fil) input)
    (spliter input fil)))

(defn pre-check [sym1 sym2 string]
  (let [av1 (.indexOf (clojure.string/split string #" ") sym1)
        av2 (.indexOf (clojure.string/split string #" ") sym2)]
    (cond (= av1 av2 -1) string
          (and (= av1 -1) (not= av2 -1)) (checker string sym2)
          (and (not= av1 -1) (= av2 -1)) (checker string sym1)
          (< av1 av2) (map #(checker % sym1) (checker string sym2))
          (> av1 av2) (map #(checker % sym2) (checker string sym1)))))

(defn all-pre-check [sym1 sym2 coll]
  (if (string? coll)
    (pre-check sym1 sym2 coll)
    (map #(if (string? %)
            (pre-check sym1 sym2 %)
            (all-pre-check sym1 sym2 %))
         coll)))

(defn arith-parser [arstring]
  (all-pre-check "*" "/"
                 (all-pre-check "+" "-"
                                (all-pre-check ">" "<"
                                               (checker (spliter arstring "or") "and")))))

(defn parser [coll]
  (if (seq? coll)
    (do
      (map #(if (= (.indexOf coll %) 0) (symbol %)
                (if (seq? %)
                  (parser %)
                  (if (re-find #"\d+" %)
                    (Integer/parseInt %)
                    ((keyword %) @varatom))))
           coll))
    (if (re-find #"\d+" coll)
      (Integer/parseInt coll)
      ((keyword coll) @varatom))))

(defn exp-builder [string]
  (let [keyvals (map clojure.string/trim (state-parser string))
        value (parser
               (arith-parser
                (last keyvals)))]
    (state (keyword (first keyvals)) value)))

(defmacro getter [symbol]
  (let [kw (keyword symbol)]
    `(get @varatom ~kw)))

(defn cond-exp [item exp]
  (if (seq? item)
    (map #(cond-exp % item) item)
    (if (= (.indexOf exp item) 0)
          (symbol item)
          (if (re-find #"\d+" item)
            (Integer/parseInt item)
            (macroexpand-1 `(getter ~item))))))

(defn cond-maker [conarray]
  (map #(cond-exp % conarray) conarray))

(defmacro while-builder [dvec]
  (let [the_cond (arith-parser
                    (last
                     (map clojure.string/trim
                          (flatten
                           (map
                            #(clojure.string/split % #"\)")
                            (clojure.string/split
                             (first dvec) #"\("))))))]
    `(while ~(cond-maker the_cond)
       (do ~@(map (fn [x]
                    `(state ~(keyword (first x))
                            ~(eval (parser
                                    (arith-parser (last
                                                   x))))))
                  (map state-parser
                       (rest dvec)))))))

(defn whiler [cursor input]
  (loop [c cursor
         limit (dec (count input))
         out []]
    (when (<= c limit)
      (let [target (nth input c)]
        (cond (or (re-find #"while" target)
                  (re-find #":=" target))
              (recur (inc c) limit (conj out target))
              (re-find #"\}" target)
              (do
                (def ttt out)
                (inc c))
              :else (recur (inc c) limit out))))))

(defn ifer [cursor limit]
  (println cursor)
  (println limit))

(defn evaluator []
  (let [input (remove
               #(= % "")
               (map clojure.string/trim
                    (take-while identity
                                (repeatedly
                                 #(.readLine *in*)))))]
    (loop [cursor 0 limit (dec (count input))]
      (if (<= cursor limit)
        (let [target (nth input cursor)]
          (cond (re-find #"while" target)
                (recur (whiler cursor input) limit)
                (re-find #"if" target)
                (recur (ifer cursor input) limit)
                (re-find #":=" target)
                (do
                  (exp-builder target)
                  (recur (inc cursor) limit))
                :else (recur (inc cursor) limit)))
        (map
         #(println
           (name
            (first %))
           (last %))
         (into
          (sorted-map)
          @varatom))))))

;; (def input '("fact := 1 ;" "val := 10000 ;" "cur := val ;" "mod := 1000000007 ;" "while ( cur > 1 )" "do" "{" "fact := fact * cur ;" "fact := fact - fact / mod * mod ;" "cur := cur - 1" "} ;" "cur := 0"))
(def dvec ["while ( cur > 1 )" "do" "{" "fact := fact * cur ;" "fact := fact - fact / mod * mod ;" "cur := cur - 1"])

(while-builder dvec)
