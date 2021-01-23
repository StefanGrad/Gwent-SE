package scala.de.htwg.se.gwent.aview.gui

import de.htwg.se.gwent.controller.controllerComponent.{CellChanged, ControllerInterface}
import scala.swing._
import javax.swing.table._
import scala.swing.event._
import scala.swing.Swing.LineBorder



class CellPanel(row :Int, column :Int ,controller: ControllerInterface) extends FlowPanel {

  val cellColor = new Color(224, 224, 255)

  def myCell = controller.field.getCard(row, column)

  def cellText(row: Int, column: Int):String = {
    if (!controller.field.isEmpty(row, column)) {
      return myCell.toString.replace("Some(","").replace(")","")
    }
    ""
  }

  val label =
    new Label {
      text = cellText(row, column)
      font = new Font("Verdana", 1, 9)
    }

  val cell = new BoxPanel(Orientation.Vertical) {
    contents += label
    preferredSize = new Dimension(120, 120)
    border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    listenTo(controller)

    reactions += {
      case e: CellChanged => {
        label.text = cellText(row, column)
        repaint
      }
      case MouseClicked(src, pt, mod, clicks, pops) => {
        // Playable cards anzeigen?
        repaint
      }
    }
  }

  def redraw = {
    contents.clear()
    if (!controller.field.isEmpty(row, column)) {
      label.text = cellText(row, column)
      setBackground(cell)
      contents += cell
    }
    repaint
  }

  def setBackground(p: Panel) = p.background = cellColor

}
