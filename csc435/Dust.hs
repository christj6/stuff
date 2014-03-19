

module Dust 
(
	main
,	adds
,	construct

) where

import Data.Array.IO 
import Data.Array.IArray
import System.IO.Unsafe                                     
import System.Random

main :: IO ()
main = putStr "\nHello World!\n"

adds :: Int -> Int -> Int
adds x y = x + y

construct :: Int -> [((Int,Int),Int)]
construct n = [((x, y), -1) | x <- [0..n-1], y <- [0..n-1]]

randomInt :: Int -> Int
randomInt n = unsafePerformIO (getStdRandom (randomR (0, n-1)))


--printArray :: ( Array (Int,Int) Int ) -> String
--printArray arr = unlines [unwords [show (arr ! (x, y)) | x <- [0..5]] | y <- [0..5]]

printBoard :: [((Int,Int),Int)] -> String
printBoard arr = "x"




showTable :: ( Array (Int,Int) Int ) -> String
showTable arr = 
  unlines $ map (unwords . map (show . (arr !))) indices
  where indices = [[(x, y) | x <- [0..8]] | y <- [0..8]]
        ((0, 0), (8, 8)) = bounds arr



--test = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]

--board = [((x, y), 0) | x <- [0..8], y <- [0..8]]

