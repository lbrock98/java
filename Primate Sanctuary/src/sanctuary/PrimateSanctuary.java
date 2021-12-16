package sanctuary;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * This class implements the Sanctuary interface, and creates a PrimateSanctuary object with
 * several attributes.
 */

public class PrimateSanctuary implements Sanctuary{
  //attributes
  private int funds;
  private List<Isolation> isolationCages;
  private List<Enclosure> enclosures;

  /**
   * Construct an Enclosure object that has the provided funds, isolation cages, and enclosures
   *
   * @param funds           the sanctuary's funds
   * @param isolationCages  the sanctuary's cages
   * @param enclosures      the sanctuary's enclosures
   */
  public PrimateSanctuary(int funds, List<Isolation> isolationCages, List<Enclosure> enclosures)
  {
    this.funds = funds;
    this.isolationCages = isolationCages;
    this.enclosures = enclosures;
  }

  //getters
  /**
   * Returns a double of the sanctuary's available funds.
   * @return funds
   */
  public double getFunds() {
    return this.funds;
  }
  /**
   * Returns a list of isolation cages.
   * @return isolation cages
   */
  public List<Isolation> getCages() {
    return this.isolationCages;
  }
  /**
   * Returns a list of enclosures.
   * @return enclosures
   */
  public List<Enclosure> getEnclosures() {
    return this.enclosures;
  }
  /**
   * Returns a string, giving the name of every animal and where they're housed.
   * @return animals and their housing
   */
  public String getAnimalsHoused() {
    String animalsHoused = "";
    List<Animal> allAnimals = getAllAnimals();
    List<String> animalNames = new ArrayList<String>();
    for (int i = 0; i < allAnimals.size(); i++) {
      String name = allAnimals.get(i).getName();
      animalNames.add(name);
    }
    Collections.sort(animalNames);
    for (int i = 0; i < animalNames.size(); i++) {
      Animal animal = getAnimalByName(animalNames.get(i));
      String name = animalNames.get(i);
      Housing house = animal.getHousing();
      String output = animalNames.get(i) + ", " + animal.getHousing().idToString() + "\n";
      animalsHoused = animalsHoused + output;
    }
    return animalsHoused;
  }
  /**
   * Returns a string, giving every species and where it's housed.
   * @return species and houses
   */
  public String getSpecies() {
    String allSpecies = "";
    for (Monkey.MonkeySpecies species : Monkey.MonkeySpecies.values()) {
      String houses = speciesLookup(species);
      String moreSpecies = species + ": " + houses + "\n";
      if (!houses.equals("")) {
        allSpecies = allSpecies + moreSpecies;
      }
    }
    return allSpecies;
  }


  //other public methods
  /**
   * Returns a string of houses when you search for a species
   * @return houses
   */
  public String speciesLookup(Monkey.MonkeySpecies species) {
    String housings = "";
    List<Enclosure> enclosures = getEnclosures();
    List<Isolation> isolationCages = getCages();
    for (int i = 0; i < isolationCages.size(); i++) {
      Monkey.MonkeySpecies iSpecies = null;
      if (!isolationCages.get(i).getAvailability()) {
        iSpecies = isolationCages.get(i).getMonkeySpecies();
      }
      if (iSpecies == species) {
        housings = housings + isolationCages.get(i).idToString() + ", ";
      }
    }
    for (int i = 0; i < enclosures.size(); i++) {
      Monkey.MonkeySpecies eSpecies = null;
      if (enclosures.get(i).getAnimals().size() > 0) {
        eSpecies = enclosures.get(i).getMonkeySpecies();
      }
      if (eSpecies == species) {
        housings = housings + enclosures.get(i).idToString() + ", ";
      }
    }
    return housings;
  }

