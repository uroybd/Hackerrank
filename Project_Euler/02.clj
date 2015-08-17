(defn fibo [range]
  (loop [dlist [1 2]]
    (if (> (last dlist) range)
      (butlast dlist)
      (recur (conj dlist (+ (last dlist) (last (butlast dlist))))))))

(loop [start 1 case (Integer/parseInt (read-line))]
  (when (<= start case)
    (println (apply + (filter even? (fibo (Integer/parseInt (read-line))))))
    (recur (inc start) case)))
