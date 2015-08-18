(def arrlnth (read-string (read-line)))
;There are to version of the next line. Maybe for clojure version
(def strvec (atom (vec (clojure.string/split (read-line) #""))))
;;(def strvec (atom (vec (rest (clojure.string/split (read-line) #"")))))
(def cipherkey (read-string (read-line)))

(defn cipher [i key]
  (let [indxval (int (.charAt i 0))]
    (cond
      (and (>= indxval 65) (<= indxval 90))
      (let [trueindx (- indxval 64)
            truestep (rem (+ key (- indxval 64)) 26)]
        (cond
          (> truestep 26)
          (char (+ 64 (rem truestep 26)))
          (= truestep 0)
          (char (+ 64 26))
          :else (char (+ 64 truestep))))
      (and (>= indxval 97) (<= indxval 122))
      (let [trueindx (- indxval 96)
            truestep (rem (+ key (- indxval 96)) 26)]
        (cond
          (> truestep 26)
          (char (+ 96 (rem truestep 26)))
          (= truestep 0)
          (char (+ 96 26))
          :else (char (+ 96 truestep))))
      :else (char indxval))))

(swap! strvec (fn [n] (map #(cipher % cipherkey) n)))
(println (clojure.string/join @strvec))
