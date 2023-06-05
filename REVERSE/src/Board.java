import java.awt.*;
import java.util.ArrayList;

public class Board {
    private Piece[][] board = new Piece[8][8];
    private final int[][] neighbors = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    public Board(){
        board[3][3] = new Piece(new int[] {3, 3}, 'B');
        board[4][4] = new Piece(new int[] {4, 4}, 'B');
        board[3][4] = new Piece(new int[] {3, 4}, 'W');
        board[4][3] = new Piece(new int[] {4, 3}, 'W');
    }
    public Piece[][] getBoard(){
        return this.board;
    }

    public void addPiece(Piece[][] board, int[] location, char colour){
        addPiece(board, location, colour, new ArrayList<Integer[]>(), true, new int[] {0,0});
    }

    private void addPiece(Piece[][] board, int[] location, char colour, ArrayList<Integer[]> squaresToChange, boolean neighbor, int[] direction){

        if (neighbor){//check neighbors
            for (int[] i : neighbors){
                squaresToChange = new ArrayList<>();
                //tahtanın dışına çıkmadıysam
                if (location[0] + i[0] < 8 && location[0] + i[0] > -1 && location[1] + i[1] < 8 && location[1] + i[1] > -1){
                    if (getPiece(new int[] {location[0] + i[0], location[1] + i[1]}) == null){
                        continue;
                    }
                    if (getPiece(new int[] {location[0] + i[0], location[1] + i[1]}).getColour() != colour){
                        squaresToChange.add(new Integer[] {location[0] + i[0], location[1] + i[1]});
                        addPiece(board, location, colour, squaresToChange, false, i);
                    }
                }
            }
        }else {
            Integer[] current = squaresToChange.get(squaresToChange.size() - 1);
            if (current[0] + direction[0] > 7 || current[0] + direction[0] < 0 || current[1] + direction[1] > 7 || current[1] + direction[1] < 0){
                return;
            }
            if (getPiece(new int[] {current[0] + direction[0],current[1] + direction[1]}) == null) {
                return;
            }else if (getPiece(new int[] {current[0] + direction[0],current[1] + direction[1]}).getColour() == colour){
                for (Integer[] i : squaresToChange){
                    board[i[0]][i[1]] = new Piece(new int[] {i[0], i[1]}, colour);
                }
                board[location[0]][location[1]] = new Piece(new int[] {location[0], location[1]}, colour);
                return;
            }else {
                squaresToChange.add(new Integer[] {current[0] + direction[0], current[1] + direction[1]});
                addPiece(board, location, colour, squaresToChange, false, direction);
            }

        }
    }


    //if colour == 'W', returns all possible moves for white
    public ArrayList<Integer[]> canPut(char colour){
        ArrayList<Integer[]> possibleMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece[][] tempBoard = new Piece[board.length][board[0].length];
                for (int x = 0; x < board.length; x++) {
                    for (int y = 0; y < board[x].length; y++) {
                        if (getPiece(new int[] {x, y}) != null){
                            tempBoard[x][y] = new Piece(board[x][y].getLocation(), board[x][y].getColour());
                        }
                    }
                }
                if (tempBoard[i][j] == null){
                    addPiece(tempBoard, new int[] {i, j}, colour);
                }
                if (tempBoard[i][j] != null && getPiece(new int[] {i, j}) == null){
                    possibleMoves.add(new Integer[] {i, j});
                }
            }
        }
        return possibleMoves;
    }


    public Piece getPiece(int[] location){
        return board[location[0]][location[1]];
    }

    public void showBoard(){
        for (Piece[] i : board){
            for (Piece j : i){
                if (j == null){
                    System.out.print("0 ");
                } else {
                    System.out.print(j.getColour() + " ");
                }
            }
            System.out.println();
        }
    }


    public boolean gameOver(){
        for (Piece[] i : board){
            for (Piece j : i){
                if (j == null){
                    return false;
                }
            }
        }
        return true;
    }

    public void winner(){
        int white = 0;
        for (Piece[] i : board){
            for (Piece j : i){
                if (j.getColour() == 'W'){
                    white++;
                }
            }
        }
        StdDraw.setPenColor(0,0,0);
        Font font = new Font("Helvetica", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(320, 320, "White = "+white+ " Black = "+(64 - white));
    }

}
