(def n (read-string (read-line)))
(def value (reduce * (map bigint (take n (range 1 (inc n))))))
(println (.toString value))
