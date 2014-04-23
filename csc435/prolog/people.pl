


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
sleepless(alex).

happy(X) :-
	watchingTV(X);
	married(X,Y), \+ sleepless(X).

sleepless(X) :-
	biologyMajor(X), job(X);
	biologyMajor(X), kids(X);
	kids(X), job(X);
	biologyMajor(X), job(X), kids(X).

