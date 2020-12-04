package scala.de.htwg.se.gwent.model

case class Deck() {
  val r = scala.util.Random
  val deck = Vector(Card("Archer", 0, 3, 1),
    Card ("Berserker", 0, 4, 0),
    Card ("Einschüchterer", 0 , 4, 0),
    Card ("Infanterie", 1 , 1, 0),
    Card ("Brigade", 0 , 1, 0),
    Card ("Junger Gesandter", 1, 5, 0),
    Card ("Fußsoldat", 0, 1, 0),
    Card ("Zwergen-Scharmützler", 2, 3, 0),
    Card ("Bogenschütze", 0, 2, 1),
    Card ("Heiler", 2, 0, 1),
    Card ("Scharfschütze", 1, 2, 1),
    Card ("Steinwerfer", 0, 1, 1),
    Card ("Kanonier", 0, 5, 1),
    Card ("Kreuzbogenschütze", 0, 4, 1),
    Card ("Speerdude", 1, 3, 0),
    Card ("Reiter", 0, 4, 0),
    Card ("Pikenier", 1, 2, 0),
    Card ("Musketier", 1, 3, 1),
    Card ("Hobbit", 2, 0, 0),
    Card ("FROST", 3, 0, 3),
    Card ("FOG", 4, 0, 3),
    Card ("Sunshine", 5, 0, 3))
  val length = deck.length

  def getRandomCard: Card ={
    deck(r.nextInt(length))
  }
}
