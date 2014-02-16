// Shell.c
// Jack Christiansen
// CSC 345 - Operating Systems

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>

#define COMMAND_LIMIT 41 // COMMAND_LIMIT = MAX_SIZE/2 + 1

char *args[COMMAND_LIMIT];
int should_run = 1;
char input[50];
int ampersand = 0; // & in command determines whether process is ran concurrently or parent waits for child to terminate
int n = 0; // used to increment through args array
int i = 0; // used for loops

char *history[10];
int historyCounter = 0;
int bump = 0;


void parse()
{
    fflush(stdout);
    printf("osh>");
    
    gets(input);
    int inputLength = strlen(input);

    if (input[strlen(input) - 1] == '&')
    {
        //checks if last character is &
        ampersand = 1;
        input[strlen(input) - 1] = NULL; //replaces & with 'end of string' character
    }

    //exclude "history" "!!" and "!n" commands from history log
    if ((strcmp(input, "history") != 0) && (input[0] != '!'))
    {
        if (historyCounter < 10)
        {
            history[historyCounter] = strdup(input);
            historyCounter++;
        }
        else
        {
            //shift over: 2 in 1's location, 3 in 2's, etc.
            int j;
            for (j = historyCounter-10; j < historyCounter-1; j++)
            {
                history[j%10] = history[(j+1)%10];
            }
            history[9] = strdup(input);
            
            bump++; //include that increment to get the "26 to 35" commands in there
        }
    }
    
    n = 0;
    args[n++] = strtok(input, " \t");
    while(((args[n] = strtok(NULL, " \t")) != NULL) && (n < COMMAND_LIMIT))
    {
        n++; //populates arguments array
    }
}

int main(void)
{
    while (should_run)
    {
        parse();
        
        int pid;
        
        if (strcmp(args[0], "exit") == 0)
        {
            should_run = 0; // terminates loop on next evaluation, user exits terminal
        }
        else if (strcmp(args[0], "history") == 0)
        {
            if (historyCounter > 0)
            {
                for (i = historyCounter-1; i >= historyCounter-10; --i)
                {
                    if (i >= 0) // prevents segmentation fault if the history contains too few items
                    {
                        printf("%d", (i+1)+bump);
                        printf("%s", " ");
                        printf("%s \n",  history[i%10]);
                    }
                }
            }
            else
            {
                printf("%s \n", "No history to display.");
            }
        }
        else
        {
            pid = fork();
            
            if (pid == -1)
            {
                //error
            }
            else if (pid == 0)
            {
                if (strcmp(args[0], "!!") == 0)
                {
                    if (historyCounter > 0)
                    {
                        // execute last command in history
                        
                        char *temp;
                        temp = strdup(history[(historyCounter-1)%10]);

                        if (temp[strlen(temp) - 1] == '&')
                        {
                            //checks if last character is &
                            ampersand = 1;
                            temp[strlen(temp) - 1] = NULL; //replaces & with 'end of string' character
                        }
                        
                        n = 0;
                        args[n++] = strtok(temp, " \t");
                        while(((args[n] = strtok(NULL, " \t")) != NULL) && (n < COMMAND_LIMIT))
                        {
                            n++;
                        }
                        execvp(args[0], args);
                        exit(0);
                    }
                    else
                    {
                        //no history to display
                        printf("%s \n", "No previous command to display.");
                        exit(0);
                    }
                }
                else if ((args[0][0] == '!') && (args[0][1] != '!'))
                {
                    // execute instruction indicated by "n" in !n
                    
                    args[0][0] = '0'; //replace ! with 0 so string can be interpreted as a number more easily ( 123 = 0123)
                    
                    int value = atoi(args[0]); // convert string to int
                    
                    if ((value > (historyCounter + bump - 10)) && (value < (historyCounter + bump + 1))) // check if value is within past history
                    {
                        char *temp;
                        
                        temp = strdup(history[value - 1 - bump]);
                        
                        if (temp[strlen(temp) - 1] == '&')
                        {
                            //checks if last character is &
                            ampersand = 1;
                            temp[strlen(temp) - 1] = NULL; //replaces & with 'end of string' character
                        }
                        
                        n = 0;
                        args[n++] = strtok(temp, " \t");
                        while(((args[n] = strtok(NULL, " \t")) != NULL) && (n < COMMAND_LIMIT))
                        {
                            n++; // populates args array
                        }
                        execvp(args[0], args);
                        //exit(0);
                    }
                    else
                    {
                        //value is outside bounds
                        printf("%s \n", "No such command exists in history.");
                        exit(0);
                    }
                }
                else
                {
                    execvp(args[0], args);
                    exit(0); // shouldn't be executed unless invalid text was entered
                }
            }
            else if (pid != 0)
            {
                if (ampersand == 1)
                {
                    //printf("%s \n", "ON");
                    //concurrency, allow child process to run in the background
                    sleep(1);

                    ampersand = 0;
                }
                else
                {
                    //printf("%s \n", "OFF");
                    // no concurrency - unless specified otherwise, parent waits for child to terminate before continuing.
                    wait(&pid);
                }
            }
        }
    }
    
    ampersand = 0; // in the event setting it to zero didn't work the first time
    
    return 0;
}


