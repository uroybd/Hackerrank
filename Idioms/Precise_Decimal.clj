(defmacro predec
[args]
`(~(first args) ~@(map bigdec (rest args))))