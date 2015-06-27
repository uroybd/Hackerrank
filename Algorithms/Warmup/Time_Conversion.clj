(def tstr (String/parseString (read-line))

(def dtype (subs tstr (- (count tstr) 2)))

(def dvec (atom (clojure.string/split (subs a 0 (- (count a) 2)) #":")))

(cond
  (= dtype "AM") (if (= (first @dvec) "00")
(do
  (swap! dvec)))
  (= dtype "PM") (step for PM))