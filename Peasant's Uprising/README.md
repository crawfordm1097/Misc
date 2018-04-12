# Welcome to Peasant's Uprising!

Humans play against Orcz in this CL fantasy version of chess. To run the game, please run the makefile. Gameplay is similar to the classic game, but there are some unique changes with the pieces in the inclusion of special cards.

### Pieces
Each team has 16 pieces, as follows:

Human | Orcz | Point Value | Move Set | No. of Pieces
------|------|-------------|----------|--------------
Crown| Orc | Infinite | 1 space in any direction | 1
Dragon | Giant Troll | 9 | Any number of spaces in any direction | 1
Spirit | Demon | 5 | 1-7 spaces left, right, forward, or backward | 2
Magician | Warlock | 3 | Any direction diagonally | 2
Archer | Archerboi | 3 | L shaped (2 spaces left, right, forward, or backward and 1 space perpendicular) | 2
Peasant | Peon | 1 | 1 forward move diagonally; attacks head-on | 8

You might’ve noticed some parallel with the pieces. Each piece grouping was based off of the King, Queen, Rook, Bishop, Knight, and Pawn, respectively, but the Peasant/Peon’s moves were reversed, making it a true Peasant’s Uprising.

On the board, each piece is represented by 2 letters. The first letter signifies the player (H for Humans and O for Orcz). The second letter is the first letter of the piece type. 

### Cards
Each player can unlock up to 3 different cards during gameplay. Cards are unlocked at different point thresholds (points are accumulated by capturing the other player’s pieces). A card can be played at any point after unlocking. Once a card is played, it cannot be played again. The cards are as follows:

Card | Points Needed | Description
-----|---------------|------------
Empower | 10 | Player can let one of their own pieces move like a Dragon/Giant Troll for a turn
Switch | 20 | Player can switch the placement of any of their own two pieces excluding their Crown/Orc
Revive | 30 | Player can bring any one of their own pieces back to life, excluding their Dragon/Giant Troll. They can place it anywhere within the first two rows on their side.

### Gameplay
The gameplay is quite straightforward. Players take turns moving pieces on the board to capture the enemy pieces. At any point, a player can type exit to end the game, otherwise it will end when the Crown or Orc is captured. Whenever a piece or spot is asked for, the player should input the space (e.g. E5, A3, etc.).

Each turn, the current score and board will be printed out. The player will then be able to specify a piece or play a card. If a piece is specified, a preview of the possible moves (spots marked with x’s) will be printed out. The player can either move that piece or pick a different piece. A turn ends when a piece has been moved or a card has been played.

There is no checkmate with this game. Instead, each player will have to pay close attention.

Good luck and good gaming!
