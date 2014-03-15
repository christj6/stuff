
#include <iostream>
#include <fstream>
#include <stdio.h>      /* printf, scanf, puts, NULL */
#include <stdlib.h> 
#include <time.h>       /* time */

#define mines 3
#define length 4


using namespace std;

//int mines = 11;
int game = 1;

int board[length][length];

int mineXcoord[mines];
int mineYcoord[mines];

int mineHit(int x, int y)
{
	if (x > -1 && x < length)
	{
		if (y > -1 && y < length)
		{
			for (int i = 0; i < mines; i++)
			{
				if (mineXcoord[i] == x)
				{
					if (mineYcoord[i] == y)
					{
						return 1;
					}
				}
			}
		}
	}

	return 0;
}

// checks if it's possible to click another square without a mine being found there
int winCheck()
{
	int untouchedSpots = 0;
	int untouchedMines = 0;

	for (int i = 0; i < length; i++)
	{
		for (int j = 0; j < length; j++)
		{
			if (board[i][j] == -1)
			{
				untouchedSpots++;

				if (mineHit(i, j) == 1)
				{
					untouchedMines++;
				}
			}
		}
	}

	if (untouchedMines == untouchedSpots)
	{
		return 1;
	}

	return 0;
}


int search(int x, int y)
{
	int surroundingHits = 0;

	if (mineHit(x-1, y) == 1)
	{
		surroundingHits++;
	}
	if (mineHit(x-1, y-1) == 1)
	{
		surroundingHits++;
	}
	if (mineHit(x, y-1) == 1)
	{
		surroundingHits++;
	}
	if (mineHit(x+1, y-1) == 1)
	{
		surroundingHits++;
	}
	if (mineHit(x+1, y) == 1)
	{
		surroundingHits++;
	}
	if (mineHit(x+1, y+1) == 1)
	{
		surroundingHits++;
	}
	if (mineHit(x, y+1) == 1)
	{
		surroundingHits++;
	}
	if (mineHit(x-1, y+1) == 1)
	{
		surroundingHits++;
	}

	return surroundingHits;
}

void printBoard()
{
	for (int i = 0; i < length; i++)
	{
		for (int j = 0; j < length; j++)
		{
			//board[i][j] = 0;
			if (board[i][j] == -1)
			{
				cout << "-" << " ";
			}
			else
			{
				cout << board[i][j] << " ";
			}
		}
		cout << endl;
	}
}


int main()
{
	srand (time(NULL));

	// initialize board
	for (int i = 0; i < length; i++)
	{
		for (int j = 0; j < length; j++)
		{
			board[i][j] = -1;
		}
	}

	for (int i = 0; i < mines; i++)
	{
		mineXcoord[i] = rand() % length;
		mineYcoord[i] = rand() % length;    

		cout << "x: " << mineXcoord[i] << ", y: " << mineYcoord[i] << endl;
	}

	while (game == 1)
	{
		if (winCheck() == 1)
		{
			game = 0;
			cout << "You win. " << endl;
		}
		else
		{
			int x = 0;
			int y = 0;

			cout << "Please enter x: ";
			cin >> x;
			cout << "Please enter y: ";
			cin >> y;


			if (mineHit(x, y) == 1)
			{
				game = 0;
				cout << "Mine hit, you lose. " << endl;
			}
			else
			{
				board[x][y] = search(x, y);

				printBoard();
			}
		}

	}

	

	return 0;
}
