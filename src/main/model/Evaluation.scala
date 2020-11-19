package model

case class Evaluation() {
  def eval(field: Field, playerTop: Player, playerBot: Player):Int = {
    var topC = 0
    for {
      r <- 0 until (field.row - 2)
      c <- 0 until field.col
    } topC += field.get(r,c).strength
    val top = topC
    //topC = 0
    var botC = 0
    for {
      r <- 2 until field.row
      c <- 0 until field.col
    } botC += field.get(r,c).strength
    val bot = botC
    //botC = 0
    if (top - bot > 0) {
      println("The winner of this round is " + playerTop.name)
      return 1
    }
    if(top - bot < 0){
      println("The winner of this round is " + playerBot.name)
      return 2
    }
    println("The game ended with a tie")
    0
  }

}
