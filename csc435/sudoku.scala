import scala.collection.mutable.Stack

object board {

  //var sudokuBoard = Array.ofDim[Int](9,9) // pass it as Array[Array[Int]]

  var xSpots = new Stack[Int]
  var ySpots = new Stack[Int]
  var prevCand = new Stack[Int]

  def sudokuBoard(input: String, n: Int) : Array[Array[Int]] =
  {
    var x = 0

    //var main = List[List[Int]]()
    var main = Array.ofDim[Int](n,n)

    for( i <- 0 to (n-1))
    {
        //var row = List[Int]()
         for( j <- 0 to (n-1))
         {
             if (x <= (input.length - 1))
             {
                if (input.charAt(x) == '.')
                {
                  //row = row :+ 0
                  main(i)(j) = 0
                }
                else
                {
                  //row = row :+ input.charAt(x).asDigit
                  main(i)(j) = input.charAt(x).asDigit
                }
                x += 1
             }
          }
          //main = main :+ row
    }

    return main
  }

  def printBoard(sudokuBoard: Array[Array[Int]])
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


def containsZeroes(board: Array[Array[Int]]) : Int =
{
  val n = 9

    for ( x <- 0 to (n-1))
    {
      for ( y <- 0 to (n-1))
        {
          if (board(x)(y) == 0)
          {
            return 1
          }
        }
    }

    return 0
}

def solve(board: Array[Array[Int]], candidate: Int) : Array[Array[Int]] = 
{
  val n = 9
  //var candidate = 1


  def isValid (x: Int, y: Int, suspect: Int) : Int =
  {
    for( i <- 0 to (n-1))
    {
      var compare = board(i)(y)

      if ((compare == suspect) && (i != x))
      {
        return 0
      }
    }

    for( j <- 0 to (n-1))
    {
      var compare = board(x)(j)

      if ((compare == suspect) && (j != y))
      {
        return 0
      }
    }

    // can only be 9 of each number on each board
    var count = 0
    for (i <- 0 to (n-1))
        {
          for (j <- 0 to (n-1))
          {
            var compare = board(i)(j)
            if ((compare == suspect))
            {
              count = count + 1
            }
          }
        }
        
    if (count > n)
    {
      return 0
    }

    return 1
  }

// start of solve function proper
  for (i <- 0 to (n-1))
  {
    for (j <- 0 to (n-1))
    {
      if (board(i)(j) == 0)
      {
        xSpots.push(i)
        ySpots.push(j)

        var copy = board

        /*
        while (isValid(i, j, candidate) == 0 && candidate < n)
        {
          candidate = candidate + 1
        }
        */

        copy(i)(j) = candidate

        prevCand.push(candidate)
        
        /*
        if (containsZeroes(copy) == 0)
        {
          return copy
        } 
        */

        /*
        if (isValid(i, j, candidate) == 0)
        {
          if (candidate < n)
          {
            /*
            copy(xSpots.pop())(ySpots.pop()) = 0
            solve(copy, candidate + 1)
            */
          }
          else
          {
            /*
            copy(xSpots.pop())(ySpots.pop()) = 0
            prevCand.pop()
            copy(xSpots.pop())(ySpots.pop()) = 0
            solve(copy, candidate + 1)
            */
          }
        }
        else
        {
          return copy
        }
        */

        

      }
    }
  }

  return board
}

