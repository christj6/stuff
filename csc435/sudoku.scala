/*
/** Quick sort, imperative style */
object sort {

  /** Nested methods can use and even update everything 
   *  visible in their scope (including local variables or 
   *  arguments of enclosing methods). 
   */
  def sort(a: Array[Int]) {

    def swap(i: Int, j: Int) {
      val t = a(i); a(i) = a(j); a(j) = t
    }

    def sort1(l: Int, r: Int) {
      val pivot = a((l + r) / 2)
      var i = l
      var j = r
      while (i <= j) {
        while (a(i) < pivot) i += 1
        while (a(j) > pivot) j -= 1
        if (i <= j) {
          swap(i, j)
          i += 1
          j -= 1
        }
      }
      if (l < j) sort1(l, j)
      if (j < r) sort1(i, r)
    }

    if (a.length > 0)
      sort1(0, a.length - 1)
  }

  def println(ar: Array[Int]) {
    def print1 = {
      def iter(i: Int): String =
        ar(i) + (if (i < ar.length-1) "," + iter(i+1) else "")
      if (ar.length == 0) "" else iter(0)
    }
    Console.println("[" + print1 + "]")
  }

/*
  def main(args: Array[String]) {
    /*
    val ar = Array(6, 2, 8, 5, 1)
    println(ar)
    sort(ar)
    println(ar)
    */

    //var array = Array.ofDim[Int](9,9)
  }
  */

}
*/

object board {

  var sudokuBoard = Array.ofDim[Int](9,9)

  /*
  def populate2(ar: Array[Int]) {
      var x = 0
      for( i <- 0 to 8)
      {
           for( j <- 0 to 8)
           {
               if (x <= (ar.length - 1))
               {
                  sudokuBoard(i)(j) = ar(x)
                  x += 1
               }
            }
      }
  }
  */

  def populate(input: String) {
      var x = 0
      for( i <- 0 to 8)
      {
           for( j <- 0 to 8)
           {
               if (x <= (input.length - 1))
               {
                  if (input.charAt(x) == '.')
                  {
                    sudokuBoard(i)(j) = 0
                  }
                  else
                  {
                    sudokuBoard(i)(j) = input.charAt(x).asDigit
                  }
                  x += 1
               }
            }
      }
  }

  def printBoard
  {
    // print sudoku board
      for( i <- 0 to 8)
      {
           for( j <- 0 to 8)
           {
               Console.print(sudokuBoard(i)(j) + " ")
            }
        Console.print("\n")
      }
  }

  def solve
  {

  }

  def check(x: Int, y: Int)
  {
    val suspect = sudokuBoard(x)(y)

    for( i <- 0 to 8)
    {
      if (sudokuBoard(i)(y) == suspect)
      {
        return false;
      }
    }

    for( j <- 0 to 8)
    {
      if (sudokuBoard(x)(j) == suspect)
      {
        return false;
      }
    }

    //now check the 3x3 square the suspect resides in

  }

  def main(args: Array[String]) {
    //val square = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
    //populate2(square)

    val string = "8....42..3...5..6.5...32..........42.21...38.47..........39...6.8..7...5..65....9";
    populate(string)

    // put other stuff here 


    printBoard
  }
}

