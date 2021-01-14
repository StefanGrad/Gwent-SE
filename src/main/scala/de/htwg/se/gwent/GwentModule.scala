package de.htwg.se.gwent

import com.google.inject.AbstractModule
import de.htwg.se.gwent.controller.controllerComponent.ControllerInterface
import de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.gwent.model.playerComponent.Player
import net.codingwell.scalaguice.ScalaModule

class GwentModule extends AbstractModule with ScalaModule {

  //val defaultPlayerTop: Player = Player()

  override def configure() = {
    bind[ControllerInterface].to[Controller]
  }
}
