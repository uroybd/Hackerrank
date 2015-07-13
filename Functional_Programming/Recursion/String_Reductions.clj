(defn big-boy [dstring]
  (loop [i 0 l (count dstring) s dstring prin []]
    (if (= i l)
      (println (apply str prin))
      (recur (inc i) l s
             (if (some #{(str (nth dstring i))} prin)
               prin
               (concat prin [(str (nth dstring i))]))))))

(big-boy (read-line))
