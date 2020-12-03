package de.htwg.se.gwent.model

object PlayerArea {
  var row = Vector[Int]()
  def handel(b:Boolean) = {
    b match {
      case true => row = Vector[Int](0,1)
      case false => row = Vector[Int](2,3)
    }
    row
  }
}
