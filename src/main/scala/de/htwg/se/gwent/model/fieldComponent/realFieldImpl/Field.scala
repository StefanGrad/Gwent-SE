package de.htwg.se.gwent.model.fieldComponent.realFieldImpl
import com.google.inject.Inject
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field => BaseField}

class Field @Inject() extends BaseField(Vector[Vector[Option[Card]]](),new Sunshine){
}
