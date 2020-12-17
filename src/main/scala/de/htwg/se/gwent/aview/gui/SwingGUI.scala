package de.htwg.se.gwent.aview.gui
import de.htwg.se.gwent.controller.{CellChanged, PlayerChanged}

import scala.de.htwg.se.gwent.Gwent.controller
import scala.de.htwg.se.gwent.aview.gui.CellPanel
import scala.de.htwg.se.gwent.controller.Controller
import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import scala.io.Source._

class SwingGUI(c :Controller) extends Frame {
  listenTo(c)

  title = "Gwent"

  var cells = Array.ofDim[CellPanel](controller.field.size, controller.field.size)

  /*def highlightpane1 = new FlowPanel{
    contents += new Label("Hightlight:")
    for {index <- 0 to controller.field.size} {
      val button = Button(if (index == 0) "" else index.toString){
        controller.highlight(index)
      }

    }
  }*/
  def gridPanel = new GridPanel(4, 4) {
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.BLACK
    for {
      outerRow <- 0 until 4
      outerColumn <- 0 until 4
    } {
      contents += new GridPanel(4, 4) {
        border = LineBorder(java.awt.Color.BLACK, 2)
        for {
          innerRow <- 0 until 4
          innerColumn <- 0 until 4
        } {
          val x = outerRow * 4 + innerRow
          val y = outerColumn * 4 + innerColumn
          val cellPanel = new CellPanel(x, y, controller)
          cells(x)(y) = cellPanel
          contents += cellPanel
          listenTo(cellPanel)
        }
      }
    }
  }
  val gameMessage = new TextField(controller.gameMessage, 20)

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.North)
    add(gameMessage, BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Quit") {System.exit(0)})
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
    }
  }

  visible = true
  redraw

  reactions += {
    case event: CellChanged   => redraw
    case event: PlayerChanged => redraw
  }

  def redraw = {
    for {
      row <- 0 until 4
      column <- 0 until 4
    } cells(row)(column).redraw
    gameMessage.text = controller.gameMessage
    repaint
  }
}
