(defmacro predec
  "Evaluate decimal operation precisely"
  [op & args]
  `(~op ~@(map bigdec args)))