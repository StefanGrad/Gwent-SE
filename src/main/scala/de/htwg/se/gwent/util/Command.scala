package scala.de.htwg.se.gwent.util

trait Command {

  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit

}
