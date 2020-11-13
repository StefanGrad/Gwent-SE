package main.model

case class Evaluation() {
  def eval(field: Field, playerTop: Player, playerBot: Player):Int = {
    var topC = 0
    for {
      r <- 0 until (field.row - 2)
      c <- 0 until field.col
    } topC += field.get(c,r).strength
    val top = topC
    var botC = 0
    for {
      r <- 2 until field.row
      c <- 0 until field.col
    } botC += field.get(c,r).strength
    val bot = botC
    var winner = Player("Unentschieden")
    if (top - bot > 0) {
      println("The winner of this round is " + playerTop)
    }
    if(top - bot < 0){
      println("The winner of this round is " + playerBot)
    }
    if (top - bot == 0) {
      println("The game ended with a tie")
    }
    top - bot
  }

}
