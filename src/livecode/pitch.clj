(ns livecode.pitch)


; a note has a fundamental freq somewhere between 0 and 20000 Hz in the range of human hearing

;------------------------------------
;     equal temperment tuning       |
;------------------------------------

(defn lower
  "takes any frequency and lowers it to under 20 Hz"
  [note]
  (if (< note 20.0)
    note
    (lower (/ note 2))))

(defn equaltemp
  "returns a vector of all equal temperment frequencies in human range, defaults to 440"
  ([] (equaltemp (lower 440.0) (vector)))
  ([a] (equaltemp (lower a) (vector)))
  ([a acc]
   (if (> a 20000.0)
     (vec (reverse acc))
     (equaltemp (* a (Math/pow 2.0 (/ 1.0 12.0)))
                (cons a acc)))))

(def notes (vec (concat [0 0 0 0 0 0 0 0 0] (equaltemp))))

; only these four things
(def major [2 2 1 2 2 2 1])
(def melodic-minor [2 1 2 2 2 2 1])
(def harmonic-minor [2 1 2 2 1 3 1])
(def harmonic-major [2 2 1 2 1 3 1])

(map #(+ % ) major)

; given a starting note, return one octave from notes

(defn scale

(scale 60 [] major)



