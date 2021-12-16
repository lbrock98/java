package sanctuary;
/**
 * This class implements the Animal interface, and creates a Monkey object with several attributes.
 */
public class Monkey implements Animal {
  //attributes
  private final int id;
  private String name;
  private MonkeySpecies species;
  private Sex sex;
  private int size;
  private int age;
  private double weight;
  private FavFood favFood;
  private Housing housing;

  /**
   * Construct a Monkey object, with all of its attributes
   *
   * @param id      the Monkey's identification number
   * @param name    the monkey's name
   * @param species the monkey's species
   * @param sex     the monkey's sex
   * @param size    the monkey's size in centimeters
   * @param weight  the monkey's weight in lb
   * @param age     the monkey's approximate age
   * @param favFood the monkey's favorite food
   * @param housing the Monkey's assigned housing
   */

  public Monkey(int id, String name, MonkeySpecies species, Sex sex, int size, double weight, int age,
                FavFood favFood, Housing housing) {
    this.id = id;
    this.name = name;
    this.species = species;
    this.sex = sex;
    this.size = size;
    this.weight = weight;
    this.age = age;
    this.favFood = favFood;
    this.housing = housing;
  }

  //getter methods
  /**
   * Returns an integer, which represents the ID numbers of the animal.
   *
   * @return id
   */
  @Override
  public final int getId() {
    return this.id;
  }

  /**
   * @return the monkey's name
   */
  @Override
  public final String getName() {
    return this.name;
  }

  /**
   * @return the monkey's species
   */
  public final MonkeySpecies getMonkeySpecies() {
    return this.species;
  }
  /**
   * @return the monkey's sex, either F for female or M for male
   */
  @Override
  public final Sex getSex() {
    return this.sex;
  }
  /**
   * @return the monkey's size in cm
   */
  @Override
  public final int getSize() {
    return this.size;
  }
  /**
   * @return the monkey's weight in lb
   */
  @Override
  public final double getWeight() {
    return this.weight;
  }
  /**
   * @return the monkey's age
   */
  @Override
  public final int getAge() {
    return this.age;
  }
  /**
   * @return the monkey's favorite food
   */
  @Override
  public final FavFood getFavFood() {
    return this.favFood;
  }
  /**
   * @return the housing
   */
  @Override
  public final Housing getHousing() {
    return this.housing;
  }


  //setter methods
  /**
   * Sets housing for the monkey
   *
   * @return void
   */
  @Override
  public final Void setHousing(Housing housing) {
    this.housing = housing;
    return null;
  }
  private final void setName(String newName) {
    this.name = newName;
  }

  private final void setSpecies(MonkeySpecies determineSpecies) {
    this.species = determineSpecies;
  }

  private final void setSex(Sex determineSex) {
    this.sex = determineSex;
  }

  private final void setSize(int determineSize) {
    this.size = determineSize;
  }

  private final void setWeight(double determineWeight) {
    this.weight = determineWeight;
  }

  private final void setAge(int determineAge) {
    this.age = determineAge;
  }

  private final void setFavFood(FavFood determineFavFood) {
    this.favFood = determineFavFood;
  }

  //method to be used later to create a sign about each monkey in an enclosure
  /**
   * A class that outputs some information about the monkey.
   * @return String saying "Meet NAME. (S)he is a SEX SPECIES monkey, and her(his) favorite food
   * is FAVFOOD."
   */
  public String monkeyString() {
    String stringSex = "female";
    String pronounOne = "She";
    String pronounTwo = "her";
    if (this.sex == Sex.M) {
      stringSex = "male";
      pronounOne = "He";
      pronounTwo = "his";
    }
    String finalString = String.format("Meet %s. %s is a %s %s monkey, and %s favorite " +
            "food is %s. \n", this.name, pronounOne, stringSex, this.species, pronounTwo, this.favFood);
    return finalString;
  }

  /**
   * A class that updates the monkey's information if it goes in for a medical check.
   * @return void
   */
  public void medUpdate(String name, Animal.MonkeySpecies species, Animal.Sex sex, int size,
                        Double weight, int age, Animal.FavFood favFood) {
    setName(name);
    setSpecies(species);
    setSex(sex);
    setSize(size);
    setWeight(weight);
    setAge(age);
    setFavFood(favFood);
  }



}