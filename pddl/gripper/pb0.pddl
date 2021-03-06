(define (problem pb0)
    (:domain gripper)
    (:requirements :strips)
  	(:objects roomA roomB Ball1 Ball2 left)
	(:init 
		(room roomA)
		(room roomB)
		(ball Ball1)
		(ball Ball2)
		(gripper left)
		(at-robby roomA) 
		(free left) 
		(at Ball1 roomA)
		(at Ball2 roomA))
	(:goal (and (at Ball1 roomB) 
		(at Ball2 roomB)))
)