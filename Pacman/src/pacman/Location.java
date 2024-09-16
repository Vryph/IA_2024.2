/**
 * 
 */
package pacman;

import java.util.Collection;

/**
 * A class representing a location on the Pac-Man game board, with x and y coordinates.
 * @author grenager
 *
 */
public class Location {

  int x;

  int y;

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean equals(Object o) {
    if (!(o instanceof Location))
      return false;
    Location l = (Location) o;
    if (l.x != x)
      return false;
    if (l.y != y)
      return false;
    return true;
  }

  public int hashCode() {
    return x * 37 + y;
  }
  
  public String toString() {
    return "("+x+","+y+")";
  }

  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static double euclideanDistance(Location a, Location b) {
    // sqrt(x^2 + y^2)
    return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY()));
  }

  public static double manhattanDistance(Location a, Location b) {
    return Math.abs(a.getX()-b.getX())+Math.abs(a.getY()-b.getY());
  }
  
  public static double euclideanDistanceToClosest(Location a, Collection<Location> c) {
    double minDistance = Double.POSITIVE_INFINITY;
    for (Location b : c) {
      double distance = euclideanDistance(a, b);
      if (distance<minDistance) {
        minDistance = distance;
      }
    }
    return minDistance;
  }

  public static double manhattanDistanceToClosest(Location a, Collection<Location> c) {
    double minDistance = Double.POSITIVE_INFINITY;
    for (Location b : c) {
      double distance = manhattanDistance(a, b);
      if (distance<minDistance) {
        minDistance = distance;
      }
    }
    return minDistance;
  }
  
  public static boolean checkForCorners(Location pacManPosition) {
	  //System.out.println(pacManPosition); - Prints every PACMAN position
	  double minDistance = Double.POSITIVE_INFINITY; 
	  Location[] Corners = new Location[4];
	  Corners[0] = new Location(25, 0);
	  Corners[1] = new Location(0, 0);
	  Corners[2] = new Location(0, 28);
	  Corners[3] = new Location(25, 28);
	  for (int i=0; i<Corners.length; i++) {
		double cornerDistance = Location.euclideanDistance(pacManPosition, Corners[i]);
	   	if(cornerDistance < minDistance) minDistance = cornerDistance;
	  }
	  if(minDistance < 3) return true;
	  else return false;
  }
  
  public static boolean checkForPortals(Location ghostPosition, int distance) {
	  //System.out.println(pacManPosition); - Prints every PACMAN position
	  double minDistance = Double.POSITIVE_INFINITY; 
	  Location[] Portals = new Location[2];
	  Portals[0] = new Location(3, 15);
	  Portals[1] = new Location(22, 15);

	  for (int i=0; i<Portals.length; i++) {
		double cornerDistance = Location.euclideanDistance(ghostPosition, Portals[i]);
	   	if(cornerDistance < minDistance) minDistance = cornerDistance;
	  }
	  if(minDistance > distance) return true;
	  else return false;
  }

}