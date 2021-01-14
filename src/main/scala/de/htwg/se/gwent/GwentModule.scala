package de.htwg.se.gwent

import com.google.inject.AbstractModule
import de.htwg.se.gwent.controller.controllerComponent.ControllerInterface
import de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl.Controller
import net.codingwell.scalaguice.ScalaModule

class GwentModule extends AbstractModule with ScalaModule {
  override def configure() = {
    bind[ControllerInterface].to[Controller]
  }
}
