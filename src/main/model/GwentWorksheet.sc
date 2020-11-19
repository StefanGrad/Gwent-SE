val v = Vector[Int](0,1,2)
val v2 = Vector[String]("2,3,3")
val v3 = v++v2
println(v2.apply(0))
val t = (1,"s")
t._1
Array.fill(4,4)(1).size

/*
      var newHand = Vector[Card]()
      for (x <- hand.indices) {
        if (returnIndex != x) {
          newHand = newHand++Vector[Card](hand(x))
        }
      }
     HandCard(newHand)
    */

val z = Vector.fill(4, 2)(0)
val a = Array.fill(4,1)("emptyCard")
a(0)(0) = "test"
println(a(1)(0))
println(a(0)(0))

Vector(1, 2, 3, 4, 5) patch (from = 2, patch = Nil, replaced = 1)
