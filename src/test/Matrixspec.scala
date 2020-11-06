import org.scalatest.{Matchers, WordSpec}

class Matrixspec extends WordSpec with Matchers {
  "A Matrix is a tailor-make immutable data type that contains a two-dimensional Vector of Cells. A Matrix" when {
    "empty" should {
      "be created by using a dimension and a sample cell" in {
        val matrix = new Matrix[Cell](2, Cell(emptyCard))
        matrix.size should be(2)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(Cell(emptyCard))))
        testMatrix.size should be(1)
      }
    }
    "filled" should {
      val matrix = new Matrix[Cell](2, Cell(Card("Archer", 0, 3))
      "give access to its cells" in {
        matrix.cell(0, 0) should be(Cell(Card("Archer", 0, 3)))
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, Cell(Card("Crossbowmen", 0, 5)))
        matrix.cell(0, 0) should be(Cell(5))
        returnedMatrix.cell(0, 0) should be(Cell(Card("Crossbowmen", 0, 5)))
      }
    }
  }
}