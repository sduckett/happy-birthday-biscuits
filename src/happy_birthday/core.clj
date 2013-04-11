(ns happy-birthday.core
  (:use [overtone.live :only [kill-server midi->hz]]
        [overtone.inst.sampled-piano]
        [overtone.inst.synth]
        [leipzig.melody]
        [leipzig.canon]
        [leipzig.scale]))

(defmethod play-note :piano
  [{midi :pitch}]
  (sampled-piano midi))

(defmethod play-note :follower
  [{midi :pitch}]
  (->
   (midi->hz (+ 7 midi))
   (cs80lead 1 0.2 0.5 0 0.8)))

(defn in-time [signature notes]
  (->> notes
       (where :time signature)
       (where :duration signature)))

(defn finish []
  (kill-server)
  (System/exit 0))

;; The song is in the key of C major.
(def lead-in
  (phrase
   [1/6 1/6]
   [4 4]))

(def main-theme
  (phrase
   [1/3 1/3 1/3 2/3 1/6 1/6 1/3 1/3 1/3 2/3 1/6 1/6 1/3 1/3 1/3 1/3 1/3 1/6 1/6]
   [5 4 7 6 4 4 5 4 8 7 4 4 11 9 7 6 5 10 10]))

(def first-ending
  (phrase
   [1/3 1/3 1/3 2/3 1/6 1/6]
   [9 7 8 7 4 4]))

(def second-ending
  (phrase
   [1/3 1/3 1/3 2/3]
   [9 7 8 7]))

(def lead
  (->>
   lead-in
   (then main-theme)
   (then first-ending)
   (then main-theme)
   (then second-ending)))

(def happy-birthday
  (->>
   (canon (interval -7) lead)
   (where :part (is :piano))
   (with
    (after 8/3 (->> lead (where :part (is :follower)) mirror)))
   (where :pitch (comp C major))
   (in-time (bpm 60))))

(defn run [music]
  (play music)
  (->>
   music
   last
   ((fn [{:keys [time duration]}] (+ time duration)))
   Thread/sleep))

(defn -main []
  (run happy-birthday)
  (finish))

