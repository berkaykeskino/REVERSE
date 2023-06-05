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

        int[][] scoreTable = {
                {600, -40, 8, 60, 60, 8, -40, 600},
                {-40, -20, 3, 3, 3, 3, -20, -40},
                {8, 3, 4, 4, 4, 4, 3, 8},
                {60, 3, 4, 4, 4, 4, 3, 60},
                {60, 3, 4, 4, 4, 4, 3, 60},
                {8, 3, 4, 4, 4, 4, 3, 8},
                {-40, -20, 3, 3, 3, 3, -20, -40},
                {600, -40, 8, 60, 60, 8, -40, 600}
        };

        Player playerWhite = new Player(scoreTable, 'W');
        Player playerBlack = new Player(scoreTable, 'B');

        char turn = 'W';


        //if 64 squares are full, then game over
        while (!board.gameOver()) {
            //draw squares
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((i + j) % 2 == 0) {
                        StdDraw.setPenColor(233, 206, 168);
                    } else {
                        StdDraw.setPenColor(106, 78, 66);
                    }
                    StdDraw.filledSquare(80 * i + 40, 80 * j + 40, 40);
                }
            }

            //if white's turn
            if (turn == 'W') {
                //find best move for white
                int[] a = playerWhite.findBestMove(board, 2, playerWhite.colour);
                //play the move
                board.addPiece(board.getBoard(), new int[]{a[0], a[1]}, turn);
                //change turn
                turn = 'B';
            } else {
                //find best move for black
                int[] a = playerBlack.findBestMove(board, 3, playerBlack.colour);
                //play the move
                board.addPiece(board.getBoard(), new int[]{a[0], a[1]}, 'B');
                //change turn
                turn = 'W';
            }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board.getPiece(new int[]{i, j}) != null) {
                        //determine colour, draw pieces
                        if (board.getPiece(new int[]{i, j}).getColour() == 'W') {
                            StdDraw.setPenColor(255, 240, 225);
                        } else {
                            StdDraw.setPenColor(44, 34, 43);
                        }
                        StdDraw.filledCircle(80 * i + 40, 80 * j + 40, 25);
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
            if (turn == 'W') {
                StdDraw.setPenColor(255, 240, 225);
            } else {
                StdDraw.setPenColor(46, 34, 43);
            }
            //draw small circles where turn(W/B) can put piece to
            for (Integer[] i : board.canPut(turn)) {
                StdDraw.filledCircle(i[0] * 80 + 40, i[1] * 80 + 40, 6);

            }

            StdDraw.show();
            StdDraw.pause(800);
            }



        //show number of pieces
        board.winner();
        StdDraw.show();
        }




}