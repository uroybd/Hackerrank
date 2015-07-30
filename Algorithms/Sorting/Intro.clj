(let [key (read-string (read-line))
      notimp (read-string (read-line))
      dvec (map read-string (clojure.string/split (read-line) #" "))]
  (println (.indexOf dvec key)))
