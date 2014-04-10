
#include <time.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

// Compile using: gcc clock.c -o clock.o -lrt
// Run using: ./clock.o

int main()
{
	/*
	clock_t start;
	clock_t end;
	double time;
	
	start = clock();


	// do something here
	int i;
	for (i = 0; i < 100000000; i++)
	{
		
	}
	// stop doing something here

	end = clock();
	printf("%s %d \n", "clock ticks: ", end-start);

	time = ((double)(end-start))/CLOCKS_PER_SEC;
	printf("%s %f \n", "time is ", time);

	return 0;
	*/
	struct timespec start;
	struct timespec end;
	double time;

	if (clock_gettime (CLOCK_REALTIME, &start) == -1)
	{
		// error
	}

	// do something here
	int i;
	for (i = 0; i < 100000000; i++)
	{

	}	

	if (clock_gettime (CLOCK_REALTIME, &end) == -1)
	{
		// error
	}

	
	time = ((end.tv_sec - start.tv_sec) + (end.tv_nsec - start.tv_nsec));
	printf("%s %f \n", "time is ", time);

}
