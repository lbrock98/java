package sanctuary;

import java.util.Arrays;
import java.util.List;
/**
 * This class gives print statements of the capabilities of the sanctuary.
 */
public class ControlSanctuary {

  private static String checkForSpecies(PrimateSanctuary sanctuary, Animal.MonkeySpecies species) {
    String input = sanctuary.speciesLookup(Animal.MonkeySpecies.TAMARIN);
    String output = "";
    if (input.equals("")) {
      output = "There are no animals of this species currently being housed.";
    }
    else {
      output = input;
    }
    return output;
  }

  public static void main(String[] args) {
    Isolation a = new Isolation (1);
    Isolation b = new Isolation (2);
    Isolation c = new Isolation (3);
    Enclosure one = new Enclosure (1, 25);
    Enclosure two = new Enclosure(2, 50);
    List<Isolation> isolationCages = Arrays.asList(a,b,c);
    List<Enclosure>enclosures = Arrays.asList(one, two);
    PrimateSanctuary mySanctuary = new PrimateSanctuary (1200, isolationCages, enclosures);


    System.out.println("Welcome to the sanctuary! We can currently accommodate eight different " +
            "species of primates:" + java.util.Arrays.asList(Animal.MonkeySpecies.values()));
    System.out.println("When a new monkey arrives, we assign it an identification number and " +
            "move it to an isolation cage.");
    Monkey amy = (Monkey) mySanctuary.newAnimal();
    Monkey bobo = (Monkey) mySanctuary.newAnimal();
    Monkey emu = (Monkey) mySanctuary.newAnimal();
    System.out.println(String.format("Animals  with id numbers %d, %d, %d have been added to the " +
            "sanctuary, and assigned to isolation cages %s, %s, %s", amy.getId(), bobo.getId(),
            emu.getId(), amy.getHousing().idToString(), bobo.getHousing().idToString(),
            emu.getHousing().idToString()) + ". They will now each get a medical check.");
    amy.medUpdate("Amy", Animal.MonkeySpecies.DRILL, Animal.Sex.F, 40, 9.3,
            5, Animal.FavFood.EGGS);
    bobo.medUpdate("Bobo", Animal.MonkeySpecies.SPIDER, Animal.Sex.M, 30, 4.5,
            2, Animal.FavFood.NUTS);
    emu.medUpdate("Emu", Animal.MonkeySpecies.DRILL, Animal.Sex.M, 30, 4.5,
            2, Animal.FavFood.NUTS);
    System.out.println(String.format("Animals %d, %d, and %d have been named %s, %s, and %s. " +
            "Their species, sex, size (in cm), weight (in lb), age, and favorite food has been" +
            "determined.", amy.getId(), bobo.getId(), emu.getId(), amy.getName(), bobo.getName(),
            emu.getName()));
    mySanctuary.rehouseAnimal(amy);
    System.out.println(String.format("Amy has now been moved to housing unit %s",
            amy.getHousing().idToString()));
    System.out.println("These are the species currently being housed, and the " +
            "housing units in which they reside:\n" + mySanctuary.getSpecies());
    System.out.println("I would like to look up if there are any tamarin primates in the " +
            "sanctuary. " + checkForSpecies(mySanctuary, Animal.MonkeySpecies.TAMARIN));
    System.out.println(String.format("Here is a sign for each monkey currently housed in %s: " +
            "", one.idToString()) + one.makeSign());
    System.out.println("Here is a list of all the monkeys currently housed in the sanctuary and " +
            "where they can be found: \n" + mySanctuary.getAnimalsHoused());
    System.out.println("Finally, here is a shopping list for the animals: \n" +
            mySanctuary.shoppingList());
  }
}
