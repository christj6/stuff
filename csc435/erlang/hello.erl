-module(hello).
-export([hello_world/0,hello_world/1]).
hello_world() -> io:fwrite("hello,world\n").
hello_world(Name) -> io:format("Hello ~s~n",[Name]).
