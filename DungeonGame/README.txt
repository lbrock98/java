1.	About/Overview: 
      The problem is to create a 2D model of a dungeon, for which the user inputs the dungeon’s size, it’s level of interconnectivity (number of paths between any two locations in the dungeon), whether the dungeon can wrap (the player could exit the dungeon to the south and reenter from the north), and how many monsters are found in the dungeon. The dungeon includes caves and tunnels. Caves can contain treasure and arrows. 
	For the model, I created dungeon, player, monster, and location interfaces, as well as dungeon, player, location, monster, and edge classes. The location and edge classes were used to create a dungeon using Kruskal’s algorithm. An edge represents a path between two locations. Edges are chosen at random until all locations are connected by one path. More edges are added depending on the desired level of connectivity. 
	The program can be run as a text-based (command-line) game, or as a graphical (GUI) game. The objective of the game is to reach the end cave without dying. 
2.	List of Features:
      In this program, you can get treasure from a location, get moves from a location, and add and remove treasure to/from a location. You can also get the treasure that the player currently has, have the player collect treasure from their current location, and get the location of the 
player. If you would like, you can print out a view of the dungeon, have a player enter the dungeon, move a player to a certain location in a dungeon, get the path from one location in the dungeon to another, get the location at a given x and y coordinate within the dungeon, and get 
descriptions of a location or player. 
	I use depth first search or breadth first search to find a path between a given start and end location, whichever’s shorter. 
	I used enums to represent the treasure available in a cave, the available moves from a 
Location, and the smell given off by a monster. 
	A player starts with three arrows and is given the option to either move, pickup treasure and/or arrows in their location, or shoot. If a player shoots in their desired location, they will hit a monster only if that monster is the exact distance away as specified by the player. Arrows will move along any tunnel (even bent tunnels) and will move straight through caves. 
	If a player is near a monster, they will smell that monster. 
	There is also a moving monster that moves their location each time a player moves.
	There is a thief hidden somewhere in the cave that will steal a piece of the player’s treasure every time the thief is encountered. 
	There is also a wolf pit in the cave that the monster will fall into and die when encountered. The player will hear howling when it is near the wolf pit. 
3.	How to Run: 
      The text-based game can be run from the command-line from within this /res folder with ‘java -jar proj5.jar text’
	The graphical game can be run from the command-line from within this /res folder with ‘java -jar proj5.jar graphics’
4.	How to Use the Program:
      Run the program. When prompted, input if you would like the dungeon to wrap, as well as the width, length, and degree of interconnectivity of the dungeon, the percentage of caves in the dungeon that you would like to add treasure to, and the number of monster’s you would like in the dungeon. You will then be prompted to move, pick up treasure and/or arrows, or shoot from your given location until you either find the end location or are killed by a monster. You can use arrow keys or click into an adjacent square in the dungeon to move the player around (after indicating ‘m’ for move). The player can press ‘s’ for shoot, and then indicate the direction and how long they would like their arrow to shoot. The player can press ‘p’ for pickup. In the graphical game, there are buttons to restart the game with the same dungeon parameters (although dungeons are constructed randomly, so the contents of the dungeon may change), or redesign the dungeon with new parameters. 
5.	Description of Examples:
	I’ve included several screenshots of the graphical game: 
a) Shows the directions of the game, which is the prelaunch view. There is a jmenu at the bottom for the user to give their inputs, and buttons to begin the game or quit. 
b) Shows that an error pops up if the user’s inputs are invalid. 
c) Shows that an error pops up if the user’s inputs are invalid.
d) Shows how the dungeon looks at the beginning of the game. Gives a description of the current location, the player, and directions for what to do. 
e) The player has moved in the dungeon, and they are near a monster as indicated by the smell, and near the wolf pit as indicating by howling. 
f) The player has encountered a thief.
g) This is the game in cheat mode. It shows the player the entire dungeon. The player can walk through yellow lines but not black lines. The player’s box is highlighted in red and the end location is highlighted in green. 
h) Shows the player winning the game! The player had shot at the monster twice and won. 
6.	Design and Model Changes:
      I created two controller interfaces, one for the text-based game and one for the graphical game. For the graphical game, I added a folder for the view with prelaunch view and view interfaces, along with implementing classes, and a GameGrid abstract class for drawing the dungeon. The model had very small changes, such as having an attribute of “visited” for locations, and adding a cheat() method to the Dungeon interface, which marks each location as visited. 
7.	Assumptions:
      I still assume that a player wants to pick up both arrows and treasure in a location if they choose to pick up. I assume there can only be one monster per cave. I assume that while a player falls into a pit and dies, a monster is large enough to stand over a pit and can exist in the same cave as one. I assume that a thief stays in the same location throughout the game. 
8.	Limitations:
      My getPath() method still does not get the shortest path between locations. In a wrapping dungeon, the user cannot click a square to go to the otherside of the dungeon, they can only do this with arrow keys. 
9.	Citations:
N/A
