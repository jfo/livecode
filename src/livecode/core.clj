(ns livecode.core
  (:use overtone.live))

(definst foo [] (square 220))
(definst foo [] )
(foo)
(kill foo)
(kill 59)

(definst baz [freq 440] (* 0.1 (saw freq)))
(baz 440)
(baz (/ (* 440 81) 64))
(baz 660)
(kill baz)

(ctl baz :freq 220)


(saw 440)

(definst trem [freq 439 depth 10 rate 6 length 3]
  (* 0.3 
     (line:kr -1 1 length FREE)
     (saw (+ freq (* depth (sin-osc:kr rate))))
     ))

 trem2 [freq 439 depth 10 rate 6 length 3]
  (* 0.3
     (saw (+ freq (* depth (sin-osc:kr rate))))
     (line:kr 0.1 -1 length FREE)
     ))
(trem)

(line:kr 0 1 3 FREE)

(type trem)

(defn derp []
  (trem 20 1000 1 10)
  (trem2 20 1000 1 10)
)

(derp)

(stop)

(trem 2099 1000 1 10)
(trem)

