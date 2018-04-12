#ifndef PIECE_H
#define PIECE_H

#include "Point.h"	
#include <string>
#include "Enumerations.h"

using namespace std;

class Piece {

private:
	Point pos;
	bool knightCheck(Piece ***playerPieces, Point tip);
	int score;
public:

	char symb;
	string name;
	Type bit;
	bool isHuman;

	Piece(Type type, int x, int y, bool isHuman);
	void move(Piece ***playerPieces, int x, int y);
	Point* determineMoveSet(Piece ***playerPieces);
	Point getPos();
	bool danger(Piece ***playerPieces, Point tip);
	bool discoverCheck(Piece ***playerPieces, Point tryMove);
	int getScore();
};

#endif