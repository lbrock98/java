package Assignment5;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

//Student name: Lucy Brock
//Student ID: 260716544
public class FileIO {

	// takes the name of a file as input and returns a new character, with specific attributes from the file
	public static Character readCharacter(String fileName) {
		// declare an array of strings that will be the lines in the file (the file should have 4 lines)
		String [] lines= new String [4];
		// create try block for the fileReader
		try { // read each line of the file
			FileReader fr=new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
			String currLine= br.readLine();
			int index=0;
			// store the lines of the file in the string array
			while (currLine!=null) {
				lines[index] = currLine;
				currLine= br.readLine(); 
				index++;
			}
			br.close();
			fr.close();
		} // catch exceptions
		catch (FileNotFoundException e) {
			System.out.println("The file was not there.");
			return null;
		}
		catch (IOException e) {
			System.out.println("Something went wrong");
			return null;
		} // declare variables for the character, and assign their values to the respective Strings in the array (representing the lines in the file)
		String name=lines[0];
		double attackVal=Double.parseDouble(lines[1]);
		double maxHealthVal=Double.parseDouble(lines[2]);
		int numOfWins=Integer.parseInt(lines[3]);
		// declare a new character, and return said character
		Character character= new Character(name, attackVal, maxHealthVal, numOfWins);
		return character;
	}

	// a method that takes a string array, splits each string into new strings, assigns those strings to the variables 
	// name, min and max damage, and chance of spell success, declares spells with these variables, and populates a spell arrayList with those spells
	private static ArrayList<Spell> fromString(String [] lines) {
		// create spell 1
		String spell1=lines[0];
		String [] Spell1=spell1.split("\t");
		String name1=Spell1[0];
		double minDamage1=Double.parseDouble(Spell1[1]);
		double maxDamage1=Double.parseDouble(Spell1[2]);
		double spellSuccess1=Double.parseDouble(Spell1[3]);
		Spell fromFile1 = new Spell(name1, minDamage1, maxDamage1, spellSuccess1); 
		// create spell 2
		String spell2=lines[1];
		String [] Spell2=spell2.split("\t");
		String name2=Spell2[0];
		double minDamage2=Double.parseDouble(Spell2[1]);
		double maxDamage2=Double.parseDouble(Spell2[2]);
		double spellSuccess2=Double.parseDouble(Spell2[3]);
		Spell fromFile2 = new Spell(name2, minDamage2, maxDamage2, spellSuccess2); 
		// create spell 3
		String spell3=lines[2];
		String [] Spell3=spell3.split("\t");
		String name3=Spell3[0];
		double minDamage3=Double.parseDouble(Spell3[1]);
		double maxDamage3=Double.parseDouble(Spell3[2]);
		double spellSuccess3=Double.parseDouble(Spell3[3]);
		Spell fromFile3 = new Spell(name3, minDamage3, maxDamage3, spellSuccess3); 
		// create spell 4
		String spell4=lines[3];
		String [] Spell4=spell4.split("\t");
		String name4=Spell4[0];
		double minDamage4=Double.parseDouble(Spell4[1]);
		double maxDamage4=Double.parseDouble(Spell4[2]);
		double spellSuccess4=Double.parseDouble(Spell4[3]);
		Spell fromFile4 = new Spell(name4, minDamage4, maxDamage4, spellSuccess4); 
		// declare ArrayList of spells, return arrayList
		ArrayList<Spell> spells = new ArrayList<Spell>();
		spells.add(fromFile1);
		spells.add(fromFile2);
		spells.add(fromFile3);
		spells.add(fromFile4);
		return spells;
	}

	public static ArrayList<Spell> readSpells(String fileName) {
		// declare array list of spells and string array (to store file content)
		ArrayList<Spell> spells= new ArrayList<Spell>();
		String [] lines= new String [4]; 
		// create try block for the fileReader
		try { // read each line of the file
			FileReader fr=new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
			// store the lines of the file in the string array
			String currLine= br.readLine();
			int index=0;
			while (currLine!=null) {
				lines[index] = currLine;
				currLine= br.readLine(); 
				index++;
			}
			br.close();
			fr.close();
		} // catch exceptions
		catch (FileNotFoundException e) {
			System.out.println("The file was not there.");
			return null;
		}
		catch (IOException e) {
			System.out.println("Something went wrong");
			return null;
		}
		spells=fromString(lines);
		return spells; 
	} 

	public static void writeCharacter(Character character, String file) {
		try {
			FileWriter fw= new FileWriter(file);
			BufferedWriter bw= new BufferedWriter (fw);
			bw.write(character.getName());
			bw.newLine();
			bw.write(Double.toString(character.getAttackValue()));
			bw.newLine();
			bw.write(Double.toString(character.getMaxHealth()));
			bw.newLine();
			bw.write(Integer.toString(character.getNumWins()));
			bw.close();
			fw.close();
		} catch (FileNotFoundException e) {
			System.out.println("The file was not there.");
		}
		catch (IOException e) {
			System.out.println("Something went wrong");
		}
	}
}