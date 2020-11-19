
import aview.Tui
import model.{Card, Evaluation, Field, HandCard, Player}


object Gwent{
  var field = Field(4,4)
  var playerTop = Player("Adrian",HandCard(Vector[Card]()).newDeck())
  var playerBot = Player("Stefan",HandCard(Vector[Card]()).newDeck())
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      println("Field : \n" + field.toString)
      input = scala.io.StdIn.readLine()
      val tuple = tui.processInputLine(input, field, playerTop, playerBot)
      field = tuple._1
      playerTop = tuple._2
      playerBot = tuple._3

    } while (input != "close")

  }

}