(defn sqrt
([x] (sqrt x 0.001))
([x precision]
  (letfn [(abs [x]
           (if (< x 0)
            (- x)
            x))
          (square [x]
           (* x x))
          (average [& args]
           (/ (reduce + args) (count args)))
          (good-enough? [guess x precision]
           (< (abs (- (square guess) x)) precision))
          (improve [guess x]
           (average guess (/ x guess)))]
   (loop [guess 1.0 i x]
    (if (good-enough? guess i precision)
     guess
     (recur (improve guess i) i))))))