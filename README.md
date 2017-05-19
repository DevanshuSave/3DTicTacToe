# 3DTicTacToe

A 4x4x4 version of the classical tic-tac-toe game played against a computer.
Implemented in Java using Artificial intelligence algorithms like Alpha-Beta pruning, Minimax Search and Heuristics.

Heuristics formula = ( Number of axes with 3 points of player * 10000 + Number of axes with 2 points of player * 100 + Number of axes with 1 point of player * 10 ) - ( Number of axes with 3 points of opponent * 9999 + Number of axes with 2 points of opponent * 99 + Number of axes with 1 point of opponent * 9 )

Decision Tree is a 4-ply deep tree i.e. it looks 2 steps further for each player and decides the best move.

Future Work:
1. Expectimax implementation
2. Better heuristics
3. Multi-level gameplay (Easy, Medium, Hard) by 2-ply deep tree/4-ply deep tree/ 6-ply deep tree
4. UI improvements
