import processing.core.*;
import java.util.*;
public class Item {


  public Item(ProjectSem1RPG p, int type)
  {
      this.p = p;
      this.type = type;

      star = p.loadImage("star.png");
      star.resize(Square.getSquareSize()/2, Square.getSquareSize()/2);
      water = p.loadImage("water.png");
      water.resize(Square.getSquareSize()/2, Square.getSquareSize()/2);
      weapon = p.loadImage("sword.png");
      weapon.resize(Square.getSquareSize()/2, Square.getSquareSize()/2);

      /*
      randomX = (int) p.random(0, 10);
      randomY = (int) p.random (2, 10);
      */

      randomX = (int) p.random(0, p.columnCount);
      randomY = (int) p.random (1, p.rowCount);


      itemPositionX = randomX * Square.getSquareSize() + offsetItemPosition;
      itemPositionY = randomY * Square.getSquareSize() + offsetItemPosition;

  }

  public void display(float x, float y)
  {
    p.imageMode(p.CENTER);
    if (type == 0){
      p.image(star, x, y);
    }
    else if(type == 1){
        p.image(water, x, y);
    }
    else if(type == 2){
      p.image(weapon, x, y);
    }

  }

  public int getType()
  {
    return type;
  }

  public int getRandomX(){
    return randomX;
  }

  public int getRandomY(){
    return randomY;
  }

  public static float getItemOffset(){
    return offsetItemPosition;
  }

  public String toString(){
    return "item type " + type;
  }

  private ProjectSem1RPG p;
  private PImage star;
  private PImage water;
  private PImage weapon;
  private float itemPositionX;
  private float itemPositionY;
  private int randomX;
  private int randomY;
  private int type;
  private static final float offsetItemPosition = Square.getSquareSize()/2;

}
