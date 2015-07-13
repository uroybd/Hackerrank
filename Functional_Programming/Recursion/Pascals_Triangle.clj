(defn factorial [n]
  (reduce * (take n (range 1 (inc n)))))

(defn triangle [limit]
  (loop [xis 0]
    (when (< xis limit)
      (loop [yis 0]
        (when (<= yis xis)
          (print (quot (factorial xis) (* (factorial yis) (factorial (- xis yis)))))
          (if (not= yis xis)
            (print " ")
            (println))
          (recur (inc yis))))
      (recur (inc xis)))))

(triangle (read-string (read-line)))
