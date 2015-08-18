(defn rev_num
  "Reverse a number"
  [num]
  (Integer/parseInt
   (clojure.string/join
    (reverse
     (str num)))))
