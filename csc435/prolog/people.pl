


female(mary).
female(alex).
male(joe).
male(carl).

% our assumptions
married(mary,carl).
married(carl,mary).
job(mary).
biologyMajor(joe).
watchingTV(joe).

biologyMajor(alex).
job(alex). % sleepless(alex) didnt work the way I intended

kids(X) :- 
	married(X, Y).

happy(X) :-
	watchingTV(X);
	married(X,Y), \+ sleepless(X).

sleepless(X) :-
	biologyMajor(X), job(X), \+ kids(X);
	biologyMajor(X), \+ job(X), kids(X);
	\+ biologyMajor(X), kids(X), job(X);
	biologyMajor(X), job(X), kids(X).
