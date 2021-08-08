package GuessTheWord;
//Name: Lucy Brock

import java.util.Random;
import java.util.Scanner;

public class GuessTheWord {

	private static final String[] words = {"perfect", "country", "pumpkin", "special", "freedom", "picture", "husband", 
			"monster", "seventy", "nothing", "sixteen", "morning", "journey", "history", "amazing", "dolphin", "teacher", 
			"forever", "kitchen", "holiday", "welcome", "diamond", "courage", "silence", "someone", "science", "revenge", 
			"harmony", "problem","awesome", "penguin", "youtube", "blanket", "musical", "thirteen", "princess", "assonant", 
			"thousand", "language", "chipotle", "business", "favorite", "elephant", "children", "birthday", "mountain", 
			"football", "kindness", "abdicate", "treasure", "strength", "together", "memories", "darkness", "sandwich", 
			"calendar", "marriage", "building", "function", "squirrel", "tomorrow", "champion", "sentence", "daughter", 
			"hospital", "identical", "chocolate", "beautiful", "happiness", "challenge", "celebrate", "adventure", 
			"important", "consonant", "dangerous", "irregular", "something", "knowledge", "pollution", "wrestling", 
			"pineapple", "adjective", "secretary", "ambulance", "alligator", "congruent", "community", "different", 
			"vegetable", "influence", "structure", "invisible", "wonderful", "nutrition", "crocodile", "education", 
			"beginning", "everything", "basketball", "weathering", "characters", "literature", "perfection", "volleyball", 
			"homecoming", "technology", "maleficent", "watermelon", "appreciate", "relaxation", "abominable", "government", 
			"strawberry", "retirement", "television", "silhouette", "friendship", "loneliness", "punishment", "university", 
			"confidence", "restaurant", "abstinence", "blackboard", "discipline", "helicopter", "generation", "skateboard", 
			"understand", "leadership", "revolution"};  

	// this method takes an integer as input and returns a random String from the array above. 
	public static String getRandomWord(int seed) {
		Random gen = new Random(seed);
		int randomIndex = gen.nextInt(words.length);
		return words[randomIndex];
	}

	// a method that takes a string as input and returns an array that has as many elements as the string had characters
	public static int [] generateArrayOfGuesses(String guess) {
		//compare length of array to length of string
		int [] guesses= new int [guess.length()];
		return guesses;
	}
	// a method that checks if a character will be found in a string; this method will return true if the string contains the character, false otherwise
	public static boolean check(String word, char lastGuess) {
		// loop to iterate through the string
		for (int i=0; i<word.length(); i++) {
			// if statement to confirm if the character is contained in the string
			if (word.charAt(i)==Character.toLowerCase(lastGuess)) {
				return true;
			} 
		}  return false;
	}
	// a method that takes a string, int array, and character as input
	// this method updates the array by assigning a value of 1 to the element corresponding to the position in the string where the given character can be found
	public static void update(String word, int [] guesses, char lastGuess) {
		// loop to iterate through the string
		for (int i=0; i<word.length(); i++) {
			// if statement to confirm if the character is contained in the string
			if (word.charAt(i)==Character.toLowerCase(lastGuess)) {
				//if yes, assign a value of 1 to the corresponding element in the array
				guesses[i]=1;
				i+=1;
			} // loop to iterate through the rest of the string 
			for (int j=i; j<word.length(); j++) {
				// if statement to confirm if the character is contained in the string
				if (word.charAt(j)==lastGuess) {
					//if yes, assign a value of 1 to the corresponding element in the array
					guesses [j]=1;
					j+=1;
				} 
			}
		} 
	}
	//this method takes a string, an int array, and a character as input, and returns a true or false value depending on if the character is contained within the string
	// it also updates the array by assigning a value of one to an element in the array that corresponds to the position of the character in the string (provided it is there)
	public static boolean checkAndUpdate(String word, int [] guesses, char lastGuess) {
		//if statement to check if the character is contained in the string, if so we update the array and return true; false otherwise
		if (check(word, lastGuess)) {
			update(word, guesses, lastGuess);
			return true;
		} return false;
	}

	// this method takes a string, an int array, and a character as input; it returns a string
	// it checks if the character is contained in the string; if so it prints the string, replacing the given character with an uppercase version of itself
	// and replacing all other characters in the string with '-'
	// if a letter has already been placed, it is left as lowercase, only the most recent guess is uppercase
	public static String getWordToDisplay(String word, int[] guesses, char guess) {
		//make sure guess is in lowercase
		char lastGuess=Character.toLowerCase(guess);
		// convert String to char array
		char [] wordChars= word.toCharArray();
			// loop to iterate through the length of the int array to see if any elements are already =1
			for (int i=0; i<guesses.length; i++) {
				char c=word.charAt(i);
				// if an element is equal to 1 and the corresponding character of the String equals the last guess, then replace this corresponding character 
				// with an uppercase version of itself
				if ((guesses[i]==1) && (c==lastGuess)) {
						wordChars [i]=Character.toUpperCase(c);
				} 
				// if an element is equal to 1 and the corresponding character of the String was not the last guess, then leave the corresponding character as is
				else if ((guesses[i]==1) && (c!=lastGuess)) {
					wordChars [i]=c;
				} // replace all other characters in the string with '-', using the char array
				else {
					wordChars [i]='-';
				}
			} 		// if statement to check if the character is NOT contained in the string
		 // convert the char array back to a string, and return the string 
		word=String.valueOf(wordChars); 
		return word;
	}

