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
#define USERS 20 // number of user threads operating at a given time

#define MAX_EMAIL_ADDRESS_LENGTH 50

// format of the input file (for simulation purposes)
// example line: 1 alice@gmail.com 109 0 1 3 0 1 0
// userID, email, room requested, day requested, time requested, # of hours requested, willingness to sub, priority, whether they are making or canceling a reservation
// day can go from 0 (day 1) to 29 (day 30)
// time can go from 0 (8 am) to 15 (11 pm)
// hours can be 1, 2 or 3

// reminder: using gdb:
// compile: gcc -g -pthread -o lib.o library.c
// start gdb: gdb lib.o
// set breakpoint at line #220: b 220
// run, step
// look at current value of x: print x

typedef struct
{
	int roomNumber; /* 109, 202, 301, etc. */
	int seating; /* number of people who can fit in the room */

	pthread_mutex_t available;
	pthread_cond_t cond;
	
	int admin; // priority = 0
	int students; // priority = 1
	int faculty; // priority = 2

	

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

void *schedule (void *arg, int count)
{
	User *user = arg;
	/* critical section */
	if (user->cancel == 0)
	{
		//int index = -1; // if a reservation cannot be made for the requested room, this value will never change.

		int indexArray [user->hoursRequested]; // stores the indexes of the consecutive userID arrays 


		int j;

		for (j = 0; j < user->hoursRequested; j++)
		{
			indexArray[j] = -1;
		}
		
		
		
		for (j = 0; j < studyRooms[count].seating; j++)
		{
			int k;
			for (k = 0; k < user->hoursRequested; k++)
			{
				if (studyRooms[count].seats[user->dayRequested][user->timeRequested+k][j] == 0 && indexArray[k] == -1)
				{
					// searches array of userIDs for the first blank spot it finds. If one is found, the others are ignored.
					indexArray[k] = j;
				}
			}

		}
		
		// at this point, if index is still == -1, no spots were found.
		if (indexArray[0] == -1)
		{
			if (user->sub == 0)
			{
				// FOR DEBUGGING PURPOSES, PLEASE REMOVE THIS LATER
				//printf("%s %d %s %d %s %d %s %d  \n", "REJ'D ID: ", user->userID, "room requested: ", user->roomRequested, "day requested: ", user->dayRequested, "time requested: ", user->timeRequested);
				//printf("%d %s %d \n", user->userID, " gave up the lock to ", studyRooms[count].roomNumber);
				//pthread_mutex_unlock (&(studyRooms[count].available)); // unlock mutex before exiting function
				
				return NULL;
			}
			else if (user->sub == 1)
			{
				
				// find substitute room
				
				// not sure what will go here yet
				
				/*
				int z;
				int prospectiveRoom;
				
				for (z = 0; z < ROOMS; z++)
				{
					if (z != count && studyRooms[z].seating >= studyRooms[count].seating) // if (studyRooms[count].seating == studyRooms[z].seating)
					{
						prospectiveRoom = z;
						int a;
						for (a = 0; a < studyRooms[prospectiveRoom].seating; a++)
						{
							int b;
							for (b = 0; b < user->hoursRequested; b++)
							{
								if (studyRooms[prospectiveRoom].seats[user->dayRequested][user->timeRequested+b][a] == 0 && indexArray[b] == -1)
								{
									// searches array of userIDs for the first blank spot it finds. If one is found, the others are ignored.
									indexArray[b] = a;
								}
							}
						}
					}
				}
				
				if (indexArray[0] != -1)
				{
					int a;
					for (a = user->timeRequested; a < (user->timeRequested + user->hoursRequested); a++)
					{
						int b;
						for (b = 0; b < user->hoursRequested; b++)
						{
							studyRooms[prospectiveRoom].seats[user->dayRequested][a][indexArray[b]] = user->userID;
						}
					}
					
					if (1)
					{
						printf("%s %d %s \n", "user ", user->userID, "had botched scheduling. ");
					}
				}
				else
				{
					// try something else
					printf("%s %d %s \n", "user ", user->userID, "couldn't get scheduled. ");
				}
				
				
				//printf("%d %s %d \n", user->userID, " gave up the lock to ", studyRooms[count].roomNumber);
				//pthread_mutex_unlock (&(studyRooms[count].available)); // unlock mutex before exiting function
				*/
				
				return NULL;
				// -----
			}
		}
		
		// now assign the user's ID to that location in the 3d array		
		for (j = user->timeRequested; j < (user->timeRequested + user->hoursRequested); j++)
		{
			int k;
			for (k = 0; k < user->hoursRequested; k++)
			{
				studyRooms[count].seats[user->dayRequested][j][indexArray[k]] = user->userID;
			}
		}
		
		return NULL;
		
		// FOR DEBUGGING PURPOSES, PLEASE REMOVE THIS LATER
		//printf("%s %d %s %d %s %d %s %d %s %d \n", "User ID: ", user->userID, "room requested: ", user->roomRequested, "day requested: ", user->dayRequested, "time requested: ", user->timeRequested, "index ", index);
	}
	else if (user->cancel == 1)
	{
		// cancel user's reservation
		
		// first check if they made a reservation in the first place. If they didn't, error.

		// this block of code assumes that it's possible that a user can request 3 consecutive hours in the same room,
		// yet their user ID may be stored in different indexes for each of the 3 user-ID holding arrays.
		int j;
		if (user->hoursRequested >= 1)
		{
			for (j = 0; j < studyRooms[count].seating; j++)
			{
				if (studyRooms[count].seats[user->dayRequested][user->timeRequested][j] == user->userID)
				{
					// first hour that was reserved is set to zero
					studyRooms[count].seats[user->dayRequested][user->timeRequested][j] = 0;

					if (user->hoursRequested >= 2)
					{
						int k;
						for (k = 0; k < studyRooms[count].seating; k++)
						{
							if (studyRooms[count].seats[user->dayRequested][user->timeRequested+1][k] == user->userID)
							{
								// second hour set to zero
								studyRooms[count].seats[user->dayRequested][user->timeRequested+1][k] = 0;

								if (user->hoursRequested >= 3)
								{
									int m;
									for (m = 0; m < studyRooms[count].seating; m++)
									{
										if (studyRooms[count].seats[user->dayRequested][user->timeRequested+2][m] == user->userID)
										{
											// final hour set to zero
											studyRooms[count].seats[user->dayRequested][user->timeRequested+2][m] = 0;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		// Went through list of userIDs for that user's supposed time slot and did not find their userID.
		// User must not have made a reservation in the first place.

		return NULL;
		
	}
	/* end of critical section */
	return 0;
}

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
			if (user->priority == 0)
			{
				pthread_mutex_lock (&(studyRooms[count].available));
				studyRooms[count].admin++;
				pthread_mutex_unlock (&(studyRooms[count].available));
			}
			if (user->priority == 1)
			{
				pthread_mutex_lock (&(studyRooms[count].available));
				studyRooms[count].students++;
				pthread_mutex_unlock (&(studyRooms[count].available));
			}
			if (user->priority == 2)
			{
				pthread_mutex_lock (&(studyRooms[count].available));
				studyRooms[count].faculty++;
				pthread_mutex_unlock (&(studyRooms[count].available));
			}
			
			if (user->priority == 0)
			{
				
				pthread_mutex_lock (&(studyRooms[count].available));
				
				// call administrator function
				// admin()
				studyRooms[count].admin--;

				pthread_mutex_unlock (&(studyRooms[count].available));
			}

			if (user->priority == 1)
			{
				pthread_mutex_lock (&(studyRooms[count].available));	
				
				schedule (user, count);
				
				studyRooms[count].students--;
				
				if (studyRooms[count].students <= 0)
				{
					pthread_cond_signal(&(studyRooms[count].cond));
				}
				
				pthread_mutex_unlock (&(studyRooms[count].available));
			}
			
			if (user->priority == 2)
			{
				pthread_mutex_lock (&(studyRooms[count].available));	
				
				while (studyRooms[count].students > 0)
				{	
					pthread_cond_wait(&(studyRooms[count].cond), &(studyRooms[count].available));
				}
				pthread_cond_signal(&(studyRooms[count].cond));
				
				schedule (user, count);
				
				studyRooms[count].faculty--;
				
				pthread_mutex_unlock (&(studyRooms[count].available));
			}
			
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
		pthread_cond_init ( &(studyRooms[i].cond), NULL);
		
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

	// In the input file, cancel all the reservations after they were made. Check if each 3d array contains all zeroes. If it does, the cancel works.
	for (i = 0; i < ROOMS; i++)
	{		
		int a;
		int b;
		int c;
	
		for (a = 0; a < DAYS; a++)
		{
			for (b = 0; b < HOURS; b++)
			{
				for (c = 0; c < MAX_SEATING; c++)
				{
					if (studyRooms[i].seats[a][b][c] != 0)
					{
						printf("%s%d%s%d%s%d%s%d%s%d\n", "Room # ", i, ", Day ", a, " Hour ", b, " Room Slot ", c, " has user # ", studyRooms[i].seats[a][b][c]);
					}
				}
			}
		}
	}

	pthread_exit(NULL);

	return 0;
}
