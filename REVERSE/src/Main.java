import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        //create StdDraw canvas
        int canvas_width = 640;
        int canvas_height = 640;
        StdDraw.setCanvasSize(canvas_width, canvas_height);
        StdDraw.setXscale(0, 640);
        StdDraw.setYscale(0, 640);
        StdDraw.enableDoubleBuffering();


        Board board = new Board();

        char turn = 'W';

        //if 64 squares are full, then game over
        while (!board.gameOver()){
            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if ((i + j) % 2 == 0){
                        StdDraw.setPenColor(233, 206, 168);
                    }else {
                        StdDraw.setPenColor(106, 78, 66);
                    }
                    StdDraw.filledSquare( 80 * i + 40, 80 * j + 40, 40);
                }
            }

            if (turn == 'W'){
                StdDraw.setPenColor(255, 240, 225);
            }else {
                StdDraw.setPenColor(46, 34, 43);
            }
            if (board.canPut(turn).size() == 0){
                if (turn == 'W') {
                    turn = 'B';

                }else {
                    turn = 'W';
                }
            }
            for (Integer[] i : board.canPut(turn)){
                StdDraw.filledCircle(i[0] * 80 + 40, i[1] * 80 + 40, 6);
                if (StdDraw.isMousePressed()){
                    int mouseX = ((int)StdDraw.mouseX() / 80) ;
                    int mouseY = ((int)StdDraw.mouseY() / 80);
                    if (mouseX == i[0] && mouseY == i[1]){
                        board.addPiece(board.getBoard(), new int[] {i[0], i[1]}, turn);
                        if (turn == 'W') {
                            turn = 'B';

                        }else {
                            turn = 'W';
                        }

                    }


                }
            }
            //if turn can not play, change turn
            if (board.canPut(turn).size() == 0) {
                if (turn == 'W') {
                    turn = 'B';

                } else {
                    turn = 'W';
                }
            }


            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    if (board.getPiece(new int[] {i, j}) != null){
                        if (board.getPiece(new int[] {i, j}).getColour() == 'W'){
                            StdDraw.setPenColor(255, 240, 225);
                        }else {
                            StdDraw.setPenColor(44, 34, 43);
                        }
                        StdDraw.filledCircle(80 * i + 40, 80 * j + 40, 25);
                    }
                }
            }
            StdDraw.show();
        }
        board.winner();
        StdDraw.show();

    }
}