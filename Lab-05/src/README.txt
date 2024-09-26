# Rock Paper Scissors Game

## Author
Silas Curry

## Description
This project is a simple Rock Paper Scissors game developed using Java Swing. It allows the user to play against a computer opponent that employs various strategies to determine its moves. The game keeps track of player statistics, including wins, losses, and ties.

## Project Structure
- `src`: Contains the main source code for the application.
- `resources`: Holds the image files used for the game buttons (rock, paper, scissors).
- `CheatStrategy.java`: Implements a strategy where the computer tries to predict the player's next move based on their last choice.
- `LastUsedStrategy.java`: Uses the player's last move to determine the computer's move.
- `LeastUsedStrategy.java`: Chooses the computer's move based on the player's least used move.
- `MostUsedStrategy.java`: Selects the computer's move based on the player's most used move.
- `RandomStrategy.java`: Randomly selects a move for the computer.
- `RockPaperScissorsFrame.java`: The main class for the game that initializes the user interface and handles game logic.

## This project is submitted for Computer Programming with Professor Tom Wulf.

