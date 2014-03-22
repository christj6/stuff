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


typedef struct
{
	int roomNumber; /* 109, 202, 301, etc. */
	int seating; /* number of people who can fit in the room */

	pthread_mutex_t available;

	int taken; /* Will be either 0 to 1 -- later will be implemented as a two-dimensional array of 0s and 1s (day and hour) */
	/* NOOOOOOOOOOOPE, nope. */
	/* Instead, each room will get its own 2d array of seating values,
	when a user requests a room at a given time, if the number isn't 0,
	1 will be subtracted from the number. */
	
	/* For specialPurpose: 0 = none, 1 = storage, 2 = group listening,
	3 = group viewing, 4 = graduate students */
	int specialPurpose;
} Room;

Room studyRooms[26];

int rmNumbers[26] = {109, 110, 111, 202, 205, 220, 224, 225, 226, 228, 301, 308, 309, 310, 311, 315, 316, 317, 319, 404, 406, 411, 412, 413, 414, 415};

int stNumbers [26] = {4, 6, 4, 6, 6, 12, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 8, 6, 6, 12, 6, 6};

typedef struct
{
	int userID;
	char email[50];
	int roomRequested;
	int hoursRequested;
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
	for (count = 0; count < 26; count++)
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
				studyRooms[count].taken = 1;
			}
			else
			{
				studyRooms[count].seating--;
			}
			/* unlock */
			pthread_mutex_unlock (&(studyRooms[count].available));
			
		}
	}

   return NULL;
}

int main()
{
	

	int i;
	for (i = 0; i < 26; i++)
	{
		//studyRooms[i].roomNumber = rmNumbers[i];
		//studyRooms[i].seating = stNumbers[i];
		//studyRooms[i].taken = 0;
	}
	
	for (i = 0; i < 26; i++)
	{
		//printf("%s", "room: ");
		//printf("%d\n", studyRooms[i].roomNumber);
	}
	
	// at this point, all rooms are scanned inside the simulation
	
	// users: User ID, email, room requested, hours requested, willing to sub
	User users[10]; // for testing purposes, right now it has 10 users
	
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
	
	pthread_t threads[10];
	
	for (i = 0; i < 10; i++)
	{
		pthread_create(&threads[i], NULL, calendarize, (void *) &users[i]);
	}
	
	for (i = 0; i < 10; i++)
	{
		pthread_join(threads[i], NULL);
	}

	for (i = 0; i < 26; i++)
	{
		//printf("%s", "room: ");
		//printf("%d\n", studyRooms[i].roomNumber);
		//printf("%s", "taken? ");
		//printf("%d\n", studyRooms[i].taken);
	}

	pthread_exit(NULL);

	return 0;
}
