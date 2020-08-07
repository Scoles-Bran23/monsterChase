import processing.core.*;
import java.util.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;

public class ProjectSem1RPG extends PApplet {


  public void settings()
  {
    //fullScreen();
    size(800, 800);
    //size(400,400);
  }

  public void setup()
  {

    background(255);
    gameState = 0;

    rowCount = height/Square.getSquareSize();
    columnCount = width/Square.getSquareSize();

    squares = new ArrayList<Square>();

    squareCount = (rowCount-1)*columnCount;

    indexSquareX = 0;
    indexSquareY = 0;

    randomForColorTheme = (int)random(4);
    imagePosition = 0;

    //TIMERS AND SCORE
    interval = 9000;
    interval2 = 3000;
    interval3 = 30000;
    timer = millis();
    timer2 = millis();
    timer3 = millis();

    startTime = millis();

    soundInterval = 1500;
    soundTimer = millis();

    //SOUNDS
    minim = new Minim(this);
    fighting = minim.loadFile("fighting.mp3");
    gulp = minim.loadFile("gulp.mp3");
    over = minim.loadFile("over.mp3");
    star = minim.loadFile("starDing.wav");
    weapon = minim.loadFile("weapon.mp3");
    busy = false;

    causeOfDeath = 0;

    //GRID
    //i is y
    //j is x
    for(int i = 1; i < rowCount; i++) {
      for(int j = 0; j < columnCount; j++){
        float x = j*Square.getSquareSize();
        float y = i*Square.getSquareSize();

        Square s = new Square(x, y, j, i, randomForColorTheme, this);
        squares.add(s);
      }
    }

    //PERSON
    person = new Person(1, 0, this);

    //MONSTER
    monster = new Monster (10, 10, this);

    //STAR
    for(int i = 0; i < rowCount/2; i++){
      int randomNumber = (int)(random(0, this.getSquareCount()));
      squares.get(randomNumber).addItem(new Item(this, 0));
    }
    //WATER
    for(int i = 0; i < rowCount/3; i++){
      int randomNumber = (int)(random(0, this.getSquareCount()));
      squares.get(randomNumber).addItem(new Item(this, 1));
    }
    //WEAPON
    for(int i = 0; i < rowCount/4; i++){
      int randomNumber = (int)(random(0, this.getSquareCount()));
      squares.get(randomNumber).addItem(new Item(this, 2));
    }

  }

