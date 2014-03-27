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
#define USERS 1000 // number of user threads operating for each execution of the program

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
	
	pthread_mutex_t adminThreadcountLock;
	pthread_mutex_t studentThreadcountLock;
	pthread_mutex_t facultyThreadcountLock;
	
	pthread_cond_t high;
	pthread_cond_t adminLock;
	
	
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

typedef struct
{
	int userID;
	char email[MAX_EMAIL_ADDRESS_LENGTH];
	int roomRequested;
	int dayRequested;
	int timeRequested;
	int hoursRequested; /* can be 1, 2, or 3. */
	int sub; /* will be 0 or 1, irrelevant for admin */
	int priority; /* 0 = admin, 1 = student, 2 = faculty */
	int cancel; /* 0 if signing up for a room, 1 if canceling a reservation */
} User;

Room studyRooms[ROOMS];

int rmNumbers [ROOMS] = {109, 110, 111, 202, 205, 220, 224, 225, 226, 228, 301, 308, 309, 310, 311, 315, 316, 317, 319, 404, 406, 411, 412, 413, 414, 415};

int stNumbers [ROOMS] = {4, 6, 4, 6, 6, 12, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 8, 6, 6, 12, 6, 6};

int purpose [ROOMS] = {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 0, 0, 4, 0, 0};

User users[USERS];

// The administrator doesn't reserve individual seats in a room, but instead reserves the entire room for a block of time.
// This is for special events, or when a room's under construction, etc.
// An admin can also clear out an entire room, whether or not he/she initially registered for it, unlike students and faculty.
void *adminSchedule (void *arg, int count)
{
	User *user = arg;
	

	int j;
	for (j = user->timeRequested; j < (user->timeRequested + user->hoursRequested); j++)
	{
		int k;
		for (k = 0; k < studyRooms[count].seating; k++)
		{
			if (user->cancel == 0)
			{
				if (studyRooms[count].seats[user->dayRequested][j][k] != 0)
				{
					int canceledUserIndex = -1;
					int i;
					for (i = 0; i < USERS; i++)
					{
						if (users[i].userID == studyRooms[count].seats[user->dayRequested][j][k])
						{
							canceledUserIndex = i;
						}
					}
					
					int incomingDay = user->dayRequested % 7;
					int time = j + 8;
					char *timeString;
					char *dayString;
					
					if (time >= 12)
					{
						timeString = ":00 PM";
					}
					else
					{
						timeString = ":00 AM";
					}
					
					switch (incomingDay)
					{
						case 0:
						dayString = "Monday";
						break;
						case 1:
						dayString = "Tuesday";
						break;
						case 2:
						dayString = "Wednesday";
						break;
						case 3:
						dayString = "Thursday";
						break;
						case 4:
						dayString = "Friday";
						break;
						case 5:
						dayString = "Saturday";
						break;
						case 6:
						dayString = "Sunday";
						break;
						default:
						dayString = "error\n";
						break;
					}
					
					// email user whose reservation is being overwritten
					
					printf("%s%s%s%d%s%s%s%d%s%s\n", "Email to ", users[canceledUserIndex].email, ": Your reservation at room #", users[canceledUserIndex].roomRequested, " for ", dayString, " at ", time, timeString, " has been canceled.");
					
					//users[canceledUserIndex].hoursRequested--;
					
				}
				studyRooms[count].seats[user->dayRequested][j][k] = user->userID;
			}
			else if (user->cancel == 1)
			{
				studyRooms[count].seats[user->dayRequested][j][k] = 0;
			}
		}
	}

	return NULL;
}


