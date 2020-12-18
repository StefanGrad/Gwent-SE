package de.htwg.se.gwent.aview.gui
import de.htwg.se.gwent.controller.CellChanged

import scala.swing._
import javax.swing.table._

import scala.swing.event._
import scala.de.htwg.se.gwent.controller.{Controller, choosePlayer}
import scala.de.htwg.se.gwent.model.PlayerType



class CardPanel(playerType: PlayerType.Value, cardIndex: Int,controller: Controller) extends FlowPanel {

  val cellColor = new Color(224, 224, 255)
  //val highlightedCellColor = new Color(192, 255, 192)
  val player = choosePlayer.choice(playerType).player(controller)
  def myCard = player.handCard.show(cardIndex)

  def cardText(index: Int) = if (player.handCard.size >= index) " " + myCard.toString.replace("Some(","").replace(")","") else " "

  val label =
    new Label {
      text = cardText(cardIndex)
      font = new Font("Verdana", 1, 9)
    }

  val card = new BoxPanel(Orientation.Vertical) {
    contents += label
    preferredSize = new Dimension(100, 60)
    border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    listenTo(controller)
    reactions += {
      case e: CellChanged => {
        label.text = cardText(cardIndex)
        repaint
      }
      case MouseClicked(src, pt, mod, clicks, pops) => {
        val turnCange = controller.turn
        controller.playCard(controller.field,playerType,cardIndex)
        if (turnCange +1  == controller.turn) {
          this.visible = false
        }
        repaint
      }
    }
  }

  def redraw = {
    contents.clear()
    if (player.handCard.size > cardIndex) {
      label.text = cardText(cardIndex)
      setBackground(card)
      contents += card
    }
    repaint
  }

  def setBackground(p: Panel) = p.background = cellColor

}
