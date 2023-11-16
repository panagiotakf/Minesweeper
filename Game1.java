import java.util.*;
import java.util.Scanner; 
import java.io.File;  
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Timer;
import java.util.TimerTask;

public class Game1 {
    private int difficulty_level;
    private int mines;
    private int maxTime;
    private boolean is_there_hypermine;
    private int grid_size;
    private int[][] mines_positions;
    private Board board;
    private StatusOfGame gamestatus;
    private int moves;
    private TimerTask task;
    
    //constructor
    
    public Game1(File scenarioFile) throws InvalidDescriptionException1,InvalidValueException1,FileNotFoundException{
        try{
            Scanner fileReader = new Scanner(scenarioFile);
            String scenario = fileReader.nextLine();
            int data;
            // first row of the file is for difficulty
            try{
            data = Integer.parseInt(scenario);
            if(data==1||data==2){
                this.difficulty_level=data;
                if(data==1){
                    this.grid_size=9;
                }
                else{
                    this.grid_size=16;
                }
            }
            else{
                fileReader.close();
                throw new InvalidValueException1("",null);
            }
            }catch(NumberFormatException | InvalidValueException1 ex){
                throw new InvalidValueException1("Bad difficulty value",null);
            }

            //second row sets thw number of mines
            scenario=fileReader.nextLine();
            try{
                data = Integer.parseInt(scenario);
                if(this.difficulty_level==1){
                    //check if number of mines is acceptable for the difficulty level
                    if(data>8 && data<12){
                        this.mines=data; 
                    }
                    else{
                        fileReader.close();
                        throw new InvalidValueException1("",null);

                    }
                }
                else{
                    if(data>34 && data<46){
                        this.mines=data;
                    }
                    else{
                        fileReader.close();
                        throw new InvalidValueException1("",null);

                    }

                }
            }catch(NumberFormatException | InvalidValueException1 ex){
                throw new InvalidValueException1("Bad mines value",null);
            }

            //third row sets max time
            scenario=fileReader.nextLine();
            //check if maxTime values are acceptable for the level of difficulty
            try{
                data = Integer.parseInt(scenario);
                if(this.difficulty_level==1){
                    //check if number of mines is acceptable for the difficulty level
                    if(data>119 && data<181){
                        this.maxTime=data;
                    }
                    else{
                        fileReader.close();
                        throw new InvalidValueException1("",null);

                    }
                }
                else{
                    if(data>239 && data<361){
                        this.maxTime=data;
                    }
                    else{
                        fileReader.close();
                        throw new InvalidValueException1("",null);

                    }

                }
            }catch(NumberFormatException | InvalidValueException1 ex){
                throw new InvalidValueException1("Bad maxTime value",null);
            }

            //last row sets if there is a hypermine
            scenario=fileReader.nextLine();
            //check for hypermine
            try{
                data = Integer.parseInt(scenario);
                if(data==0){
                    this.is_there_hypermine=false; 
                }
                else if(data==1 && this.difficulty_level==2){
                    this.is_there_hypermine=true;
                }
                else{
                    fileReader.close();
                    throw new InvalidValueException1("",null);
                    
                }
            }catch(NumberFormatException | InvalidValueException1 ex){
                throw new InvalidValueException1("Bad hypermine value",null);
            }
            //close file
            fileReader.close();
        }
        catch(FileNotFoundException fileNotFound){
            throw new FileNotFoundException("File not found");
      }
      catch(NoSuchElementException notEnoughLines){
        throw new InvalidDescriptionException1("File doesn't have enough lines", null);
      }
      //generate random positions of mines
      this.generateMineData();
      // call Board constructor
      this.board=new Board(this.grid_size,this.mines_positions,this.mines);
      //initialize game status
      this.gamestatus=StatusOfGame.PLAY;
      //set moves zero
      this.moves=0;
      //start timer  
      Timer timer = new Timer(true);
      task = new Helper(this);
       
      timer.schedule(task, (this.maxTime*1000));
    }
    // generate the file of mines
    private void generateMineData(){
        int n = this.grid_size*this.grid_size;
        int[] candidatePositions = new int[n]; 
        for(int i=0; i<candidatePositions.length; i++){
          candidatePositions[i] = i;
        }
        // This matrix has all the available positions in which a mine can be placed. 
        
        //Now array is shuffled.
        shuffleArray(candidatePositions);
        
        mines_positions = new int[this.mines][3];

        //Gives random positions to all mines
        for(int i=0; i<this.mines; i++){
            int currentIndex = candidatePositions[i];
            mines_positions[i][0] = currentIndex / this.grid_size;
            mines_positions[i][1] = currentIndex % this.grid_size;
            mines_positions[i][2] = 0;
        }
         
        //Sets last mine as supermine if there is a supermine
        if(this.is_there_hypermine) mines_positions[this.mines-1][2] = 1;

        //Creates the file and writes the mine data in it.
        try {
            File myObj = new File("mines.txt");
            myObj.createNewFile();
            
          } catch (IOException e) {
            System.out.println("An error occurred, file mines.txt not created.");
            e.printStackTrace();
        }

        try {
            new PrintWriter("mines.txt").close(); //flushes file

            //Now we write the mine positions into the file.
            BufferedWriter myWriter = new BufferedWriter(new FileWriter("mines.txt", true));
            String str;
            for(int i=0; i<this.mines; i++){
              str = mines_positions[i][0] + "," + mines_positions[i][1] + "," + mines_positions[i][2]+ "\n";
              myWriter.write(str);
            }
            myWriter.close();

          } catch (IOException e) {
            System.out.println("An error occurred in writing the file.");
            e.printStackTrace();
        }

    }

