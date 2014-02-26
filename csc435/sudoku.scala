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

    def repackage(old: List[List[Int]]): List[List[Int]] =
    {
      // return new BOARD that has entry modified -- 0 is replaced with new candidate
      var newString = ""

      for ( z <- 0 to (n-1))
      {
        for ( h <- 0 to (n-1))
        {
          if ((z == x) && (h == y))
          {
            newString = newString + candidate
          }
          else
          {
            newString = newString + (old.apply(z)).apply(h)
          }
          
        }
      }

      var newBoard = sudokuBoard(newString, n)

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

    if ((x == n-1) && (y == n-1))
    {
      // end of board reached
      //Console.println((board.apply(x)).apply(y))

      return repackage(board)
    }
    if ((board.apply(x)).isDefinedAt(y+1)) // go to the right
    {
      return repackage(solve(board, x, y+1, n))
    }
    else // end of row reached, go down to next row and start from its index 0
    {
      return repackage(solve(board, x+1, 0, n))
    }
  }

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
    printList(sudokuBoard(string, 9))

    //Console.print((sudokuBoard(string, 9).apply(0)).apply(0))
    // solve(sudokuBoard(string, 9), 0, 0, 9)

    printList(solve(sudokuBoard(string, 9), 0, 0, 9))

  }
}
