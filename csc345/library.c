// library.c
// Jack Christiansen
// CSC 345 - Operating Systems

// on Linux machine, compile using: gcc -pthread -o lib library.c
// and run using: ./lib (or whichever filename is preferred)

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

#define MAX_EMAIL_ADDRESS_LENGTH 50


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
	mod 7 = 1, tues etc. 
	3rd index refers to the current index at which to store a userID 
	Time slot completely empty, index = 0 
	1 person, index = 1, etc 
	Check if index is == to seating-1 (ie full) */
	
	/* For specialPurpose: 0 = none, 1 = storage, 2 = group listening,
	3 = group viewing, 4 = graduate students */
	int specialPurpose;
} Room;

Room studyRooms[ROOMS];

int rmNumbers [ROOMS] = {109, 110, 111, 202, 205, 220, 224, 225, 226, 228, 301, 308, 309, 310, 311, 315, 316, 317, 319, 404, 406, 411, 412, 413, 414, 415};

int stNumbers [ROOMS] = {4, 6, 4, 6, 6, 12, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 8, 6, 6, 12, 6, 6};

int purpose [ROOMS] = {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 0, 0, 4, 0, 0};

typedef struct
{
	int userID;
	char email[MAX_EMAIL_ADDRESS_LENGTH];
	int roomRequested;
	int dayRequested;
	int timeRequested;
	int hoursRequested; /* can be 1, 2, or 3. */
	int sub; /* will be 0 or 1 */
	int priority; /* 0 = admin, 1 = student, 2 = faculty */
	int cancel; /* 0 if signing up for a room, 1 if canceling a reservation */
} User;