    // to shuffle the array of mines
    private static void shuffleArray(int ar[]){
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
          int index = rnd.nextInt(i + 1);
          int a = ar[index];
          ar[index] = ar[i];
          ar[i] = a;
        }
    }

    //just a method to print Game fields for check
    public void printGame(){

        System.out.println(this.difficulty_level);
        System.out.println(this.mines);
        System.out.println(this.maxTime);
        System.out.println(this.is_there_hypermine);
        System.out.println(this.grid_size);
        
    

    }
    //a method that prints the field in every repeat
    public void printField(){
       
        this.board.printBoard(this.grid_size);
    }
    //Get Game Status
    public StatusOfGame getGameStatus(){
        return this.gamestatus;
    }
    //Set Game Status
    public void setGameStatus(StatusOfGame status){
        this.gamestatus=status;
    }
    // To set a flag in position x,y
    public void setFlag(int x , int y){
      
        this.board.SetFlaginBoard(x,y);
    }
    //To unset a flag from position x,y
    public void unsetFlag(int x, int y){
        this.board.UnsetFlaginBoard(x, y);
    }
    //a method that returns true if we have less flags than mines
    public boolean getFlags(){
        return (this.board.GetFlagsofBoard()<this.mines);

    }
    //Get Content of Tile in x,y position
    public Content getTile(int x, int y){
        return this.board.getTilesContent(x,y);
    }
    //Set Tile Status
    public void setTileStatus(int x, int y, Status status){
        this.board.setTilesStatus(x,y,status);

    }
    //Get Tile Status
    public Status getTileStatus(int x, int y){
        return this.board.getTilesStatus(x,y);
    }
    //A method that calls the recursive reveal for a Tile
    public void recursiveReveal(int x , int y){
        this.board.recursiveRevealinBoard(x, y);
    }
    //A method that calls another method that reveals all the field
    public void revealAll(){
        this.board.setAllOpen(this.grid_size);
        }
    //Checks for Victory
    public void checkVictory(){

        if(this.board.victoryChecker()) this.gamestatus=StatusOfGame.WON;
    }
    //subtracks the number of unclicked tiles
    public void subtractTiles(){
        this.board.subtracker();
    }
    //counts moves
    public void setMoves(){
        this.moves++;
    }
    //Get moves
    public int getMoves(){
        return this.moves;
    }
    //A method if you found Hypermine
    public void foundHypermine(int x, int y){
        this.board.hypermineReveal(x,y,this.grid_size);
    }
    //A method to cancel Timer
    public void cancelTimer(){
        this.task.cancel();
    }
}
