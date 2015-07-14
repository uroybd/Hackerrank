(defn gcd [a b]
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

(defn connecter [a bv c dv]
  (println (.toString (mod (gcd (reduce * bv) (reduce * dv)) 1000000007))))

(connecter (read-line) (map bigint (map read-string (re-seq #"\d+" (read-line)))) (read-line) (map bigint (map read-string (re-seq #"\d+" (read-line)))))
