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
 * A JUnit test class for the Enclosure class.
 */

public class EnclosureTest {
  private Monkey clarence;
  private Monkey tabitha;
  private Monkey george;
  private Monkey ada;
  private Enclosure one;
  private List<Isolation> isolationCages;
  private List<Enclosure> enclosures;
  private PrimateSanctuary mySanctuary;


  @Before
  public void setUp() throws Exception {
    one = new Enclosure (1, 25);
    clarence = new Monkey(1, "Clarence", Animal.MonkeySpecies.SAKI, Animal.Sex.M, 25,
            3.5, 1, Animal.FavFood.NUTS, null);
    tabitha = new Monkey(2, "Tabitha", Animal.MonkeySpecies.SAKI, null, 25, 4.3, 4, Animal.FavFood.NUTS, null);
    george = new Monkey(3, null, Animal.MonkeySpecies.SAKI, null, 25, 0, 0, null, null);
    ada = new Monkey(4, "Ada", Animal.MonkeySpecies.DRILL, Animal.Sex.F, 25,
            3.5, 1, Animal.FavFood.NUTS, null);
    isolationCages = new ArrayList<Isolation>();
    enclosures = Arrays.asList(one);
    mySanctuary = new PrimateSanctuary (1200, isolationCages, enclosures);
  }

  @Test
  public void testGetId() {
    assertEquals(1, one.getId());
  }

  @Test
  public void testGetSize() {
    assertEquals(25, one.getSize());
  }

  @Test
  public void testGetAvailability() {
    assertEquals(true, one.getAvailability(clarence));
    one.addAnimal(clarence);
    one.addAnimal(tabitha);
    assertEquals(false, one.getAvailability(george));
  }

  @Test
  public void testGetAnimals() {
    one.addAnimal(clarence);
    one.addAnimal(tabitha);
    List<Animal> animals = Arrays.asList(clarence, tabitha);
    assertEquals(animals, one.getAnimals());
  }

  @Test
  public void testGetMonkeySpecies() {
    one.addAnimal(clarence);
    assertEquals(Animal.MonkeySpecies.SAKI, one.getMonkeySpecies());
  }

  @Test
  public void testAddAnimal() {
    one.addAnimal(clarence);
    one.addAnimal(ada);
    List<Animal> animals = Arrays.asList(clarence);
    assertEquals(animals, one.getAnimals());
  }

  @Test
  public void testIdToString() {
    assertEquals("E1", one.idToString());
  }

  @Test
  public void testMakeSign() {
    one.addAnimal(clarence);
    one.addAnimal(tabitha);
    assertEquals("Meet Clarence. He is a male SAKI monkey, and his favorite food is " +
            "NUTS. \nMeet Tabitha. She is a female SAKI monkey, and her favorite food is NUTS. \n",
            one.makeSign());
  }
}