  public void draw()
  {
    if(gameState == 0){

      if(randomForColorTheme == 0){
        background(255,240,245);
      }
      else if(randomForColorTheme == 1){
        background(153,255,178);
      }
      else if(randomForColorTheme == 2){
        background(92,181,225);
      }
      else{
        background(254, 177, 68);
      }

      //background(255);
      //TEXT
      fill(0);
      textAlign(CENTER);
      textSize(width/30);

      text("Use Arrow Keys or WASD to move", width/2, height/2 - height/5 - height/5);
      text("Avoid the Monster & Collect Stars, Swords, and Water", width/2, height/2 - height/5);
      text("Too Little Water and You Die", width/2, height/2);
      text("Swords Can Save Your Life", width/2, height/2 + height/5);
      text("Press Space to Start!", width/2, height/2 + height/5 + height/5);
    }
    else if(gameState == 1){

      //BACKGROUND
      //background(255);
      if(randomForColorTheme == 0){
        background(255,240,245);
      }
      else if(randomForColorTheme == 1){
        background(153,255,178);
      }
      else if(randomForColorTheme == 2){
        background(92,181,225);
      }
      else{
        background(158, 193, 207);
      }

      //startTimer();

      //TIMER FOR REFILLING STARS AND WATERS ONSCREEN (every 9 seconds)
      if(timer + interval < millis())
      {
        timer = millis();
        //RESETTING ITEMS

          updateStars();
          updateWaters();
          //updateWeapons();
      }

      //TIMER FOR TAKING AWAY WATER (every 3 seconds)
      if(timer2 + interval2 < millis()){
        timer2 = millis();
        //remove water
        //to replicate needing sustenence
        //to stay alive
        energyLow = true;
        removeWaters();
      }

      //TIMER FOR REFILLING WEAPONS ONSCREEN (every 30 seconds)
      if(timer3 + interval3 < millis()){
        timer3 = millis();
        updateWeapons();
      }

      if(soundTimer + soundInterval < millis()){
          if(busy == true){
            soundTimer = millis();
            busy = false;
          }

      }

      score = (int)(millis() - startTime)/1000;


      deadOrAlive();
      //starPoints();

      //TEXT
      fill(0);
      textAlign(CORNER);
      textSize(width/60);
      //text("Fight to survive! Use arrow keys to move.", 20, 20);
      text("Waters = " + countWaters(), 20, 20);
      text("Stars =  " + countStars(), 100, 20);
      text("Weapons = " + countWeapons(), 200, 20);
      //text("GameState = " + gameState, 300, 20);
      //text("Is Dead = " + isDead, 400, 20);
      text("Is Busy = " + busy, 500, 20);
      text("Score = " + cumulativeScore(), 600, 20);
      //text("Touching = " + monster.monsterAndPersonTouching(), 600, 20);
      //text("Extra Score: " + extraScore, 600, 20);
      //text("Score: " + score, 700, 20);


      //SQUARES
      for(Square s : squares){
        s.display();
      }
      //PERSON
      person.display();

      //MONSTER
      monster.display();
      monster.moving();

    }

    else if(gameState == 2){

      background(0);
      fill(255, 0, 0);
      textAlign(CENTER);
      text("Game Over: Press Space to Play Again", width/2, height/2);

      int finalScore = cumulativeScore();
      text("Score: " + finalScore, width/2, height/2 + height/3);
      if(causeOfDeath == 1){
        text("Cause Of Death: The Monster Got You", width/2, height/2 + height/4);
      }
      else if(causeOfDeath == 2){
        text("Cause Of Death: Dehydration", width/2, height/2 + height/4);
      }
    }

  }

  public int getSquareCount(){
    return squareCount;
  }

  private int countStars()
  {
    int result = 0;

    for(Item n: person.getMyItems()){
      if(n.getType() == 0){
        result++;
      }
    }
    return result;

  }

  private int countWaters()
  {
    int result = 0;

    for(Item n : person.getMyItems()){
      if(n.getType() == 1){
        result++;
      }
    }
    return result;
  }

  private int countWeapons()
  {
    int result = 0;
    for(Item n: person.getMyItems()){
      if(n.getType() == 2){
        result++;
      }
    }
    return result;
  }


  public int cumulativeScore(){
    return score + extraScore;
  }

  public void addToExtraScore(int num){
    extraScore += num;
  }

  public static ArrayList<Item> getItems(){
    return items;
  }

  public ArrayList<Square> getSquares(){
    return squares;
  }

  public float getIndexSquareX(){
    return indexSquareX;
  }
  public float getIndexSquareY(){
    return indexSquareY;
  }

  public static int getColumnCount(){
    return columnCount;
  }
  public int getRowCount(){
    return rowCount;
  }

  public Person getPerson(){
    return person;
  }

  public static int getImagePosition(){
    return imagePosition;
  }

  public AudioPlayer getGulp(){
    return gulp;
  }
  public AudioPlayer getWeapon(){
    return weapon;
  }
  public AudioPlayer getStar(){
    return star;
  }
  public boolean getBusy(){
    return busy;
  }
  public void setBusyFalse(){
    busy = false;
  }
  public void setBusyTrue(){
    busy = true;
  }

  public void updateWeapons(){
    //WEAPON
    for(int i = 0; i < rowCount/10; i++){
      int randomNumber = (int)(random(0, this.getSquareCount()));
      squares.get(randomNumber).addItem(new Item(this, 2));
    }
  }

  public void updateStars(){
    //STAR
    for(int i = 0; i < rowCount/10; i++){
      int randomNumber = (int)(random(0, this.getSquareCount()));
      squares.get(randomNumber).addItem(new Item(this, 0));
    }
  }

  public void updateWaters(){
    //WATER
    extraScore+=30;
    for(int i = 0; i < rowCount/10; i++){
      int randomNumber = (int)(random(0, this.getSquareCount()));
      squares.get(randomNumber).addItem(new Item(this, 1));
    }
  }

