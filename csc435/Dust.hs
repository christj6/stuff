module Dust where

import Data.List
import Data.Array.IO
import Data.Array.IArray
import System.IO.Unsafe
import System.Random

import Control.Monad

-- user input still needs to be implemented:
-- to play it right now, initiate Dust.hs
-- type "let a = construct n" where n is the length of the side (could be any number)
-- type "printBoard n 0 a" to display the board
-- type "let b = sweep x y a" to examine (x, y) on board b
-- type "printBoard n 0 b" to display the board after that change
-- continue until... ???

--adds :: Int -> Int -> Int
--adds x y = x + y

construct :: Int -> [((Int,Int),Int)]
construct n = [((x, y), z) | x <- [0..n-1], y <- [0..n-1], z <- [(randomInt 2 0) - 2]] -- totally random number of mines

-- populates board with random mines
populate :: Int -> Int -> [((Int,Int),Int)] -> [((Int,Int),Int)] -- call like: populate 0 0 board
populate x y board = do
	let index = serveIndex x y board
	let secondaryIndex = serveIndex (x+1) 0 board
	let value = (randomInt 2 0) - 2
	if index >= 0
		then populate x (y+1) (sweep x y value board)
		else if secondaryIndex >= 0
			then populate (x+1) 0 (sweep x y value board)
			else sweep x y value board

main = do
	putStrLn "Enter side length: "
	n <- grab
	let board = populate 0 0 (construct n)

	-- need to figure out a better way of doing this
	-- apparently if I copy and paste these lines 100000 times in a row,
	-- it's possible to play a game, but it would be really nice to
	-- get a function or something happening here

	printBoard n 0 board

	let f = do
		coordinates <- turn
		let result = sweep (fst coordinates) (snd coordinates) 0 board
		printBoard n 0 result
		let board = sweep (fst coordinates) (snd coordinates) 0 result
		putStr ""

	--delete
	
	coordinates <- turn
	let result = sweep (fst coordinates) (snd coordinates) 0 board
	printBoard n 0 result
	let board = sweep (fst coordinates) (snd coordinates) 0 result
	
	coordinates <- turn
	let result = sweep (fst coordinates) (snd coordinates) 0 board
	printBoard n 0 result
	let board = sweep (fst coordinates) (snd coordinates) 0 result
	
	coordinates <- turn
	let result = sweep (fst coordinates) (snd coordinates) 0 board
	printBoard n 0 result
	let board = sweep (fst coordinates) (snd coordinates) 0 result
	
	coordinates <- turn
	let result = sweep (fst coordinates) (snd coordinates) 0 board
	printBoard n 0 result
	let board = sweep (fst coordinates) (snd coordinates) 0 result
	
	coordinates <- turn
	let result = sweep (fst coordinates) (snd coordinates) 0 board
	printBoard n 0 result
	let board = sweep (fst coordinates) (snd coordinates) 0 result
	
	coordinates <- turn
	let result = sweep (fst coordinates) (snd coordinates) 0 board
	printBoard n 0 result
	let board = sweep (fst coordinates) (snd coordinates) 0 result
	
	coordinates <- turn
	let result = sweep (fst coordinates) (snd coordinates) 0 board
	printBoard n 0 result
	let board = sweep (fst coordinates) (snd coordinates) 0 result
	
	coordinates <- turn
	let result = sweep (fst coordinates) (snd coordinates) 0 board
	printBoard n 0 result
	let board = sweep (fst coordinates) (snd coordinates) 0 result

	-- end delete

	replicateM_ 2 $ do
		putStrLn ""

-- ///////////////////////////////////////////////////////////////////////

turn :: IO(Int, Int)
turn = do
   putStrLn "Enter x coord: "
   x <- readLn
   putStrLn "Enter y coord: "
   y <- readLn
   return (x, y)

grab :: IO(Int)
grab = do
	x <- readLn
	return x

-- -1 = untouched spot, -2 = untouched mine, -3 = stepped on mine, lose game, any other number = # of mines surrounding spot

