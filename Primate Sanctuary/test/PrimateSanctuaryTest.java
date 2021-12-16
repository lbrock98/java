import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sanctuary.Animal;
import sanctuary.Enclosure;
import sanctuary.Isolation;
import sanctuary.Monkey;
import sanctuary.PrimateSanctuary;

import static org.junit.Assert.assertEquals;
/**
 * A JUnit test class for the PrimateSanctuary class.
 */

public class PrimateSanctuaryTest {
  private Monkey clarence;
  private Monkey tabitha;
  private Monkey george;
  private Monkey ada;
  private Isolation a;
  private Isolation b;
  private Isolation c;
  private Enclosure one;
  private Enclosure two;
  private List<Isolation> isolationCages;
  private List<Enclosure> enclosures;
  private PrimateSanctuary mySanctuary;

  @Before
  public void setUp() throws Exception {
    a = new Isolation (1);
    b = new Isolation (2);
    c = new Isolation (3);
    one = new Enclosure (1, 25);
    two = new Enclosure(2, 50);
    clarence = new Monkey(1, "Clarence", Animal.MonkeySpecies.DRILL, Animal.Sex.M,
            25, 3.5, 1, Animal.FavFood.NUTS, null);
    tabitha = new Monkey(2, "Tabitha", Animal.MonkeySpecies.SAKI, null, 25,
            4.3, 4, Animal.FavFood.NUTS, null);
    george = new Monkey(3, null, Animal.MonkeySpecies.SAKI, null, 25,
            0, 0, null, null);
    ada = new Monkey(3, "Ada", Monkey.MonkeySpecies.SAKI, Monkey.Sex.F, 30,
            6.1, 2, Monkey.FavFood.TREE_SAP, one);
    isolationCages = Arrays.asList(a,b,c);
    enclosures = Arrays.asList(one, two);
    mySanctuary = new PrimateSanctuary (1200, isolationCages, enclosures);
  }

  @Test
  public void testGetFunds() {
    assertEquals(1200, mySanctuary.getFunds(), .001);
  }


  @Test
  public void testGetCages() {
    List<Isolation> cages = Arrays.asList(a,b,c);
    assertEquals(cages, mySanctuary.getCages());
  }

  @Test
  public void testGetEnclosures() {
    List<Enclosure> enclosures = Arrays.asList(one, two);
    assertEquals(enclosures, mySanctuary.getEnclosures());
  }

  @Test
  public void testGetAnimalsHoused() {
    Monkey amy = (Monkey) mySanctuary.newAnimal();
    Monkey bobo = (Monkey) mySanctuary.newAnimal();
    Monkey emu = (Monkey) mySanctuary.newAnimal();
    amy.medUpdate("Amy", Animal.MonkeySpecies.DRILL, Animal.Sex.F, 40, 9.3,
            5, Animal.FavFood.EGGS);
    bobo.medUpdate("Bobo", Animal.MonkeySpecies.SPIDER, Animal.Sex.M, 30, 4.5,
            2, Animal.FavFood.NUTS);
    emu.medUpdate("Emu", Animal.MonkeySpecies.DRILL, Animal.Sex.M, 30, 4.5,
            2, Animal.FavFood.NUTS);
    mySanctuary.rehouseAnimal(amy);
    assertEquals("Amy, E1\nBobo, I2\nEmu, I3\n", mySanctuary.getAnimalsHoused());
  }

  @Test
  public void testGetSpecies() {
    Monkey amy = (Monkey) mySanctuary.newAnimal();
    Monkey bobo = (Monkey) mySanctuary.newAnimal();
    Monkey emu = (Monkey) mySanctuary.newAnimal();
    amy.medUpdate("Amy", Animal.MonkeySpecies.DRILL, Animal.Sex.F, 40, 9.3,
            5, Animal.FavFood.EGGS);
    bobo.medUpdate("Bobo", Animal.MonkeySpecies.SPIDER, Animal.Sex.M, 30, 4.5,
            2, Animal.FavFood.NUTS);
    emu.medUpdate("Emu", Animal.MonkeySpecies.DRILL, Animal.Sex.M, 30, 4.5,
            2, Animal.FavFood.NUTS);
    assertEquals("DRILL: I1, I3, \nSPIDER: I2, \n", mySanctuary.getSpecies());
  }

  @Test
  public void testSpeciesLookup() {
    Monkey amy = (Monkey) mySanctuary.newAnimal();
    Monkey bobo = (Monkey) mySanctuary.newAnimal();
    Monkey emu = (Monkey) mySanctuary.newAnimal();
    amy.medUpdate("Amy", Animal.MonkeySpecies.DRILL, Animal.Sex.F, 40, 9.3,
            5, Animal.FavFood.EGGS);
    bobo.medUpdate("Bobo", Animal.MonkeySpecies.SPIDER, Animal.Sex.M, 30, 4.5,
            2, Animal.FavFood.NUTS);
    emu.medUpdate("Emu", Animal.MonkeySpecies.DRILL, Animal.Sex.M, 30, 4.5,
            2, Animal.FavFood.NUTS);
    mySanctuary.rehouseAnimal(amy);
    assertEquals("I3, E1, ", mySanctuary.speciesLookup(Animal.MonkeySpecies.DRILL));
  }

  @Test
  public void testShoppingList() {
    Monkey amy = (Monkey) mySanctuary.newAnimal();
    Monkey bobo = (Monkey) mySanctuary.newAnimal();
    Monkey emu = (Monkey) mySanctuary.newAnimal();
    amy.medUpdate("Amy", Animal.MonkeySpecies.DRILL, Animal.Sex.F, 40, 9.3,
            5, Animal.FavFood.EGGS);
    bobo.medUpdate("Bobo", Animal.MonkeySpecies.SPIDER, Animal.Sex.M, 30, 4.5,
            2, Animal.FavFood.NUTS);
    emu.medUpdate("Emu", Animal.MonkeySpecies.DRILL, Animal.Sex.M, 30, 4.5,
            2, Animal.FavFood.NUTS);
    assertEquals("EGGS, 500gr \nNUTS, 1000gr \n", mySanctuary.shoppingList());
  }

  @Test
  public void testNewAnimal() {
    Monkey amy = (Monkey) mySanctuary.newAnimal();
    assertEquals("Unnamed, I1\n", mySanctuary.getAnimalsHoused());
  }

  @Test
  public void testRehouseAnimal() {
    Monkey amy = (Monkey) mySanctuary.newAnimal();
    assertEquals(a, amy.getHousing());
    mySanctuary.rehouseAnimal(amy);
    assertEquals(one, amy.getHousing());
  }
}