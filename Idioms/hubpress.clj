(defn getpost []
  (let [input (take-while identity
                          (repeatedly
                           #(.readLine *in*)))]
    (loop [cursor 0 end (dec (count input)) postvec []]
      (if (> cursor end)
        postvec
        (if (or (= cursor end)
                (= (nth input cursor) "")
                (= (nth input (inc cursor)) ""))
          (recur (inc cursor) end
                 (conj postvec (nth input cursor)))
          (recur (inc cursor) end
                 (conj postvec (str (nth input cursor) "\\\\"))))))))

(defn prompt-read [prompt]
  (print (format "%s: " prompt))
  (flush )
  (read-line))

(defn metadatamk []
  (let [title (prompt-read "title")
        tag-read (prompt-read "tags")
        tags (if (= tag-read "")
               "লেখালেখি, প্রেম, কবিতা"
               tag-read)
        sticky (prompt-read "sticky?(y/n)")
        highlight (prompt-read "any highlight?(y/n)")
        filename (do
                   (println "Paste your main content, hit enter and then CTRL+D:")
                   (str (.format
                           (java.text.SimpleDateFormat. "yyyy-MM-dd-HH-mm-ss")
                           (java.util.Date.))
                          "-"
                          (clojure.string/replace title #" " "-")
                          ".markdown"))]
    (mapv identity
          (flatten
           [filename
            "---"
            "published: true"
            (str "title: " "\"" title "\"")
            "layout: post"
            (if (= sticky "y")
              (str "tags: [" tags ", sticky" "]")
              (str "tags: [" tags "]"))
            (when (= highlight "y")
              "highlight: true")
            "comments: true"
            "---"]))))

(defn printmatters []
  (let [metadata (metadatamk)
        post (getpost)]
    (mapv identity (concat metadata post))))

(defn bigboy [path]
  (let [data (printmatters)
        filepath (str path "/" (first data))]
    (map #(spit filepath (str % "\n") :append true)
         (rest data))))
