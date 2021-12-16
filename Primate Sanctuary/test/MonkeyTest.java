import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import sanctuary.Animal;
import sanctuary.Enclosure;
import sanctuary.Isolation;
import sanctuary.Monkey;
import sanctuary.PrimateSanctuary;

import static org.junit.Assert.assertEquals;
/**
 * A JUnit test class for the Monkey class.
 */

public class MonkeyTest {
  private Monkey clarence;
  private Monkey tabitha;
  private Isolation a;
  private Isolation b;
  private Enclosure one;
  private List<Isolation> isolationCages;
  private List<Enclosure> enclosures;
  private PrimateSanctuary mySanctuary;

  @org.junit.Before
  public void setUp() throws Exception {
    a = new Isolation (1);
    b = new Isolation (2);
    one = new Enclosure (1, 60);
    clarence = new Monkey(1, "Clarence", Animal.MonkeySpecies.SAKI, Animal.Sex.M, 15,
            3.5, 1, Animal.FavFood.NUTS, one);
    tabitha = new Monkey(0, null, null, null, 0, 0, 0, null, null);
    isolationCages = Arrays.asList(a,b);
    enclosures = Arrays.asList(one);
    mySanctuary = new PrimateSanctuary (1200, isolationCages, enclosures);
  }

  @Test
  public void testGetId() {
    assertEquals(1, clarence.getId());
  }
  @Test
  public void testGetName() {
    assertEquals("Clarence", clarence.getName());
  }

  @Test
  public void testGetMonkeySpecies() {
    assertEquals(Animal.MonkeySpecies.SAKI, clarence.getMonkeySpecies());
  }

  @Test
  public void testGetSex() {
    assertEquals(Animal.Sex.M, clarence.getSex());
  }

  @Test
  public void testGetSize() {
    assertEquals(15, clarence.getSize());
  }

  @Test
  public void testGetFavFood() {
    assertEquals(Animal.FavFood.NUTS, clarence.getFavFood());
  }

  @Test
  public void testGetHousing() {
    assertEquals(one, clarence.getHousing());
    /*
    isolationCages.add(a);
    isolationCages.add(b);
    enclosures.add(one);
    Monkey monkey = (Monkey) mySanctuary.newAnimal();
    Monkey monkeyTwo = (Monkey) mySanctuary.newAnimal();

    assertEquals(a, monkey.getHousing());
    assertEquals(b, monkeyTwo.getHousing());

    monkey.medUpdate("Tabitha", Monkey.MonkeySpecies.GUEREZA, Monkey.Sex.F, 20, 4.2,
            3, Monkey.FavFood.INSECTS);
    assertEquals(one, monkey.getHousing());
    //assertEquals(monkey, one.getMonkeys());
    assertNotEquals(null, mySanctuary);
     */
  }

  @Test
  public void testSetHousing() {
    clarence.setHousing(a);
    assertEquals(a, clarence.getHousing());
  }

  @Test
  public void testMonkeyString() {
    assertEquals("Meet Clarence. He is a male SAKI monkey, and his favorite food is NUTS. \n",
            clarence.monkeyString());
  }

  @Test
  public void testMedUpdate() {
    tabitha.medUpdate("Tabitha", Animal.MonkeySpecies.HOWLER, Animal.Sex.F, 20,
            6.1, 2, Animal.FavFood.LEAVES);
    assertEquals("Meet Tabitha. She is a female HOWLER monkey, and her favorite food is LEAVES. \n",
            tabitha.monkeyString());
    assertEquals(20, tabitha.getSize());
    assertEquals(6.1, tabitha.getWeight(), .001);
    assertEquals(2, tabitha.getAge());
  }
}