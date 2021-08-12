package Assignment5;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;


//Student name: Lucy Brock
//Student ID: 260716544
public class BattleGame {

	//attribute
	private static Random random;

	// initialize the random variable
	private static int getRandom() {
		int min=0;
		int max=10;
		Random random=new Random();
		int randomVal= min + (max-min)*random.nextInt();
		return randomVal; 
	} 

	// this method takes two characters as input and displays their name, current health, attack value, and number of wins
	private static void displayCharacters(Character player, Character monster) {
		System.out.println("Name: " + player.getName());
		System.out.println("Health: " + player.getCurrHealth());
		System.out.println("Attack: " + player.getAttackValue());
		System.out.println("Number of Wins: " + player.getNumWins());
		System.out.println();
		System.out.println("Name: " + monster.getName());
		System.out.println("Health: " + monster.getCurrHealth());
		System.out.println("Attack: " + monster.getAttackValue());
		System.out.println("Number of Wins: " + monster.getNumWins()); 
	}



	// this method takes two file names as strings, and plays a game with the user
	public static void playGame(String file1, String file2, String file3) {
		// create two characters by reading information from the given file names
		Character player= FileIO.readCharacter(file1);
		Character monster= FileIO.readCharacter(file2);		
		ArrayList<Spell> spells= FileIO.readSpells(file3);
		// try to declare an arrayList of spells, catching nullPointerExceptions
		try {
			FileIO.readSpells(file3);
		} catch (NullPointerException n) {
			System.out.println("The game shall be played without spells.");
		} 
		// initialize spells attribute
		Character.setSpells(spells); 
		// display spells
		System.out.println("Here are the available spells: ");
		Character.displaySpells();
		System.out.println();
		// try displaying the characters, catching exceptions if the files are not formatted correctly
		try {
			displayCharacters(player, monster);
		}
		catch (Exception e) {
			System.out.println("The game cannot be played.");
			return; 
		}
		// create scanner object
		Scanner read= new Scanner(System.in);
		// create while loop to ask for commands multiple times
		while (true) {
			System.out.println("Enter a command:");
			String command= read.nextLine();
			// create if statement for when the user inputs "attack" as a command
			if (command.contains("attack")) {
				// declare double variable for the attack damage done by the player				
				double attackDamage1 = player.getAttackDamage(getRandom()); 
				// print the attack damage done by the player and the monster's current health
				System.out.println(); 
				System.out.println(player.getName() + " attacks for " + String.format("%1$.2f", attackDamage1) + " of damage!");
				System.out.println(monster.getName() + "'s current health is " + String.format("%1$.2f",monster.takeDamage(attackDamage1)));
				System.out.println();
				// declare double variable for the attack damage done by the monster				
				double attackDamage2 = monster.getAttackDamage(getRandom());
				// print the attack damage done by the monster and the monster's current player's
				System.out.println(monster.getName() + " attacks for " + String.format("%1$.2f",attackDamage2) + " of damage!");
				System.out.println(player.getName() + "'s current health is " + String.format("%1$.2f", (player.takeDamage(attackDamage2))));
				System.out.println();
			} // if the command is "quit" then print goodbye message and break out of scanner while loop 
			else if (command.contains("quit")) {
				System.out.println("Goodbye!");
				break;
			} // if there is any other command, then assume the player is trying to cast a spell 
			else {
				double spellDamage;
				// loop through our spell arrayList to see if the player knows the spell
				for (int i=0; i<spells.size(); i++) {
					Spell newSpell=spells.get(i);
					// if the player does know the spell, then try to cast it
					if (command.contains(newSpell.getName())) {
						spellDamage=newSpell.getMagicDamage(getRandom());
						// if the spellDamage is less than 0 then the spell can't be cast
						if (spellDamage<=0) {
							System.out.println(player.getName() + " tried to cast " + spells.get(i).getName() + ", but it failed.");
							System.out.println();
							// declare double variable for the attack damage done by the monster				
							double attackDamage2 = monster.getAttackDamage(getRandom());
							// print the attack damage done by the monster and the monster's current player's
							System.out.println(monster.getName() + " attacks for " + String.format("%1$.2f",attackDamage2) + " of damage!");
							System.out.println(player.getName() + "'s current health is " + String.format("%1$.2f", (player.takeDamage(attackDamage2))));
							System.out.println();
						} else {
							System.out.println(player.getName() + " casts " + spells.get(i).getName() + " dealing " + String.format("%1$.2f",  spellDamage) + " damage!");
							System.out.println(monster.getName() + "'s current health is " + String.format("%1$.2f",(monster.getCurrHealth()-spellDamage)));
							System.out.println();
							// declare double variable for the attack damage done by the monster				
							double attackDamage2 = monster.getAttackDamage(getRandom());
							// print the attack damage done by the monster and the monster's current player's
							System.out.println(monster.getName() + " attacks for " + String.format("%1$.2f",attackDamage2) + " of damage!");
							System.out.println(player.getName() + "'s current health is " + String.format("%1$.2f", (player.takeDamage(attackDamage2))));
							System.out.println();
							break;
						} 
					} // if the command was not in the arrayList of spells, then tell the player they don't know that spell 
					else if (!command.contains(newSpell.getName())){
						System.out.println(player.getName() + " tried to cast " + command + ", but they don't know that spell.");
						System.out.println();
						// declare double variable for the attack damage done by the monster				
						double attackDamage2 = monster.getAttackDamage(getRandom());
						// print the attack damage done by the monster and the monster's current player's
						System.out.println(monster.getName() + " attacks for " + String.format("%1$.2f",attackDamage2) + " of damage!");
						System.out.println(player.getName() + "'s current health is " + String.format("%1$.2f", (player.takeDamage(attackDamage2))));
						System.out.println();
						break;
					}
				}
			} // if the monster's health is <=0, congratulate the player on winning
			if (monster.getCurrHealth()<=0) {
				System.out.println("Congratulations! " + player.getName() + " has won!");
				player= FileIO.readCharacter(file1);
				monster= FileIO.readCharacter(file2);
				player.increaseWins();
				FileIO.writeCharacter(player, file1);
				FileIO.writeCharacter(monster, file2);
				System.out.println(player.getName() + " has won " + player.getNumWins() + " times.");
				System.out.println();
			} // if the player's health is <=0, tell them the monster has won
			if (player.getCurrHealth()<=0) {
				System.out.println("Oh no! " + player.getName() + " has been slayed by " + monster.getName() + "!");
				player= FileIO.readCharacter(file1);
				monster= FileIO.readCharacter(file2);
				monster.increaseWins();
				FileIO.writeCharacter(player, file1);
				FileIO.writeCharacter(monster, file2);
				System.out.println(monster.getName() + " has won " + monster.getNumWins() + " times.");
				System.out.println();
			}
		} 
	}

	public static void main(String[] args) {
		playGame("player.txt", "monster.txt", "spells.txt");
	}
}