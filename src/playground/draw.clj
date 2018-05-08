(ns playground.draw
  (:require [quil.core :as q]))


(defn wiggly-circle [fragments
                     radius
                     max-tilt-deg]
  (let [scaled-time (/ (q/millis) 3000)
        segment-length (/ (* radius 2 q/PI) fragments)
        abs-tilt (* max-tilt-deg (/ (* 2 q/PI) 360) (q/sin scaled-time))]
    (doall
     (map #(let [norm-pos (/ % fragments)
                 angle (* 2 q/PI norm-pos)
                 tilt-angle abs-tilt]
               (q/with-rotation [angle]
                 (q/with-translation [0 radius]
                   (q/with-rotation [tilt-angle]
                     (q/line (* -0.5 segment-length) 0 (* 0.5 segment-length) 0)))))
          (range 0 fragments 2)))
    (doall
     (map #(let [norm-pos (/ % fragments)
                 angle (* 2 q/PI norm-pos)
                 tilt-angle (* -1 abs-tilt)]
               (q/with-rotation [angle]
                 (q/with-translation [0 radius]
                   (q/with-rotation [tilt-angle]
                     (q/line (* -0.5 segment-length) 0 (* 0.5 segment-length) 0)))))
          (range 1 fragments 2)))))

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ; Set circle color.
  (q/fill (:color state) 255 255)
                                        ; Calculate x and y coordinates of the circle.

  ;; Squiggly lines!
  ;; (let [points 100
  ;;       scaled-time (/ (q/millis) 800)]
  ;;   (q/stroke-weight 2)
  ;;   (doall
  ;;    (map #(let [norm-pos (- (/ % points) 0.5)
  ;;                y-pos (* norm-pos (* 0.8 (q/height)))
  ;;                x-pos (* 0.4 (q/width) (q/sin (+ scaled-time (* 3.1415 norm-pos))))]
  ;;            (q/with-translation [(/ (q/width) 2)
  ;;                                 (/ (q/height) 2)]
  ;;              (q/point x-pos y-pos)))
  ;;         (range points)))
  ;;   (doall
  ;;    (map #(let [norm-pos (- (/ % points) 0.5)
  ;;                x-pos (* norm-pos (* 0.8 (q/height)))
  ;;                y-pos (* 0.4 (q/width) (q/cos (+ scaled-time (* 3.1415 (+ 0.5 norm-pos)))))]
  ;;            (q/with-translation [(/ (q/width) 2)
  ;;                                 (/ (q/height) 2)]
  ;;              (q/point x-pos y-pos)))
  ;;         (range points))))


  (let [circles 5
        max-radius-factor (+ 0.34 (* 0.15 (q/sin (/ (q/millis) 1000))))
        max-radius (* max-radius-factor (min (q/width) (q/height)))
        min-radius (* 0.1 (min (q/width) (q/height)))]
  (q/with-translation [(/ (q/width) 2)
                       (/ (q/height) 2)]
    (doall
     (map #(let [scaled-time (/ (q/millis) 20000)
                 direction (if (= (mod % 2) 0) 1 -1)
                 angle (* direction 2 q/PI scaled-time)
                 norm-index (/ % circles)
                 radius (+ min-radius (* (- max-radius min-radius) norm-index))]
             (q/with-rotation [angle]
               (wiggly-circle 20
                   radius
                   90)))
          (range circles))))))