/*
  def solve(board: List[List[Int]], x: Int, y: Int, n: Int) : List[List[Int]] =
  {
    def isValid (suspect: Int) : Int =
    {
      for( i <- 0 to (n-1))
      {
        var compare = (board.apply(i)).apply(y)

        if ((compare == suspect) && (i != x))
        {
          return 0
        }
      }

      for( j <- 0 to (n-1))
      {
        var compare = (board.apply(x)).apply(j)

        if ((compare == suspect) && (j != y))
        {
          return 0
        }
      }

      //now check the 3x3 square the suspect resides in
      if (x < 3)
      {
        if (y < 3)
        {
          //top left
          for (i <- 0 to 2)
          {
            for (j <- 0 to 2)
            {
              var compare = (board.apply(i)).apply(j)
              if ((compare == suspect) && ((j != y) && (i != x)))
            {
              return 0
            }
            }
          }
        }
        else if ((y >= 3) && (y < 6))
        {
          for (i <- 0 to 2)
          {
            for (j <- 3 to 5)
            {
              var compare = (board.apply(i)).apply(j)
              if ((compare == suspect) && ((j != y) && (i != x)))
            {
              return 0
            }
            }
          }
        }
        else
        {
          for (i <- 0 to 2)
          {
            for (j <- 6 to 8)
            {
              var compare = (board.apply(i)).apply(j)
              if ((compare == suspect) && ((j != y) && (i != x)))
            {
              return 0
            }
            }
          }
        }
      }
      else if ((x >= 3) && (x < 6))
      {
        if (y < 3)
        {
          for (i <- 3 to 5)
          {
            for (j <- 0 to 2)
            {
              var compare = (board.apply(i)).apply(j)
              if ((compare == suspect) && ((j != y) && (i != x)))
            {
              return 0
            }
            }
          }
        }
        else if ((y >= 3) && (y < 6))
        {
          //middle
          for (i <- 3 to 5)
          {
            for (j <- 3 to 5)
            {
              var compare = (board.apply(i)).apply(j)
              if ((compare == suspect) && ((j != y) && (i != x)))
            {
              return 0
            }
            }
          }
        }
        else
        {
          for (i <- 3 to 5)
          {
            for (j <- 6 to 8)
            {
              var compare = (board.apply(i)).apply(j)
              if ((compare == suspect) && ((j != y) && (i != x)))
            {
              return 0
            }
            }
          }
        }
      }
      else
      {
        if (y < 3)
        {
          for (i <- 6 to 8)
          {
            for (j <- 0 to 2)
            {
              var compare = (board.apply(i)).apply(j)
              if ((compare == suspect) && ((j != y) && (i != x)))
            {
              return 0
            }
            }
          }
        }
        else if ((y >= 3) && (y < 6))
        {
          for (i <- 6 to 8)
          {
            for (j <- 3 to 5)
            {
              var compare = (board.apply(i)).apply(j)
              if ((compare == suspect) && ((j != y) && (i != x)))
            {
              return 0
            }
            }
          }
        }
        else
        {
          //bottom right
          for (i <- 6 to 8)
          {
            for (j <- 6 to 8)
            {
              var compare = (board.apply(i)).apply(j)
              if ((compare == suspect) && ((j != y) && (i != x)))
            {
              return 0
            }
            }
          }
        }
      }
      
      // can only be 9 of each number on each board
      var count = 0
      for (i <- 0 to 8)
          {
            for (j <- 0 to 8)
            {
              var compare = (board.apply(i)).apply(j)
              if ((compare == suspect))
            {
              count = count + 1
            }
            }
          }
          
      if (count > 9)
      {
        return 0
      }


      return 1
    }

    /* Runs through 1-9 or 1-16 to find candidates for occupying the current cell on the sudoku board. Returns first # that works. */
    /*
    def candidate : Int =
    {
      if ((board.apply(x)).apply(y) == 0)
      {
        for( k <- 1 to n)
        {
          
          if (isValid(k) == 1)
          {
            //Console.print(k)
            
            return k
            
          }
        }
      }

      return (board.apply(x)).apply(y)

    }
    */

    def repackage(old: List[List[Int]], candidate: Int): List[List[Int]] =
    {
      // return new BOARD that has entry modified -- 0 is replaced with new candidate
      var newString = ""

      for ( x <- 0 to (n-1))
      {
        for ( y <- 0 to (n-1))
        {
          if (((board.apply(x)).apply(y) == 0) )
          {
            if (isValid(candidate) == 1)
            {
                newString = newString + candidate
            }
            
          }
          else
          {
            newString = newString + (old.apply(x)).apply(y)
          }
          
        }
      }

      var newBoard = sudokuBoard(newString, n)

      if (containsZeroes(newBoard) == 0 || candidate > 9)
      {
        return newBoard
      }
      else
      {
        repackage(newBoard, candidate+1)
      }

      /*

      while ((containsZeroes(solve(newBoard, 0, 0, n)) == 1) && (candidate < 10))
      {
        candidate = candidate + 1
        newString = ""
        //containsZeroes(newBoard)
        for ( z <- 0 to (n-1))
        {
          for ( h <- 0 to (n-1))
          {
            if (((z == x) && (h == y)) && (((board.apply(x)).apply(y) == 0) && (isValid(candidate) == 1)) )
            {
              newString = newString + candidate
            }
            else
            {
              newString = newString + (old.apply(z)).apply(h)
            }
            
          }
        }

        newBoard = sudokuBoard(newString, n)
      }

      if (candidate > 9)
      {
        return board
      }
      */

      return newBoard
    }

    //var newBoard = Array.ofDim[Int](n-1,n-1)
    //var currentRow = board.apply(x)
    
    // if (end of list is reached)
    // actually solve last spot
    // else if (spot != 0)
    // it's a clue, ignore -- don't change
    // else
    // do something and recursively solve next cell

    /*
    if ((x == n-1) && (y == n-1))
    {
      // end of board reached
      //Console.println((board.apply(x)).apply(y))

      return repackage(board, 1)
    }
    if ((board.apply(x)).isDefinedAt(y+1)) // go to the right
    {
      //return repackage(solve(repackage(board), x, y+1, n))
      return solve(repackage(board, 1), x, y+1, n)
    }
    else // end of row reached, go down to next row and start from its index 0
    {
      //return repackage(solve(repackage(board), x+1, 0, n))
      return solve(repackage(board, 1), x+1, y, n)
    }
    */
    

    return repackage(board, 1)
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

    val string = "8....42..3...5..6.5...32..........42.21...38.47..........39...6.8..7...5..65....9"
    //val string = "897614253312859764564732918953187642621945387478263591745391826289476135136528479"

    // put other stuff here
    var board = sudokuBoard(string, 9)
    printBoard(board)
    Console.print("\n")
    printBoard(solve(board, 1))
    

    //Console.print((sudokuBoard(string, 9).apply(0)).apply(0))
    // solve(board, 0, 0, 9)
    // solve(solve(board, 0, 0, 9))

    //printList(solve(solve(sudokuBoard(string, 9), 0, 0, 9), 0, 0, 9))

  }
}
