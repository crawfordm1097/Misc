#ifndef POINT_H
#define POINT_H

#include <iostream>

class Point {
private:

public:
	int x; //Alpha characters
	int y; //Numeric characters

	Point();
	Point(int X, int Y);
	bool equals(Point tip);
	bool onBoard();
	void print();
};

#endif