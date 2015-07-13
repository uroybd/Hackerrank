(defn factorial [n]
  (reduce * (map bigint (take n (range 1 (inc n))))))

(defn triangle [limit]
  (loop [xis 0 left 31 right 31]
    (when (< xis limit)
      (loop [i 0 l left]
        (when (<= i l)
          (print "_")
          (recur (inc i) l)))
      (loop [yis 0]
        (when (<= yis xis)
          (if (even? (quot (factorial xis) (* (factorial yis) (factorial (- xis yis)))))
            (print "_")
            (print 1))
          (recur (inc yis))))
      (loop [i 0 r right]
        (when (<= i r)
          (print "_")
          (recur (inc i) r)))
      (println)
      (recur (inc xis) left (dec right)))))

(triangle (read-string (read-line)))
