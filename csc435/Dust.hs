
module Dust where

import Data.Array.IO
import Data.Array.IArray
import System.IO.Unsafe
import System.Random

import Control.Monad

main :: IO ()
main = putStr "\nHello World!\n"

adds :: Int -> Int -> Int
adds x y = x + y

construct :: Int -> [((Int,Int),Int)]
construct n = [((x, y), -1) | x <- [0..n-1], y <- [0..n-1]]

--change :: Int -> Int -> Int -> [((Int,Int),Int)] -> [((Int,Int),Int)]
--change x y val arr = do

	--where
		--let cell = arr!!
		--x = fst (fst cell)
		--y = snd (fst cell)

printBoard :: Int -> Int -> [((Int,Int),Int)] -> IO()
printBoard n m arr = do
	let cell = (arr !! m)
	let x = fst (fst cell)
	let val = snd cell
	--if x == (n-1)
		--then putStrLn (show x)
		--else putStr (show x)
	putStr (show x)
	putStr " "
	putStrLn (show val)
	printBoard n (m+1) arr

randomInt :: Int -> Int
randomInt n = unsafePerformIO (getStdRandom (randomR (0, n-1)))

-- let cell = (construct 5)!!4 where 5 and 4 are any old numbers (5 determines length of board, 4 is location in board)
-- so cell is a tuple ((x, y), val)
-- x = fst (fst cell)
-- y = snd (fst cell)
-- val = snd cell

-- replicateM_ 10 $ putStrLn "a string"
-- that prints something 10 times in a row; remember to import Control.Monad
