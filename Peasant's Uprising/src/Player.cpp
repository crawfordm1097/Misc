/*
COP3503 Final project, Player.cpp
Purpose: Construct player and association of pieces and cards
@authors: Jack Coon and Mateo Canarte
*/

#include "Player.h"

/* Constructor for a Player. It initializes the pieces, cards, and score.
@param bool: True if the Player is a human, false otherwise
*/
Player::Player(bool isHuman) {
	int start1 = (isHuman ? 7 : 0); //Start position for non-pawns
	int start2 = (isHuman ? 6 : 1); //Start position for pawns

	//Initializes pieces
	playerPieces[0] = new Piece(rook, 0, start1, isHuman);
	playerPieces[1] = new Piece(knight, 1, start1, isHuman);
	playerPieces[2] = new Piece(bishop, 2, start1, isHuman);
	playerPieces[3] = new Piece(queen, 3, start1, isHuman);
	playerPieces[4] = new Piece(king, 4, start1, isHuman);
	playerPieces[5] = new Piece(bishop, 5, start1, isHuman);
	playerPieces[6] = new Piece(knight, 6, start1, isHuman);
	playerPieces[7] = new Piece(rook, 7, start1, isHuman);
	playerPieces[8] = new Piece(pawn, 0, start2, isHuman);
	playerPieces[9] = new Piece(pawn, 1, start2, isHuman);
	playerPieces[10] = new Piece(pawn, 2, start2, isHuman);
	playerPieces[11] = new Piece(pawn, 3, start2, isHuman);
	playerPieces[12] = new Piece(pawn, 4, start2, isHuman);
	playerPieces[13] = new Piece(pawn, 5, start2, isHuman);
	playerPieces[14] = new Piece(pawn, 6, start2, isHuman);
	playerPieces[15] = new Piece(pawn, 7, start2, isHuman);

	//Initializes cards
	cards[0] = new Card(Empower);
	cards[1] = new Card(Switch);
	cards[2] = new Card(Revive);

	score = 0;
}

/* This function gets the player's score.
@return int: The player's score
*/
int Player::getScore() {
	return score;
}

/* This function updates the players score by adding the point value to the current score. Depending on the new score, it unlocks the player's cards.
@param int: The amount to add
*/
void Player::setScore(int scoreDiff) {
	score += scoreDiff;

	if (score >= 10 && !cards[0]->played && !cards[0]->active) { //If the score is above 10 (Empower unlocked) and Empower has not already been played
		cards[0]->active = true;
		std::cout << "\nYay! You have unlocked Empower.\n";
		printCards();
	}

	if (score >= 20 && !cards[1]->played && !cards[1]->active) { //If the score is above 20 (Switch unlocked) and Switch has not already been played
		cards[1]->active = true;
		std::cout << "\nYay! You have unlocked Switch.\n";
		printCards();
	}

	//If the score is above 30 (Revive unlocked) and Revive has not already been played
	if (score >= 30 && !cards[2]->played && !cards[2]->active) {
		cards[2]->active = true;
		std::cout << "\nYay! You have unlocked Revive.\n";
		printCards();
	}
}

/* This function plays a player's card.
@param int: The index of the card to play
@return bool: True if the card was played, false otherwise
*/
bool Player::playCard(int index) {
	return cards[index]->playCard();
}

/* This function prints the player's cards. If a card is not active, it's type is hidden.
*/
void Player::printCards() {
	std::cout << (cards[0]->active ? " _____________ " : " _____________ ") << "\t" <<
		(cards[1]->active ? " _____________ " : " _____________ ") << "\t" <<
		(cards[2]->active ? " _____________ " : " _____________ ") << "\n" <<
		(cards[0]->active ? "/             \\" : "/             \\") << "\t" <<
		(cards[1]->active ? "/             \\" : "/             \\") << "\t" <<
		(cards[2]->active ? "/             \\" : "/             \\") << "\n" <<
		(cards[0]->active ? "|   E      1  |" : "|   \\     /   |") << "\t" <<
		(cards[1]->active ? "|   S      2  |" : "|   \\     /   |") << "\t" <<
		(cards[2]->active ? "|   R      3  |" : "|   \\     /   |") << "\n" <<
		(cards[0]->active ? "|    M        |" : "|    \\   /    |") << "\t" <<
		(cards[1]->active ? "|    W        |" : "|    \\   /    |") << "\t" <<
		(cards[2]->active ? "|    E        |" : "|    \\   /    |") << "\n" <<
		(cards[0]->active ? "|     P       |" : "|     \\ /     |") << "\t" <<
		(cards[1]->active ? "|     I       |" : "|     \\ /     |") << "\t" <<
		(cards[2]->active ? "|     V       |" : "|     \\ /     |") << "\n" <<
		(cards[0]->active ? "|      O      |" : "|      X      |") << "\t" <<
		(cards[1]->active ? "|      T      |" : "|      X      |") << "\t" <<
		(cards[2]->active ? "|      I      |" : "|      X      |") << "\n" <<
		(cards[0]->active ? "|       W     |" : "|     / \\     |") << "\t" <<
		(cards[1]->active ? "|       C     |" : "|     / \\     |") << "\t" <<
		(cards[2]->active ? "|       V     |" : "|     / \\     |") << "\n" <<
		(cards[0]->active ? "|        E    |" : "|    /   \\    |") << "\t" <<
		(cards[1]->active ? "|        H    |" : "|    /   \\    |") << "\t" <<
		(cards[2]->active ? "|        E    |" : "|    /   \\    |") << "\n" <<
		(cards[0]->active ? "|  1      R   |" : "|   /     \\   |") << "\t" <<
		(cards[1]->active ? "|  2      !   |" : "|   /     \\   |") << "\t" <<
		(cards[2]->active ? "|  3      !   |" : "|   /     \\   |") << "\n" <<
		(cards[0]->active ? "\\_____________/" : "\\_____________/") << "\t" <<
		(cards[1]->active ? "\\_____________/" : "\\_____________/") << "\t" <<
		(cards[2]->active ? "\\_____________/" : "\\_____________/") << "\n";
}

/* This function gets the player's pieces.
@return Piece**: A pointer to an array of the player's pieces
*/
Piece** Player::getPieces() {
	return playerPieces;
}

/* This function gets the player's cards.
@return Card*: An array of the players cards
*/
Card** Player::getCards() {
	return cards;
}