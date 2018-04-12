DIR = ./src/
OBJS = Card.o Point.o PeasantsUprising.o Piece.o Player.o
CC = g++
LFLAGS = -Wall
CFLAGS = -Wall -c

all : PeasantsUprising.out cleanAll

PeasantsUprising.out : $(OBJS)
	$(CC) $(LFLAGS) $(OBJS) -o PeasantsUprising.out

Card.o : $(DIR)Card.cpp
	$(CC) $(CFLAGS) $(DIR)Card.cpp

Point.o : $(DIR)Point.cpp
	$(CC) $(CFLAGS) $(DIR)Point.cpp

PeasantsUprising.o : $(DIR)PeasantsUprising.cpp
	$(CC) $(CFLAGS) $(DIR)PeasantsUprising.cpp

Piece.o : $(DIR)Piece.cpp
	$(CC) $(CFLAGS) $(DIR)Piece.cpp

Player.o : $(DIR)Player.cpp
	$(CC) $(CFLAGS) $(DIR)Player.cpp

cleanAll:
	rm -f $(OBJS)

clean:
	rm -f $(OBJS) Peasants