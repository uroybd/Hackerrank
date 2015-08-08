(defn average [& args]
(/ (reduce + args) (count args)))