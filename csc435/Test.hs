module Test where

import Data.Time
import Data.Maybe (listToMaybe)
import System.IO (hSetBuffering, BufferMode(NoBuffering), stdout)


main = do
  hSetBuffering stdout NoBuffering
  putStr "Enter an Int: "

  x <- fmap maybeRead getLine :: IO (Maybe Int)
  maybe (putStrLn "Not an integer")
        (putStrLn . ("The Int is " ++) . show)
        x

  --putStr (show x)

maybeRead :: Read a => String -> Maybe a
maybeRead = fmap fst . listToMaybe . filter (null . snd) . reads

isInt :: (RealFrac a) => a -> Bool
isInt x = x == fromInteger (round x)
