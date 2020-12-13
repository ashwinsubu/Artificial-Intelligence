Ashwin Subramaniam
AXS190172@UTDALLAS.EDU
Homework 2 (AI CS6364)
Number of problems solved: 3 (including bonus question for Extra-Credit)
Programming Language: Java

==============Steps to Compile and Run=============
___1st program___
go to /multiplayer folder and run
javac MultiPlayer.java
 
go to src/ folder and run
java -Xss64M multiplayer.MultiPlayer
 
___2nd program___
go to /multiplayer folder and run
javac MiniMaxMultiPlayer.java
 
go to src/ folder and run
java -Xss64M multiplayer.MiniMaxMultiPlayer
 
___3rd program___
go to /multiplayer folder and run
javac MultiplayerJump.java 
 
go to src/ folder and run
java -Xss64M multiplayer.MultiplayerJump

=============Detailed Analysis====================================
____Problem 1____
There are three files for problem 1.

#Question 1 -> MultiPlayer.java

Performs DFS on all the states of the multiplayer game described in the problem statement.
Logic: 
> Move each player one by one either up down left right, player is tracked by the variable count modulo 4.
> At each recursion check if the coordinates are within the range 0 to 3 and also check if two players are on the same cell.
> At each recursion find if any of the players have reached the goal state.
> At each recursion use an explored set called "state" and add all the configurations of coordinates into the set.
> If the set already contains the current coordinate configuration then return.
**Find the entire output file in the directory where src/ folder is present or inside multiplayer/ folder.
**File name is multiplayer.txt


#Question 3 -> MiniMaxMultiPlayer.java

Performs DFS on all the states of the multiplayer game described in the problem statement.
Logic: 
> Move each player one by one either up down left right, player is tracked by the variable count modulo 4.
> At each recursion check if the coordinates are within the range 0 to 3 and also check if two players are on the same cell.
> At each recursion find if any of the players have reached the goal state, if YES return corresponding players minimax array.
> At each recursion use an explored set called "state" and add all the configurations of coordinates into the set.
> If the set already contains the current coordinate configuration then return.
> Get 4 minimax arrays for each player returned by moving in the 4 directions. Find and return the best among the minimax arrays based on current player.
**Find the entire output file in the directory where src/ folder is present or inside multiplayer/ folder.
**File name is minimax.txt


#Extra-Credit -> MultiplayerJump.java

Performs DFS on all the states of the multiplayer game described in the problem statement.
Logic: 
> Move each player one by one either up down left right, player is tracked by the variable count modulo 4.
> At each recursion check if the coordinates are within the range 0 to 3
> At each recursion check if two players are on the same cell, if YES then make a jump in the current direction by calling the recursion again.
> At each recursion find if any of the players have reached the goal state
> At each recursion use an explored set called "state" and add all the configurations of coordinates into the set.
> If the set already contains the current coordinate configuration then return.
**Find the entire output file in the directory where src/ folder is present or inside multiplayer/ folder.
**File name is multiplayerjump.txt



