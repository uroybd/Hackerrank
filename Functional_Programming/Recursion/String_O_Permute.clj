(defn swapper [string]
  (loop [c 1 l (count string) s (clojure.string/split string #"")]
    (when (<= c l)
      (print (nth s c))
      (print (nth s (dec c)))
      (recur (+ 2 c) l s)))
  (println))

(loop [c 1 l (read-string (read-line))]
  (when (<= c l)
    (swapper (read-line))
    (recur (inc c) l)))
