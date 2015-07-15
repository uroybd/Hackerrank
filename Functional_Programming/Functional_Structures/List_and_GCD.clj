(defn pow [n m]
  (reduce * (repeat m n)))

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

(defn lisp [dv]
  (loop [counter 0 range (- (count dv) 2) lst [] vec dv]
    (if (= counter range)
      lst
      (recur (+ 2 counter) range (conj lst (pow (nth vec counter) (nth vec (inc counter)))) vec))))

(defn big-boy []
  (loop [counter 0 total (read) arr []]
    (if (= counter total)
      arr
      (recur (inc counter) total ()))
    ))

(defn connecter [a bv c dv]
  (println (.toString (mod (gcd (reduce * bv) (reduce * dv)) 1000000007))))

(connecter (read-line) (map bigint (map read-string (re-seq #"\d+" (read-line)))) (read-line) (map bigint (map read-string (re-seq #"\d+" (read-line)))))
