

module Dust where

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



