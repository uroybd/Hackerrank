(defn halfer [pt1 pt2]
  (let [x1 (first pt1)
        y1 (last pt1)
        x2 (first pt2)
        y2 (last pt2)]
    [(+ (quot (+ x1 x2) 2) (rem (+ x1 x2) 2)) (+ (quot (+ y1 y2) 2) (rem (+ y1 y2) 2))]))

(defn trisplitter [trivec]
  (let [a (first trivec)
        b (second trivec)
        c (last trivec)
        p (halfer (first trivec) (second trivec))
        q (halfer (second trivec) (last trivec))
        r (halfer (first trivec) (last trivec))]
    [[a
      [(first p) (dec (last p))]
      [(dec (first r)) (dec (last r))]]
     [[(dec (first p)) (last p)] b
      [(dec (first q)) (last q)]]
     [r
      [(inc (first q)) (last q)] c]]))

(defn generator [dvec lvl]
  (loop [v dvec l lvl s 0]
    (if (not (< s l))
      v
      (recur (vec (apply concat (map trisplitter v))) l (inc s)))))

(defn ranger [i m n]
  (loop [upper i start m end n ar []]
    (if (not (<= start end))
      ar
      (recur upper (inc start) end (vec (concat ar [[start upper]]))))))

(defn ellaborator [dvec]
  (loop [start (second (first dvec)) end (second (second dvec)) counter 0 li []]
    (if (not (<= start end))
      li
      (recur (inc start) end (inc counter) (concat li (ranger start (- (first (first dvec)) counter) (+ counter (first (first dvec)))))))))

(defn combinator [dvec]
  (vec (apply concat (map ellaborator dvec))))

(defn print-line [linum dvec]
  (loop [num linum start 1 end 63 vect dvec]
    (when (<= start end)
      (if (some #{[start num]} vect)
        (print 1)
        (print "_"))
      (recur num (inc start) end vect))))

(defn printer [dvec]
  (loop [start 1 end 32 vect dvec]
    (when (<= start end)
      (do (print-line start vect)
          (println))
      (recur (inc start) end vect))))

(printer (combinator (generator [[[32 1] [1 32] [63 32]]] (read))))
