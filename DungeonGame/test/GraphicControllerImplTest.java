import org.junit.Test;

import dungeon.control.GraphicController;
import dungeon.control.GraphicControllerImpl;
import dungeon.model.DungeonModel;
import dungeon.view.PrelaunchView;
import dungeon.view.PrelaunchViewImpl;

/**
 * This is the test class for the graphic controller interface. It tests if the model passed to the
 * view throws an exception when the model is null.
 */
public class GraphicControllerImplTest {

  @Test(expected = IllegalStateException.class)
  public void game() {
    PrelaunchView prelaunchView = new PrelaunchViewImpl();
    GraphicController cont = new GraphicControllerImpl(prelaunchView);
    DungeonModel model = null;
    cont.game(model);
  }
}