(defn fibo
  ([]
   (concat [0 1] (fibo 0N 1N)))
  ([a b]
   (let [n (+ a b)]
     (lazy-seq
      (cons n (fibo b n))))))

(println (.toString (nth (fibo) (dec (read-string (read-line))))))
