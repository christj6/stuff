

object board {

  //var sudokuBoard = Array.ofDim[Int](9,9)
  def sudokuBoard(input: String, n: Int) : List[List[Int]] =
  {
    var x = 0

    var main = List[List[Int]]()

    for( i <- 0 to (n-1))
    {
        var row = List[Int]()
         for( j <- 0 to (n-1))
         {
             if (x <= (input.length - 1))
             {
                if (input.charAt(x) == '.')
                {
                  row = row :+ 0
                } 
                else
                {
                  row = row :+ input.charAt(x).asDigit
                }
                x += 1
             }
          }
          main = main :+ row
    }

    return main
  }

def printList(board: List[List[Int]])
{
  board.foreach(printElement)
}

def printElement(row: List[Int])
{
  row.foreach(print)
  Console.println("")
}

  /*
  def populate(input: String, n: Int) {
      var x = 0
      for( i <- 0 to (n-1))
      {
           for( j <- 0 to (n-1))
           {
               if (x <= (input.length - 1))
               {
                  if (input.charAt(x) == '.')
                  {
                    sudokuBoard(i)(j) = 0
                  } //add in char == A, B, C, etc. later
                  else
                  {
                    sudokuBoard(i)(j) = input.charAt(x).asDigit
                  }
                  x += 1
               }
            }
      }
  }
  */

  /*
  def printBoard(n: Int)
  {
    // print sudoku board
      for( i <- 0 to (n-1))
      {
        for( j <- 0 to (n-1))
        {
          Console.print(sudokuBoard(i)(j) + " ")
        }

        Console.print("\n")
      }
  }
  */

  /*
  def printBoard(n: Int)
  {
    // print sudoku board
      for( i <- 0 to (n-1))
      {
        for( j <- 0 to (n-1))
        {
          Console.print(sudokuBoard(i)(j) + " ")
        }

        Console.print("\n")
      }
  }
  */

/*
  def solve(ar: Array[Array[Int]], n: Int)
  {
    //var newBoard = Array.ofDim[Int](n-1,n-1)
    if (n == 3)
    {

    }
    else
    {

    }
  }
  */

  /*
  def check(x: Int, y: Int, n: Int) : Int = 
  {
    val suspect = sudokuBoard(x)(y)

    for( i <- 0 to (n-1))
    {
      if ((sudokuBoard(i)(y) == suspect) && (i != x))
      {
        return 0

      }
    }

    for( j <- 0 to (n-1))
    {
      if ((sudokuBoard(x)(j) == suspect) && (j != y))
      {
        return 0
      }
    }

    //now check the 3x3 square the suspect resides in


    return 1
  }
  */

  /*
  def megaCheck(n: Int): Int = 
  {
    for( i <- 0 to (n-1))
    {
      for( j <- 0 to (n-1))
      {
          if (check(i, j, n) == 0)
          {
              return 0
          }
      }
    }

    return 1
  }
  */

  def main(args: Array[String]) {

    //val string = "8....42..3...5..6.5...32..........42.21...38.47..........39...6.8..7...5..65....9"
    val string = "897614253312859764564732918953187642621945387478263591745391826289476135136528479"
    //populate(string, 9)

    // put other stuff here 
    //Console.print(sudokuBoard(string, 9))
    printList(sudokuBoard(string, 9))

    //printBoard(9)

    //solve(sudokuBoard, 9)

  }
}

