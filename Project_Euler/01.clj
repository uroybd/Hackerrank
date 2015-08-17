(defn bigboy [x]
  (apply + (filter #(or (zero? (rem % 3))
                        (zero? (rem % 5)))
                   (range x))))

(loop [start 1 end (Integer/parseInt (read-line))]
  (when (<= start end)
    (println (bigboy (Integer/parseInt (read-line))))
    (recur (inc start) end)))
