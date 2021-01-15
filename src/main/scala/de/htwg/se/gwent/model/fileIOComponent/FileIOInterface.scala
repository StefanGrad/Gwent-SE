package de.htwg.se.gwent.model.fileIOComponent

import de.htwg.se.gwent.model.fieldComponent.FieldInterface

trait FileIOInterface {

  def load: FieldInterface
  def save(grid: FieldInterface): Unit

}
