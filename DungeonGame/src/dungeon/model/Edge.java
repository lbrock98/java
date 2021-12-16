package dungeon.model;

//package private class, used to create the dungeon
class Edge {
  private int oneX;
  private int oneY;
  private int twoX;
  private int twoY;

  Edge(int oneX, int oneY, int twoX, int twoY) {
    this.oneX = oneX;
    this.oneY = oneY;
    this.twoX = twoX;
    this.twoY = twoY;
  }

  //methods used for debugging
  /*
  String edgeString() {
    String output = "[" + (oneX - 1) + ", " + (oneY - 1) + ", " + (twoX - 1) + ", " +
    (twoY - 1) + "]";
    return output;
  }

  boolean edgeEquals(Object other) {
    if (!(other instanceof Edge)) {
      return false;
    }
    Edge o = (Edge) other;
    if ((this.oneX == o.getVertices()[0] && this.oneY == o.getVertices()[1] &&
            this.twoX == o.getVertices()[2] && this.twoY == o.getVertices()[3]) ||
            (this.oneX == o.getVertices()[2] && this.oneY == o.getVertices()[3] &&
                    this.twoX == o.getVertices()[0] && this.twoY == o.getVertices()[1])) {
      return true;
    }
    return false;
  }
   */

  int[] getVertices() {
    return new int[]{oneX, oneY, twoX, twoY};
  }

}