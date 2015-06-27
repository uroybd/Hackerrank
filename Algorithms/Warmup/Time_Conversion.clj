(def tstr (read-line))

(def dtype (subs tstr (- (count tstr) 2)))

(def dvec (atom (clojure.string/split (subs tstr 0 (- (count tstr) 2)) #":")))

(cond
  (= dtype "AM") (if (= (first @dvec) "12")
  (swap! dvec #(assoc % 0 "00")))
  (= dtype "PM") (if (not= (first @dvec) "12")
                   (swap! dvec #(assoc % 0 (+ 12 (Integer/parseInt (first @dvec)))))))
(println (clojure.string/join [(first @dvec) ":" (nth @dvec 1) ":" (nth @dvec 2)]))
