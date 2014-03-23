// library.c
// Jack Christiansen
// CSC 345 - Operating Systems

// on Linux machine, compile using: gcc -pthread -o lib library.c
// and run using: ./lib

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#include <pthread.h>

#define DAYS 30 // the number of days the simulation will cover
#define HOURS 16 // 8am - 12am, the max number of hours that can be occupied on a given day
#define MAX_SEATING 12 // the maximum number of seats that a room in the set of rooms can offer

#define ROOMS 26 // number of rooms
#define USERS 10 // number of users operating at a given time (may be changed to a variable)


typedef struct
{
	int roomNumber; /* 109, 202, 301, etc. */
	int seating; /* number of people who can fit in the room */

	pthread_mutex_t available;
	

	/* Monday-Thursday, 8:00 a.m. – 12:00 a.m. (16 hours)
	Friday, 8:00 a.m. – 6:00 p.m. (10 hours)
	Saturday, 10:00 a.m. – 7 p.m. (9 hours)
	Sunday, 11:00 a.m. – 11:00 p.m. */ 

	// Mon, Tues, Wed, Thurs, Fri, Sat, Sun (7)
	// 8 9 10 11 12 1 2 3 4 5 6 7 8 9 10 11 (not 12, it's closed at that point) (16)
	
	
	int seats [DAYS][HOURS][MAX_SEATING]; 
	/* first index is day of the week, 2nd is the hour
	 30 refers to the number of days
	 mod 7 = 0, monday
	mod 7 = 1, tues etc. */
	
	/* For specialPurpose: 0 = none, 1 = storage, 2 = group listening,
	3 = group viewing, 4 = graduate students */
	int specialPurpose;
} Room;

Room studyRooms[ROOMS];

int rmNumbers[ROOMS] = {109, 110, 111, 202, 205, 220, 224, 225, 226, 228, 301, 308, 309, 310, 311, 315, 316, 317, 319, 404, 406, 411, 412, 413, 414, 415};

int stNumbers [ROOMS] = {4, 6, 4, 6, 6, 12, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 8, 6, 6, 12, 6, 6};

typedef struct
{
	int userID;
	char email[50];
	int roomRequested;
	int hoursRequested; /* can be 1, 2, or 3. */
	int sub; /* will be 0 or 1 */
	int priority; /* 0 = admin, 1 = student, 2 = faculty */
} User;

void *calendarize (void *arg)
{
 	// do things here
 	User *user = arg; 
 	
 	//printf("%s", "Room requested: ");
 	//printf("%d\n", user->roomRequested);

	int count;
	for (count = 0; count < ROOMS; count++)
	{
		if (studyRooms[count].roomNumber == user->roomRequested)
		{

			/* lock */
			pthread_mutex_lock (&(studyRooms[count].available));
			/* access something */
			if (studyRooms[count].seating == 0)
			{
				printf("%s\n", "Sorry, room is full.");
				printf("%d\n", user->userID);
			}
			else
			{
				studyRooms[count].seating--;
			}
			/* unlock */
			pthread_mutex_unlock (&(studyRooms[count].available));

			return NULL;
			
		}
	}

   return NULL;
}

int main()
{
	

	int i;
	for (i = 0; i < ROOMS; i++)
	{
		studyRooms[i].roomNumber = rmNumbers[i];
		studyRooms[i].seating = stNumbers[i];
		
		int a;
		int b;
		int c;
	
		for (a = 0; a < DAYS; a++)
		{
			for (b = 0; b < HOURS; b++)
			{
				for (c = 0; c < MAX_SEATING; c++)
				{
					studyRooms[i].seats[a][b][c] = 0;
				}
			}
		}
		// end of loops
	}
	
	for (i = 0; i < ROOMS; i++)
	{
		//printf("%s", "room: ");
		//printf("%d\n", studyRooms[i].roomNumber);
	}
	
	// at this point, all rooms are scanned inside the simulation
	
	// users: User ID, email, room requested, hours requested, willing to sub

	User users[USERS]; // for testing purposes, right now it has 10 users
	
	FILE *textFile;
	textFile = fopen("users.txt", "r");
	
	if (textFile == NULL)
	{
		printf("%s", "error");
	}
	
	i = 0;
	while (fscanf(textFile, "%d %s %d %d %d %d", &(users[i].userID), users[i].email, &(users[i].roomRequested), &(users[i].hoursRequested), &(users[i].sub), &(users[i].priority)) != EOF) 
	{
		//printf("%d\n", users[i].userID);
		//printf("%s\n", users[i].email);
		i++;
	}
	
	// at this point, all "users" are scanned inside the simulation
	// ------------------------------------------------------------------
	
	pthread_t threads[USERS];
	
	for (i = 0; i < USERS; i++)
	{
		pthread_create(&threads[i], NULL, calendarize, (void *) &users[i]);
	}
	
	for (i = 0; i < USERS; i++)
	{
		pthread_join(threads[i], NULL);
	}

	for (i = 0; i < ROOMS; i++)
	{
		//printf("%s", "room: ");
		//printf("%d\n", studyRooms[i].roomNumber);
		//printf("%s", "taken? ");
		//printf("%d\n", studyRooms[i].taken);
	}

	pthread_exit(NULL);

	return 0;
}
