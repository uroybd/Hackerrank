(defn rev_num
  "Reverse a number"
  [num]
  (Integer/parseInt
   (clojure.string/join
    (reverse
     (str num)))))

(defn palindrome?
  "Check a number palindrome or not"
  [num]
  (= num (rev_num num)))

(loop [num1 999]
  (when (>= num1 100)
    (loop [upper num1 start 999]
      (when (>= start 100)
        (if (palindrome? (* upper start))
          (println (* start upper))
          (recur upper (dec start)))))
    (recur (dec num1))))
