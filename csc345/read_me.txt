Library Scheduler

To compile and run, enter the UNIX terminal,
enter the line: gcc -pthread -o lib.o library.c
To execute, enter: ./lib.o

A text file, "users.txt" must reside in the same directory.
Each row of this file contains information for a user making a request.
For example:
1 alice@gmail.com 109 0 0 3 1 2 0

The first number, "1" refers to the user ID.
The second field contains the user's email address.
The next field "109" contains the room that the user requests.
The next field "0" refers to the day the user wishes to reserve a room. (in this case, Day 1 of 30).
The next field "0" refers to the time at which the user's requested reservation begins (in this case, 8 AM).
The next field "3" contains the length of the user's requested reservation (in hours).
The next field "1" refers to whether or not the user is willing to reserve another room if their ideal room is unavailable at that time.
The next field "2" refers to priority: 0 = administrator, 1 = student, 2 = faculty. 
The last field "0" refers to whether the user is making a reservation or canceling it: 0 = reservation, 1 = canceling.

user ID | email | room | day | time | hours | substitute? | priority | canceling?

The maximum number of user threads permitted to run over the course of the program is 1000.
As long as the text document "users.txt" doesn't exceed 1000 lines, the program should execute fine.

In the simulation, emails are handled as output to the terminal.
If a user successfully reserves a room, they are notified.
If an administrator removes a user from a room, they are notified.
If no rooms are available, the user is notified.
