
;(defn three-towers [n a b c]
;	(if (= n 1)
;		(println (format "Move disk from %s to %s" a b))
;			(do (three-towers (dec n) a c b)
;				(println (format "Move disk from %s to %s" a b)
;		(recur (dec n) c b a)))))

(defn three-towers [n a b c] ; call this function like: (three-towers 3 1 2 3)
	(if (= n 1) 
 		(println "Move disk from " a " to " b) 
 			(do (three-towers (dec n) a c b) 
 				(println "Move disk from " a " to " b) 
 		(recur (dec n) c b a)))) 

; (defn loopTower [n a b c]
; 	(loop [n otherN a otherA b otherB c otherC]
; 		(when (= n 1)

(loop [n 3 a 1 b 2 c 3]
	(def x [a b c])
	(when (> n 1)
		(println "Move disk from " a " to " b) 
			(def x [a c b])
			;(recur (dec n) a c b))
	(def x [c b a])
	(recur (dec n) (apply + (take 1 x)) (- (apply + (take 2 x)) (apply + (take 1 x))) (apply + (drop 2 x)))))	

; to use one recur line while shuffling the order of the [1 2 3]
; (def x '(1 2 3))
; redefine x as needed,
; retrieve first number -> (apply + (take 1 x))
; retrieve second number -> (- (apply + (take 2 x)) (apply + (take 1 x)))
; retrieve third number -> (apply + (drop 2 x))

;(loop [x 10]
	;(when (> x 1)
		;(println x)
		;(recur (- x 1))))
