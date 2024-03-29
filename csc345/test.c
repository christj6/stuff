#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
 
#define NUM_THREADS     100

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cond = PTHREAD_COND_INITIALIZER;

int highThreads = 0;
int lowThreads = 0;

 
void *TaskCode(void *argument)
{
   int tid;
 
   tid = *((int *) argument);
   
   // ------------------------
   
   
   if (tid % 2 != 0)
   {
   		pthread_mutex_lock (&mutex);
   		//printf("odd thread %d LOCK\n", tid);
		lowThreads++;
		//printf("odd thread %d UNLOCK\n", tid);
		pthread_mutex_unlock (&mutex);
   }   
   else if (tid % 2 == 0)
   {
   		pthread_mutex_lock (&mutex);
   		//printf("even thread %d LOCK\n", tid);
   		highThreads++;
   		//printf("even thread %d UNLOCK\n", tid);
   		pthread_mutex_unlock (&mutex);
   }
   
   
   
   //printf("thread %d\n", tid);
   
   if (tid % 2 == 0)
   {
   		//return NULL;
   }
   
   
	if (tid % 2 == 0) // 0 2 4
   {
		pthread_mutex_lock (&mutex);

		printf("thread %d \n", tid);
		highThreads--;
		if (highThreads <= 0)
		{
			pthread_cond_signal(&cond);
		}

		pthread_mutex_unlock (&mutex);
   }

	if (tid % 2 != 0) // 1 3 5
   {
		pthread_mutex_lock (&mutex);

		if (highThreads > 0)
		{	
			pthread_cond_wait(&cond, &mutex);
		}
		pthread_cond_signal(&cond);
		printf("\t thread %d \n", tid);
	
		lowThreads--;

		pthread_mutex_unlock (&mutex);
   }
   
   //printf("hi %d \n", highThreads);
   //printf("lo %d \n", lowThreads);
   
   
 
   /* optionally: insert more useful stuff here */
 
   return NULL;
}
 
int main(void)
{
   pthread_t threads[NUM_THREADS];
   int thread_args[NUM_THREADS];
   int rc, i;
   
   //pthread_mutex_init (&mutex, NULL);
 
   // create all threads one by one
   for (i=0; i<NUM_THREADS; ++i) {
      thread_args[i] = i;
      //printf("In main: creating thread %d\n", i);
      rc = pthread_create(&threads[i], NULL, TaskCode, (void *) &thread_args[i]);                          
      assert(0 == rc);
   }
 
   // wait for each thread to complete
   for (i=0; i<NUM_THREADS; ++i) {
      // block until thread i completes
      rc = pthread_join(threads[i], NULL);
      //printf("In main: thread %d is complete\n", i);
      assert(0 == rc);
   }
 
   printf("In main: All threads completed successfully\n");
   exit(EXIT_SUCCESS);
}
