(defn insertionSorter []
  (let [length (Integer/parseInt (read-line))
        array (mapv identity (map #(Integer/parseInt %) (clojure.string/split (read-line) #" ")))
        sorter (fn [length array]
                 (loop
                     [fst 1 sorted array]
                   (when (<= fst (dec length))
                     (apply println (mapv identity (concat (sort (subvec sorted 0 (inc fst))) (subvec sorted (inc fst)))))
                     (recur (inc fst) (mapv identity (concat (sort (subvec sorted 0 (inc fst))) (subvec sorted (inc fst))))))))]
    (sorter length array)))

(insertionSorter)
