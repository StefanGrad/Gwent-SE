package de.htwg.se.gwent.model.fieldComponent.realFieldImpl
import com.google.inject.Inject
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, HandCard, TurnLogic, Field => BaseField}
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

class Field @Inject() extends BaseField(Vector[Vector[Option[Card]]](),new Sunshine,Player(TOP,"Adrian",HandCard(Vector[Card]()).newDeck(),0),Player(BOT,"Stefan",HandCard(Vector[Card]()).newDeck(),0),0,0){
}
