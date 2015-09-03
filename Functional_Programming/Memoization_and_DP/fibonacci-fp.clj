(defn fibo []
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0N 1N])))

(defn genfib [x]
  (last (take (inc x) (fibo))))

(loop [start 1 casenum (Integer/parseInt (read-line))]
  (when (<= start casenum)
    (println (str (mod (genfib (Integer/parseInt (read-line))) 100000007)))
    (recur (inc start) casenum)))