printBoard :: Int -> Int -> [((Int,Int),Int)] -> IO() -- call the function like: printBoard 3 0 (construct 3)
printBoard n m arr = do
	let cell = (arr !! m)
	let x = fst (fst cell)
	let y = snd (fst cell)
	let val = snd cell
	if val == (-1) || val == (-2)
		then putStr (show val)
		else putStr (show (sumAdjMines x y arr)) -- this block of code hides the untouched spots (including mines)
	-- 

	--if val == (-1) || val == (-2)
	--	then putStr "."
	--	else putStr (show (sumAdjMines x y arr)) -- old thing that used to be here


	--putStr (show (sumAdjMines x y arr))
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
	--if referenceCell x y board == -2
	--then []
	--else firstChunk ++ fillIn : tailEnd
	firstChunk ++ fillIn : tailEnd

-- takes in an x,y coordinate pair and returns the coordinates for the list itself (since the 2-dimensional board is a single list)
serveIndex :: Int -> Int -> [((Int,Int),Int)] -> Int
serveIndex x y board = do
	let sideLength = sqrt (fromIntegral (length board)) 
	let index = (truncate sideLength)*x + y
	if index < 0 || index >= (length board) || x < 0 || x >= (truncate sideLength) || y < 0 || y >= (truncate sideLength)
		then -1
		else index

referenceCell :: Int -> Int -> [((Int,Int),Int)] -> Int -- given x y coordinates and a board, this returns the value stored in that location
referenceCell x y board = do
	let index = serveIndex x y board
	if index >= 0 && index < (length board)
		then snd (board !! index)
		else 0

sumAdjMines :: Int -> Int -> [((Int,Int),Int)] -> Int
sumAdjMines x y board = do
	--let neighbors = [(referenceCell (x-1) y board), (referenceCell (x-1) (y-1) board), (referenceCell x (y-1) board), (referenceCell (x+1) (y-1) board), (referenceCell (x+1) y board), (referenceCell (x+1) (y+1) board), (referenceCell x (y+1) board), (referenceCell (x-1) (y+1) board)]
	let left 		= 	referenceCell (x-1) (y)   board
	let topLeft 	= 	referenceCell (x-1) (y-1) board
	let top 		= 	referenceCell (x) 	(y-1) board
	let topRight 	= 	referenceCell (x+1) (y-1) board
	let right 		= 	referenceCell (x+1) (y)   board
	let bottomRight = 	referenceCell (x+1) (y+1) board
	let bottom 		= 	referenceCell (x) 	(y+1) board
	let bottomLeft 	= 	referenceCell (x-1) (y+1) board

	let neighbors = [left, topLeft, top, topRight, right, bottomRight, bottom, bottomLeft]
	let mines = (filter (== -2) neighbors)
	--if referenceCell x y board == -2
	--then -3
	--else length mines
	length mines


randomInt :: Int -> Int -> Int -- map friendly: if you're not using a map, call the function like randomInt n 0
randomInt n m = unsafePerformIO (getStdRandom (randomR (0, n-1)))

placeMine :: Int
placeMine = do
	if (randomInt 10 0) > 5
		then -1
		else -2

--placeMine :: Int -> Int -> Int -- map friendly: if you're not using a map, call the function like placeMine n 0
--placeMine n m = do
	--if (randomInt n 0) > 2
		--then -1 -- safe square
		--else -2 -- mine

--generateMines :: Int -> [Int] -- generates list of n random -1s or -2s
--generateMines n = map (placeMine n) [0..n-1]

-- n = map (+1) (map (*0) [0..n]) -- creates length n list full of 1s

-- let cell = (construct 5)!!4 where 5 and 4 are any old numbers (5 determines length of board, 4 is location in board)
-- so cell is a tuple ((x, y), val)
-- x = fst (fst cell)
-- y = snd (fst cell)
-- val = snd cell

-- replicateM_ 10 $ putStrLn "a string"
-- that prints something 10 times in a row; remember to import Control.Monad
