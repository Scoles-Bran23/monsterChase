import processing.core.*;
import java.util.*;
public class Person {


  public Person(float i, float j, ProjectSem1RPG p)
  {

      this.i = i;
      this.j = j;
      this.p = p;


      person = p.loadImage("person.png");
      personRight = p.loadImage("pr.png");
      personLeft = p.loadImage("pl.png");
      personStand = p.loadImage("pf.png");

      person.resize(Square.getSquareSize(), Square.getSquareSize());
      personRight.resize(Square.getSquareSize(), Square.getSquareSize());
      personLeft.resize(Square.getSquareSize(), Square.getSquareSize());
      personStand.resize(Square.getSquareSize(), Square.getSquareSize());

      myItems = new ArrayList<Item>();
      for(int count = 0; count < 12; count++){
        myItems.add(new Item(this.p, 1));
      }

  }

  public void display()
  {
    //p.imageMode(p.CENTER);
    p.imageMode(p.CORNER);
    //float x = j*Square.getSquareWidth() + offsetPersonPosition;
    //float y = i*Square.getSquareWidth() + offsetPersonPosition;
    float x = j*Square.getSquareSize();
    float y = i*Square.getSquareSize();

    if(ProjectSem1RPG.getImagePosition() == 1){
      p.image(personLeft, x, y);
    }
    else if(ProjectSem1RPG.getImagePosition() == 2){
      p.image(personRight, x, y);
    }
    else{
      p.image(personStand, x, y);
    }
    //p.image(person, x, y);
  }

  public static float getI(){
    return i;
  }
  public static float getJ(){
    return j;
  }

  public void setI(float newI){
    i = newI;
  }
  public void setJ(float newJ){
    j = newJ;
  }

  public void increaseJ(){
    if(j < p.columnCount-1){
      j++;
    }
  }

  public void increaseI(){
    if(i < p.rowCount-1){
      i++;
    }
  }

  public void decreaseJ(){
    if(j > 0){
      j--;
    }
  }

  public void decreaseI(){
    if(i > 1){
      i--;
    }
  }

  public ArrayList<Item> getMyItems(){
    return myItems;
  }

  public void checkForItem(){
    for(Square square:p.getSquares()){
      if(this.getJ() == square.getJ() && this.getI() == square.getI() && square.getSquareItem() != null){
        myItems.add(square.getSquareItem());

        if(square.getSquareItem().getType() == 1){ //WATER
           /*if(p.getBusy() == false){
             if(p.getGulp().position() == p.getGulp().length()){
               p.getGulp().rewind();
               p.getGulp().play();
               //p.setBusyTrue();
             }
             else{
               p.getGulp().play();
               //p.setBusyTrue();
             }
           }
           p.setBusyTrue();*/

           p.getGulp().rewind();
           p.getGulp().play();
         }

        else if(square.getSquareItem().getType() == 0){ //STAR
          p.addToExtraScore(10);
          /*if(p.getBusy() == false){
            if(p.getStar().position() == p.getStar().length()){
              p.getStar().rewind();
              p.getStar().play();
              //p.setBusyTrue();
            }
            else{
              p.getStar().play();
              //p.setBusyTrue();
            }
          }
          p.setBusyTrue();*/
          p.getStar().rewind();
          p.getStar().play();

        }

        else if(square.getSquareItem().getType() == 2){/* //WEAPON


          if(p.getBusy() == false){
            if(p.getWeapon().position() == p.getWeapon().length()){
              p.getWeapon().rewind();
              p.getWeapon().play();
              //p.setBusyTrue();
            }
            else{
              p.getWeapon().play();
              //p.setBusyTrue();
            }
          }
          p.setBusyTrue();

          */
          p.getWeapon().rewind();
          p.getWeapon().play();

        }
        square.removeItem();
      }
    }
  }

/*
  public void noMoreItems(){
    for(int i = 0; i<myItems.size(); i++){
      myItems.remove(i);
    }
  }
*/


  private ProjectSem1RPG p;

  private PImage person;
  private PImage personRight;
  private PImage personLeft;
  private PImage personStand;

  private static float i;
  private static float j;
  private ArrayList<Item> myItems;
}
