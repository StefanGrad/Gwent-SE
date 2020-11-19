package controller

import util.Observable
import model.{Field, HandCard}

class Controller(var field: Field, var handTop:HandCard, var handBot:HandCard) extends Observable {

}
