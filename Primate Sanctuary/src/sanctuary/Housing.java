package sanctuary;

import javafx.util.Pair;

/**
 * A housing unit is represented by its id, the species it houses, the number of spaces for an
 * animal it has available, and its size.
 */

public interface Housing {

  /**
   * Returns an integer, which represents the ID of the isolation cage.
   * @return id
   */
  int getId();

  /**
   * Returns an int, which is the number of square meters in the housing unit.
   *
   * @return the size
   */
  int getSize();

  /**
   * Returns a string, which is the species housed in the housing unit. Returns null if the housing
   * unit is empty.
   *
   * @return the species
   *
   * @throws IllegalArgumentException if argument is not defined in enum.
   */
  Animal.MonkeySpecies getMonkeySpecies();

  /**
   * Combines a letter indicating the type of housing with the housing unit's id number. Used to
   * output the housing of each animal.
   *
   * @return id as a string
   */
  String idToString();
}