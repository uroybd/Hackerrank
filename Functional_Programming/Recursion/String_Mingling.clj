(defn big-boy [str1 str2]
  (loop [start 0 end (count str1) s1 str1 s2 str2 list []]
    (if (= start end)
      (println (apply str list))
      (recur (inc start) end s1 s2 (conj (conj list (str (nth s1 start))) (str (nth s2 start)))))))

(big-boy (read-line) (read-line))
