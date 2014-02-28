object magic {

	def squareOOP (n: Int) : Array[Array[Int]] =
	{
		var main = Array.ofDim[Int](n,n)

		val sum = (n*(n*n + 1))/2

		 def clearMatrix
		  {
		         // print sudoku board
		        for( i <- 0 to (n-1))
		        {
		            for( j <- 0 to (n-1))
		            {
		                matrix(i)(j) = 0
		             }
		        }
		  }

		//Console.print(sum)

		printBoard(main, n)
		return main
	}

	def squareFunctional
	{
		// nothing yet
	}

  def printBoard(matrix: Array[Array[Int]], n: Int)
  {
         // print sudoku board
        for( i <- 0 to (n-1))
        {
            for( j <- 0 to (n-1))
            {
                Console.print(matrix(i)(j) + " \t")
             }
          Console.print("\n")
        }
  }

	def main(args: Array[String]) 
	{
		val number = 3

		squareOOP(number)
	}
}
