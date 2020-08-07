import processing.core.*;
import java.util.*;
public class Monster {

  public Monster(float i, float j, ProjectSem1RPG p)
  {

      this.i = i;
      this.j = j;
      this.p = p;


      monster = p.loadImage("m.png");
      monster.resize(Square.getSquareSize(), Square.getSquareSize());


  }

  public void display()
  {

    float x = j*Square.getSquareSize();
    float y = i*Square.getSquareSize();
    p.image(monster, x, y);
  }

  public boolean monsterAndPersonTouching(){
    if(Person.getI() == i && Person.getJ() == j){
      return true;
    }
    else{
      return false;
    }
  }


  public void moving(){

    boolean a = false;
    boolean b = false;
    boolean c = false;
    boolean d = false;

    int num = (int)(Math.random()*16)+1;
    if(num == 1){
      a = true;
    }
    else if (num == 2){
      b = true;
    }
    else if(num == 3){
      c = true;
    }
    else if(num == 4){
      d = true;
    }

    if(a == true){
      if(Person.getI() < i){
        i--;
      }
    }
    else if(b == true){
      if(Person.getI() > i){
        i++;
      }
    }
    else if(c == true){
      if(Person.getJ() < j){
        j--;
      }
    }
    else if(d == true){
      if(Person.getJ() > j){
        j++;
      }
    }


  }


  public float getI(){
    return i;
  }
  public float getJ(){
    return j;
  }

  private ProjectSem1RPG p;
  private PImage monster;
  private float i;
  private float j;

}
