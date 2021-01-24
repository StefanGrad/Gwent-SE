package scala.de.htwg.se.gwent.aview.gui

import de.htwg.se.gwent.aview.gui.ImagePanel
import de.htwg.se.gwent.controller.controllerComponent.{GameChange, ControllerInterface, Fogy, Frosty, Sunny}
import scala.swing._



class CellPanel(row :Int, column :Int ,controller: ControllerInterface) extends FlowPanel {

  val cellColor = new Color(224, 224, 255)
  val localFile = System.getProperty("user.dir")

  def myCell = controller.field.getCard(column, row)

  def cellText(row: Int, column: Int):String = {
    if (!controller.field.isEmpty(row, column)) {
      return myCell.toString.replace("Some(","").replace(")","")
    }
    ""
  }

  var label =
    new Label {
      text = cellText(row, column)
      font = new Font("Verdana", 1, 9)
    }

  var image = new ImagePanel{
    imagePath = localFile + "/src/main/resources/sun.png"
  }

  val cell = new BoxPanel(Orientation.Vertical) {
    contents += label
    contents += image
    preferredSize = new Dimension(120, 70)
    border = Swing.BeveledBorder(Swing.Raised)
    listenTo(mouse.clicks)
    listenTo(controller)

    reactions += {
      case e: GameChange => {
        label.text = cellText(row, column)
        repaint
      }
      case frozen: Frosty => {
        image.imagePath = localFile + "/src/main/resources/snowflake.png"
        repaint
      }
      case fog: Fogy => {
        image.imagePath = localFile + "/src/main/resources/cloud.png"
        repaint
      }
      case sun: Sunny => {
        image.imagePath = localFile + "/src/main/resources/sun.png"
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
