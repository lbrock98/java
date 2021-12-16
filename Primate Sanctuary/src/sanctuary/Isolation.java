package sanctuary;

/**
 * This class implements the Housing interface, and creates an Isolation object with several
 * attributes.
 */
public class Isolation implements Housing{
  //attributes
  private final int id;
  private final int size = 10;
  private boolean availability;
  private Animal animal;

  /**
   * Construct an Isolation object that has the provided identification number
   *
   * @param id      the isolation cage's id
   */
  public Isolation(int id) {
    this.id = id;
    this.availability = true;
    this.animal = null;
  }

  //getters
  /**
   * Returns an integer, which represents the ID of the isolation cage.
   * @return id
   */
  @Override
  public final int getId() {
    return this.id;
  }
  /**
   * @return size in centimeters
   */
  @Override
  public final int getSize() {
    return this.size;
  }
  /**
   * Returns True if there's room in a housing unit, False otherwise.
   *
   * @return availability
   */
  public boolean getAvailability() {
    return this.availability;
  }
  /**
   * Returns null before an animal has been placed in the isolation cage.
   *
   * @return the animal housed
   */
  public Animal getAnimal() {
    return this.animal;
  }
  /**
   * Returns the species of monkey that is currently housed in the isolation cage. Returns null if
   * the cage is empty.
   *
   * @return species
   */
  @Override
  public Animal.MonkeySpecies getMonkeySpecies() {
    return this.animal.getMonkeySpecies();
  }

  //setter
  /**
   * Places an animal in the isolation cage and toggles availability. When an animal is taken
   * out of isolation, this is set to null.
   *
   * @return void
   */
  public void setAnimal(Animal animal) {
    if (availability) {
      this.animal = animal;
      this.availability = false;
    }
    else if (animal == null) {
      this.animal = animal;
      this.availability = true;
    }
  }

  /**
   * Combines an 'I' for isolation with the isolation cage's id number. Used to output the housing
   * of each animal.
   *
   * @return id as a string
   */
  @Override
  public String idToString() {
    String id = "I" + this.id;
    return id;
  }
}