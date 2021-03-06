(defn equation-divider [string]
  (flatten (map #(clojure.string/split % #";")
                (map clojure.string/trim
                     (clojure.string/split string #":=")))))

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
    (map #(cond (= (.indexOf coll %) 0) (symbol %)
                (seq? %) (parser %)
                (re-find #"\d+" %) (bigdec (Integer/parseInt %))
                :else (let [x %]
                        `(bigdec (deref ~(symbol (str "whl-" x))))))
         coll)
    (if (re-find #"\d+" coll)
      (bigdec (Integer/parseInt coll))
      `(bigdec (deref ~(symbol (str "whl-" coll)))))))

(defmacro definer [string]
  (let [keyvals (map clojure.string/trim (equation-divider string))
        value (parser
               (arith-parser
                (last keyvals)))
        dkey (str "whl-" (first keyvals))]
    (if (some #(= dkey %) (map str (keys (ns-publics 'user))))
      `(reset! ~(symbol dkey) ~value)
      `(def ~(symbol dkey) (atom ~value)))))

(defn dofi [x] (macroexpand-1 `(definer ~x)))

(defmacro while-builder [condi exps]
  (let [cond_exp (parser
                     (arith-parser
                      (last
                       (map clojure.string/trim
                            (flatten
                             (map
                              #(clojure.string/split % #"\)")
                              (clojure.string/split
                               condi #"\(")))))))]
    `(loop []
       (when ~cond_exp
         ~@(map dofi exps)
         (recur)))
   ))

(defmacro if-builder [condi expa expb]
  (let [cond_exp (parser
                  (arith-parser
                   (nth
                    (map clojure.string/trim
                         (flatten
                          (map
                           #(clojure.string/split % #"\)")
                           (clojure.string/split
                            condi #"\(")))) 1)))]
    `(if ~@cond_exp
       (do ~@(map dofi expa))
       (do ~@(map dofi expb)))))

(defn initializer [dvec]
  (map (fn [x]
         (when (re-find #":=" x)
           (let [keyvals (map clojure.string/trim
                              (equation-divider x))
                 dkey (symbol (str "whl-" (first keyvals)))]
             (eval `(def ~dkey (atom 0)))))) dvec))

(defn whiler [cursor input]
  (loop [c cursor
         limit (dec (count input))
         out []]
    (when (<= c limit)
      (let [target (nth input c)]
        (cond (re-find #":=" target)
              (recur (inc c) limit (conj out target))
              (re-find #"\}" target)
              (do
                (while-builder (nth input cursor) out)
                (inc c))
              :else (recur (inc c) limit out))))))

(defn ifer [cursor input]
  (loop [c (inc cursor) expa [] expb []  els 0]
    (when (<= c (dec (count input)))
      (let [target (nth input c)]
        (cond (re-find #":=" target)
              (if (= els 0)
                (recur (inc c) (conj expa target) expb els)
                (recur (inc c) expa (conj expb target) els))
              (re-find #"else" target)
              (recur (inc c) expa expb 1)
              (re-find #"/}" target)
              (if (= els 1)
                (do
                  (if-builder (nth input cursor) expa expb)
                  (inc c))
                (recur (inc c) expa expb els))
              :else (recur (inc c) expa expb els))))))

(defn printer []
  (map #(println (clojure.string/trim (last (clojure.string/split % #"-"))) (deref (symbol %)))
       (sort (filter #(re-find #"whl-" %)
                         (map str
                              (keys (ns-publics 'user)))))))

(defn main []
  (let [input (remove
               #(= % "")
               (map clojure.string/trim
                    (take-while identity
                                (repeatedly
                                 #(.readLine *in*)))))]
    (initializer input)
    (loop [cursor 0]
      (if (> cursor (dec (count input)))
        (printer)
        (let [target (nth input cursor)]
          (cond (re-find #"while" target)
                (recur (whiler cursor input))
                (re-find #"if" target)
                (recur (ifer cursor input))
                (re-find #":=" target)
                (do (definer target)
                    (recur (inc cursor)))
                :else (recur (inc cursor))))))))
