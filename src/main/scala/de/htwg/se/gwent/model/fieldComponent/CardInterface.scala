package de.htwg.se.gwent.model.fieldComponent

trait CardInterface {
  def name: String

  def ability: Int

  def strength: Int

  def range: Int

  def isEmpty: Boolean
}
