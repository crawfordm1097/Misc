/*
COP3503 Final Project, Card.cpp
Purpose: Stores whether a specific card is active and has been played
@authors: Jack Coon and Mateo Canarte
*/

#include "./Card.h"

/* Constructor for Card. It initializes the name and whether the card is active and has been played.
@param CardType: The enumeration type of the card.
*/
Card::Card(CardType kind) {
	name = kind;
	active = false;
	played = false;
}

/* This function plays the card if it is active (has been unlocked). Once it is played, it updates the active and played variables.
@return bool: True if the card was played, false otherwise
*/
bool Card::playCard() {
	if (active)
	{
		active = false;
		played = true;
		return true;
	}
	return false;
}