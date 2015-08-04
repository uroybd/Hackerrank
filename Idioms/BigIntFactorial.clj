(defn BigIntFactorial [n]
  "Large factorial function"
  (reduce * (map bigint (take n (range 1 (inc n))))))