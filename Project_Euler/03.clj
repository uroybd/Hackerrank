(defn prime? [n]
  (if (even? n) false
      (let [root (num (int (Math/sqrt n)))]
	(loop [i 3]
	  (if (> i root) true
	      (if (zero? (mod n i)) false
		  (recur (+ i 2))))))))

(defn bigboy [num]
  (loop [i 1]
    (if (and (= (rem num i) 0) (prime? (quot num i)))
      (quot num i)
      (recur (inc i)))))

(loop [start 1 end (Integer/parseInt (read-line))]
  (when (<= start end)
    (println (bigboy (Integer/parseInt (read-line))))
    (recur (inc start) end)))
