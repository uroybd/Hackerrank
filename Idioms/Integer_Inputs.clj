;; Get one integer
(Integer/parseInt (read-line))

;; Get a vector of integers
(mapv identity (map #(Integer/parseInt %) (clojure.string/split (read-line) #" ")))