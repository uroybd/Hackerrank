(defn namef [string]
  (last (clojure.string/split string #"/")))


(defn file-linker [dirstring link files imagedir]
  (let [dname (namef dirstring)
        imager (fn [z]
              (clojure.java.shell/sh
               "imager"
               z
               (str imagedir "/" (namef z) ".png"))
              (str "<img src=\"images/" (namef z) ".png\">"))]
    (mapv
     identity
     (sort
      (map
       (fn [y]
         (str "<a href=" \"
              link "/raw/master/"
              dname "/" (namef y) \" ">"
              (imager y)
              "</a>"))
       (filter
        #(re-find (re-pattern dname) %)
        files))))))

(fn dir-linker [dirstring link]
  (let [dname (namef dirstring)]
    (str "<h3><a href=" \"
         link "/tree/master/"
         dname "/" \" ">"
         (clojure.string/replace
          dname #"_" " ")
         "</a></h3>")))

(defn dir? [x]
  (.isDirectory x))

(defn linklister [path outdir]
  (let [imagedir (str outdir "/images")
        metadata (:out
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
    (.mkdir (java.io.File. imagedir))
    (loop [cursor 0 dvec []]
      (if (> cursor (dec (count dirs)))
        dvec
        (recur
         (inc cursor)
         (conj dvec (dir-linker (nth dirs cursor) link)
               "<br><p>"
               (file-linker (nth dirs cursor) link files imagedir)
               "</p>"
               \newline
               ))))))


(defn generator [wrtvec repovec outdir]
  (loop [c 0 dvec []]
    (if (> c (dec (count repovec)))
      (map println
           (flatten (concat ["<html><meta charset=\"UTF-8\"><head><title>ই-বইপত্র | বাঙলা ইপাব লাইব্রেরী</title><body><h1>ই-বইপত্র<h1><h2>বাঙলা ইপাব লাইব্রেরী<h2>"] dvec ["</body></html>"])))
      (recur (inc c) (conj dvec (str "<h3>" (nth wrtvec c) "</h3") (linklister (nth repovec c) outdir))))))
