#include <iostream>
#include <fstream>
#include <stdio.h>      /* printf, scanf, puts, NULL */
#include <stdlib.h> 
#include <time.h>       /* time */

#include "dust.h"

#define MINES 100 // max number of mines
#define LENGTH 10 // max length of board

// g++ dust.cpp -o dust.o
// ./dust.o

using namespace std;

//int MINES = 11;
int game = 1;

int workingLength = 4;
int workingMines = 3;

int board[LENGTH][LENGTH];

int mineXcoord[MINES];
int mineYcoord[MINES];

int mineHit(int x, int y)
{
	if (x > -1 && x < workingLength)
	{
		if (y > -1 && y < workingLength)
		{
			for (int i = 0; i < workingMines; i++)
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

int returnValue(int x, int y)
{
	if (x > -1 && x < workingLength)
	{
		if (y > -1 && y < workingLength)
		{
			return board[x][y];
		}
	}

	return -10;
}

// checks if it's possible to click another square without a mine being found there
int winCheck()
{
	int untouchedSpots = 0;
	int untouchedMines = 0;

	for (int i = 0; i < workingLength; i++)
	{
		for (int j = 0; j < workingLength; j++)
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

void exploreLeft(int x, int y)
{
	if (search(x-1,y) == 0 && returnValue(x-1, y) == -1)
	{
		board[x-1][y] = 0;
		exploreLeft(x-1, y);
		exploreUp(x-1, y);
		exploreDown(x-1, y);
	}
}

void exploreUp(int x, int y)
{
	if (search(x, y-1) == 0 && returnValue(x, y-1) == -1)
	{
		board[x][y-1] = 0;
		exploreUp(x, y-1);
		exploreLeft(x, y-1);
		exploreRight(x, y-1);
	}
}

void exploreRight(int x, int y)
{
	if (search(x+1, y) == 0 && returnValue(x+1, y) == -1)
	{
		board[x+1][y] = 0;
		exploreRight(x+1, y);
		exploreUp(x+1, y);
		exploreDown(x+1, y);
	}
}

void exploreDown(int x, int y)
{
	if (search(x, y+1) == 0 && returnValue(x, y+1) == -1)
	{
		board[x][y+1] = 0;
		exploreDown(x, y+1);
		exploreLeft(x, y+1);
		exploreRight(x, y+1);
	}
}

void printBoard()
{
	for (int i = 0; i < workingLength; i++)
	{
		for (int j = 0; j < workingLength; j++)
		{
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

void generateMines()
{
	for (int i = 0; i < workingMines; i++)
	{
		mineXcoord[i] = rand() % workingLength;
		mineYcoord[i] = rand() % workingLength;    

		//mineXcoord[i] = -500; // used to eliminate MINES from playing field; makes it easier to debug recursive zero display functions
		//mineYcoord[i] = -500;

		//cout << "x: " << mineXcoord[i] << ", y: " << mineYcoord[i] << endl;
	}
}


int main()
{
	srand (time(NULL));

	cout << "Please enter length of board (up to 10): ";
	cin >> workingLength;
	cout << "Please enter number of mines (up to 100): ";
	cin >> workingMines;

	// initialize board
	for (int i = 0; i < workingLength; i++)
	{
		for (int j = 0; j < workingLength; j++)
		{
			board[i][j] = -1;
		}
	}

	generateMines();

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
				
				// when a user hits a spot with 0 MINES
				// the surrounding 0-spots will be revealed, like in normal minesweeper
				if (board[x][y] == 0)
				{
					exploreLeft(x, y);
					exploreUp(x, y);
					exploreRight(x, y);
					exploreDown(x, y);
				}
				


				printBoard();
			}
		}

	}

	

	return 0;
}

