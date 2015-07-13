(let [f (fn [a b] (cond
                    (= a b)
                    a
                    (> a b)
                    (loop [x a y b]
                      (if (= 0 (rem x y))
                        y
                        (recur y (rem x y))))
                    (< a b)
                    (loop [x b y a]
                      (if (= 0 (rem x y))
                        y
                        (recur y (rem x y))))))
      [m n] (map read-string (re-seq #"\d+" (read-line)))]
  (println (f m n)))
