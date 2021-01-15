package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.gwent.controller.controllerComponent.{ControllerInterface, choosePlayer}
import de.htwg.se.gwent.model.fieldComponent.FieldInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import de.htwg.se.gwent.model.playerComponent.{Player, PlayerType}

import scala.de.htwg.se.gwent.util.Command
//Der Prof hat bei sich nicht das controllerInterface eingebaut sondern ist weiterhin beim Controller geblieben, ist das ein Fehler (freie Punkte)?
class PlayCardCommand(fieldPlay: FieldInterface, row: Int, col:Int, playerType: PlayerType.Value, cardIndex: Int, controller: Controller) extends Command{
  val player = choosePlayer.choice(playerType).player(controller)
  val hand = player.handCard
  val card = hand.show(cardIndex)
  val weather = controller.field.weather.weather
  override def doStep: Unit = {
    val tuple1 = hand.playCard(cardIndex,fieldPlay,row,col)
    controller.field = tuple1._3.changeWeather(card).doTurn
    playerType match {
      case TOP => controller.field = Field(controller.field.field,controller.field.weather,Player(playerType,player.name,tuple1._2,player.wins),controller.field.playerBot,controller.field.turn,controller.field.round)
      case BOT => controller.field = Field(controller.field.field,controller.field.weather,controller.field.playerTop,Player(playerType,player.name,tuple1._2,player.wins),controller.field.turn,controller.field.round)
    }
  }

  override def undoStep: Unit = {
    controller.field = controller.field.setCard(col,row, None).changeWeather(weather).undoTurn
    playerType match {
      case TOP => controller.field =  Field(controller.field.field,controller.field.weather,player,controller.field.playerBot,controller.field.turn,controller.field.round)
      case BOT => controller.field =  Field(controller.field.field,controller.field.weather,controller.field.playerTop,player,controller.field.turn,controller.field.round)
    }
  }

  override def redoStep: Unit = {
    this.doStep
  }
}
