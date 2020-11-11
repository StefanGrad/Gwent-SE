import main.model.{Card, Cell, Grid, Matrix}
import org.scalatest.{Matchers, WordSpec}


class Matrixspec extends WordSpec with Matchers {
  "A Matrix is a tailor-make immutable data type that contains a two-dimensional Vector of Cells. A Matrix" when {
    "empty" should {
      "be created by using a dimension and a sample cell" in {
        val emptyCard = new Card("",0,0, 0)
        val matrix = new Matrix[Cell](2, Cell(emptyCard))
        matrix.size should be(2)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val emptyCard = new Card("",0,0, 0)
        val testMatrix = Matrix(Vector(Vector(Cell(emptyCard))))
        testMatrix.size should be(1)
      }
    }
    "filled" should {
      val matrix = new Matrix[Cell](2, Cell(new Card("Archer", 0, 3, 1)))
      "give access to its cells" in {
        matrix.cell(0, 0) should be(Cell(new Card("Archer", 0, 3, 1)))
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, Cell(new Card("Crossbowmen", 0, 5, 1)))
        matrix.cell(0, 0) should be(Cell(5))
        returnedMatrix.cell(0, 0) should be(Cell(new Card("Crossbowmen", 0, 5, 1)))
      }
    }
  }
}