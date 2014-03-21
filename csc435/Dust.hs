
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

adds :: Int -> Int -> Int
adds x y = x + y

construct :: Int -> [((Int,Int),Int)]
construct n = [((x, y), z) | x <- [0..n-1], y <- [0..n-1], z <- [(randomInt 2 0) - 2]] -- totally random number of mines

main = do
   putStrLn "Enter the board length: "
   n <- getLine
   putStrLn "Enter the number of mines: "
   m <- getLine
   let z = playGame n m
   putStrLn ""

turn :: [((Int,Int),Int)] -> IO()
turn board = do
   putStrLn "Enter x coord: "
   x <- getLine
   putStrLn "Enter y coord: "
   y <- getLine
   let k = reveal x y board
   putStrLn ""

playGame :: String -> String -> [((Int,Int),Int)]
playGame n m = do
	--let board = construct (read n)
	-- stuff in between here
	construct (read n)

reveal :: String -> String -> [((Int,Int),Int)] -> Int
reveal x y board = do
	let val = referenceCell (read x) (read y) board
	if val == -2
		then -3 -- mine found, you lose
		else sumAdjMines (read x) (read y) board -- call # func

-- -1 = untouched spot, -2 = untouched mine, -3 = stepped on mine, lose game, any other number = # of mines surrounding spot

printBoard :: Int -> Int -> [((Int,Int),Int)] -> IO() -- call the function like: printBoard 3 0 (construct 3)
printBoard n m arr = do
	let cell = (arr !! m)
	let y = snd (fst cell)
	let val = snd cell
	if val == (-1) || val == (-2)
		then putStr "."
		else putStr (show val)
	if y == (n-1)
		then putStr "\n"
		else putStr " "
	if (m+1) < (length arr)
		then printBoard n (m+1) arr
		else putStr ""

sweep :: Int -> Int -> [((Int,Int),Int)] -> [((Int,Int),Int)]
sweep x y board = do
	let index = serveIndex x y board
	let cell = board!!index
	let chunks = splitAt index board
	let firstChunk = fst chunks
	let secondChunk = snd chunks
	let tailEnd = snd (splitAt 1 secondChunk)
	let fillIn = ((x, y), sumAdjMines x y board)
	if referenceCell x y board == -3
	then []
	else firstChunk ++ fillIn : tailEnd

serveIndex :: Int -> Int -> [((Int,Int),Int)] -> Int
serveIndex x y board = do
	let sideLength = sqrt (fromIntegral (length board)) 
	let index = (truncate sideLength)*x + y
	if index >= 0 && index < (length board)
		then index
		else -1

referenceCell :: Int -> Int -> [((Int,Int),Int)] -> Int -- given x y coordinates and a board, this returns the value stored in that location
referenceCell x y board = do
	let index = serveIndex x y board
	if index >= 0 && index < (length board)
		then snd (board !! index)
		else 0

sumAdjMines :: Int -> Int -> [((Int,Int),Int)] -> Int
sumAdjMines x y board = do
	let neighbors = [referenceCell (x-1) y board, referenceCell (x-1) (y-1) board, referenceCell x (y-1) board, referenceCell (x+1) (y-1) board, referenceCell (x+1) y board, referenceCell (x+1) (y+1) board, referenceCell x (y+1) board, referenceCell (x-1) (y+1) board]
	let mines = filter (== -2) neighbors
	if referenceCell x y board == -2
	then -3
	else length mines


randomInt :: Int -> Int -> Int -- map friendly: if you're not using a map, call the function like randomInt n 0
randomInt n m = unsafePerformIO (getStdRandom (randomR (0, n-1)))

placeMine :: Int -> Int -> Int -- map friendly: if you're not using a map, call the function like placeMine n 0
placeMine n m = do
	if (randomInt n 0) > 2
		then -1 -- safe square
		else -2 -- mine

generateMines :: Int -> [Int] -- generates list of n random -1s or -2s
generateMines n = map (placeMine n) [0..n-1]

-- n = map (+1) (map (*0) [0..n]) -- creates length n list full of 1s

-- let cell = (construct 5)!!4 where 5 and 4 are any old numbers (5 determines length of board, 4 is location in board)
-- so cell is a tuple ((x, y), val)
-- x = fst (fst cell)
-- y = snd (fst cell)
-- val = snd cell

-- replicateM_ 10 $ putStrLn "a string"
-- that prints something 10 times in a row; remember to import Control.Monad
