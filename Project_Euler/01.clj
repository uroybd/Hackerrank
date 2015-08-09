(defn bigboy [x]
  (reduce + (distinct (concat (take-nth 3 (range x)) (take-nth 5 (range x))))))

(loop [start 1 end (Integer/parseInt (read-line))]
  (when (<= start end)
    (println (bigboy (Integer/parseInt (read-line))))
    (recur (inc start) end)))