void *schedule (void *arg, int count)
{
	User *user = arg;

	if (user->cancel == 0)
	{
		int indexArray[3] = {-1, -1, -1}; // stores the indexes of the consecutive userID arrays 
		int i = 0;
		int j = 0;
		int k = 0;
		int roomNeeded = 1;

		while (roomNeeded == 1 && j < studyRooms[count].seating)
		{

			if (studyRooms[count].seats[user->dayRequested][user->timeRequested][j] == 0)
			{
				// searches array of userIDs for the first blank spot it finds. If one is found, the others are ignored.
				indexArray[0] = j;	
			}

			if (studyRooms[count].seats[user->dayRequested][user->timeRequested+1][j] == 0)
			{
				indexArray[1] = j;			
			}

			if (studyRooms[count].seats[user->dayRequested][user->timeRequested+2][j] == 0)
			{
				indexArray[2] = j;
			}

			roomNeeded = 0;

			for (k = 0; k < user->hoursRequested; k++)
			{
				if (indexArray[k] == -1)
				{
					// if at any point, a -1 is found in the array, the whole reservation is invalid.
					// try another room
					roomNeeded = 1;
					j++;
					k = user->hoursRequested;
				}
			}

		}

		// user's desired room is filled -- find substitute room?
		if (roomNeeded == 1)
		{
			if (user->sub == 0)
			{				
				return NULL;
			}
			else if (user->sub == 1)
			{
				printf("%s \n", "sub routine");

				i = 0;
				j = 0;
				k = 0;

				while (roomNeeded == 1 && j < studyRooms[i].seating && i < ROOMS)
				{
		
					if (studyRooms[i].seats[user->dayRequested][user->timeRequested][j] == 0)
					{
						// searches array of userIDs for the first blank spot it finds. If one is found, the others are ignored.
						indexArray[0] = j;	
					}
		
					if (studyRooms[i].seats[user->dayRequested][user->timeRequested+1][j] == 0)
					{
						indexArray[1] = j;			
					}
		
					if (studyRooms[i].seats[user->dayRequested][user->timeRequested+2][j] == 0)
					{
						indexArray[2] = j;
					}
		
					roomNeeded = 0;
		
					for (k = 0; k < user->hoursRequested; k++)
					{
						if (indexArray[k] == -1)
						{
							// if at any point, a -1 is found in the array, the whole reservation is invalid.
							// try another room
							roomNeeded = 1;

							j++;

							if (j >= studyRooms[i].seating)
							{
								i++;
								j = 0;
							}

							k = user->hoursRequested;
						}
					}
		
				}
			
				return NULL;
			}
			else
			{
				return NULL;
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
	}
	else if (user->cancel == 1)
	{
		// cancel user's reservation
		int cancelSuccess = 0;
		
		// this block assumes that a user can reserve only 1 room for 1 time each day. It searches the entire room and day for occurrences of the canceled userID.
		int k;
		for (k = 0; k < HOURS; k++)
		{
			int j;
			for (j = 0; j < studyRooms[count].seating; j++)
			{
				if (studyRooms[count].seats[user->dayRequested][j][k] == user->userID)
				{
					studyRooms[count].seats[user->dayRequested][j][k] = 0;
					cancelSuccess = 1;
					//printf("%s %d %s %d %s %d %s %d \n", "CANCELED: room # ", studyRooms[count].roomNumber, "day", user->dayRequested, "hour", j, "slot", k);
				}
			}
		}
		
		if (cancelSuccess == 0)
		{
			// if no rooms using the userID were found, the user's cancel thread was called before its reservation thread,
			// or there is no reservation and the user is at fault for trying to cancel a nonexistent reservation.
			printf("%s \n", "Error: no reservation exists for that user at that time.");
		}
		
		// Went through list of userIDs for that user's supposed time slot and did not find their userID.
		// User must not have made a reservation in the first place.

		return NULL;
		
	}

	return 0;
}

int filter (void *arg)
{
	User *user = arg; 

	if (user->priority < 0 || user->priority > 2)
 	{
 		return 0; // invalid priority value
 	}

	// Error check for invalid day, time, hours
 	if (user->dayRequested > DAYS-1 || user->dayRequested < 0)
 	{
 		// Day falls outside of 0-30 range
 		return 0;
 	}
 	
 	// Check for invalid hours based on day:
 	// If the user types in Day #1, 8 am, it will be parsed here as [0][0]
 	// dayIndex = day - 1, hourIndex = hour - 8
	
 	int incomingDay = user->dayRequested % 7;
 	
 	if (incomingDay == 0 || incomingDay == 1 || incomingDay == 2 || incomingDay == 3)
 	{
 		// Monday, Tuesday, Wednesday or Thursday
 		if (user->timeRequested > HOURS-1-(user->hoursRequested) || user->timeRequested < 0)
 		{
 			// Hour falls outside of 8 am to 12 am range
 			// 8 9 10 11 12 1 2 3 4 5 6 7 8 9 10 11
 			
 			return 0;
 		}
 	}
 	else if (incomingDay == 4)
 	{
 		// Friday: 8 am to 6 pm
 		if (user->timeRequested > HOURS-6-(user->hoursRequested) || user->timeRequested < 0)
 		{
 			// 8 9 10 11 12 1 2 3 4 5 (not 6 7 8 9 10 11)
 			return 0;
 		}
 	}
 	else if (incomingDay == 5)
 	{
 		// Saturday: 10 am to 7 pm
 		if (user->timeRequested > (HOURS-5-(user->hoursRequested)) || user->timeRequested < 2)
 		{
 			// (not 8 9) 10 11 12 1 2 3 4 5 6 (not 7 8 9 10 11)
 			return 0;
 		}
 	}
 	else if (incomingDay == 6)
 	{
 		// Sunday: 11 am to 11 pm
 		if (user->timeRequested > (HOURS-2-(user->hoursRequested)) || user->timeRequested < 3)
 		{
 			// (not 8 9 10) 11 12 1 2 3 4 5 6 7 8 9 10 (not 11)
 			return 0;
 		}
 	}
 	else
 	{
 		// some weird number that doesn't work for anything
 		return 0;
 	}
 	
 	// check invalid hours
 	if ((user->hoursRequested > 3 && user->hoursRequested < 1) && (user->priority != 0)) // admin can reserve as many hours as they want, provided it fits within the hour range corresponding to the day
 	{
 		// Asking for hours less than 1 or more than 3
 		return 0;
 	}

 	if (user->sub < 0 || user->sub > 1)
 	{
 		return 0; // invalid sub value
 	}

 	return 1;
}

void *calendarize (void *arg)
{
 	// do things here
 	User *user = arg; 
 	
 	if (filter(user) == 0)
 	{
 		return NULL;
 	}

	int count;
	for (count = 0; count < ROOMS; count++)
	{
		if (studyRooms[count].roomNumber == user->roomRequested)
		{
			if (user->priority == 0)
			{
				pthread_mutex_lock (&(studyRooms[count].available));
				
				pthread_mutex_lock (&(studyRooms[count].adminThreadcountLock));
				studyRooms[count].admin++;
				pthread_mutex_unlock (&(studyRooms[count].adminThreadcountLock));
			
				
				// call administrator function
				adminSchedule(user, count);
			
				pthread_mutex_lock (&(studyRooms[count].adminThreadcountLock));
				studyRooms[count].admin--;
				pthread_mutex_unlock (&(studyRooms[count].adminThreadcountLock));

				// ---- new, bug prone
				if (studyRooms[count].admin <= 0)
				{
					pthread_cond_signal(&(studyRooms[count].adminLock));
				}
				// ---- new, bug prone

				pthread_mutex_unlock (&(studyRooms[count].available));
			}
			if (user->priority == 1)
			{
				pthread_mutex_lock (&(studyRooms[count].available));
				
				pthread_mutex_lock (&(studyRooms[count].studentThreadcountLock));
				studyRooms[count].students++;
				pthread_mutex_unlock (&(studyRooms[count].studentThreadcountLock));

				// ----- new, bug prone
				while (studyRooms[count].admin > 0)
				{	
					pthread_cond_wait(&(studyRooms[count].adminLock), &(studyRooms[count].available));
				}
				pthread_cond_signal(&(studyRooms[count].adminLock));		
				// -------- new, bug prone
			
				schedule (user, count);
			
				pthread_mutex_lock (&(studyRooms[count].studentThreadcountLock));
				studyRooms[count].students--;
				pthread_mutex_unlock (&(studyRooms[count].studentThreadcountLock));
			
				if (studyRooms[count].students <= 0)
				{
					pthread_cond_signal(&(studyRooms[count].high));
				}
			
				pthread_mutex_unlock (&(studyRooms[count].available));
			}
			if (user->priority == 2)
			{
				pthread_mutex_lock (&(studyRooms[count].available));
				
				pthread_mutex_lock (&(studyRooms[count].facultyThreadcountLock));
				studyRooms[count].faculty++;
				pthread_mutex_unlock (&(studyRooms[count].facultyThreadcountLock));

				// ---- new
				while (studyRooms[count].admin > 0)
				{	
					pthread_cond_wait(&(studyRooms[count].adminLock), &(studyRooms[count].available));
				}
				pthread_cond_signal(&(studyRooms[count].adminLock));	
				// --- new
			
				while (studyRooms[count].students > 0)
				{	
					pthread_cond_wait(&(studyRooms[count].high), &(studyRooms[count].available));
				}
				pthread_cond_signal(&(studyRooms[count].high));
			
				schedule (user, count);
			
				pthread_mutex_lock (&(studyRooms[count].facultyThreadcountLock));
				studyRooms[count].faculty--;
				pthread_mutex_unlock (&(studyRooms[count].facultyThreadcountLock));
			
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
		
		pthread_mutex_init ( &(studyRooms[i].available), NULL);
		
		pthread_mutex_init ( &(studyRooms[i].adminThreadcountLock), NULL);
		pthread_mutex_init ( &(studyRooms[i].studentThreadcountLock), NULL);
		pthread_mutex_init ( &(studyRooms[i].facultyThreadcountLock), NULL);
		
		pthread_cond_init ( &(studyRooms[i].high), NULL);
		pthread_cond_init ( &(studyRooms[i].adminLock), NULL);
		
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
	
	i = 0;
	for (i = 0; i < USERS; i++)
	{
		users[i].userID = -1;
	}
	
	FILE *textFile;
	textFile = fopen("users.txt", "r");
	
	if (textFile == NULL)
	{
		printf("%s \n", "error");
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
		if (users[i].userID != -1)
		{
			pthread_create(&threads[i], NULL, calendarize, (void *) &users[i]);
		}
	}
	
	for (i = 0; i < USERS; i++)
	{
		if (users[i].userID != -1)
		{
			pthread_join(threads[i], NULL);
		}
	}	
	
	printf("%s \n", "THREADS JOINED");

	// "canceled" threads that weren't canceled yet:
	// FOUND IT: reservations and cancellations are separate threads, and sometimes the cancellation thread executes before the reservation executes.

	
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
