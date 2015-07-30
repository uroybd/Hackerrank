(defn insertionSorter []
  (let [length (Integer/parseInt (read-line))
        array (mapv identity (map #(Integer/parseInt %) (clojure.string/split (read-line) #" ")))
        sorter (fn [length array]
                 (loop
                     [point (last array) cursor (- length 2) sorted array]
                   (if (> point (nth sorted cursor))
                     (apply println (assoc sorted (inc cursor) point))
                     (if (= cursor 0)
                       (do
                         (apply println (assoc sorted (inc cursor) (nth sorted cursor)))
                         (apply println (assoc (assoc sorted (inc cursor) (nth sorted cursor)) cursor point)))
                       (do
                         (apply println (assoc sorted (inc cursor) (nth sorted cursor)))
                         (recur point (dec cursor) (assoc sorted (inc cursor) (nth sorted cursor))))))))]
    (sorter length array)))

(insertionSorter)
