package main.model


case class Player(name:String) {
  var hand = HandCard()
  override def toString: String = name
}
