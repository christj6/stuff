-module(calculator).
-export([add/2,subtract/2,multiply/2,divide/2,exp/2]).

add(Int1, Int2) -> (Int1 + Int2).
subtract(Int1, Int2) -> (Int1 - Int2).
multiply(Int1, Int2) -> (Int1 * Int2).
divide(Int1, Int2) -> (Int1 / Int2).

exp(Num, 0) -> 1;
exp(Num, Mag) when Mag > 0 -> Num*exp(Num, Mag-1);
exp(Num, Neg) when Neg < 0 -> Num/exp(Num, (Neg-1)*-1).

		
