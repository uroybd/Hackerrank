(defn e-to-the-power-x [x]
  (letfn [(factorial [n] ; Factorial Function
           (reduce * (take n (range 1 (inc n)))))
          (to-the-power [n m] ; To the power Function
           (reduce * (repeat m n)))]
  (inc
   (apply +
    (map
     #(/ (to-the-power x %)
         (factorial %))
      (range 1 10))))))

(loop [start 1 case (Integer/parseInt (read-line))]
  (if (<= start case)
   (println (format "%.4f" (e-to-the-power-x (Float/parseFloat (read-line)))))
   (recur (inc start) case)))