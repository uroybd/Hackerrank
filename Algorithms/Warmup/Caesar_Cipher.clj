(def arrlnth (read-string (read-line)))
(def strvec (atom (vec (clojure.string/split (read-line) #""))))
(def cipherkey (read-string (read-line)))

(defn cipher [i key]
  (let [inum (int (.charAt i 0))]
    (cond (and (>= inum 65) (<= inum 90))
          (if (> (+ inum key) 90)
            (char (- (+ inum key) 26))
            (char (+ inum key)))
          (and (>= inum 91) (<= inum 122))
          (if (> (+ inum key) 122)
            (char (- (+ inum key) 26))
            (char (+ inum key)))
          (or (< inum 65) (> inum 122))
          (char inum))))

(swap! strvec (fn [n] (map #(cipher % cipherkey) n)))
(println (clojure.string/join @strvec))
