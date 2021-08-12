package Assignment5;
import java.util.ArrayList;
import java.util.Random;

//Student name: Lucy Brock
//Student ID: 260716544
public class Spell {
	
	// attributes
	private String name;
	private double minDamage;
	private double maxDamage;
	private double spellSuccess;
	
	// constructor 
	public Spell(String name, double minDamage, double maxDamage, double spellSuccess) {
		this.name=name;
		this.minDamage=minDamage;
		this.maxDamage=maxDamage;
		this.spellSuccess=spellSuccess;
		// check that the minimum damage is greater than 0 and less than the maximum damage
		if (minDamage<0 || minDamage>maxDamage) {
			throw new IllegalArgumentException ("Invalid damage parameters.");
		} // check that the spell success is greater than 0 and less than 1 
		if (spellSuccess<0 || spellSuccess>1) {
			throw new IllegalArgumentException ("Invalid chance of spell success.");
		}
	}
	
	// get method
	public String getName() {
		return this.name;
	}
	
	// returns the damage produced by a spell as a double
	public double getMagicDamage(int seed) {
		// declare min and max of the possible damage produced
		double min=0.0;
		double max=1.0;
		Random r=new Random(seed);
		double randomVal= min + (max-min)*r.nextDouble();
		// check that the random number is greater than the chance of spell success, if so the spell has failed, return 0.0
		if (randomVal>this.spellSuccess) {
			return 0.0;
		} // otherwise, return the damage done by the spell (a random number between the min and max)
		else { 
			return (this.minDamage + (this.maxDamage-this.minDamage)*r.nextDouble());
		}
	}

	// a toString method that displays the name, minimum and maximum damage, and chance of success of a spell
	public String toString() {
		String info= "Name: " + this.name + "; " + "Minimum Damage: " + this.minDamage + "; " + "Maximum Damage: " + this.maxDamage + "; " + 
				"Chance of Spell Success: " + (this.spellSuccess*100) + "%";
		return info;
	}
	
}