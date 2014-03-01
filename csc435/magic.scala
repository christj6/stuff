object magic {

	def squareOOP (n: Int) : Array[Array[Int]] =
	{
		var main = Array.ofDim[Int](n,n)

		//var main = Array(Array(2, 7, 6), Array(9, 5, 1), Array(4, 3, 8)) // test solved method

		val theSum = (n*(n*n + 1))/2

		  def findDuplicate(candidate: Int) : Boolean = 
		  {
	        var sightings = 0
        	for( i <- 0 to (n-1))
	        {
	            for( j <- 0 to (n-1))
	            {
	                if (main(i)(j) == candidate)
	                {
	                	sightings = sightings + 1
	                }

	                if (sightings > 1)
	                {
	                	// main(i)(j) has a duplicate
	                	return true
	                }
	             }
	        }

	        return false
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



	  /*
	  def populate(x: Int, y: Int)
	  {
	  	if (x == n-1 && y == n-1)
	  	{
	  		Console.print(main(x)(y) + "\n")
	  	}
	  	else
	  	{
	  		if (x == n-1)
	  		{
	  			populate(0, y+1)
	  		}
	  		else
	  		{
	  			populate(x+1, y)
	  		}
	  	}

	  }
	  */

	  def fetchValue(x: Int, y: Int) : Int =
	  {
	  	for (a <- 1 to n*n)
	  	{
	  		if(!findDuplicate(a))
	  		{
	  			return a
	  		}
	  	}
	  	return 1
	  }

	  def populate(x: Int, y: Int) : Boolean =
	  {
	  	if (main(x)(y) == 0)
	  	{
	  		main(x)(y) = fetchValue(x, y)
	  	}

	  	if (main(x)(y) > n*n)
	  	{
	  		main(x)(y) = fetchValue(x, y)
	  	}

	  	if (x == n-1 && y == n-1)
	  	{
	  		//return true
	  		if (solved)
	  		{
	  			return true
	  		}
	  		else
	  		{
	  			return false
	  		}
	  	}
	  	else
	  	{	
	  		if (x == n-1) // move down to next row
	  		{
	  			if(!populate(0, y+1))
	  			{
	  				//main(0)(y+1) = main(0)(y+1) + 1
	  				main(0)(y+1) = fetchValue(0, y+1)
	  				
	  				populate(0, y+1)
	  			}
	  			return false
	  		}
	  		else // slide along row
	  		{
	  			if (!populate(x+1, y))
	  			{
	  				//main(x+1)(y) = main(x+1)(y) + 1
	  				main(x+1)(y) = fetchValue(x+1, y)

				  	populate(x+1, y)
	  			}
	  			return false
	  		}
	  	}

	  }

	  populate(0,0)


        printBoard(main, n)
        return main

	}

	def squareFunctional(n: Int) : Array[Array[Int]] =
	{
		// nothing yet
		var main = Array.ofDim[Int](n,n)

		val theSum = (n*(n*n + 1))/2





		return main
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
