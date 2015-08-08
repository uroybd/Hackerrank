(defn abs [x]
  "Return absolute value of x"
  (if (< x 0)
   (- x)
   x))