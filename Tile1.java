public class Tile1 {
    public Content content;
    public Status status;
    private int numbers_of_neighbors_mines;
    private Tile1[] neighbors;
    /**
     * Constructs a Tile and initializes the tile's status and content 
     */
    public Tile1(){
        this.status=Status.UNCLICKED;
        this.content=Content.EMPTY; 

    }
    /**
     * Counts and sets how many neighbors with mines this Tile has
     */
    public void SetNumberOfNeighborsMines(){
        this.numbers_of_neighbors_mines=0;
        for(Tile1 tile:neighbors){
            if(tile.content!=Content.EMPTY){
                this.numbers_of_neighbors_mines++;
            }

        }

    }
    /**
     * Returns the number of neighbors with mines for this Tile
     * @return this Tile's number_of_neighbors_mines
     */
    public int GetNumberOfNeighborsMines(){
        return this.numbers_of_neighbors_mines;
    }
    /**
     * Sets the neighbors' list for this Tile
     * @param neighbors the neighbors' list of this Tile
     */
    public void setNeighbors(Tile1[] neighbors){
        this.neighbors=neighbors;
    }
    /**
     * Returns the list of neighbors for this Tile
     * @return this Tile's neighbors
     */
    public Tile1[] getNeighbors(){
        return this.neighbors;
    }
    
}
