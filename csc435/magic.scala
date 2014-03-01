
// Jack Christiansen
// CSC 435 - Assignment 3
// This program generates Magic Squares given a dimension n
// It uses two methods to generate squares.
// Both methods are activated concurrently and timed.
// The squares and time to generate each are written to a text file.

import scala.io._
import scala.concurrent._
import ExecutionContext.Implicits.global

object magic {
	
	def squareOOP (n: Int) : Array[Array[Int]] =
	{

		var matrix = Array.ofDim[Int](n,n)

		//var matrix = Array(Array(2, 7, 6), Array(9, 5, 1), Array(4, 3, 8)) // used to test solved method

		val theSum = (n*(n*n + 1))/2

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
	  		
	  		if (value%n != 0)
	  		{
	  			if (i < 0)
	  			{
	  				i = i + n
	  			}
	  			else if (j == n)
	  			{
	  				j = j - n
	  			}
	  		}
	  		else
	  		{
	  			i = i + 2
	  			j = j - 1
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

	  if (n%2 == 0 && n != 4)
	  {
	  	// for other evens
	  	return null
	  }

	  return matrix
	}

	
	def squareFunctional(n: Int) : Array[Array[Int]] =
	{
		// nothing yet
		var matrix = Array.ofDim[Int](n,n)
		val theSum = (n*(n*n + 1))/2


		def odds(i: Int, j: Int, value: Int) // similar to the algorithm in the OOP method, but this operates recursively
		{
			var a = 0
			var b = 0

			if (value > n*n)
			{
		  		// do nothing, array is populated
		  	}
		  	else
		  	{
		  		matrix(i)(j) = value
		  		a = i - 1
		  		b = j + 1

		  		if (value%n != 0)
		  		{
		  			if (i-1 < 0)
		  			{
		  				a = i-1 + n
		  				//odds(i-1+n, j+1, value+1)
		  			}
		  			else if (j+1 == n)
		  			{
		  				b = j+1 - n
		  				//odds(i-1, j+1-n, value+1)
		  			}
		  		}
		  		else
		  		{
		  			a = i-1 + 2
		  			b = j+1 - 1
		  			//odds(i-1+2, j, value+1)
		  		}
		  		odds(a, b, value+1)
		  	}
		  }

		  def fourFunc(b: Array[Array[Int]]) : Array[Array[Int]] =
		  {
			// only works for 4x4 squares
			var mat = b

			var temp = mat(0)(0)
			mat(0)(0) = mat(3)(3)
			mat(3)(3) = temp

			temp = mat(1)(1)
			mat(1)(1) = mat(2)(2)
			mat(2)(2) = temp

			temp = mat(2)(1)
			mat(2)(1) = mat(1)(2)
			mat(1)(2) = temp

			temp = mat(0)(3)
			mat(0)(3) = mat(3)(0)
			mat(3)(0) = temp

			return mat
		}

		if (n%2 != 0)
		{
			odds(0, n/2, 1)
		}

		if (n == 4)
		{
			var copy = matrix
			var copy2 = matrix

			var list = List(copy, copy2)
			var list2 = list.map((b: Array[Array[Int]]) => fourFunc(b))
			return squareOOP(4)
		}

		return matrix
	}

	// checks if magic square is solved by computing sums for rows, columns and diagonals
	def solved (board: Array[Array[Int]]) : Boolean = 
	{
		var rowSum = 0
		var columnSum = 0
		var diagonalSumA = 0
		var diagonalSumB = 0
		val n = board(0).length
		val theSum = (n*(n*n + 1))/2

		for( i <- 0 to (n-1))
		{
			rowSum = 0
			columnSum = 0

			for( j <- 0 to (n-1))
			{
				rowSum = rowSum + board(i)(j)
				columnSum = columnSum + board(j)(i)

				if (i == j)
				{
	            		//diagonal going from top left to bottom right
	            		diagonalSumA = diagonalSumA + board(i)(j)

	            		if (i + j == n-1)
	            		{
	            			diagonalSumB = diagonalSumB + board(i)(j)
	            		}
	            	}

	            	if (i + j == n-1 && i != j)
	            	{
	            		//diagonal going from bottom left to top right
	            		diagonalSumB = diagonalSumB + board(i)(j)
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
	    
	  // returns string containing the matrix, formatted for output
	  def printBoard(m: Array[Array[Int]], n: Int) : String = 
	  {
         // print array
         var string = ""
         for( i <- 0 to (n-1))
         {
         	for( j <- 0 to (n-1))
         	{
         		string = string + (m(i)(j) + " \t")
         	}
         	string = string + "\n"
         }
         return string
     }

     def main(args: Array[String]) 
     {
     	Console.print("Please enter an integer\n")
		var number = readLine().toInt // scan integer from user
		while (number <= 2)
		{
			Console.print("Must be greater than 2: \n")
			number = readLine().toInt 
		}
		var timeA = 0.0
		var timeB = 0.0
		var s1 = System.nanoTime
		var s2 = System.nanoTime

		var first = Array.ofDim[Int](number,number)
		var second = Array.ofDim[Int](number,number)

		// Execute the two function calls concurrently, and calculating their execution times
		// s1 and s2 were used to prevent a race condition over one variable
		var f: Future[Array[Array[Int]]] = Future {
			s1 = System.nanoTime
			squareOOP(number)
		}
		var f2: Future[Array[Array[Int]]] = Future {
			s2 = System.nanoTime
			squareFunctional(number)
		}

		f onSuccess {
			case result => 
			timeA = (System.nanoTime - s1)
			if (solved(result))
			{
				first = result
			}
			Console.print("A:\n" + printBoard(first, number))
		}		
		f2 onSuccess {
			case result2 => 
			timeB = (System.nanoTime - s2)
			if (solved(result2))
			{
				second = result2
			}
			Console.print("B:\n" + printBoard(second, number))
		}

		//write data to a file
		import java.io._

		def printToFile(file: java.io.File)(print: java.io.PrintWriter => Unit) 
		{
			val write = new java.io.PrintWriter(file)
			try 
			{ 
				print(write) 
			} 
			finally 
			{ 
				write.close() 
			}
		}

		printToFile(new File("output.txt"))(write => {

			write.println(printBoard(first, first(0).length))
			write.println("execution time using OOP method: " + timeA/1e6 + "ms\n")

			write.println(printBoard(second, second(0).length))
			write.println("execution time using functional method: " + timeB/1e6 + "ms\n")

			})

	}
}
