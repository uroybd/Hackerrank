(defn move
  ([dvec direction indx]
   (cond (= direction :up) (move dvec :swap indx (dec indx))
         (= direction :down) (move dvec :swap indx (inc indx))
         :else (println "Wrong Number of args")))
  ([dvec direction indx1 indx2]
   (if (= direction :swap)
     (assoc dvec indx2 (nth dvec indx1) indx1 (nth dvec indx2))
     (println "Invalid arguments"))))
