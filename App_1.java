
import java.io.File;
import java.util.Scanner;

public class App_1 {
    public static void main(String[] args) throws Exception {
       
        //read the file
        System.out.println("Give me the path for the scenario file:");
        Scanner scanner_for_file=new Scanner(System.in);
        String path_of_file=scanner_for_file.nextLine();
        File scenario = new File(path_of_file);
        //start the game
        Game1 game = new Game1(scenario);
      
        //game.printGame();
        game.printField();
        Scanner scanner = new Scanner(System.in);
        while(game.getGameStatus() == StatusOfGame.PLAY){
        
            System.out.println("Enter 1 to step on tile, 0 to flag:");
            int input = scanner.nextInt();

            if(input==1){
                System.out.println("Enter x, y to step on:");
                int posX = scanner.nextInt();
                int posY = scanner.nextInt();

                if(game.getTile(posX, posY) == Content.EMPTY){
                    //open the tile
                    game.recursiveReveal(posX,posY);
                    game.setTileStatus(posX, posY, Status.OPEN);
                    game.setMoves();
                    
                }
                else{
                    //if open then i open it with a hyperminus and i dont want to press it
                    if(game.getTileStatus(posX, posY)!=Status.OPEN){
                        game.setGameStatus(StatusOfGame.LOST);
                        game.cancelTimer();
                        System.out.println("You lost!");
                        game.revealAll();
                        game.printField();
                        break;
                    }
                }
            }
            if(input==0){
                System.out.println("Enter x, y to flag:");
                int posX = scanner.nextInt();
                int posY = scanner.nextInt();
                // if you have not already a flag in this tile
                if(game.getTileStatus(posX, posY)==Status.UNCLICKED){
                    //check if you are allowed to have more flags
                    if(game.getFlags()){
                    game.setFlag(posX, posY);
                    //if we have less than 5 tries check if it is hypermine
                    if(game.getMoves()<5){
                        if(game.getTile(posX, posY) == Content.HYPERMINE){
                            game.foundHypermine(posX,posY);
                        }
                    }
                    game.setMoves();
                    }
                    else{
                        System.out.println("No more flags");
                    }
                }
                // if you have already flag unset it
                else if(game.getTileStatus(posX, posY)==Status.FLAGGED){
                    game.unsetFlag(posX, posY);
                    game.setMoves();
                }
                else{}
                
            }
            
            game.printField();
            game.checkVictory();
           
        }

        // if we won
        if(game.getGameStatus() == StatusOfGame.WON) {
            game.cancelTimer();
            System.out.println("You won!");
            game.revealAll();
            game.printField();

        }
        //if time out
        if(game.getGameStatus()==StatusOfGame.TIME_OUT){
            System.out.println("Time Out!");
            game.revealAll();
            game.printField();
        }

        

    scanner.close();
   
        

}
    }

