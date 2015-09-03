(defn pentnum [x]
  (quot (- (* 3 x x) x) 2))

(loop [start 1 casenum (Integer/parseInt (read-line))]
  (when (<= start casenum)
    (println (pentnum (Integer/parseInt (read-line))))
    (recur (inc start) casenum)))
