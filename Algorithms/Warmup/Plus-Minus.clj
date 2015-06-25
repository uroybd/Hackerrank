(fn []
  (let [arraysize (Float/parseFloat (read-line))
        numbers (vec (map #(Integer/parseInt %) (clojure.string/split (read-line) #" ")))
        printit (fn [filter-param] (println (format "%.6f" (/ (count (filter filter-param numbers)) arraysize))))] (printit pos?) (printit neg?) (printit zero?)))
