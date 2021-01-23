package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl

import de.htwg.se.gwent.model.fieldComponent.CardInterface


case class Deck() {
  val r = scala.util.Random
  val deck = Vector(Card("Archer", 0, 3, 1),
    Card("Berserker", 0, 4, 0),
    Card("Badass", 0, 5, 0),
    Card("Infantry", 0, 1, 0),
    Card("Mace wielder", 0, 1, 0),
    Card("Assasin", 0, 5, 0),
    Card("Footsoldier", 0, 1, 0),
    Card("Dwarf", 0, 3, 0),
    Card("Lancer", 0, 3, 0),
    Card("Rider", 0, 4, 0),
    Card("Man catcher", 0, 2, 0),
    Card("Hobbit", 0, 1, 0),

    Card("Archer", 0, 2, 1),
    Card("Sniper", 0, 2, 1),
    Card("Stone-thrower", 0, 1, 1),
    Card("Blaster", 0, 5, 1),
    Card("Musketeer", 0, 3, 1),
    Card("Crossbowman", 0, 3, 1),
    Card("Arquebus", 0, 4, 1),
    Card("Ballista", 0, 4, 1),
    Card("Kunai-Ninja", 0, 2, 1),
    Card("Hwacha", 0, 5, 1),
    Card("Shuriken-Ninja", 0, 1, 1),

    Card ("FROST", 1, 0, 1),
    Card ("FOG", 2, 0, 1),
    Card ("SUNSHINE", 3, 0, 1),
    Card ("FROST", 1, 0, 1),
    Card ("FOG", 2, 0, 1),
    Card ("SUNSHINE", 3, 0, 1))
  val length = deck.length

  def getRandomCard: CardInterface = {
    deck(r.nextInt(length))
  }
}