  public void deadOrAlive(){

    if(monster.monsterAndPersonTouching() == true && countWeapons() != 0){
      person.setI((int)random(1, columnCount));
      person.setJ((int)random(0, rowCount));
      removeWeapons();
      isDead = false;
      isFighting = true;

      if(busy == false){
        if(fighting.position() == fighting.length()){
          fighting.rewind();
          fighting.play();
          //busy = true;
        } else{
          fighting.play();
          //busy = true;
        }
      }
      busy = true;
    }
    else if(monster.monsterAndPersonTouching() == true && countWeapons() == 0){
      /*
      if(busy == false){
        if(over.position() == over.length()){
          over.rewind();
          over.play();
          //busy = true;
        }
        else{
          over.play();
          //busy = true;
        }

      }
      */

      over.rewind();
      over.play();


      busy = true;

      isDead = true;
      causeOfDeath = 1; //Cause Of Death is Monster
      isFighting = false;

      gameState++;

    }
    else if(countWaters() <= 0){
      person.setI((int)random(1, columnCount));
      person.setJ((int)random(0, rowCount));
      isDead = true;
      causeOfDeath = 2; //Cause Of Death is Dehydration
      isFighting = false;

      while(countWaters() <= 10){
        person.getMyItems().add(new Item(this, 1));
      }
      gameState++;

    }

  }


  public void removeWeapons(){
    for(int i = 0; i < person.getMyItems().size(); i++){
      if(person.getMyItems().get(i).getType() == 2){
        person.getMyItems().remove(i);
        return;
      }
    }
  }

  public void removeWaters(){
    /*
    int index = 0;

    while(index < person.getMyItems().size()){
      int num = (int)(Math.random() * 5) + 1;
      if(person.getMyItems().get(index).getType() == 1 && num == 5){
        person.getMyItems().remove(index);
      }
      else{
        index++;
      }
    }*/

    for(int i = 0; i < person.getMyItems().size(); i++){
      if(person.getMyItems().get(i).getType() == 1){
        person.getMyItems().remove(i);
        return;
      }
    }

  }


  public void keyPressed()
  {
    //i is x axis
    //j is y axis
    if(keyCode == UP || key == 'w' || key == 'W'){
      person.decreaseI();
      person.checkForItem();
      imagePosition = 0;
    }
    else if(keyCode == DOWN || key == 's' || key == 'S'){
      person.increaseI();
      person.checkForItem();
      imagePosition = 0;
    }
    else if(keyCode == LEFT || key == 'a' || key == 'A'){
      person.decreaseJ();
      person.checkForItem();
      imagePosition = 1;
    }
    else if(keyCode == RIGHT || key == 'd' || key == 'D'){
      person.increaseJ();
      person.checkForItem();
      imagePosition = 2;
    }
    else if(key == ' ' && gameState == 0){
      gameState++;
    }
    else if(key == ' ' && gameState == 2){

      score = 0;
      extraScore = 0;
      startTime = millis();


      person.setI((int)random(1, columnCount));
      person.setJ((int)random(0, rowCount));
      updateWeapons();
      gameState = 0;
    }
  }

  private static int gameState;

  public static int rowCount;
  public static int columnCount;
  private int squareCount;

  public static float indexSquareX;
  public static float indexSquareY;
  private int randomForColorTheme;

  private ArrayList<Square> squares;
  private Person person;
  private PFont myFont;
  private static ArrayList<Item> items;

  private Monster monster;

  private int timer;
  private int timer2;
  private int timer3;
  private int interval;
  private int interval2;
  private int interval3;

  private int soundTimer;
  private int soundInterval;

  private int score;
  private int startTime;
  private int extraScore;

  private boolean energyLow;
  private boolean isDead;
  private boolean isFighting;

  private static int imagePosition;

  private int causeOfDeath;

  private Minim minim;
  private AudioPlayer fighting;
  private AudioPlayer over;
  private AudioPlayer gulp;
  private AudioPlayer star;
  private AudioPlayer weapon;
  private boolean busy;

  public static void main(String[] args) {
    PApplet.main("ProjectSem1RPG");
  }

}
