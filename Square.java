import processing.core.*;
import java.util.*;

public class Square {

  public Square(float squareX, float squareY, float j, float i, int colorTheme, PApplet p)
  {
      item = null;
      this.i = i;
      this.j = j;
      this.p = p;
      this.squareX = squareX;
      this.squareY = squareY;
      this.colorTheme = colorTheme;

      if(colorTheme == 0){ //BLUE, WHITE, PURPLE, PINK THEME
        int randomValue1 = (int) p.random(1, 101);
        if(randomValue1 <= 25){
          colorOfSquare = p.color(151,229,239);
        }
        else if(randomValue1 <= 50 && randomValue1 >= 26){
          colorOfSquare = p.color(255,240,245);
        }
        else if (randomValue1 <= 75 && randomValue1 >= 51){
          colorOfSquare = p.color(248,225,244);
        }
        else{
          colorOfSquare = p.color(239,168,228);
        }
      }
      else if(colorTheme == 1){ //GREEN PALETTE
        int randomValue2 = (int) p.random(1, 101);
        if(randomValue2 <= 25){
          colorOfSquare = p.color(153,255,178);
        }
        else if(randomValue2 <= 50 && randomValue2 >= 26){
          colorOfSquare = p.color(122,204,143);
        }
        else if (randomValue2 <= 75 && randomValue2 >= 51){
          colorOfSquare = p.color(92,153,107);
        }
        else{
          colorOfSquare = p.color(61,102,71);
        }
      }
      else if(colorTheme == 2){ //BLUE PALETTE
        int randomValue3 = (int) p.random(1, 101);
        if(randomValue3 <= 25){
          colorOfSquare = p.color(92,181,225);
        }
        else if(randomValue3 <= 50 && randomValue3 >= 26){
          colorOfSquare = p.color(62,164,240);
        }
        else if (randomValue3 <= 75 && randomValue3 >= 51){
          colorOfSquare = p.color(93,151,231);
        }
        else{
          colorOfSquare = p.color(153,192,227);
        }
      }
      else{ //RAINBOW
        int randomValue4 = (int) p.random(6);
        if(randomValue4 == 0){
          colorOfSquare = p.color(204, 153, 201);
        }
        else if(randomValue4 == 1){
          colorOfSquare = p.color(158, 193, 207);
        }
        else if(randomValue4 == 2){
          colorOfSquare = p.color(158, 224, 158);
        }
        else if(randomValue4 == 3){
          colorOfSquare = p.color(253, 253, 151);
        }
        else if(randomValue4 == 4){
          colorOfSquare = p.color(254, 177, 68);
        }
        else{
          colorOfSquare = p.color(255, 102, 99);
        }

      }

  }

  public void display()
  {
    p.stroke(0);
    //p.strokeWeight(2);
    p.noStroke();
    p.fill(colorOfSquare);
    p.rect(squareX, squareY, Square.getSquareSize(), Square.getSquareSize(), 10);
    if(item != null){
      item.display(squareX + Item.getItemOffset(), squareY + Item.getItemOffset());
    }
  }

  public static int getSquareSize(){
    return squareSize;
  }

  public float getSquareX(){
    return squareX;
  }
  public float getSquareY(){
    return squareY;
  }

  public void addItem(Item x){
    item = x;
  }
  public void removeItem(){
    item = null;
  }

  public Item getSquareItem(){
    return item;
  }

  public float getI(){
    return i;
  }
  public float getJ(){
    return j;
  }

  private PApplet p;
  private float squareX;
  private float squareY;
  private int colorOfSquare;
  public static final int squareSize = 40;
  private Item item;
  private float i;
  private float j;
  private int colorTheme;
}
