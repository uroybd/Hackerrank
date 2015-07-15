(defn matcher [text pat]
  (if (nil? (re-find (re-pattern pat) text))
    (println "NO")
    (println "YES")))

(loop [c 1 lim (read-string (read-line))]
  (when (<= c lim)
    (matcher (read-line) (read-line))
    (recur (inc c) lim)))
