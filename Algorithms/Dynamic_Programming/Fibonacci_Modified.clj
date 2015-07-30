(defn fibonacciModified []
  (let [array (mapv identity (map #(Integer/parseInt %) (clojure.string/split (read-line) #" ")))
        square (fn [i]
                 (* i (bigint i)))
        computer (fn [i j limit]
                 (loop
                     [fst i scnd j pt 3]
                   (if (= pt limit)
                     (println (.toString (+ fst (square scnd))))
                     (recur scnd (+ fst (square scnd)) (inc pt)))))]
    (computer (first array) (second array) (last array))))

(fibonacciModified)