	// this method takes an int array as input and returns true if all elements of the int array=1, false otherwise
	public static boolean isWordGuessed(int [] guesses) {
		// loop to iterate through the elements of the array
		for (int i=0; i<guesses.length; i++) {
			// if statement to return false if any element does NOT equal one, return true otherwise
			if (guesses[i]!=1) {
				return false;
			} 
		} return true;
	}
	// this method takes an int as input; it retrieves a random word, asks the user to guess characters within the word
	// it gives appropriate responses if the user's guess is invalid, valid but incorrect (which results in losing a life), or valid but correct
	// the method ends when either the user loses all of their lives, or they guess all of the characters in the word correctly
	public static void play() {
		// call a scanner method
		Scanner read = new Scanner(System.in);
		// print welcome message
		System.out.println("Welcome to \"Guess the Word!\" Please enter a number to generate your word!");
		String input = read.nextLine();
		try
	    {
	     // the String to int conversion happens here
	     int n = Integer.parseInt(input.trim());
		// create a random secret word
		String secretWord=getRandomWord(n);
		// generate the corresponding array of guesses
		int [] guesses=generateArrayOfGuesses(secretWord);
		System.out.println("Your secret word has been generated. It has " + secretWord.length() + " characters. You have 10 lives left. Good luck!");
		// declare variable for number of lives--> this will change
		int lives=10;
		// create while statement, so that we may have multiple guesses
		while (true) {
			// print number of lives left and ask user for a guess
			System.out.println();
			System.out.println("You have " + lives + " lives left! Please enter a letter: ");
			String guess= read.nextLine();
			// print error messages if the input is invalid
			if (guess.length()!=1) {
				System.out.println("You can only enter one single character. Try again!");
				continue;
			} 
			// print whether the guess is correct or incorrect; if incorrect subtract a life 
			else if (guess.length()==1) {
				if (checkAndUpdate(secretWord, guesses, Character.toLowerCase(guess.charAt(0)))) {
					System.out.println("Good job! The secret word contains the letter '" + guess + "'");
					System.out.print(getWordToDisplay(secretWord, guesses, guess.charAt(0)));
				}  if ((!checkAndUpdate(secretWord, guesses, guess.charAt(0))) && lives>1) {
					System.out.println("There is no such letter. Try again!");
					System.out.print(getWordToDisplay(secretWord, guesses, guess.charAt(0)));
					lives--;
				} // for the user's last life, print they have one life left
				// check whether their last guess is correct or incorrect; if correct return to the beginning
				// if incorrect display message letting the user know they've lost,  display secret word, and break out of while loop
				if (lives==1) {
					System.out.println();
					System.out.println("You have 1 life left! Please enter a letter: ");
					read.nextLine();
					if (checkAndUpdate(secretWord, guesses, guess.charAt(0))) {
						System.out.println("Good job! The secret word contains the letter '" + guess + "'");
						System.out.print(getWordToDisplay(secretWord, guesses, guess.charAt(0)));
						continue;
					} else if (!checkAndUpdate(secretWord, guesses, guess.charAt(0))) {
						System.out.println("You have no lives left, better luck next time! The secret word was: \"" + secretWord + "\"");
						System.out.println("Would you like to play again?");
						String answer=read.nextLine(); 
						if ((answer.toLowerCase()).contains("yes")) {
							play(); 
						} else if ((answer.toLowerCase()).contains("no")) {
							System.out.println("Thank you for playing!");
						} else System.out.println("This is an invalid response. Please enter 'yes' or 'no'. Thank you!");
					} // if array contains only 1s, this means the word has been guessed, display congratulatory message, and break out of while loop 
				} if (isWordGuessed(guesses)) {
					System.out.println();
					System.out.println("Congratulations you guessed the secret word!");
					System.out.println("Would you like to play again?");
					String answer=read.nextLine(); 
					if ((answer.toLowerCase()).contains("yes")) {
						play(); 
					} else if ((answer.toLowerCase()).contains("no")) {
						System.out.println("Thank you for playing!");
					} else System.out.println("This is an invalid response. Please enter 'yes' or 'no'. Thank you!");
				}
			}
		}
		}
	    catch (NumberFormatException nfe)
	    {
	      System.out.println("This is an invalid input. Please enter an integer to begin the game!");
	      play();
	    }
	}

	public static void main(String[] args) {
		play();
	}
	
}
