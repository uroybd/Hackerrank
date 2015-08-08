(loop [test-case-remaining (Integer/parseInt (read-line))] ; First read a line and get the test case number.
  (while (not= 0 test-case-remaining) ; Loop for test case
    (do (println (let [factorial 
                       to-the-power 
                       e-to-the-power-x]
                   (e-to-the-power-x (Float/parseFloat (read-line))))) ; Do the thing.
        (dec test-case-remaining)))) ; Reloop

(defn e-to-the-power-x []
  (letfn [(factorial [n] ; Factorial Function
           (reduce * (take n (range 1 (inc n)))))
          (to-the-power [n m] ; To the power Function
           (reduce * (repeat m n)))]
(inc
(apply +
(map
#(float (/ (to-the-power x % (factorial %)) (range 1 10))))))
