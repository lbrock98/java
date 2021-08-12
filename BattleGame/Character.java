package Assignment5;
import java.util.Random;
import java.util.ArrayList;

//Student name: Lucy Brock
//Student ID: 260716544
public class Character {
	// attributes
	private String name;
	private double attackVal; 
	private double maxHealthVal;
	private double currentHealthVal;
	private int numOfWins;
	private static ArrayList<Spell> spells;


	// constructors
	// create a character with name, attack value, maximum health value, and number of wins
	public Character(String name, double attackVal, double maxHealthVal, int numOfWins) {
		this.name=name;
		this.attackVal=attackVal;
		this.maxHealthVal=maxHealthVal;
		this.currentHealthVal=maxHealthVal;
		this.numOfWins=numOfWins; 
	}

	// get methods
	public String getName() {
		return this.name;
	}

	public double getAttackValue() {
		return this.attackVal;
	}

	public double getMaxHealth() {
		return this.maxHealthVal;
	}

	public double getCurrHealth() {
		return this.currentHealthVal;
	}

	public int getNumWins() {
		return this.numOfWins;
	}

	// toString method to return the character's name and current health
	public String toString() {
		return this.name + "'s current health is " + this.currentHealthVal;
	}

	// takes an integer that will seed a random value, and returns a double that represents how much damage a character inflicts when they attack
	public double getAttackDamage(int seed) {
		// declare min and max of random value
		double min=0.7;
		double max=1.0;
		Random r=new Random(seed);
		double randomVal= min + (max-min)*r.nextDouble();
		// multiply random number by a character's given attack value to find damage inflicted, return attack damage
		double attackDamage=attackVal*randomVal;
		return attackDamage;
	}

	// takes the damage done to a character as type double, returns the character's current health, and updates the current health value
	public double takeDamage(double damageDone) {
		double newCurrentHealth=currentHealthVal-damageDone;
		currentHealthVal=newCurrentHealth;
		return newCurrentHealth;
	}

	// this method increases the number of wins of a character by one
	public void increaseWins() {
		numOfWins++;
	}

	// setter method; copies the spells from the input arraylist of spells to the arraylist in the spells attribute
	public static void setSpells(ArrayList<Spell> Spells) {
		spells=Spells;
	}

	// this method displays the spells in the arrayList of spells, utilizing the toString method from the Spell class
	public static void displaySpells() {
		for (int i=0; i<spells.size(); i++) {
			Spell newSpell=spells.get(i);
			System.out.println(newSpell);
		}
	}

	// this method takes the name of a spell and an integer to seed the random variable in the getMagicDamage method in the Spell class
	// it returns the damage done by casting a spell
	public static double castSpell(String name, int seed) {
		double damage=0;
		// loop through the arrayList of spells to check for the String input with the getName() method
		for (int i=0; i<spells.size(); i++) {
			String spellName=spells.get(i).getName();
			// if the String input matches the name of a spell from the arrayList of spells then return the magic damage of said spell (with a seed)
			// else return -1.0
			if (spellName.equals(name)) {
				Spell spell=spells.get(i);
				damage=spell.getMagicDamage(seed);
			} else {
				damage=-1.0;
			}
		} return damage;
	}

}