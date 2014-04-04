
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
	(when (= n 1)
		(println "Move disk from " a " to " b) 
			(loop [n 3 a 1 b 2 c 3]
				(when (= n 1)
				(println "Move disk from " a " to " b))
				;(recur (- n 1) a c b))
		(recur (- n 1) c b a))))

;(loop [x 10]
	;(when (> x 1)
		;(println x)
		;(recur (- x 1))))
