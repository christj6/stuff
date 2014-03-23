#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
 
#define NUM_THREADS     5

pthread_mutex_t mutex;
pthread_cond_t cond;

int current = 4;

 
void *TaskCode(void *argument)
{
   int tid;
 
   tid = *((int *) argument);
   
   // ------------------------
   
   if (tid == 2)
   {
   		int x;
   		for (x = 0; x < 10000; x++){	}
   }
   
   if (tid == 1)
   {
   		int x;
   		for (x = 0; x < 100000; x++){	}
   }
   
   if (tid == 4)
   {
   		int x;
   		for (x = 0; x < 1000000; x++){	}
   }
   
   if (tid == 0)
   {
   		int x;
   		for (x = 0; x < 10000000; x++){	}
   }
   
   if (tid == 3)
   {
   		int x;
   		for (x = 0; x < 100000000; x++){	}
   }
   
   pthread_mutex_lock (&mutex);
   printf("thread %d\n", tid);
   pthread_mutex_unlock (&mutex);
   

   /*
   pthread_mutex_lock (&mutex);
   //while (?????)
   //{
   		//pthread_cond_wait(&cond, &mutex);
   //}
   //printf("thread %d\n", tid);
   pthread_mutex_unlock (&mutex);
   
   pthread_mutex_lock (&mutex);
   //if (?????)
   //{
   		//pthread_cond_signal(&cond);
   //}
   //printf("thread %d\n", tid);
   pthread_mutex_unlock (&mutex);
   */
   
   
 
   /* optionally: insert more useful stuff here */
 
   return NULL;
}
 
int main(void)
{
   pthread_t threads[NUM_THREADS];
   int thread_args[NUM_THREADS];
   int rc, i;
   
   pthread_mutex_init (&mutex, NULL);
 
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
