package sanctuary;

import javafx.util.Pair;

/**
 * An animal is represented by its id number, name, species, size, sex, favorite food, and
 * housing placement.
 */

public interface Animal {
  //enums defining monkey species, sex, and favorite food
  enum MonkeySpecies {
    DRILL, GUEREZA, HOWLER, MANGABEY, SAKI, SPIDER, SQUIRREL, TAMARIN;
  }

  enum Sex {
    F, M;
  }

  enum FavFood {
    EGGS, FRUITS, INSECTS, LEAVES, NUTS, SEEDS, TREE_SAP;
  }

  /**
   * Returns an integer, which represents the ID numbers of the animal.
   *
   * @return the id
   */
  int getId();

  /**
   * Returns a string, which is the name of the animal.
   *
   * @return the name
   */
  String getName();

  /**
   * @return the monkey's species
   */
  MonkeySpecies getMonkeySpecies();

  /**
   * Returns a char, either F for female or M for male.
   *
   * @return the sex
   *
   * @throws IllegalArgumentException if argument is not defined in enum.
   */
  Sex getSex();

  /**
   * Returns an integer, approximating the size of the monkey in centimeters.
   *
   * @return the size
   */
  int getSize();

  /**
   * Returns an double, representing the monkey's weight in lb.
   *
   * @return the weight
   */
  double getWeight();

  /**
   * @return the monkey's age
   */
  int getAge();

  /**
   * A class that outputs some information about the monkey.
   * @return String saying "Meet NAME. (S)he is a SEX SPECIES monkey, and her(his) favorite food
   * is FAVFOOD."
   *
   * @throws IllegalArgumentException if argument is not defined in enum.
   */
  FavFood getFavFood();

  /**
   * Returns a housing unit.
   *
   * @return the housing
   */
  Housing getHousing();

  /**
   * A class that sets the housing for the monkey.
   *
   * @return void
   */
  Void setHousing(Housing housing);

  /**
   * A class that updates the monkey's information if it goes in for a medical check.
   *
   * @return void
   */
  void medUpdate(String name, Animal.MonkeySpecies species, Animal.Sex sex, int size,
                 Double weight, int age, Animal.FavFood favFood);

}