package sanctuary;

import java.util.List;

/**
 * A sanctuary is represented by the number of isolation cages and enclosures it has, the animals
 * it houses, and its available funds.
 */

public interface Sanctuary {
  /**
   * Returns a double of the sanctuary's available funds.
   *
   * @return the fund
   */
  double getFunds();
  /**
   * Returns a list of isolation cages.
   *
   * @return the cageIds
   */
  List<Isolation> getCages();
  /**
   * Returns a list of enclosures.
   *
   * @return the enclosuresIds
   */
  List<Enclosure> getEnclosures();
  /**
   * Returns a string of the animals and where they're housed.
   *
   * @return animals
   */
  String getAnimalsHoused();
  /**
   * Returns a string of houses when you search for a species
   *
   * @return houses
   */
  String speciesLookup(Monkey.MonkeySpecies species);
  /**
   * Returns a shopping list of how much food to buy, so that every animal in the sanctuary can
   * have their favorite food.
   * @return shopping list
   */
  String shoppingList();

  /**
   * Creates and returns a new animal. This animal is given an id number and placed in an isolation
   * cage, if one is available.
   * @return animal
   */
  Animal newAnimal();

  /**
   * Moves monkey to an enclosure if one is available.
   * @return void
   */
  void rehouseAnimal(Animal animal);
}