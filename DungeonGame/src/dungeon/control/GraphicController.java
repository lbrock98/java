package dungeon.control;


/**
 * Represents a controller for the dungeon game. Handles user inputs by executing the model.
 */
public interface GraphicController extends TextController {

  /**
   * Sets up the game by launching the prelaunchView, which gets arguments from the user to
   * create a model which is then passed into the View.
   */
  void setUp();

  /**
   * Gets the coordinates of where a mouse is clicked on the view.
   * @param xClicked    xCoord
   * @param yClicked    yCoord
   */
  void mouseClick(int xClicked, int yClicked);

}