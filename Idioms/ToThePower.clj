(defn ToThePower [n m]
  "Returns mth power of n"
  (reduce * (repeat m n)))