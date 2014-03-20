
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

--generateMines :: Int -> [Int] -> [Int]
--generateMines n m = replicateM_ n $ (randomInt n) : m

-- m = []
-- x = replicateM_ 30 $ (randomInt 9) : m -- that does not work

--change :: Int -> Int -> Int -> [((Int,Int),Int)] -> [((Int,Int),Int)]
--change x y val arr = do
	--let n = sqrt (fromIntegral (length arr))
	--let index = x*n + y


printBoard :: Int -> Int -> [((Int,Int),Int)] -> IO() -- call the function like: printBoard 3 0 (construct 3)
printBoard n m arr = do
	let cell = (arr !! m)
	let y = snd (fst cell)
	let val = snd cell
	if val == (-1)
		then putStr "-"
		else putStr (show val)
	if y == (n-1)
		then putStr "\n"
		else putStr " "
	if (m+1) < (length arr)
		then printBoard n (m+1) arr
		else putStr ""

randomInt :: Int -> Int
randomInt n = unsafePerformIO (getStdRandom (randomR (0, n-1)))

-- let cell = (construct 5)!!4 where 5 and 4 are any old numbers (5 determines length of board, 4 is location in board)
-- so cell is a tuple ((x, y), val)
-- x = fst (fst cell)
-- y = snd (fst cell)
-- val = snd cell

-- replicateM_ 10 $ putStrLn "a string"
-- that prints something 10 times in a row; remember to import Control.Monad
