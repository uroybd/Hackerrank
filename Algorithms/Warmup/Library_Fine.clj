(def returndate (vec (map #(Integer/parseInt %) (clojure.string/split (read-line) #" "))))
(def expecteddate (vec (map #(Integer/parseInt %) (clojure.string/split (read-line) #" "))))

(cond (> (nth returndate 2) (nth expecteddate 2))
      (println 10000)
      (< (nth returndate 2) (nth expecteddate 2))
      (println 0)
      (= (nth returndate 2) (nth expecteddate 2)) 
      (cond (> (nth returndate 1) (nth expecteddate 1))
            (println (* 500 (- (nth returndate 1) (nth expecteddate 1))))
            (< (nth returndate 1) (nth expecteddate 1))
            (println 0)
            (= (nth returndate 1) (nth expecteddate 1))
            (cond (<= (first returndate) (first expecteddate))
                  (println 0)
                  (> (first returndate) (first expecteddate))
                  (println (* 15 (- (first returndate) (first expecteddate)))))))
