(defn factorial [n]
  (reduce * (take n (range 1 (inc n)))))

(defn triangle [limit]
  (loop [xis (bigint 0)]
    (when (< xis limit)
      (loop [yis (bigint 0)]
        (when (<= yis xis)
          (if (even? (quot (factorial xis) (* (factorial yis) (factorial (- xis yis)))))
            (print "_")
            (print 1))
          (if (= yis xis)
            (println))
          (recur (inc yis))))
      (recur (inc xis)))))

(triangle (bigint (read-string (read-line))))
