(ns playground.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [playground.setup :as setup]
            [playground.update :as update]
            [playground.draw :as draw]))


(defn run-sketch []
  (q/defsketch playground
    :title "You spin my circle right round"
    :size [500 500]
    ; setup function called only once, during sketch initialization.
    :setup setup/setup
    ; update-state is called on each iteration before draw-state.
    :update update/update-state
    :draw draw/draw-state
    :features [:keep-on-top]
    ; This sketch uses functional-mode middleware.
    ; Check quil wiki for more info about middlewares and particularly
    ; fun-mode.
    :middleware [m/fun-mode]))

(defn -main [& args]
  (run-sketch))
