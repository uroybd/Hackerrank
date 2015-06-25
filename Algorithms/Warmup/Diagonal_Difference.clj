(defn insertvec []
  (vec (map #(Integer/parseInt %) (clojure.string/split (read-line) #" "))))

(def thenum (Integer/parseInt (read-line)))
(def thelist (atom '[]))
(def loopnum (atom 0))
(while (< @loopnum thenum)
  (do
    (def insertation (insertvec))
    (swap! thelist #(conj % insertation))
    (swap! loopnum inc)))

(def veccount (atom 0))
(def firstdiag (atom 0))
(while (< @veccount thenum)
  (do
    (swap! firstdiag #(+ % (nth (nth @thelist @veccount) @veccount)))
    (swap! veccount inc)))

(def lastdiag (atom 0))
(def recount (atom 0))
(while (> @veccount 0)
  (do
    (swap! lastdiag #(+ % (nth (nth @thelist (dec @veccount)) @recount)))
    (swap! veccount dec)
    (swap! recount inc)))

(def defer (- @firstdiag @lastdiag))
(if (neg? defer)
  (print (* defer -1))
  (print defer))