  /**
   * Returns a shopping list of how much food to buy, so that every animal in the sanctuary can
   * have their favorite food.
   * @return shopping list
   */
  public String shoppingList() {
    List<Animal> allAnimals = getAllAnimals();
    String shoppingList = "";
    for (Monkey.FavFood foods : Monkey.FavFood.values()) {
      int finalAmount = 0;
      for (int i = 0; i < allAnimals.size(); i++) {
        int size = allAnimals.get(i).getSize();
        int amount = 0;
        if (size > 20) {
          amount = 500;
        } else if (size > 10) {
          amount = 250;
        } else {
          amount = 100;
        }
        Monkey.FavFood favFood = allAnimals.get(i).getFavFood();
        if (favFood == foods) {
          finalAmount += amount;
        }
      }
      if (finalAmount > 0) {
        String addFood = foods + ", " + finalAmount + "gr \n";
        shoppingList = shoppingList + addFood;
      }
    }
    return shoppingList;
  }
  /**
   * Creates and returns a new animal. This animal is given an id number and placed in an isolation
   * cage, if one is available.
   * @return animal
   */
  public Animal newAnimal() {
    int id = getAllAnimals().size() + 1;
    Animal newAnimal = new Monkey (id, "Unnamed", null, null, 0, 0,
            0, null, null);
    List<Isolation> isolationCages = getCages();
    int updated = 0;
    for (int i = 0; i < isolationCages.size(); i++) {
      if (isolationCages.get(i).getAvailability()) {
        Isolation cage = isolationCages.get(i);
        newAnimal.setHousing(cage);
        cage.setAnimal(newAnimal);
        updated++;
        break;
      }
    }
    if (updated == 0) {
      if (expandIsolation()) {
        System.out.println("There are no available isolation cages. However, the sanctuary has " +
                "the funds to add another cage!");
        Isolation cage = new Isolation(getCages().size());
        updateHousing(cage);
        newAnimal.setHousing(cage);
        cage.setAnimal(newAnimal);
      }
      else {
        System.out.println("There are no available isolation cages. This monkey has been sent " +
                "directly to the clinic for medical attention and will hopefully be placed" +
                "in an enclosure.");
      }
    }
    return newAnimal;
  }
  /**
   * Moves monkey to an enclosure if one is available.
   * @return void
   */
  public void rehouseAnimal(Animal animal) {
    List<Enclosure> enclosures = getEnclosures();
    Isolation currentHouse = (Isolation) animal.getHousing();
    int updated = 0;
    for (int i = 0; i < enclosures.size(); i++) {
      if (enclosures.get(i).getMonkeySpecies() == animal.getMonkeySpecies() ||
              enclosures.get(i).getMonkeySpecies() == null) {
        if (enclosures.get(i).getAvailability(animal)) {
          Enclosure enclosure = enclosures.get(i);
          animal.setHousing(enclosure);
          enclosure.addAnimal(animal);
          updated++;
          break;
        }
      }
    }
    if (updated == 0) {
      System.out.print("There is no available space in our current enclosures for this monkey. ");
      if (expandEnclosure() > 0) {
        System.out.println("However, the sanctuary has the funds to add another enclosure!");
        Enclosure enclosure = new Enclosure(getEnclosures().size(),
                expandEnclosure());
        updateHousing(enclosure);
        animal.setHousing(enclosure);
        enclosure.addAnimal(animal);
      } else {
        System.out.println("The monkey will be moved to another primate sanctuary.");
        animal.setHousing(null);
      }
    }
    currentHouse.setAnimal(null);
  }

  //helper methods
  private List<Animal> getAllAnimals() {
    List<Animal> allAnimals = new ArrayList<Animal>();
    List<Enclosure> enclosures = getEnclosures();
    List<Isolation> isolationCages = getCages();
    //iterate through each isolation cage and get the animal inside
    //if there is an animal inside, add it to the list
    for (int i = 0; i < isolationCages.size(); i++) {
      Animal animal = isolationCages.get(i).getAnimal();
      if (animal != null) {
        allAnimals.add(animal);
      }
    }
    for (int i = 0; i < enclosures.size(); i++) {
      List<Animal> animals = enclosures.get(i).getAnimals();
      if (animals != null) {
        for (int j = 0; j < animals.size(); j++) {
          Animal animal = animals.get(i);
          if (animal != null) {
            allAnimals.add(animal);
          }
        }
      }
    }
    return allAnimals;
  }

  private Animal getAnimalByName(String name) {
    List<Animal> allAnimals = getAllAnimals();
    for (int i = 0; i < allAnimals.size(); i++) {
      if (allAnimals.get(i).getName().equals(name)) {
        return allAnimals.get(i);
      }
    }
    return null;
  }

  private boolean expandIsolation() {
    if (getFunds() >= 1000) {
      return true;
    }
    return false;
  }

  private int expandEnclosure() {
    int sqFt = 0;
    if (getFunds() >= 2000) {
      double amount = getFunds() / 2000;
      sqFt = (int) Math.floor(amount) * 20;
    }
    return sqFt;
  }

  private void updateHousing(Housing house) {
    if (house.idToString().indexOf('I') == 0) {
      if (!isolationCages.contains(house)) {
        this.isolationCages.add((Isolation) house);
      }
    }
    else if (house.idToString().indexOf('E') == 0) {
      if (!enclosures.contains(house)) {
        this.enclosures.add((Enclosure) house);
      }
    }
    else {
      System.out.println("Invalid housing id.");
    }
  }

}