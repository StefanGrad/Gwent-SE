package de.htwg.se.gwent.aview.gui

import de.htwg.se.gwent.model.playerComponent.PlayerType

import scala.de.htwg.se.gwent.aview.gui.CellPanel
import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import PlayerType.{BOT, TOP}
import de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl.choosePlayer
import de.htwg.se.gwent.controller.controllerComponent.{CellChanged, ControllerInterface, PlayerChanged}

class SwingGUI(c :ControllerInterface) extends Frame {
  listenTo(c)

  title = "Gwent"

  var cells = Array.ofDim[CellPanel](c.field.size, c.field.size)
  var topHand = Array.ofDim[CardPanel](c.field.playerTop.handCard.size)
  var botHand = Array.ofDim[CardPanel](c.field.playerBot.handCard.size)

  def gridPanel = new GridPanel(4, 4) {
    preferredSize = new Dimension(125*4, 65*4)
    background = java.awt.Color.BLACK
    border = LineBorder(java.awt.Color.BLACK, 2)
    for {
      row <- 0 until 4
      column <- 0 until 4
    } {
      val cellPanel = new CellPanel(column, row, c)
      cells(row)(column) = cellPanel
      contents += cellPanel
      cellPanel.border = LineBorder(java.awt.Color.YELLOW,1)
      listenTo(cellPanel)
    }
  }
  val gameMessage = new TextField(c.gameMessage, 20)

  def handcardPanel(playerType: PlayerType.Value) = {
    val handSize = choosePlayer.choice(playerType).player(c).handCard.size
    //new GridPanel(1,handSize) {
    new FlowPanel() {
      preferredSize = new Dimension(125*(handSize + 1), 65)
      border = LineBorder(java.awt.Color.BLACK, 2)
      background = java.awt.Color.BLACK
      for {
        index <- 0 until handSize
      } {
        val cardPanel = new CardPanel(playerType,index,c)
        playerType match {
          case TOP => topHand(index) = cardPanel
          case BOT => botHand(index) = cardPanel
        }
        contents += cardPanel
        listenTo(cardPanel)
      }
    }
  }

  contents = /*new GridPanel(4,1){
    background = java.awt.Color.BLACK
    border = LineBorder(java.awt.Color.BLACK, 2)
    contents += handcardPanel(TOP)
    contents += gridPanel
    contents += gameMessage
    contents += handcardPanel(BOT)
  }*/new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(gameMessage, BorderPanel.Position.North)
    /*
    add(handcardPanel(TOP), BorderPanel.Position.North)
    add(gridPanel, BorderPanel.Position.Center)
    add(gameMessage, BorderPanel.Position.East)
    add(handcardPanel(BOT), BorderPanel.Position.South)*/
  }

  val frameTop = new Frame {
    title = c.field.playerTop.name
    contents = handcardPanel(TOP)
    listenTo(c)

    reactions += {
      case e: CellChanged => {
        c.field.whoCanPlay match {
          case TOP => {
            visible = true
            repaint
          }
          case BOT => {
            visible = false
            repaint
          }
        }
      }
    }
    centerOnScreen()
    redraw

    def redraw = {
      for (index <- 0 until topHand.size) topHand(index).redraw
    }
  }
  val frameBot = new Frame {
    title = c.field.playerBot.name
    contents = handcardPanel(BOT)
    listenTo(c)

    reactions += {
      case e: CellChanged => {
        c.field.whoCanPlay match {
          case TOP => {
            visible = false
            repaint
          }
          case BOT => {
            visible = true
            repaint
          }
        }
      }
    }
    centerOnScreen()
    redraw

    def redraw = {
      for (index <- 0 until botHand.size) botHand(index).redraw
    }
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("Quit") {System.exit(0)})
      contents += new MenuItem(Action("Safe") {c.safe})
      contents += new MenuItem(Action("Load") {c.load})
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { c.undo })
      contents += new MenuItem(Action("Redo") { c.redo })
    }
    contents += new Menu("HandCard") {
      contents += new MenuItem(Action(c.field.playerTop.name) {
        frameTop.visible = true
      })
      contents += new MenuItem(Action(c.field.playerBot.name) {
        frameBot.visible = true
      })
    }
    contents += new Menu("End Round") {
      mnemonic = Key.P
      contents += new MenuItem(Action("Pass") { c.passRound() })
    }
  }

  minimumSize = new Dimension(800,600)
  visible = true
  centerOnScreen()
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
    //for (index <- 0 until topHand.size) topHand(index).redraw
    //for (index <- 0 until botHand.size) botHand(index).redraw
    gameMessage.text = c.gameMessage
    repaint
  }
}
