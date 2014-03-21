// library.c
// Jack Christiansen
// CSC 345 - Operating Systems

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#include <pthread.h> // definitely needed
#include <assert.h> // not sure if needed

typedef struct
{
    int roomNumber; /* 109, 202, 301, etc. */
    int seating; /* number of people who can fit in the room */
    int taken; /* Will be either 0 to 1 */
    
    /* For specialPurpose: 0 = none, 1 = storage, 2 = group listening,
     3 = group viewing, 4 = graduate students */
    int specialPurpose;
} Room;

typedef struct
{
	int userID;
	char *email;
	int roomRequested;
	int hoursRequested;
	int sub; /* will be 0 or 1 */
	int priority; /* 0 = admin, 1 = student, 2 = faculty */
} User;

int main()
{
    Room studyRooms[26];
    int rmNumbers[26] = {109, 110, 111, 202, 205, 220, 224, 225, 226, 228, 301, 308, 309, 310, 311, 315, 316, 317, 319, 404, 406, 411, 412, 413, 414, 415};
    int stNumbers [26] = {4, 6, 4, 6, 6, 12, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 8, 6, 6, 12, 6, 6};
    
    for (int i = 0; i < 26; i++)
    {
        studyRooms[i].roomNumber = rmNumbers[i];
        studyRooms[i].seating = stNumbers[i];
        studyRooms[i].taken = 0;
    }
    
    for (int i = 0; i < 26; i++)
    {
        //printf("%s", "room: ");
        //printf("%d\n", studyRooms[i].roomNumber);
    }
    
    // users: User ID, email, room requested, hours requested, willing to sub
    User users[10];
    
    int idNumbers[4] = {2, 3, 4, 5};
    char *emails[4] = {"fef", "eeb", "sef", "qweqw"};
    
    for (int i = 0; i < 4; i++)
    {
        users[i].userID = idNumbers[i];
        users[i].email = emails[i];
        
        printf("%s", "room: ");
        printf("%d\n", users[i].userID);
        printf("%s", "email: ");
        printf("%s\n", users[i].email);
    }
    
    return 0;
}
