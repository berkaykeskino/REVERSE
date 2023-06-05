public class Piece {
    private char colour;
    private int[] location;

    public Piece(int[] location, char colour){
        this.location = location;
        this.colour = colour;
    }
    public char getColour(){
        return this.colour;
    }
    public int[] getLocation(){
        return this.location;
    }
}
