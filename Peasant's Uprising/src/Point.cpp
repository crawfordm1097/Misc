/*
COP3503 Final Project, Point.cpp
Purpose: stores an x and y value
@authors Andrew Helms, Chris Stauffer
@version 1.4 11/14/2017
*/

#include "./Point.h"

using namespace std;

/*
Constructor for Point
@param 	x: the horizonal component
y: the vertical component
*/
Point::Point(int X, int Y) {
	x = X;
	y = Y;
}

Point::Point() {//Default Constructor
	x = 0;
	y = 0;
}

/*
Checks if two points are equal
@param 	tip: the point being evaluated against
@return a boolean: true = equal
*/
bool Point::equals(Point tip) {
	if (x != tip.x)
		return false;
	return y == tip.y;
}

bool Point::onBoard() {//checks if a point is on the board
	return !(x < 0 || x > 7 || y < 0 || y > 7);
}

void Point::print() {//prints out the point
	cout << "(" << x << ", " << y << ")\n";
}