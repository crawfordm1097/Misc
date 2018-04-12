#ifndef PEASANTS_H
#define PEASANTS_H

#include <iostream>
#include "./Card.h"
#include "./Player.h"
#include "./Piece.h"
#include "./Point.h"
#include "./Enumerations.h"

//Global variables
char board[8][8][2]; //Spaces that are not occupied will be ' '; [] is vertical [][] horizontal
bool empowered;
Player* players[2] = { NULL, NULL };
Piece** pieces[2] = {NULL, NULL};

//Functions
void printBoard();
void previewBoard(Point* move);
void createBoard();
Piece* findPiece(Point spot, bool isHuman);
void movePiece(Piece* piece, Point spot);
bool spotOccupied(Point spot);
string toLower(string s1);
Point boardSpotToPoint(string s1);
void switchCard(Piece* piece1, Piece* piece2);
int empowerCard(Piece* piece);
void reviveCard(Piece* res);

#endif