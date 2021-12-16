package dungeon.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import dungeon.control.GraphicController;
import dungeon.model.Location;

/**
 * Interface for a view for the dungeon game. Creates a menu, panel, game view and instructions.
 */
public interface View {

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  /**
   * Signal the view to draw itself.
   */
  void refresh();

  /**
   * Transmit an error message to the view, in case
   * the command could not be processed correctly.
   * @param error   error message that will be shown
   */
  void showErrorMessage(String error);

  /**
   * Provide the view with an action listener and key listener.
   * @param clicks  action listener
   * @param keys    key listener
   * @param cont    controller
   */
  void addListeners(ActionListener clicks, KeyListener keys, GraphicController cont);

  /**
   * Sets the jframe to not visible. This "closes" the window, but does not end the game.
   */
  void close();

  /**
   * Returns the last key pressed, so the controller knows to shoot or move.
   * @return  character of last key pressed
   */
  Character lastKey();

  /**
   * Returns the location pressed by the user.
   * @return    move, as translated by the direction keys
   */
  Location.Move getDirection();

  /**
   * Sets a line of instructions in the view.
   * @param text  instructions in view
   */
  void setInstructions(String text);

  /**
   * Disables listeners that would enable the player to continue playing the game.
   * @param keys    listener that is being removed
   */
  void disableSomeListeners(KeyListener keys);

  /**
   * Returns the coordinates of the most recent click to the screen.
   * @return
   */
  int[] getClick();
}
