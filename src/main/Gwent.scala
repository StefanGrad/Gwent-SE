package main

import gwentpackage.model.Player


object Gwent{
  def main(args: Array[String]): Unit = {
    val student1 = Player("Adrian Weishaupt")
    val student2 = Player("Stefan Grad")
    println("Hallo und Willkommen zu Gwent, ")
    println(student1.name + " und " + student2.name)
  }
}