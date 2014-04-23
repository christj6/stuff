
red(a).
blue(b).
yellow(c).
green(d).
blue(e).
red(f).

touching(a,b).
touching(a,c).
touching(a,d).

touching(b,a).
touching(b,c).
touching(b,f).

touching(f,b).
touching(f,c).
touching(f,e).

touching(e,c).
touching(e,d).
touching(e,f).

touching(d,a).
touching(d,c).
touching(d,e).

touching(c,a).
touching(c,b).
touching(c,d).
touching(c,e).
touching(c,f).

redConflict(X,Y) :-
	touching(X,Y),
	red(X),
	red(Y).

greenConflict(X,Y) :-
	touching(X,Y),
	green(X),
	green(Y).

yellowConflict(X,Y) :-
	touching(X,Y),
	yellow(X),
	yellow(Y).

blueConflict(X,Y) :-
	touching(X,Y),
	blue(X),
	blue(Y).

conflict(X,Y) :-
	redConflict(X,Y);
	greenConflict(X,Y);
	yellowConflict(X,Y);
	blueConflict(X,Y).
