#ifndef DUST_H_   /* Include guard */
#define DUST_H_

//int game;
//int board[int][int];
//int mineXcoord[int];
//int mineYcoord[int];
int mineHit(int, int);
int returnValue(int, int);
int winCheck();
int search(int, int);
void exploreLeft(int, int);
void exploreUp(int, int);
void exploreRight(int, int);
void exploreDown(int, int);
void printBoard();
//int main(int);

#endif
