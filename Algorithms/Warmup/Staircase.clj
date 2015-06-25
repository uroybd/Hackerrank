(defn printer [atom-i]
  (swap! atom-i dec)
  (def hashnum (atom 1))
  (while (not= @atom-i 0)
    (do 
      (println (clojure.string/join (concat (repeat (dec @atom-i) " ") (repeat @hashnum "#"))))
      (swap! atom-i dec)
      (swap! hashnum inc))))

(printer (atom (Integer/parseInt (read-line))))
