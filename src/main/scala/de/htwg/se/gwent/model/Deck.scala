package scala.de.htwg.se.gwent.model

case class Deck() {
  val r = scala.util.Random
  val deck = Vector(Card("Archer", 0, 3, 1),
    Card ("Berserker", 0, 4, 0),
    Card ("Einschüchterer", 0 , 4, 0),
    Card ("Infanterie", 0 , 1, 0),
    Card ("Brigade", 0 , 1, 0),
    Card ("Junger Gesandter", 1, 5, 0),
    Card ("Fußsoldat", 0, 1, 0),
    Card ("Zwergen-Scharmützler", 2, 3, 0),
    Card ("Bogenschütze", 0, 2, 1),
    Card ("Heiler", 0, 0, 1),
    Card ("Scharfschütze", 0, 2, 1),
    Card ("Steinwerfer", 0, 1, 1),
    Card ("Kanonier", 0, 5, 1),
    Card ("Kreuzbogenschütze", 0, 4, 1),
    Card ("Speerdude", 0, 3, 0),
    Card ("Reiter", 0, 4, 0),
    Card ("Pikenier", 0, 2, 0),
    Card ("Musketier", 0, 3, 1),
    Card ("Hobbit", 0, 0, 0),
    Card ("FROST", 0, 0, 3),
    Card ("FOG", 2, 0, 3),
    Card ("Sunshine", 3, 0, 3))
  val length = deck.length

  def getRandomCard: Card ={
    deck(r.nextInt(length))
  }
}
