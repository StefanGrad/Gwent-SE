

val l1 = List("hallo")
val l2 = List("hi")
val l3 = l1.++(l2)
println(l3.toString())
println(l1)

val z = Vector.fill(4, 2)(0)
val a = Array.fill(4,1)("emptyCard")
a(0)(0) = "test"
println(a(1)(0))
println(a(0)(0))

Vector(1, 2, 3, 4, 5) patch (from = 2, patch = Nil, replaced = 1)
