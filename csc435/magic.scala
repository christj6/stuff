import scala.io._
/*
import scala.actors._
import Actor._

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
*/

import scala.concurrent._
import ExecutionContext.Implicits.global

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

        //Console.print(printBoard(matrix, n))
        return matrix

	}

	
	def squareFunctional(n: Int) : Array[Array[Int]] =
	{
		// nothing yet
		var square = Array.ofDim[Int](n,n)

		val theSum = (n*(n*n + 1))/2

		def odds
		{

		}



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
	

  def printBoard(m: Array[Array[Int]], n: Int) : String = 
  {
         // print array
         var string = ""
        for( i <- 0 to (n-1))
        {
            for( j <- 0 to (n-1))
            {
                //Console.print(m(i)(j) + " \t")
                string = string + (m(i)(j) + " \t")
             }
         //Console.print("\n")
         string = string + "\n"
        }
        return string
  }

/*
  class task extends Actor {

	  def receive = {
	    case "test" => 
	    	println("received test")
	    case "A" => 
	    	println("received a")
	    case "B" => 
	    	println("received b")
	    case _       => 
	    	println("???")
	  }
	}
	*/
	

	def main(args: Array[String]) 
	{
		def example // delete this
		{
			val start = "Hello"
			val f: Future[String] = Future {
			  start + " future!"
			}
			f onSuccess {
			  case msg => println(msg)
			}
		}
		//val system = ActorSystem("HelloSystem")
  		//val taskA = system.actorOf(Props[task], name = "OOP")
  		//val taskB = system.actorOf(Props[task], name = "Functional")
  		//taskA ! "A"
  		//taskB ! "buenos dias"

		Console.print("Please enter an integer\n")
		val number = readLine().toInt
		var timeA = 0.0
		var timeB = 0.0
		var s1 = System.nanoTime
		var s2 = System.nanoTime

		//var first = Array.ofDim[Int](number,number)
		//var second = Array.ofDim[Int](number,number)

		var first = Array.ofDim[Int](number,number)
		var f: Future[Array[Array[Int]]] = Future {
			s1 = System.nanoTime
			squareOOP(number)
		}
		f onSuccess {
		  case result => 
		  	timeA = (System.nanoTime - s1)
		  	Console.print("A:\n" + printBoard(result, number))
		  	first = result
		}

		var second = Array.ofDim[Int](number,number)
		var f2: Future[Array[Array[Int]]] = Future {
			s2 = System.nanoTime
		  	squareOOP(number)
		}
		f2 onSuccess {
		  case result => 
		  	timeB = (System.nanoTime - s2)
		  	Console.print("B:\n" + printBoard(result, number))
		  	second = result
		}




		/*
		var s = System.nanoTime
		first = squareOOP(number)
		if (solved(first))
		{
			//taskA ! "A"
		}
		timeA = (System.nanoTime - s).toInt

		s = System.nanoTime
		second = squareOOP(number)
		if (solved(second))
		{
			//taskB ! "B"
		}
		timeB = (System.nanoTime - s).toInt
		*/


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

		  write.println(printBoard(first, first(0).length))
		  write.println("execution time using OOP method: " + timeA/1e6 + "ms\n")

		  write.println(printBoard(second, second(0).length))
		  write.println("execution time using functional method: " + timeB/1e6 + "ms\n")


		})
	}
}