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
 * A JUnit test class for the Isolation class.
 */

public class IsolationTest {
  private Isolation a;
  private Isolation b;
  private Isolation c;
  private PrimateSanctuary mySanctuary;
  private List<Isolation> isolationCages;
  private List<Enclosure> enclosures;
  private Monkey clarence;

  @Before
  public void setUp() throws Exception {
    a = new Isolation (1);
    b = new Isolation (2);
    c = new Isolation (3);
    isolationCages = Arrays.asList(a,b, c);
    enclosures = new ArrayList<Enclosure>();
    mySanctuary = new PrimateSanctuary (1200, isolationCages, enclosures);
  }

  @Test
  public void testGetId() {
    assertEquals(1, a.getId());
    assertEquals(2, b.getId());
  }

  @Test
  public void testGetSize() {
    assertEquals(10, a.getSize());
    assertEquals(10, b.getSize());
    assertEquals(10, c.getSize());
  }

  @Test
  public void testGetAvailability() {
    assertEquals(true, a.getAvailability());
    Animal clarence = mySanctuary.newAnimal();
    assertEquals(false, a.getAvailability());
  }

  @Test
  public void testGetAnimal() {
    Animal clarence = mySanctuary.newAnimal();
    assertEquals(clarence, a.getAnimal());
    assertEquals(null, c.getAnimal());
  }

  @Test
  public void testGetMonkeySpecies() {
    Animal clarence = mySanctuary.newAnimal();
    clarence.medUpdate("Clarence", Animal.MonkeySpecies.DRILL, Animal.Sex.M, 5, 0.8, 0,
            Animal.FavFood.EGGS);
    assertEquals(Animal.MonkeySpecies.DRILL, a.getMonkeySpecies());
  }

  @Test
  public void testSetAnimal() {
    Animal clarence = mySanctuary.newAnimal();
    c.setAnimal(clarence);
    assertEquals(clarence, c.getAnimal());
  }

  @Test
  public void testIdToString() {
    assertEquals("I1", a.idToString());
  }

}