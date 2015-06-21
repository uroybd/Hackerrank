(loop [test-case-remaining (Integer/parseInt (read-line))] ; First read a line and get the tet case number.
  (while (not= 0 test-case-remaining) ; Loop for test case
    (do (println (let [factorial (fn [n] ; Factorial Function
                                   (reduce * (take n (range 1 (inc n)))))
                       to-the-power (fn [n m] ; To the power Function
                                      (reduce * (repeat m n)))
                       e-to-the-power-x (fn [x] ;The main functionality function
                                          (inc (apply + (map #(float (/ (to-the-power x %)
                                                                        (factorial %)))
                                                             (range 1 10)))))]
                   (e-to-the-power-x (Float/parseFloat (read-line))))) ; Do the thing.
        (dec test-case-remaining)))) ; Reloop
