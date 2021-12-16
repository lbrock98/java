package sanctuary;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Housing interface, and creates an Enclosure object with several
 * attributes.
 */
public class Enclosure implements Housing{
  //attributes
  private final int id;
  private final int size;
  private int availability;
  List<Animal> animals;

  /**
   * Construct an Enclosure object that has the provided identification number and size
   *
   * @param id      the enclosure's id
   * @param size    the enclosure's size in sq ft
   */
  public Enclosure(int id, int size) {
    this.id = id;
    this.size = size;
    this.availability = size;
    this.animals = new ArrayList<Animal>();
  }

  //getters
  /**
   * Returns an integer, which represents the ID of the enclosure.
   *
   * @return id
   */
  public final int getId() {
    return this.id;
  }
  /**
   * Returns an int, which is the number of square meters in the housing unit.
   *
   * @return size
   */
  public final int getSize() {
    return this.size;
  }
  /**
   * Returns True if there's room in a housing unit, False otherwise.
   */
  public boolean getAvailability(Animal animal) {
    int size = animal.getSize();
    int availability = spaceLeft();
    if (availability >= 10) {
      return true;
    }
    else if (availability >= 5) {
      if (size <= 20) {
        return true;
      }
    }
    else if (availability >= 1) {
      if (size <= 10) {
        return true;
      }
    }
    return false;
  }
  /**
   * Returns a list of animals in the enclosure. Returns null before an animal has been placed in
   * the enclosure.
   *
   * @return the animals housed
   */
  public final List<Animal> getAnimals() {
    return this.animals;
  }
  /**
   * Returns the species of monkey that is currently housed in the isolation cage. Returns null if
   * the cage is empty.
   *
   * @return species
   */
  public Monkey.MonkeySpecies getMonkeySpecies() {
    if (animals.size() > 0) {
      Animal oneMonkey = animals.get(0);
      return oneMonkey.getMonkeySpecies();
    }
    else {
      return null;
    }
  }


  //setter
  /**
   * Places an animal in the enclosure after checking for availability.
   *
   * @return void
   */
  public void addAnimal(Animal animal) {
    if (getAvailability(animal)) {
      if (getMonkeySpecies() == null || getMonkeySpecies() == animal.getMonkeySpecies()) {
        this.animals.add(animal);
      }
    }
  }

  //other public methods
  /**
   * Combines an 'E' for enclosure with the enclosure's id number. Used to output the housing
   * of each animal.
   *
   * @return id as a string
   */
  public String idToString() {
    String id = "E" + getId();
    return id;
  }

  /**
   * Gives information on each monkey in the enclosure.
   *
   * @return sign
   */
  public String makeSign() {
    String sign = "";
    List<Animal> animals = getAnimals();
    for (int i = 0; i < animals.size(); i++) {
      Monkey currentAnimal = (Monkey) animals.get(i);
      String monkeySign = currentAnimal.monkeyString();
      sign = sign + monkeySign;
    }
    return sign;
  }

  //helper
  private int spaceLeft() {
    List<Animal> animals = getAnimals();
    int filledSpace = 0;
    if (animals != null) {
      for (int i = 0; i < animals.size(); i++) {
        Animal currentAnimal = animals.get(i);
        if (currentAnimal.getSize() < 10) {
          filledSpace++;
        }
        else if (currentAnimal.getSize() < 20) {
          filledSpace += 5;
        }
        else {
          filledSpace += 10;
        }
      }
    }
    int emptySpace = getSize() - filledSpace;
    return emptySpace;
  }

}