
val d = Vector[Vector[Int]](Vector[Int](0,1),Vector[Int](1,0))
val v = Vector[Int](0,1)
v(0) == 0
d(1)(1) == 1

Vector.fill(4,4)(1,2,3,4)
print(res2(0))
val c = Vector[Int](1,2,3,4)
val e = Vector[Vector[Int]](c,c,c,c)
val feld = e(0).updated(2,5)
val f = c.updated(2,5)
