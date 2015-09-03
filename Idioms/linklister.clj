(defn namef [string]
  (last (clojure.string/split string #"/")))

(defn file-linker [dirstring link files]
  (let [dname (namef dirstring)]
    (mapv
     identity
     (sort
      (map
       (fn [y]
         (str "<a href=" \"
              link "/raw/master/"
              dname "/" (namef y) \" ">"
              (clojure.string/replace
               (namef y)
               #"_" " ")
              "</a>"))
       (filter
        #(re-find (re-pattern dname) %)
        files))))))

(fn dir-linker [dirstring link]
  (let [dname (namef dirstring)]
    (str "<b><a href=" \"
         link "/tree/master/"
         dname "/" \" ">"
         (clojure.string/replace
          dname #"_" " ")
         "</a></b>")))

(defn dir? [x]
  (.isDirectory x))

(defn linklister [path]
  (let [metadata (:out
                  (clojure.java.shell/sh
                   "git" "remote" "-v" :dir path))
        repostring (subs metadata
                         (inc (.indexOf metadata "\t"))
                         (- (.indexOf metadata " ") 4))
        link (if (re-find #"https://" repostring)
               repostring
               (str "https://github.com/"
                    (subs repostring
                          (inc (.indexOf repostring ":")))))
        directory (clojure.java.io/file path)
        files (sort
               (mapv identity
                     (filter
                      #(and
                        (not (re-find #"README" %))
                        (not (re-find #".git" %)))
                      (mapv str (filter #(not (dir? %))
                                        (file-seq directory))))))
        dirs (mapv identity
                   (sort
                    (rest
                     (filter #(not (re-find #".git" %))
                             (mapv str
                                   (filter dir?
                                           (file-seq directory)))))))]
    (loop [cursor 0 dvec []]
      (if (> cursor (dec (count dirs)))
        (map println (flatten dvec))
        (recur
         (inc cursor)
         (conj dvec (dir-linker (nth dirs cursor) link)
               (file-linker (nth dirs cursor) link files)
               \newline))))))
