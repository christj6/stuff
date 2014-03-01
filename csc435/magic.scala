object magic {

	def squareOOP (n: Int) : Array[Array[Int]] =
	{
		var matrix = Array.ofDim[Int](n,n)

		//var matrix = Array(Array(2, 7, 6), Array(9, 5, 1), Array(4, 3, 8)) // test solved method

		//var matrix = Array(Array(9, 7, 6), Array(2, 5, 1), Array(4, 3, 8)) // test swap

		val theSum = (n*(n*n + 1))/2

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
	            	rowSum = rowSum + matrix(i)(j)
	            	columnSum = columnSum + matrix(j)(i)

	            	if (i == j)
	            	{
	            		//diagonal going from top left to bottom right
	            		//Console.print("Diag A: " + matrix(i)(j) + "\n")
	            		diagonalSumA = diagonalSumA + matrix(i)(j)

	            		if (i + j == n-1)
	            		{
	            			//Console.print("Diag B: " + matrix(i)(j) + "\n")
	            			diagonalSumB = diagonalSumB + matrix(i)(j)
	            		}
	            	}

	            	if (i + j == n-1 && i != j)
	            	{
	            		//diagonal going from bottom left to top right
	            		//Console.print("Diag B: " + matrix(i)(j) + "\n")
	            		diagonalSumB = diagonalSumB + matrix(i)(j)
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

            if (diagonalSumB != theSum)
            {
            	return false
            }

	        return true
	  }

	  // initialize array at sequential numbers 1 to n^2
	  var x = 1
	  for (a <- 0 to n-1)
	  {
	  	for (b <- 0 to n-1)
	  	{
	  		matrix(a)(b) = x
	  		x = x + 1
	  	}
	  }

	  if (n%2 != 0)
	  {
	  	// only works for odd squares
	  	var i = 0
	  	var j = n/2
	  	for (value <- 1 to n*n)
	  	{
	  		matrix(i)(j) = value
	  		i = i - 1
	  		j = j + 1

	  		if (value%n == 0)
	  		{
	  			i = i + 2
	  			j = j - 1
	  		}
	  		else
	  		{
	  			if (j == n)
	  			{
	  				j = j - n
	  			}
	  			else if (i < 0)
	  			{
	  				i = i + n
	  			}
	  		}
	  	}
	  }

	  if (n == 4)
	  {
	  	// only works for 4x4 squares
	  	var temp = matrix(0)(0)
	  	matrix(0)(0) = matrix(3)(3)
	  	matrix(3)(3) = temp

	  	temp = matrix(1)(1)
	  	matrix(1)(1) = matrix(2)(2)
	  	matrix(2)(2) = temp

	  	temp = matrix(2)(1)
	  	matrix(2)(1) = matrix(1)(2)
	  	matrix(1)(2) = temp

	  	temp = matrix(0)(3)
	  	matrix(0)(3) = matrix(3)(0)
	  	matrix(3)(0) = temp
	  }

        printBoard(matrix, n)
        return matrix

	}

	/*
	def squareFunctional(n: Int) : Array[Array[Int]] =
	{
		// nothing yet
		var square = Array.ofDim[Int](n,n)

		val theSum = (n*(n*n + 1))/2





		return square
	}
	*/

  def printBoard(m: Array[Array[Int]], n: Int)
  {
         // print sudoku board
        for( i <- 0 to (n-1))
        {
            for( j <- 0 to (n-1))
            {
                Console.print(m(i)(j) + " \t")
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
