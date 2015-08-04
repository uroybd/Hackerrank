(defn Factorial [n]
  "Simple factorial function"
  (reduce * (take n (range 1 (inc n)))))