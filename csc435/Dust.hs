module Dust where

import Data.List
import System.IO.Unsafe
import System.Random

main = do
	putStrLn "Enter side length: "
	n <- grab
	let board = populate 0 0 (construct n)

	printBoard n 0 board

	let play board = do
		let numberOfTurns = length (filter ((==0).snd) board) -- finds the number of zeroes (uncovered spots) on the board
		if (mod numberOfTurns 2) == 0
			then putStrLn "Player 1's turn."
			else putStrLn "Player 2's turn."
		coordinates <- turn
		let result = sweep (fst coordinates) (snd coordinates) 0 board
		printBoard n 0 result
		if referenceCell (fst coordinates) (snd coordinates) board == -2
			then putStrLn "You lost."
			else if gameOver result
				then putStrLn "You won."
				else if referenceCell (fst coordinates) (snd coordinates) board == 0
					then putStrLn "Error -- That spot was already played." -- allowing players to uncover an already uncovered spot would mess up the number of turns
					else play result

	play board


-- used for letting the user choose which spot on the board to uncover
turn :: IO(Int, Int)
turn = do
   putStrLn "Enter x coord: "
   x <- readLn
   putStrLn "Enter y coord: "
   y <- readLn
   return (x, y)

-- used for letting the user determine the size of the board at the start of the game
grab :: IO(Int)
grab = do
	x <- readLn
	return x

-- -1 = untouched spot, -2 = untouched mine, 0 = spot already uncovered

-- creates grid full of zeroes, later to be populated with mines/not mines
construct :: Int -> [((Int,Int),Int)]
construct n = [((x, y), 0) | x <- [0..n-1], y <- [0..n-1]]

-- populates board with random mines
populate :: Int -> Int -> [((Int,Int),Int)] -> [((Int,Int),Int)] -- call like: populate 0 0 board
populate x y board = do
	let index = serveIndex x y board
	let secondaryIndex = serveIndex (x+1) 0 board
	let value = placeMine 9 -- the higher the number, the less likely mines will appear
	if index >= 0
		then populate x (y+1) (sweep x y value board)
		else if secondaryIndex >= 0
			then populate (x+1) 0 (sweep x y value board)
			else sweep x y value board

printBoard :: Int -> Int -> [((Int,Int),Int)] -> IO() -- call the function like: printBoard 3 0 (construct 3)
printBoard n m arr = do
	let cell = (arr !! m)
	let x = fst (fst cell)
	let y = snd (fst cell)
	let val = snd cell
	if val == (-1) || val == (-2)
		then putStr "."
		else putStr (show (sumAdjMines x y arr))
	if y == (n-1)
		then putStr "\n"
		else putStr "\t"
	if (m+1) < (length arr)
		then printBoard n (m+1) arr
		else putStr ""

sweep :: Int -> Int -> Int -> [((Int,Int),Int)] -> [((Int,Int),Int)]
sweep x y value board = do
	let index = serveIndex x y board
	let cell = board!!index
	let chunks = splitAt index board
	let firstChunk = fst chunks
	let secondChunk = snd chunks
	let tailEnd = snd (splitAt 1 secondChunk)
	let fillIn = ((x, y), value) -- replaces whatever's at that location with the chosen value
	firstChunk ++ fillIn : tailEnd

-- takes in an x,y coordinate pair and returns the coordinates for the list itself (since the 2-dimensional board is a single list)
serveIndex :: Int -> Int -> [((Int,Int),Int)] -> Int
serveIndex x y board = do
	let sideLength = sqrt (fromIntegral (length board)) 
	let index = (truncate sideLength)*x + y
	if index < 0 || index >= (length board) || x < 0 || x >= (truncate sideLength) || y < 0 || y >= (truncate sideLength)
		then -1
		else index

-- find value at a location specified by x, y coordinates
referenceCell :: Int -> Int -> [((Int,Int),Int)] -> Int -- given x y coordinates and a board, this returns the value stored in that location
referenceCell x y board = do
	let index = serveIndex x y board
	if index >= 0 && index < (length board)
		then snd (board !! index)
		else 0

-- find number of mines that surround a given location
sumAdjMines :: Int -> Int -> [((Int,Int),Int)] -> Int
sumAdjMines x y board = do
	let left = referenceCell (x-1) (y) board
	let topLeft = referenceCell (x-1) (y-1) board
	let top = referenceCell (x) (y-1) board
	let topRight = referenceCell (x+1) (y-1) board
	let right = referenceCell (x+1) (y) board
	let bottomRight = referenceCell (x+1) (y+1) board
	let bottom = referenceCell (x) (y+1) board
	let bottomLeft = referenceCell (x-1) (y+1) board

	let neighbors = [left, topLeft, top, topRight, right, bottomRight, bottom, bottomLeft]
	let mines = (filter (== -2) neighbors)
	length mines

-- Determine whether or not a given location should have a mine there. Used when board is initially created.
placeMine :: Int -> Int -- Function needed an argument, I couldn't just hard-code "9" in there.
placeMine n = do
	if unsafePerformIO (getStdRandom (randomR (0, n))) > 2
		then -1 -- safe square
		else -2 -- mine

-- win condition: returns false if a given index does not store -1. Filter board down to those entries that store -1. If the length is zero, the game is won.
gameOver :: [((Int,Int),Int)] -> Bool
gameOver board = do
	let untouchedSpots = filter ((==(0 - 1)).snd) board
	if (length untouchedSpots) == 0
		then True
		else False
