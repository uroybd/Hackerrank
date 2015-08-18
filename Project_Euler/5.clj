(defn even_div? [num divi]
  (= (rem num divi) 0))

(defn chklst_gen [num]
  (let [dlist (mapv identity (range 1 (inc num)))]
    (loop [now 1 filtered []]
      (if (> now num)
        (sort (distinct filtered))
        (recur (inc now)
               (conj filtered
                     (first (reverse (filter #(even_div? % now) dlist)))))))))


(defn checker [num]
  (let [dlist (chklst_gen 20)
        sml (apply * dlist)]
    (loop [case (- sml 2520) smallest sml]
      (println "Now trying:" case ", Smallest is:" smallest)
      (if (>= 0 case)
        smallest
        (recur (- case 2520)
               (if (every? #(even_div? case %) dlist)
                 case
                 smallest))))))

(defn chk2 [num]
  (let [dlist (chklst_gen num)
        sml (apply * dlist)]
    (loop [case 2520 smallest 2520]
;;      (println "Now checking:" case ", smallest is " smallest)
      (if (>= case sml)
        (println "Smallest is" smallest)
        (recur
         (+ case 2520)
         (if (every? #(even_div? case %) dlist)
           case
           smallest))))))
