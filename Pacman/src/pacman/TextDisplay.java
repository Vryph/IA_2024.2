package pacman;

public class TextDisplay implements Display {

  public void updateDisplay(Game game) {
    System.out.println("---------------------------------------------------");
    System.out.println("Time:   " + game.getTime());
    System.out.println("Level:  " + game.getLevel());
    System.out.println("Points: " + game.getPoints());
    System.out.println("Lives:  " + game.getLives());
    System.out.println("---------------------------------------------------");
    System.out.println(game);
    if (Game.isFinal(game.getCurrentGameState()))   System.out.println("                       GAME OVER");
    if (Game.isWinning(game.getCurrentGameState())) System.out.println("                      Pac-Man wins!");
    if (Game.isLosing(game.getCurrentGameState()))  System.out.println("                      Pac-Man loses!");
  }

}
