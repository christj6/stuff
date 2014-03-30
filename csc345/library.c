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

// Format of the input file (for simulation purposes)
// example line: 1 alice@gmail.com 109 0 1 3 0 1 0
// userID, email, room requested, day requested, time requested, # of hours requested, willingness to sub, priority, whether they are making or canceling a reservation
// day can go from 0 (day 1) to 29 (day 30)
// time can go from 0 (8 am) to 15 (11 pm)
// hours can be 1, 2 or 3

// Reminder:  when using gdb,
// compile: gcc -g -pthread -o lib.o library.c
// start gdb: gdb lib.o
// set breakpoint at line #220: b 220
// run, step
// look at current value/state of x throughout various steps: print x

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
	/* First index refers to the day of the month (this simulation assumes a 30 day month),
		while the second index refers to the hour of the day.
		The third index refers to an array of integers (user IDs) stored to determine who is reserved to the room at a given time.
		Each of those indexes in that array function like a "chair" or "seat" in the room. The system allows the user to grab the 
		first available index in that room. If none are available, the user is provided another room of similar size. 
	*/
	
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

// This function modifies the input string so it follows the form: " for" + day + " at " + time
// for example: " for Monday at 8:00AM" 
void dayAndTime (int day, int time, char *incomingString)
{
	char *dayAndTimeString = incomingString;

	char *timeString;
	char *dayString;
	
	if (time >= 12)
	{
		if (time != 12)
		{
			time -= 12; // allow 1:00 PM instead of 13:00 PM, while also preventing 0:00 PM
		}
		timeString = ":00 PM";
	}
	else
	{
		timeString = ":00 AM";
	}
	
	switch (day)
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

	// Constructing the string
	strcpy (dayAndTimeString, " for ");
	strcat (dayAndTimeString, dayString);
	strcat (dayAndTimeString, " at ");
	char string[10];
	sprintf(string, "%d", time);
	strcat (dayAndTimeString, string);
	strcat (dayAndTimeString, timeString);
}

