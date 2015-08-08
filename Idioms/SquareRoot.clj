(defn abs [x]
  "Return absolute value of x"
  (if (< x 0)
   (- x)
   x))

(defn square [x]
  (* x x))

(defn good-enough? [guess x precision]
  (< (abs (- (square guess) x)) precision))

(defn average [& args]
  (/ (reduce + args) (count args)))

(defn improve [guess x]
  (average guess (/ x guess)))

(defn sqrt-iter [guess x precision]
  (if (good-enough? guess x precision)
      guess
      (sqrt-iter (improve guess x) x precision)))

(defn sqrt [x precision]
  (sqrt-iter 1.0 x precision))