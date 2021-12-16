package dungeon.view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import dungeon.model.Location;
import dungeon.model.ReadOnlyDungeon;

class GameGrid extends JPanel {
  private final ReadOnlyDungeon model;
  private final int width;
  private final int height;
  private int[] playerLoc;

  GameGrid(ReadOnlyDungeon model) {
    this.model = model;
    this.width = model.getDimensions()[0];
    this.height = model.getDimensions()[1];
    this.setSize(width + 20, height + 20);
    this.setLayout(new FlowLayout());
    this.playerLoc = new int[4];
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    //draw table
    int x = 10;
    int y = 10 + ((height - 1) * 60);
    for (int i = 0; i < this.width; i++) {
      for (int j = 0; j < this.height; j++) {
        Location loc = model.getLocation(i, j);
        if (loc == null) {
          throw new IllegalStateException("No locations should be null");
        }
        else if (loc.beenVisited()) {
          if (loc.getMonster() != null) {
            g2d.setColor(Color.GREEN);
            //ImageIcon monster = new ImageIcon("../../../res/images/monster.jpg");
            //monster.paintIcon(this, g, x+10, y);
            //g2d.drawImage(monster.getImage(), x + 10, y, 40, 40, null);
            g2d.setFont(new Font("Serif", Font.PLAIN, 40));
            g2d.drawString("M", x + 12, y + 30);
          }
          if (loc.hasPit()) {
            g2d.setColor(Color.DARK_GRAY);
            g2d.setFont(new Font("Serif", Font.PLAIN, 40));
            g2d.drawString("P", x + 18, y + 30);
          }
          if (loc.hasThief()) {
            g2d.setColor(Color.ORANGE);
            g2d.setFont(new Font("Serif", Font.PLAIN, 40));
            g2d.drawString("T", x + 18, y + 30);
          }
          if (loc.getTreasure().size() > 0) {
            g2d.setColor(Color.MAGENTA);
            g2d.setFont(new Font("Serif", Font.PLAIN, 18));
            g2d.drawString("t", x + 2, y + 58);
          }
          if (loc.getArrows() > 0) {
            g2d.setColor(Color.CYAN);
            g2d.setFont(new Font("Serif", Font.PLAIN, 18));
            g2d.drawString("a", x + 42, y + 58);
          }
          g2d.setColor(Color.BLACK);
          g2d.setStroke(new BasicStroke(2));
          g2d.drawRect(x, y, 60, 60);

          g2d.setStroke(new BasicStroke(2));
          g2d.setColor(Color.YELLOW);
          Set<Location.Move> moves = loc.getMoves();
          Iterator<Location.Move> iterator = moves.iterator();
          while (iterator.hasNext()) {
            String name = iterator.next().name();
            switch (name) {
              case "NORTH":
                g2d.draw(new Line2D.Float(x, y, x + 60, y));
                break;
              case "SOUTH":
                g2d.draw(new Line2D.Float(x, y + 60, x + 60, y + 60));
                break;
              case "EAST":
                g2d.draw(new Line2D.Float(x + 60, y, x + 60, y + 60));
                break;
              case "WEST":
                g2d.draw(new Line2D.Float(x, y, x, y + 60));
                break;
              default:
                throw new IllegalStateException("A move should be one of these four");
            }
          }
        }
        else {
          g2d.setColor(Color.BLACK);
          g2d.fillRect(x, y, 60, 60);
        }
        if (loc == this.model.getPlayer().getLocation()) {
          g2d.setColor(Color.RED);
          g2d.setStroke(new BasicStroke(4));
          g2d.drawRect(x, y, 60, 60);
          this.playerLoc[0] = x;
          this.playerLoc[1] = y;
          this.playerLoc[2] = x + 60;
          this.playerLoc[3] = y + 60;
        }
        if (loc == this.model.getEnd() && this.model.getEnd().beenVisited()) {
          g2d.setColor(Color.GREEN);
          g2d.setStroke(new BasicStroke(4));
          g2d.drawRect(x, y, 60, 60);
        }
        y -= 60;
      }
      y = 10 + ((height - 1) * 60);
      x += 60;
    }
  }

  //package private class
  int[] getPlayerLoc() {
    int[] playerLoc = this.playerLoc;
    return playerLoc;
  }
}