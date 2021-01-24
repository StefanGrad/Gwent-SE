package de.htwg.se.gwent.aview.gui

import de.htwg.se.gwent.model.playerComponent.PlayerType

import scala.de.htwg.se.gwent.aview.gui.CellPanel
import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import PlayerType.{BOT, TOP}
import de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl.choosePlayer
import de.htwg.se.gwent.controller.controllerComponent.{CellChanged, ControllerInterface, Fogy, Frosty, PlayerChanged, Sunny}

class SwingGUI(controller :ControllerInterface) extends Frame {
  listenTo(controller)

  title = "Gwent"

  var cells = Array.ofDim[CellPanel](controller.field.size, controller.field.size)
  var topHand = Array.ofDim[CardPanel](controller.field.playerTop.handCard.size)
  var botHand = Array.ofDim[CardPanel](controller.field.playerBot.handCard.size)

  def gridPanel = new GridPanel(4, 4) {
    preferredSize = new Dimension(125*4, 67*4)
    background = java.awt.Color.BLACK
    border = LineBorder(java.awt.Color.BLACK, 2)
    for {
      row <- 0 until 4
      column <- 0 until 4
    } {
      val cellPanel = new CellPanel(column, row, controller)
      cells(row)(column) = cellPanel
      contents += cellPanel
      cellPanel.border = LineBorder(java.awt.Color.YELLOW,1)
      listenTo(cellPanel)
    }
  }
  val gameMessage = new TextField(controller.gameMessage, 20)

  def handcardPanel(playerType: PlayerType.Value) = {
    val handSize = choosePlayer.choice(playerType).player(controller).handCard.size
    new FlowPanel() {
      preferredSize = new Dimension(125*(handSize + 1), 65)
      border = LineBorder(java.awt.Color.BLACK, 2)
      background = java.awt.Color.BLACK
      for {
        index <- 0 until handSize
      } {
        val cardPanel = new CardPanel(playerType,index,controller)
        playerType match {
          case TOP => topHand(index) = cardPanel
          case BOT => botHand(index) = cardPanel
        }
        contents += cardPanel
        listenTo(cardPanel)
      }
    }
  }

  def handCardFrame(playerType: PlayerType.Value) = new Frame{
    listenTo(controller)
    title = choosePlayer.choice(playerType).player(controller).name

    val hand = playerType match {
      case TOP => topHand
      case BOT => botHand
    }

    val rememberType = playerType

    contents = handcardPanel(playerType)

    reactions += {
      case e: CellChanged => {
        controller.field.whoCanPlay match {
          case this.rememberType => {
            visible = true
            repaint
          }
          case _ => {
            visible = false
            repaint
          }
        }
      }
    }
    centerOnScreen()
    redraw

    def redraw = {
      for (index <- 0 until hand.size) hand(index).redraw
    }
  }

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(gameMessage, BorderPanel.Position.North)
  }

  val frameTop = handCardFrame(TOP)
  val frameBot = handCardFrame(BOT)

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Quit") {System.exit(0)})
      contents += new MenuItem(Action("Safe") {controller.safe})
      contents += new MenuItem(Action("Load") {controller.load})
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
    }
    contents += new Menu("HandCard") {
      contents += new MenuItem(Action(controller.field.playerTop.name) {
        frameTop.visible = true
      })
      contents += new MenuItem(Action(controller.field.playerBot.name) {
        frameBot.visible = true
      })
    }
    contents += new Menu("End Round") {
      mnemonic = Key.P
      contents += new MenuItem(Action("Pass") { controller.passRound() })
    }
  }

  minimumSize = new Dimension(800,600)
  visible = true
  centerOnScreen()
  redraw

  reactions += {
    case event: CellChanged   => redraw
    case event: PlayerChanged => redraw
    case event: Frosty => redraw
    case event: Fogy => redraw
    case event: Sunny => redraw
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
