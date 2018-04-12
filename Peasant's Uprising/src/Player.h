#ifndef PLAYER_H
#define PLAYER_H

#include "Piece.h"
#include "Card.h"

class Player {
private:
	int score;
	Piece *playerPieces[16];
	Card *cards[3];

public:
	Player(bool isHuman);
	int getScore();
	void setScore(int scoreDiff);
	void printCards();
	bool playCard(int index);
	Card** getCards();
	Piece** getPieces();
};

#endif