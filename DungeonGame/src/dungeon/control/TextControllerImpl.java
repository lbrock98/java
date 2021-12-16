package dungeon.control;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import dungeon.model.Dungeon;
import dungeon.model.Location;
import dungeon.model.Monster;
import dungeon.model.Player;

/**
 * This class implements the TextController interface, and controls the text-based dungeon game,
 * using a dungeon model.
 */
public class TextControllerImpl implements TextController {
  private final Appendable out;
  private final Scanner scan;


  /**
   * Constructor for the text based controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   */
  public TextControllerImpl(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    this.scan = new Scanner(in);
  }

  @Override
  public void game(Dungeon dungeon) {
    Player player = dungeon.getPlayer();
    Location current = player.getLocation();
    Location end = dungeon.getEnd();
    try {
      out.append("Your dungeon has been created! This dungeon has a kind of monster called an " +
              "Otyugh. It takes two direct hits to kill an Otyugh.\nA healthy Otyugh will surely " +
              "kill you if you enter its cave, and an injured Otyugh will kill you 50% of the " +
              "time. Otyughs cannot move from their cave.\nHint: Look out for wolf pits! \n\n" +
              "You can now start the game! You are at the starting cave.\nYour options are to " +
              "move on, pickup treasure and any arrows you might find, or shoot.\n");
    }
    catch (IOException ioe) {
      throw new IllegalStateException("Otyugh message append failed", ioe);
    }

    while (current != end) {
      try {
        out.append("\nDoors lead to: " + current.getMoves().toString() + "\nMove, Pickup, or " +
                "Shoot (M-P-S)?\n");
      }
      catch (IOException ioe) {
        throw new IllegalStateException("Move options append failed", ioe);
      }
      String option = "";
      while (true) {
        option = scan.next().toLowerCase();
        switch (option) {
          case "m":
            current = move(dungeon, current);
            player.move(current);
            if (current.getMonster() != null) {
              Monster.Health health = current.getMonster().getHealth();
              try {
                out.append("There is a " + health + " monster in this dungeon!\n");
                switch (health.name()) {
                  case "HEALTHY":
                    out.append("You have been slain! Game over. \n");
                    return;
                  case "INJURED":
                    Random random = new Random();
                    if (random.nextBoolean()) {
                      out.append("You have been slain! Game over. \n");
                      return;
                    } else {
                      out.append("You have slain the Otyugh! Hurray! Continue on. \n");
                    }
                    break;
                  default:
                    out.append("This monster is already dead! Continue on. \n");
                    break;
                }
              } catch (IOException ioe) {
                throw new IllegalStateException("Meet Otyugh message fails.", ioe);
              }
            }
            break;
          case "p":
            pickup(dungeon, current, player);
            break;
          case "s":
            shoot(dungeon, current);
            break;
          default:
            try {
              out.append("This input is invalid. Please enter 'M', 'P', or 'S'.\n");
              continue;
            } catch (IOException ioe) {
              throw new IllegalStateException("Options message fails.", ioe);
            }
        }
        break;
      }
      if (!dungeon.findMonsters().name().equals("NONE")) {
        try {
          out.append("\nThere is a " + dungeon.findMonsters() + " smell of something terrible" +
                  " nearby...\n");
        } catch (IOException ioe) {
          throw new IllegalStateException("Smell message fails.", ioe);
        }
      }
    }
    try {
      out.append("You have successfully reached the end cave, slain the Otyugh, and won the game!" +
              "\nHere is what you were able to collect: \n" + dungeon.description(player));
    } catch (IOException ioe) {
      throw new IllegalStateException("Won game message fails.", ioe);
    }
  }

  private Location.Move getDirection(Set<Location.Move> moves) {
    String direction = "";
    Location.Move move = null;
    while (true) {
      direction = scan.next();
      try {
        move = Location.Move.valueOf(direction.toUpperCase());
        if (!moves.contains(move)) {
          throw new IllegalArgumentException("Not in list of moves");
        }
        return move;
      } catch (IllegalArgumentException iae) {
        try {
          out.append("This is not a valid direction, please try again: \n");
        } catch (IOException ioe) {
          throw new IllegalStateException("Invalid direction message fails.", ioe);
        }
      }
    }
  }

  private void shoot(Dungeon dungeon, Location loc) {
    Set<Location.Move> moves = loc.getMoves();
    Location.Move move = null;
    try {
      out.append("Please enter the direction in which you would like to shoot your arrow: \n");
      move = getDirection(moves);

    } catch (IOException ioe) {
      throw new IllegalStateException("Shoot arrow message fails.", ioe);
    }
    String distanceString = "";
    int distance = 0;
    while (true) {
      try {
        out.append("Please enter the distance that you would like to shoot your arrow: \n");
      }
      catch (IOException ioe) {
        throw new IllegalStateException("Shoot arrow distance message fails.", ioe);
      }
      distanceString = scan.next();
      try {
        distance = Integer.parseInt(distanceString);
        break;
      } catch (NumberFormatException nfe) {
        try {
          out.append("This input is invalid. Please enter your distance as a numeral. \n" +
                  "For example, you would write '1' instead of 'one'.\n");
          continue;
        } catch (IOException ioe) {
          throw new IllegalStateException("NumberFormat message fails.", ioe);
        }
      }
    }
    try {
      if (dungeon.arrowShot(move, distance)) {
        out.append("Congratulations! You have shot an Otyugh! \n");
      } else {
        out.append("Your arrow has missed an Otyugh. Better luck next time. \n");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Shot Otyugh message fails.", ioe);
    }
    catch (IllegalArgumentException iae) {
      try {
        out.append("This is not an invalid direction or distance for this location. \n");
        shoot(dungeon, loc);
      }
      catch (IOException ioe) {
        throw new IllegalStateException("Invalid direction for arrow message fails.", ioe);
      }
    }
  }

  private void pickup(Dungeon dungeon, Location loc, Player player) {
    try {
      out.append(dungeon.description(loc).split("\\r?\\n")[0] + "\n");
      if (loc.getTreasure().size() > 0) {
        out.append("Hurray! This treasure is now yours!\n");
        player.collectTreasure();
      }
      else {
        out.append("Better luck next time!\n");
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Location's treasure message fails.", ioe);
    }
    if (loc.getArrows() > 0) {
      try {
        out.append("This location also has an arrow that has been added to your quiver!\n");
        player.collectArrows();
      } catch (IOException ioe) {
        throw new IllegalStateException("Add arrow message fails.", ioe);
      }
    }
  }

  private Location move(Dungeon dungeon, Location loc) {
    Set<Location.Move> moves = loc.getMoves();
    try {
      out.append("Choose a direction to move in from the available doors:\n");
    } catch (IOException ioe) {
      throw new IllegalStateException("Direction message fails.", ioe);
    }
    Location.Move move = getDirection(moves);
    Location next = dungeon.newLoc(loc, move);
    return next;
  }

}