void *calendarize (void *arg)
{
 	// do things here
 	User *user = arg; 
 	
 	//printf("%s", "Room requested: ");
 	//printf("%d\n", user->roomRequested);
 	
 	// Error check for invalid day, time, hours
 	if (user->dayRequested > DAYS-1 && user->dayRequested < 0)
 	{
 		// Day falls outside of 0-30 range
 		return NULL;
 	}
 	
 	// Check for invalid hours based on day:
 	// If the user types in Day #1, 8 am, it will be parsed here as [0][0]
 	// dayIndex = day - 1, hourIndex = hour - 8
	
 	int incomingDay = user->dayRequested % 7;
 	
 	if (incomingDay == 0 || incomingDay == 1 || incomingDay == 2 || incomingDay == 3)
 	{
 		// Monday, Tuesday, Wednesday or Thursday
 		if (user->timeRequested > HOURS-1-(user->hoursRequested) && user->timeRequested < 0)
 		{
 			// Hour falls outside of 8 am to 12 am range
 			// 8 9 10 11 12 1 2 3 4 5 6 7 8 9 10 11
 			
 			return NULL;
 		}
 	}
 	else if (incomingDay == 4)
 	{
 		// Friday: 8 am to 6 pm
 		if (user->timeRequested > HOURS-6-(user->hoursRequested) && user->timeRequested < 0)
 		{
 			// 8 9 10 11 12 1 2 3 4 5 (not 6 7 8 9 10 11)
 			return NULL;
 		}
 	}
 	else if (incomingDay == 5)
 	{
 		// Saturday: 10 am to 7 pm
 		if (user->timeRequested > (HOURS-5-(user->hoursRequested)) && user->timeRequested < 2)
 		{
 			// (not 8 9) 10 11 12 1 2 3 4 5 6 (not 7 8 9 10 11)
 			return NULL;
 		}
 	}
 	else if (incomingDay == 6)
 	{
 		// Sunday: 11 am to 11 pm
 		if (user->timeRequested > (HOURS-2-(user->hoursRequested)) && user->timeRequested < 3)
 		{
 			// (not 8 9 10) 11 12 1 2 3 4 5 6 7 8 9 10 (not 11)
 			return NULL;
 		}
 	}
 	else
 	{
 		// some weird number that doesn't work for anything
 		return NULL;
 	}
 	
 	// check invalid hours
 	if (user->hoursRequested > 3 && user->hoursRequested < 1)
 	{
 		// Asking for hours less than 1 or more than 3
 		return NULL;
 	}
 	
 	

	int count;
	for (count = 0; count < ROOMS; count++)
	{
		if (studyRooms[count].roomNumber == user->roomRequested)
		{
			// still need to implement priority for users in the critical section

			/* lock */
			pthread_mutex_lock (&(studyRooms[count].available));
			
			printf("%d %s %d \n", user->userID, " has the lock to ", studyRooms[count].roomNumber);
			
			/* critical section */
			if (user->cancel == 0)
			{
				int index = -1; // if a reservation cannot be made for the requested room, this value will never change.
				
				int j;
				
				for (j = 0; j < studyRooms[count].seating; j++)
				{
					if (user->hoursRequested == 1)
					{
						if (studyRooms[count].seats[user->dayRequested][user->timeRequested][j] == 0 && index == -1)
						{
							// searches array of userIDs for the first blank spot it finds. If one is found, the others are ignored.
							index = j;
						}
					}
					else if (user->hoursRequested == 2)
					{
						if (studyRooms[count].seats[user->dayRequested][user->timeRequested][j] == 0 && studyRooms[count].seats[user->dayRequested][user->timeRequested + 1][j] == 0 && index == -1)
						{
							// searches array of userIDs for the first appearance of two consecutive blank spots (along the hour index). If one is found, any other clusters are ignored.
							index = j;
						}
					}
					else if (user->hoursRequested == 3)
					{
						if (studyRooms[count].seats[user->dayRequested][user->timeRequested][j] == 0 && studyRooms[count].seats[user->dayRequested][user->timeRequested + 1][j] == 0 && studyRooms[count].seats[user->dayRequested][user->timeRequested + 2][j] == 0 && index == -1)
						{
							// searches array of userIDs for the first appearance 3 consecutive blank spots (along the hour index). If one is found, any other clusters are ignored.
							index = j;
						}
					}
				}
				
				// at this point, if index is still == -1, no spots were found.
				if (index == -1)
				{
					if (user->sub == 0)
					{
						// FOR DEBUGGING PURPOSES, PLEASE REMOVE THIS LATER
						//printf("%s %d %s %d %s %d %s %d  \n", "REJ'D ID: ", user->userID, "room requested: ", user->roomRequested, "day requested: ", user->dayRequested, "time requested: ", user->timeRequested);
						printf("%d %s %d \n", user->userID, " gave up the lock to ", studyRooms[count].roomNumber);
						pthread_mutex_unlock (&(studyRooms[count].available)); // unlock mutex before exiting function
						return NULL;
					}
					else if (user->sub == 1)
					{
						// find substitute room
						
						// not sure what will go here yet
					}
				}
				
				// now assign the user's ID to that location in the 3d array		
				for (j = user->timeRequested; j < (user->timeRequested + user->hoursRequested); j++)
				{
					studyRooms[count].seats[user->dayRequested][user->timeRequested][index] = user->userID;
				}
				
				// FOR DEBUGGING PURPOSES, PLEASE REMOVE THIS LATER
				//printf("%s %d %s %d %s %d %s %d %s %d \n", "User ID: ", user->userID, "room requested: ", user->roomRequested, "day requested: ", user->dayRequested, "time requested: ", user->timeRequested, "index ", index);
			}
			else if (user->cancel == 1)
			{
				// cancel user's reservation
				
				// first check if they made a reservation in the first place. If they didn't, error.
				int j;
				for (j = 0; j < studyRooms[count].seating; j++)
				{
					if (studyRooms[count].seats[user->dayRequested][user->timeRequested][j] == user->userID)
					{
						// User did, in fact, make a reservation. Cancel it.
						int k;
						for (k = user->timeRequested; k < user->timeRequested + user->hoursRequested; k++)
						{
							studyRooms[count].seats[user->dayRequested][k][j] = 0;
						}
					}
				}
				
				// Went through list of userIDs for that user's supposed time slot and did not find their userID.
				// User must not have made a reservation in the first place.
				
				// The two lines below might not be needed, since they appear again so soon.
				//pthread_mutex_unlock (&(studyRooms[count].available));
				//return NULL;
				
			}
			/* end of critical section */
			
			/* unlock */
			pthread_mutex_unlock (&(studyRooms[count].available));
			printf("%d %s %d \n", user->userID, " gave up the lock to ", studyRooms[count].roomNumber);

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
		studyRooms[i].specialPurpose = purpose[i];
		
		pthread_mutex_init ( &(studyRooms[i].available), NULL); // almost forgot this line. This line is pretty important.
		
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
		// end of initialization loops
	}
	
	for (i = 0; i < ROOMS; i++)
	{
		//printf("%s", "room: ");
		//printf("%d\n", studyRooms[i].roomNumber);
	}
	
	// at this point, all rooms are scanned inside the simulation
	// -----------------------------------------------------------
	
	User users[USERS]; // for testing purposes, right now it has 10 users
	
	FILE *textFile;
	textFile = fopen("users.txt", "r");
	
	if (textFile == NULL)
	{
		printf("%s", "error");
	}
	
	i = 0;
	while (fscanf(textFile, "%d %s %d %d %d %d %d %d %d", &(users[i].userID), users[i].email, &(users[i].roomRequested), &(users[i].dayRequested), &(users[i].timeRequested), &(users[i].hoursRequested), &(users[i].sub), &(users[i].priority), &(users[i].cancel)) != EOF) 
	{
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

	// DEBUG loop designed to make sure data was loaded properly
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
