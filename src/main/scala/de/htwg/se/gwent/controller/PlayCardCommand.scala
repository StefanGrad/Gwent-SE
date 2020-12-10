package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.model.{Field, PlayerType}
import scala.de.htwg.se.gwent.util.Command

class PlayCardCommand(fieldPlay: Field, row: Int, col:Int, playerType: PlayerType.Value, cardIndex: Int, controller: Controller) extends Command{
  val card = choosePlayer.choice(playerType).player(controller).handCard.show(cardIndex)
  override def doStep: Unit = {
    controller.field = controller.field.setCard(col,row,card)
  }

  override def undoStep: Unit = controllerfield = controller.field.setCard(row, col, row "," col, 0, 0, 0)

  override def redoStep: Unit = ???
}
