(ns livecode.core
  (:use [overtone.live]))



(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
    (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
            (saw freq)
                 vol))

(definst square-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
    (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
            (lf-pulse:ar freq)
                 vol))

(definst noisey [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
    (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
            (pink-noise) ; also have (white-noise) and others...
                 vol))

(definst triangle-wave [freq 440 attack 0.01 sustain 0.1 release 0.4 vol 0.4]
    (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
            (lf-tri freq)
                 vol))

(definst spooky-house [freq 440 width 0.2 
                       attack 0.3 sustain 4 release 0.3 
                       vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (sin-osc (+ freq (* 20 (lf-pulse:kr 0.5 0 width))))
     vol))

(demo 10 (lpf (saw 100) (mouse-x 40 5000 EXP)))
(demo 10 (hpf (saw 100) (mouse-x 40 5000 EXP)))
(demo 30 (bpf (saw 100) (mouse-x 40 5000 EXP) (mouse-y 0.01 1 LIN)))

(metronome 4)

(midi-connected-devices)
(event-debug-on)

(spooky-house)
(stop)

(lin)
(out)
(definst)

(triangle-wave 440)
(noisey)
(square-wave)
(saw-wave)



(ctl thingy :freq 550)

(lin )

(definst kick [freq 120 dur 0.3 width 0.5]
  (let [freq-env (* freq (env-gen (perc 0 (* 0.99 dur))))
        env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
        sqr (* (env-gen (perc 0 0.01)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        drum (+ sqr (* env src))]
    (compander drum drum 0.2 1 0.1 0.01 0.01)))

(definst c-hat [amp 0.8 t 0.04]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 880 0.2))
                                  filt (bpf (+ sqr noise) 9000 0.5)]
          (* amp env filt)))

(kick)
(c-hat)

(def metro (metronome 128))
(metro) ; => current beat number
(metro 1) ; => timestamp of 100th beat
(metro 2) ; => timestamp of 100th beat

(defn player [beat]
  (at (metro beat) (kick))
  (at (metro (+ 0.25 beat)) (kick))
  (at (metro (+ 0.13 beat)) (kick))
  (at (metro (+ 0.25 beat)) (c-hat))
  (at (metro (+ 0.5 beat)) (c-hat))
  (at (metro (+ 0.75 beat)) (c-hat))
  (apply-by (metro (inc beat)) #'player (inc beat) []))

(player (metro))
(metro-bpm metro 92)
(stop)

(definst o-hat [amp 0.8 t 0.5]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 880 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(defn swinger [beat]
  (at (metro beat) (o-hat))
  (at (metro (inc beat)) (c-hat))
  (at (metro (+ 1.65 beat)) (c-hat)))

(swinger (metro))
