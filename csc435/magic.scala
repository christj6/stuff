import scala.io._
import scala.actors._
import Actor._

object magic {

	def squareOOP (n: Int) : Array[Array[Int]] =
	{
		var matrix = Array.ofDim[Int](n,n)

		//var matrix = Array(Array(2, 7, 6), Array(9, 5, 1), Array(4, 3, 8)) // test solved method

		//var matrix = Array(Array(9, 7, 6), Array(2, 5, 1), Array(4, 3, 8)) // test swap

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

        printBoard(matrix, n)
        return matrix

	}

	
	def squareFunctional(n: Int) : Array[Array[Int]] =
	{
		// nothing yet
		var square = Array.ofDim[Int](n,n)

		val theSum = (n*(n*n + 1))/2





		return square
	}

	def solved (board: Array[Array[Int]]) : Boolean = 
	  {
	  	var rowSum = 0
	  	var columnSum = 0
	  	var diagonalSumA = 0
	  	var diagonalSumB = 0
	  	val n = board(0).length
	  	val theSum = (n*(n*n + 1))/2
  		 // 
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
	            		//Console.print("Diag A: " + matrix(i)(j) + "\n")
	            		diagonalSumA = diagonalSumA + board(i)(j)

	            		if (i + j == n-1)
	            		{
	            			//Console.print("Diag B: " + matrix(i)(j) + "\n")
	            			diagonalSumB = diagonalSumB + board(i)(j)
	            		}
	            	}

	            	if (i + j == n-1 && i != j)
	            	{
	            		//diagonal going from bottom left to top right
	            		//Console.print("Diag B: " + matrix(i)(j) + "\n")
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
	

  def printBoard(m: Array[Array[Int]], n: Int)
  {
         // print array
        for( i <- 0 to (n-1))
        {
            for( j <- 0 to (n-1))
            {
                Console.print(m(i)(j) + " \t")
             }
          Console.print("\n")
        }
  }

class Ping extends Actor {
	def act()
	{

		receive
			{
				case "test" =>
					Console.print("got it")
			}
	}
}

	def main(args: Array[String]) 
	{
		val caller = self
		val number = 3
		/*
		Console.print("Please enter an integer\n")
		for (ln <- io.Source.stdin.getLines)
		{
			var number = ln.toInt
  		}
  		*/

  		//val pong = new Ping
  		//caller ! "test"



		var first = Array.ofDim[Int](number,number)

		/*
		first = squareOOP(number)
		if (solved(first))
		{
			Console.print("got it")
		}
		*/
		

		
		
		//actor (caller ! first = squareOOP(number))

/*
		val act = Actor.actorOf[MyActor]
		//act ! (first = squareOOP(number))

		if (first = squareOOP(number))
		{
			act ! "test"
		}
		*/

		/*
		first = squareOOP(number)
		if (solved(first))
		{
			Console.print("got it")
		}
		*/

		var timeA = 0
		var timeB = 0
		val data = Array(timeA, timeB)

		//write data to a file
		import java.io._

		def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
		  val write = new java.io.PrintWriter(f)
		  try 
		  { 
		  	op(write) 
		  } 
		  finally 
		  { 
		  	write.close() 
		  }
		}

		printToFile(new File("output.txt"))(write => {
		  data.foreach(write.println)
		})
	}
}
