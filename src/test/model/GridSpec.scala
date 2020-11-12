import main.model.{Card, Cell, Grid, Matrix}
import org.scalatest.{Matchers, WordSpec}

class Gridspec extends WordSpec with Matchers {
  "A Grid is the playing field of Gwent. A Grid" when {
    "it is normal" should {
      "consist out of two rows with any number of columns"
      val smallGrid = new Grid(1,2)
      val mediumGrid = new Grid(1,3)
      val largeGrid = new Grid(1,6)
    }
    "for test purposes only created with a Matrix of Cells" in {
      val emptyCard = new Card("",0,0,0)
      val smallGrid = Grid(new Matrix(2, Cell(emptyCard)))
      val testGrid = Grid(Matrix[Cell](Vector(Vector(Cell(emptyCard), Cell(emptyCard)), Vector(Cell(emptyCard), Cell(emptyCard)))))
    }
  }
  "created properly but emty" should {
    val smallGrid = new Grid(1,3)
    "give access to its Cells" in {
      val emptyCard = new Card("",0,0,0)
      smallGrid.cell(0, 0) should be (Cell(emptyCard))
      smallGrid.cell(0, 1) should be (Cell(emptyCard))
      smallGrid.cell(0, 2) should be (Cell(emptyCard))
      smallGrid.cell(0, 3) should be (Cell(emptyCard))
      smallGrid.cell(1, 0) should be (Cell(emptyCard))
      smallGrid.cell(1, 1) should be (Cell(emptyCard))
      smallGrid.cell(1, 2) should be (Cell(emptyCard))
      smallGrid.cell(1, 3) should be (Cell(emptyCard))
    }
  }
}