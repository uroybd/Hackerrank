(defn compresser [dstr]
  (let [stringvec (clojure.string/split dstr #"")]
    (apply str
           (loop [cursor 0
                 up 1
                 new []]
             (if (> (inc cursor) (count stringvec))
               new
               (if (> (+ cursor up) (dec (count stringvec)))
                 (conj new (if (= up 1)
                             (nth stringvec cursor)
                             (str (nth stringvec cursor) up)))
                 (if (= (nth stringvec cursor) (nth stringvec (+ cursor up)))
                   (recur cursor (inc up) new)
                   (recur (+ cursor up) 1 (conj new (if (= up 1)
                                                      (nth stringvec cursor)
                                                      (str (nth stringvec cursor) up)))))))))))

(println (compresser (read-line)))
