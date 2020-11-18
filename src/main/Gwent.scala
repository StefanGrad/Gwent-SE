
import aview.Tui
import model.{Card, Evaluation, Field, HandCard, Player}


object Gwent{
  var field = Field(4,4)
  val playerTop = Player("Adrian")
  val playerBot = Player("Stefan")
  val eval = new Evaluation
  var handBot = HandCard(Vector[Card]()).newDeck()
  var handTop = HandCard(Vector[Card]()).newDeck()
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      println("Field : \n" + field.toString)
      input = scala.io.StdIn.readLine()
      val tuple = tui.processInputLine(input, field, playerTop, handTop, playerBot, handBot, eval)
      field = tuple._1
      handTop = tuple._2
      handBot = tuple._3

    } while (input != "close")

  }

}