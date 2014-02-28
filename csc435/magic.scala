object magic {

	def squareOOP (n: Int) : Array[Array[Int]] =
	{
		//var main = Array.ofDim[Int](n,n)

		var main = Array(Array(2, 7, 6), Array(9, 5, 1), Array(4, 3, 8)) // test solved method

		val theSum = (n*(n*n + 1))/2

		 def revertMatrix(point: Int)
		  {
		         // might cut this
		        for( i <- 0 to (n-1))
		        {
		            for( j <- 0 to (n-1))
		            {
		                if (main(i)(j) > point)
		                {
		                	main(i)(j) = 0
		                }
		             }
		        }
		  }

	  def solved : Boolean = 
	  {
	  	var rowSum = 0
	  	var columnSum = 0
	  	var diagonalSumA = 0
	  	var diagonalSumB = 0
  		 // 
	        for( i <- 0 to (n-1))
	        {
	        	rowSum = 0
	        	columnSum = 0

	            for( j <- 0 to (n-1))
	            {
	            	rowSum = rowSum + main(i)(j)
	            	columnSum = columnSum + main(j)(i)

	            	if (i == j)
	            	{
	            		diagonalSumA = diagonalSumA + main(i)(j)
	            	}

	            	if (i + j == n)
	            	{
	            		diagonalSumB = diagonalSumB + main(i)(j)
	            	}
	            }

	            if (rowSum != theSum)
	            {
	            	return false
	            }

	            if (columnSum != theSum)
	            {
	            	return false
	            }
	        }

	        if (diagonalSumA != theSum)
            {
            	return false
            }

	        return true
	  }

		 for( i <- 0 to (n*n-1))
		 {
		 	//matrix(i)(j) = 0
		 }


		//Console.print(theSum)


		printBoard(main, n)
		Console.print(solved)
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
