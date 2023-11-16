import java.util.TimerTask;

public class Helper extends TimerTask{
    //a class that implements time out
    Game1 game;
    public Helper(Game1 game){
        this.game=game;
    }
    public void run(){
        this.game.setGameStatus(StatusOfGame.TIME_OUT);
        return;
    }
    
}
