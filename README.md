# Minesweeper
Minesweeper game running in terminal.

The version of the game is determined by a file consisting of 4 lines with a predefined syntax:
• First line: contains the value 1 or 2 and specifies the difficulty level.
• Second line: specifies the total number of mines0.
The value must be within the acceptable limits of the difficulty level, otherwise
the description is not considered valid.
• Third line: specifies the maximum available time in seconds. Must n
value to be within acceptable limits depending on the difficulty level, otherwise
the description is not considered valid.
• Fourth line: contains a value of 0 or 1 and determines whether there will be an super-mine. For
descriptions with difficulty level 1 must always be 0, otherwise n
description will not be considered valid.

For difficulty level one, grid size is 9*9, the number of mines is 9-11, time is 120-180 and there is no super-mine.
For difficulty level 2, grid size is 16*16 , the number of mines is 35-45, time is 240-360 and there is a super-mine.

If the player marks the super-mine in the first four attempts then the contents of all squares that are on the same line and column with the super-mine are revealed.

To play the game run the class App_1.java. If you want to reveal a square, type 1 and then the position of the square. If you want to mark or unmark a square, type 0 and the position of the square. Unrevealed squares are denoted by *, mines by #, super-mine by ! and the revealed squares with the number of mines that they are abjacent to.



