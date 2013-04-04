(ns happy-birthday.core
  (:use [overtone.live :only [kill-server]]
        [leipzig.melody :only [play then after]]))

(defn finish []
  (kill-server)
  (System/exit 0))

(defn -main []
  (println "Hey girl, why did Constantinople get the works?"
           "That's nobody's business but the Turks...")
  (finish))
