object magic {

	def squareOOP (n: Int) : Array[Array[Int]] =
	{
		var main = Array.ofDim[Int](n,n)

		//var main = Array(Array(2, 7, 6), Array(9, 5, 1), Array(4, 3, 8)) // test solved method

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

		  def findFirstDuplicate : Int = 
		  {
	        for (x <- 0 to (n*n))
	        {
	        	var sightings = 0
	        	for( i <- 0 to (n-1))
		        {
		            for( j <- 0 to (n-1))
		            {
		                if (main(i)(j) == x)
		                {
		                	sightings = sightings + 1
		                }

		                if (sightings > 1)
		                {
		                	// main(i)(j) has a duplicate
		                	return main(i)(j)
		                }
		             }
		        }
	        }

	        return 0
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
	            		//diagonal going from top left to bottom right
	            		//Console.print("Diag A: " + main(i)(j) + "\n")
	            		diagonalSumA = diagonalSumA + main(i)(j)

	            		if (i + j == n-1)
	            		{
	            			//Console.print("Diag B: " + main(i)(j) + "\n")
	            			diagonalSumB = diagonalSumB + main(i)(j)
	            		}
	            	}

	            	if (i + j == n-1 && i != j)
	            	{
	            		//diagonal going from bottom left to top right
	            		//Console.print("Diag B: " + main(i)(j) + "\n")
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

            if (diagonalSumB != theSum)
            {
            	return false
            }

	        return true
	  }


	  var x = 1

		 for( i <- 0 to (n-1))
        {
            for( j <- 0 to (n-1))
            {
            	main(i)(j) = x
            	x = x + 1
            }
        }

        while (!solved)
        {
        	for (a <- 0 to n-1)
        	{
        		for (b <- 0 to n-1)
	        	{
	        		for (c <- 0 to n-1)
		        	{
		        		
		        		for (d <- 0 to n-1)
			        	{
			        		var temp = main(a)(b)
        					main(a)(b) = main(c)(d)
        					main(c)(d) = temp
			        	}
		        	}
	        	}
        	}
        	
        }


		//Console.print(theSum)


		printBoard(main, n)
		//Console.print(solved)
		//Console.print(duplicates)
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
