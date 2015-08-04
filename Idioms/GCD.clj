(defn GCD [a b]
  "GCD of two numbers"
  (cond
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