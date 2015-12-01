(defn filter-elements [reptime arraysize]
  (let [inp (map #(Integer/parseInt %)
                 (clojure.string/split
                  (read-line) #" "))
        anymore? (fn [x dv]
                   (>= (count (filter #(= x %) dv)) reptime))]
    (loop [cursor 0 dvec []]
      (if (> cursor (dec arraysize))
        (if (= dvec [])
          (println "-1")
          (apply println dvec))
        (let [target (nth inp cursor)]
          (if (some #{target} dvec)
            (recur (inc cursor) dvec)
            (if (anymore? target inp)
              (recur (inc cursor) (conj dvec target))
              (recur (inc cursor) dvec))))))))

(defn main []
  (let [tim (Integer/parseInt (read-line))]
    (loop [c 1]
      (when (<= c tim)
        (let [input (map #(Integer/parseInt %)
                 (clojure.string/split
                  (read-line) #" "))]
          (filter-elements (last input) (first input)))
        (recur (inc c))))))

(main)
