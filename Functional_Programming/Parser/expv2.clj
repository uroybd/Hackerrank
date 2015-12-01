(defn issymbol? [x]
  (or (= x "+")
      (= x "-")
      (= x "/")
      (= x "*")
      (= x "(")
      (= x ")")))

(defn numify [dvec]
  (let [parted (partition-by #(not (issymbol? %)) dvec)
        end (dec (count parted))]
    (loop [i 0 dv []]
      (if (> i end)
        dv
        (let [target (nth parted i)]
          (cond (issymbol? (first target))
                (recur (inc i) (mapv identity (concat dv target)))
                :else (recur (inc i) (mapv identity (conj dv (apply str target))))))))))

(defn negetor [dvec]
  (let [end (dec (count dvec))]
    (loop [i 0 dv []]
      (if (> i end)
        dv
        (let [target (nth dvec i)]
          (if (issymbol? target)
            (if-not (= i end)
              (if (and (= (nth dvec (inc i)) "-")
                       (not (issymbol? (nth dvec (+ 2 i)))))
                (recur (+ 3 i)
                       (conj dv target
                             (str (nth dvec (inc i))
                                  (nth dvec (+ 2 i)))))
                (recur (inc i) (conj dv target)))

              (recur (inc i) (conj dv target)))
            (recur (inc i) (conj dv target))))))))

(defn bracetor [dvec]
  (mapv #(cond (= "(" %)
               "["
               (= ")" %)
               "]"
               :else %) dvec))

(defn AST []
  (let [input (read-line)]
    (read-string
     (apply str
            (interpose
             " "
             (concat ["["]
                     (bracetor
                      (negetor
                       (numify
                        (filterv (fn [x] (and (not= x "") (not= x " ")))
                                 (clojure.string/split input #"")))))
                     ["]"]))))))

(defn equator [dvec]
  (if (= (type []) (type dvec))
    (if (integer? (first dvec))
      (loop [v dvec]
        (if (= 1 (count v))
          (first v)
          (recur (mapv identity (concat [[(equator (nth v (- (count v) 2))) (equator (nth v (- (count v) 3))) (equator (last v))]] (subvec v 0 (- (count v) 2)))))))
      dvec)
    dvec))


(defn main []
  (let [data (AST)]
    ()))