// The administrator doesn't reserve individual seats in a room, but instead reserves the entire room for a block of time.
// This is for special events, or when a room's under construction, etc.
// An admin can also clear out an entire room, whether or not he/she initially registered for it, unlike students and faculty.
void *adminSchedule (void *arg, int count)
{
	User *user = arg;

	int j;
	int k;
	int i;

	for (j = user->timeRequested; j < (user->timeRequested + user->hoursRequested); j++)
	{
		for (k = 0; k < studyRooms[count].seating; k++)
		{
			// 
			if (studyRooms[count].seats[user->dayRequested][j][k] != 0)
			{
				int canceledUserIndex = -1;

				for (i = 0; i < USERS; i++)
				{
					if (users[i].userID == studyRooms[count].seats[user->dayRequested][j][k])
					{
						canceledUserIndex = i;
					}
				}
				
				// email user whose reservation is being overwritten
				char timeStamp[50];
				dayAndTime(user->dayRequested % 7, j + 8, timeStamp);
				printf("%s%s%s%d%s%s\n", "Email to ", users[canceledUserIndex].email, ": Your reservation at room #", users[canceledUserIndex].roomRequested, timeStamp, " was cancelled.");
			}

			if (user->cancel == 0)
			{
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

// This function ______
void *schedule (void *arg, int count)
{

	User *user = arg;
	
	if (user->cancel == 0)
	{
		int indexArray[3] = {-1, -1, -1}; // stores the indexes of the consecutive userID arrays 
		int i = 0;
		int j = 0;
		int k = 0;
		int roomNeeded = 0;

		// User can reserve only 1 room for 1 period of time a day.
		for (i = 0; i < ROOMS; i++)
		{
			for (j = 0; j < HOURS; j++)
			{
				for (k = 0; k < studyRooms[count].seating; k++)
				{
					if (studyRooms[i].seats[user->dayRequested][j][k] == user->userID)
					{
						char timeStamp[50];
						dayAndTime(user->dayRequested % 7, j + 8, timeStamp);
						printf("%s%s%s%d%s%s\n", "Email to ", user->email, ": You've already reserved room #", studyRooms[i].roomNumber, timeStamp, ".");
						return NULL;
					}
				}
			}
		}

		// Populate indexArray with "chair" slots for the requested room at the corresponding times.
		for (i = 0; i < user->hoursRequested; i++)
		{
			for (j = 0; j < studyRooms[count].seating; j++)
			{
				if (studyRooms[count].seats[user->dayRequested][user->timeRequested + i][j] == 0)
				{
					// searches array of userIDs for the first blank spot it finds. If one is found, the others are ignored.
					indexArray[i] = j;	
					j = studyRooms[count].seating;
				}
			}
		}

		// Scan through the indexArray to find out if the room is full at any point during the user's reservation.
		// If it is, the user needs to be moved to a different room entirely.
		// Since indexArray is always initialized to be filled with negative 1s, finding a single -1 at any point is enough to trigger the substitute routine.
		for (i = 0; i < user->hoursRequested; i++)
		{
			//printf("%s %s %d \n", user->email, ": ", indexArray[i]);
			if (indexArray[i] == -1)
			{
				roomNeeded = 1;
			}
		}

		// user's desired room is filled -- find substitute room?
		if (roomNeeded == 1)
		{
			if (user->sub == 0)
			{				
				// No, the user insists on having that particular room at that particular time.
				printf("%s \n", "Sorry, no reservations are available at that time. Please try again later. ");
				return NULL;
			}
			else if (user->sub == 1)
			{
				//printf("%s \n", "sub routine");

				indexArray[0] = -1;
				indexArray[1] = -1;
				indexArray[2] = -1;

				i = 0;
				j = 0;
				k = 0;

				for (k = 0; k < ROOMS; k++)
				{
					if (k != count && studyRooms[k].seating >= studyRooms[count].seating)
					{
						roomNeeded = 0;

						for (i = 0; i < user->hoursRequested; i++)
						{
							for (j = 0; j < studyRooms[k].seating; j++)
							{
								if (studyRooms[k].seats[user->dayRequested][user->timeRequested + i][j] == 0)
								{
									// searches array of userIDs for the first blank spot it finds. If one is found, the others are ignored.
									indexArray[i] = j;	
									j = studyRooms[k].seating;
								}
							}
						}

						for (i = 0; i < user->hoursRequested; i++)
						{
							//printf("%s %s %d \n", user->email, ": ", indexArray[i]);
							if (indexArray[i] == -1)
							{
								roomNeeded = 1;
							}
						}

						if (roomNeeded == 0)
						{
							count = k;
							k = ROOMS;
						}
					}
				}

				for (i = 0; i < user->hoursRequested; i++)
				{
					if (indexArray[i] == -1)
					{
						// If the user still cannot find a room at this time, they will need to try again later. Maybe some other users will cancel.
						printf("%s \n", "Sorry, no reservations are available at that time. Please try again later. ");
						return NULL;
					}
				}


			}
			else
			{
				return NULL;
			}
		}
		
		for (i = 0; i < user->hoursRequested; i++)
		{
			if (studyRooms[count].seats[user->dayRequested][user->timeRequested + i][indexArray[i]] == 0)
			{
				studyRooms[count].seats[user->dayRequested][user->timeRequested + i][indexArray[i]] = user->userID;
			}
		}

		// email user about their reservation
		char timeStamp[50];
		dayAndTime(user->dayRequested % 7, user->timeRequested + 8, timeStamp);	
		printf("%s%s%s%d%s%s\n", "Email to ", user->email, ": Your reservation at room #", studyRooms[count].roomNumber, timeStamp, " was successful.");

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

					char timeStamp[50];
					dayAndTime(user->dayRequested % 7, j - user->hoursRequested + 8, timeStamp);	
					printf("%s%s%s%d%s%s\n", "Email to ", user->email, ": Your reservation at room #", studyRooms[count].roomNumber, timeStamp, " was cancelled.");
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

// This function takes a user and makes sure that their request is valid. This involves checking all the parameters to ensure they fall in valid ranges.
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
 	// If the user types in Day #1, 8 am, it will be parsed here as Day 0, time 0
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
 		// Some weird number that doesn't work for anything. 
 		return 0;
 	}
 	
 	// check invalid hours
 	if ((user->hoursRequested > 3 && user->hoursRequested < 1) && (user->priority != 0)) // admin can reserve as many hours as they want, provided it fits within the hour range corresponding to the day
 	{
 		// Asking for hours less than 1 or more than 3?
 		return 0;
 	}

 	// Either the user is willing to try another room, or the user isn't. 
 	if (user->sub < 0 || user->sub > 1)
 	{
 		return 0; // invalid sub value
 	}

 	return 1;
}

void *calendarize (void *arg)
{
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
				
				
				pthread_mutex_lock (&(studyRooms[count].adminThreadcountLock));
				studyRooms[count].admin++;
				pthread_mutex_unlock (&(studyRooms[count].adminThreadcountLock));
			
				pthread_mutex_lock (&(studyRooms[count].available));
				// call administrator function
				//printf(" %s \n", user->email);
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
				
				
				pthread_mutex_lock (&(studyRooms[count].studentThreadcountLock));
				studyRooms[count].students++;
				pthread_mutex_unlock (&(studyRooms[count].studentThreadcountLock));

				pthread_mutex_lock (&(studyRooms[count].available));
				// ----- new, bug prone
				while (studyRooms[count].admin > 0)
				{	
					pthread_cond_wait(&(studyRooms[count].adminLock), &(studyRooms[count].available));
				}
				pthread_cond_signal(&(studyRooms[count].adminLock));		
				// -------- new, bug prone
			
				
				//printf(" %s \n", user->email);
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
				
				
				pthread_mutex_lock (&(studyRooms[count].facultyThreadcountLock));
				studyRooms[count].faculty++;
				pthread_mutex_unlock (&(studyRooms[count].facultyThreadcountLock));

				pthread_mutex_lock (&(studyRooms[count].available));

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


				
				//printf(" %s \n", user->email);
				schedule (user, count);
				pthread_mutex_unlock (&(studyRooms[count].available));

			
				pthread_mutex_lock (&(studyRooms[count].facultyThreadcountLock));
				studyRooms[count].faculty--;
				pthread_mutex_unlock (&(studyRooms[count].facultyThreadcountLock));
			
				
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
						printf("%s%d%s%d%s%d%s%d%s%d\n", "Room # ", studyRooms[i].roomNumber, ", Day ", a, " Hour ", b, " Room Slot ", c, " has user # ", studyRooms[i].seats[a][b][c]);
						
					}
				}
			}
		}
	}

	pthread_exit(NULL);

	return 0;
}
