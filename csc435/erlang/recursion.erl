-module(recursion).
-export([write/1,pascal/1,listAdd/2,pascalString/1]).


write(Data) -> {ok, IODevice} = file:open("Pascal.txt", [write]), file:write(IODevice, Data), file:close(IODevice).

pascal(Int) ->
	if
		Int == 1 ->
			[[1]];
		Int > 1 ->
			Tail = pascal(Int-1),
			Head = hd(Tail),
    		[listAdd([0]++Head, Head++[0])] ++ Tail
	end.

% adds corresponding elements of 2 lists together, compiles corresponding sums in one list
listAdd(List1, List2) ->
	if
		length(List1) == 1 ->
			[hd(List1) + hd(List2)];
		length(List1) /= 0 ->
			[hd(List1) + hd(List2)]++listAdd(tl(List1),tl(List2))
	end.

pascalString(List) -> 
	if
		tl(List) == [] ->
			" ";
		tl(List) /= [] ->
			(hd(List)++" ")++pascalString(tl(List))
	end.
