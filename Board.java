public class Board {
    private Tile1 minefield[][];
    private int flags;
    private int remainTiles;
    //Constructor
    public Board(int grid_size, int mines_positions[][],int mines){
        //number of flags
        this.flags=0;
        //number of tiles we have to click without lose, to win the game
        this.remainTiles=((grid_size)*(grid_size))-mines;
        //array of tiles
        this.minefield= new Tile1[grid_size][grid_size];
        Tile1 tile;
        //initialize the board
        for(int i=0; i<grid_size; i++){
            for(int j=0; j<grid_size; j++){
                minefield[i][j] = new Tile1(); 
            }
        }
        //set the tiles with mines
        for(int i=0; i<mines; i++){
            if(mines_positions[i][2]==0){
                this.minefield[mines_positions[i][0]][mines_positions[i][1]].content=Content.MINE;
            }
            else{
                this.minefield[mines_positions[i][0]][mines_positions[i][1]].content=Content.HYPERMINE;
            }
        }
        //create neighbors array for every tile
        Tile1[] neighbors;
        for(int i=0; i<grid_size; i++){
            for(int j=0; j<grid_size; j++){
                tile=minefield[i][j];
                if(i==0 && j==0){ 
                    neighbors=new Tile1[3];
                    neighbors[0]=minefield[0][1];
                    neighbors[1]=minefield[1][0];
                    neighbors[2]=minefield[1][1];
                    tile.setNeighbors(neighbors);

                }
                else if(i==grid_size-1 && j==grid_size-1){
                    neighbors=new Tile1[3];
                    neighbors[0]=minefield[grid_size-1][grid_size-2];
                    neighbors[1]=minefield[grid_size-2][grid_size-1];
                    neighbors[2]=minefield[grid_size-2][grid_size-2];
                    tile.setNeighbors(neighbors);

                }
                else if(i==0 && j==grid_size-1){
                    neighbors=new Tile1[3];
                    neighbors[0]=minefield[0][grid_size-2];
                    neighbors[1]=minefield[1][grid_size-1];
                    neighbors[2]=minefield[1][grid_size-2];
                    tile.setNeighbors(neighbors);

                }
                else if(i==grid_size-1 && j==0){
                    neighbors=new Tile1[3];
                    neighbors[0]=minefield[grid_size-1][1];
                    neighbors[1]=minefield[grid_size-2][0];
                    neighbors[2]=minefield[grid_size-2][1];
                    tile.setNeighbors(neighbors);
                }

                else if(i==0){
                    neighbors=new Tile1[5];
                    neighbors[0]=minefield[0][j-1];
                    neighbors[1]=minefield[0][j+1];
                    neighbors[2]=minefield[1][j];
                    neighbors[3]=minefield[1][j-1];
                    neighbors[4]=minefield[1][j+1];
                    tile.setNeighbors(neighbors);
                }
                else if(j==0){
                    neighbors=new Tile1[5];
                    neighbors[0]=minefield[i-1][0];
                    neighbors[1]=minefield[i+1][0];
                    neighbors[2]=minefield[i][1];
                    neighbors[3]=minefield[i-1][1];
                    neighbors[4]=minefield[i+1][1];
                    tile.setNeighbors(neighbors);
                }
                else if(j==grid_size-1){
                    neighbors=new Tile1[5];
                    neighbors[0]=minefield[i-1][grid_size-1];
                    neighbors[1]=minefield[i+1][grid_size-1];
                    neighbors[2]=minefield[i][grid_size-2];
                    neighbors[3]=minefield[i-1][grid_size-2];
                    neighbors[4]=minefield[i+1][grid_size-2];
                    tile.setNeighbors(neighbors);
                }
                else if(i==grid_size-1){
                    neighbors=new Tile1[5];
                    neighbors[0]=minefield[grid_size-1][j-1];
                    neighbors[1]=minefield[grid_size-1][j+1];
                    neighbors[2]=minefield[grid_size-2][j];
                    neighbors[3]=minefield[grid_size-2][j-1];
                    neighbors[4]=minefield[grid_size-2][j+1];
                    tile.setNeighbors(neighbors);

                }
                else{
                    neighbors=new Tile1[8];
                    neighbors[0]=minefield[i][j-1];
                    neighbors[1]=minefield[i][j+1];
                    neighbors[2]=minefield[i-1][j];
                    neighbors[3]=minefield[i-1][j-1];
                    neighbors[4]=minefield[i-1][j+1];
                    neighbors[5]=minefield[i+1][j-1];
                    neighbors[6]=minefield[i+1][j+1];
                    neighbors[7]=minefield[i+1][j];
                    tile.setNeighbors(neighbors);

                }
                tile.SetNumberOfNeighborsMines();
            }
        }

        
        
        

    }
    //A method to print the Board
    public void printBoard(int grid_size){
        Tile1 tile;
        for(int i=0; i<grid_size; i++){
            for(int j=0; j<grid_size; j++){
                tile=this.minefield[i][j];
                if(tile.status==Status.UNCLICKED){

                    System.out.print("* ");
                }
                else if(tile.status==Status.FLAGGED){
                 
                    System.out.print("F ");
                }
                else{
                    if(tile.content==Content.MINE){
                        System.out.print("# ");

                    }
                    else if(tile.content==Content.HYPERMINE){
                        System.out.print("! ");

                    }
                    else{
                        System.out.print(tile.GetNumberOfNeighborsMines()+ " ");
                    }
                }

    }
    System.out.println();
}
    }
    //Set a flag in position x,y
    public void SetFlaginBoard(int x, int y){
        this.minefield[x][y].status=Status.FLAGGED;
        this.flags++;
       
    }
    //Unset a flag from position x,y
    public void UnsetFlaginBoard(int x, int y){
        this.minefield[x][y].status=Status.UNCLICKED;
        this.flags--;
    }
    // Get the number of flags
    public int GetFlagsofBoard(){
        return this.flags;
    }
    //Get Content of Tile
    public Content getTilesContent(int x, int y){
        return this.minefield[x][y].content;

    }
    //Set Tile's Status
    public void setTilesStatus(int x , int y, Status status){
        this.minefield[x][y].status=Status.OPEN;
    }
    //Get Tile's Status
    public Status getTilesStatus(int x, int y){
        return this.minefield[x][y].status;
    }
    //Calls openTiles for the Tile in position x,y
    public void recursiveRevealinBoard(int x, int y){
        this.openTiles(this.minefield[x][y]);
    }
    //Recursive Reveal of the Tile's neighbors 
    public void openTiles(Tile1 tile){
        //if your status is unclicked and you are not a mine
        if(tile.status!=Status.UNCLICKED || tile.content!=Content.EMPTY){
            return;
        }
        else{
            tile.status=Status.OPEN;
            this.subtracker();
            for(Tile1 t:tile.getNeighbors()){
                //if you have not neighbors that are mines, reveal your neighbors
                if(tile.GetNumberOfNeighborsMines()==0){
                    this.openTiles(t);
                }
            }
        }
        
        

    }
    //Opens all the tiles
    public void setAllOpen(int grid_size){
        Tile1 tile;
        for(int i=0; i<grid_size; i++){
            for(int j=0; j<grid_size; j++){
                tile=this.minefield[i][j];
                tile.status=Status.OPEN;
    }
}
    }
  //Checks if we have remainTiles. If we dont, then we win
  public boolean victoryChecker(){
    return (this.remainTiles==0);

  }
  //Subtracks the remainTiles
  public void subtracker(){
    this.remainTiles--;
  }
  //Reveals the horizontal and vertical of a hypermine
  public void hypermineReveal(int x,int y,int grid_size){
    for(int i=0; i<grid_size; i++){
        this.minefield[x][i].status=Status.OPEN;
    }
    for(int i=0; i<grid_size; i++){
        this.minefield[i][y].status=Status.OPEN;
    }
  }
}
