-module(recursion).
-export([write/1,pascal/1]).


write(Data) -> {ok, IODevice} = file:open("Pascal.txt", [write]), file:write(IODevice, Data), file:close(IODevice).

pascal(Int) ->
	if
		Int == 1 ->
			" 1 ";
		Int == 2 ->
			pascal(Int-1) ++ " 1 1 ";
		Int == 3 ->
			pascal(Int-1) ++ " 1 2 1 "

	end.
