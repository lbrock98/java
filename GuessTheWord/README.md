# Guess the Word
by Lucy Brock

The classic game!

## What this application does:
This program begins by welcoming the user to the game, and asks for the user to input an integer, which is then used to seed a random generator, which selects a word at random from a static list. The user then guesses different characters and, if they are correct, is shown the word, populated with letters for correctly guessed characters, and dashes for characters yet to be guessed. An incorrect guess loses the user 1 life. The player either correctly guesses all the letters and is congratulated, or loses all of their lives and dies.
Previously correctly guessed characters are lower case, and newly correctly guessed characters are upper case. Error messages are printed if the user gives an invalid input, with instructions on what input should be provided.

## What technologies/techniques it uses:
Random generator, classes, loops, conditionals, command-line inputs

## Challenges and future implementations:
I'd like to use a dynamic list of words from which to choose, possibly by calling them from an API. I could also add word categories, and/or different levels of difficulty (i.e. shorter and longer words). 

## Installation
This app runs on any system that supports java code. 

## License
[GNU General Public License v3.0](https://choosealicense.com/licenses/gpl-3.0/#)
