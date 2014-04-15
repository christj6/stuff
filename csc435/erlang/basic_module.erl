-module(basic_module).
-export([lightMixing/3, palindrome/1]).

% RGB values: first int is Red, second is Green, third is Blue
% Used this to derive color wheel: http://en.wikipedia.org/wiki/File:RBG_color_wheel.svg
lightMixing(0, 0, 0) -> "Black"; % 0 in binary
lightMixing(0, 0, 1) -> "Blue"; % 1 in binary
lightMixing(0, 1, 0) -> "Green"; % 2 in binary
lightMixing(0, 1, 1) -> "Cyan"; % 3 in binary
lightMixing(1, 0, 0) -> "Red"; % 4 in binary
lightMixing(1, 0, 1) -> "Magenta"; % 5 in binary
lightMixing(1, 1, 0) -> "Yellow"; % 6 in binary
lightMixing(1, 1, 1) -> "White". % 7 in binary

% A = string:substr(Input, 1, 1).
% B = string:substr(Input, length(Input) - 1, length(Input) - 1);
palindrome(Input) ->
	A = string:substr(string:to_lower(Input), 1, 1),
	B = string:substr(string:to_lower(Input), length(Input), length(Input)),
	if
		(length(Input) < 2) ->
			true;
		A /= B ->
			false;
		A == B ->
			palindrome(string:substr(string:to_lower(Input), 2, length(Input) - 2))
	end.

% palindrome(Input) when length(Input) > 1 -> palindrome(string:substr(string:to_lower(Input), 2, length(Input) - 1)).


% palindrome(Input) when 3 < 0 -> false.

% string:substr(Input, 1, 1) /= string:substr(Input, length(Input) - 1, length(Input) - 1)
