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

palindrome(string) -> "boo".
