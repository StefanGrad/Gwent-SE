package scala.de.htwg.se.gwent.controller

import de.htwg.se.gwent.model.playerComponent.{Player, PlayerType, choosePlayer}
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

import scala.de.htwg.se.gwent.model.{Card, Field}
import scala.de.htwg.se.gwent.util.Command

class PlayCardCommand(fieldPlay: Field, row: Int, col:Int, playerType: PlayerType.Value, cardIndex: Int, controller: Controller) extends Command{
  val player = choosePlayer.choice(playerType).player(controller)
  val hand = player.handCard
  val card = hand.show(cardIndex)
  val weatherState = controller.weather
  override def doStep: Unit = {
    controller.weather = controller.weather.changeWeather(card)
    val tuple1 = hand.playCard(cardIndex,fieldPlay,row,col)
    controller.field = tuple1._3
    controller.turn += 1
    playerType match {
      case TOP => controller.playerTop = Player(playerType,player.name,tuple1._2,player.wins)
      case BOT => controller.playerBot = Player(playerType,player.name,tuple1._2,player.wins)
    }
  }

  override def undoStep: Unit = {
    controller.field = controller.field.setCard(col,row, None)
    controller.weather = weatherState
    controller.turn = controller.turn
    playerType match {
      case TOP => controller.playerTop =  player  //Player(playerType,player.name ,hand ,player.wins)
      case BOT => controller.playerBot =  player
    }
  }

  override def redoStep: Unit = {
    this.doStep

    /*val tuple1 = hand.playCard(cardIndex,fieldPlay,row,col)
    controller.field = tuple1._3
    playerType match {
      case TOP => controller.playerTop = Player(playerType,player.name,tuple1._2,player.wins)
      case BOT => controller.playerBot = Player(playerType,player.name,tuple1._2,player.wins)
    }*/
  }
}
