import java.util.ArrayList;

public class Player {
    private int[][] scoreTable;
    char colour;
    public Player(int[][] scoreTable, char colour){
        this.scoreTable = scoreTable;
        this.colour = colour;
    }

    //minimax algorithm
    public int think(Board board, int depth, char colour, ArrayList<Integer[]> path){
        if (depth == 0){
            return score(board, colour);
        }
        if (colour == 'W'){
            int maxEval = -10000;
            for (Integer[] i : board.canPut(colour)){

                Piece[][] copy = board.copyOf(board.getBoard());
                path.add(i);
                board.addPiece(board.getBoard(), new int[] {i[0], i[1]}, 'W');
                int eval = think(board, depth - 1, 'B', path);
                path.remove(i);
                board.setBoard(copy);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        }else {
            int minEval = 10000;
            for (Integer[] i : board.canPut(colour)){
                Piece[][] copy = board.copyOf(board.getBoard());
                path.add(i);
                board.addPiece(board.getBoard(), new int[] {i[0], i[1]}, 'W');
                int eval = think(board, depth - 1, 'B', path);
                path.remove(i);
                board.setBoard(copy);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }


    //store best move from minimax
    public int[] findBestMove(Board board, int depth, char colour){
        int bestVal = -10000;
        int[] bestMove = new int[] {board.canPut(colour).get(0)[0],board.canPut(colour).get(0)[1]};
        char rival = 'B';
        if (colour == 'B'){
            bestVal = -bestVal;
            rival = 'W';
        }
        for (Integer[] i : board.canPut(colour)){
            Piece[][] copy = board.copyOf(board.getBoard());
            board.addPiece(board.getBoard(), new int[] {i[0], i[1]}, colour);
            ArrayList<Integer[]> moves = new ArrayList<>();
            moves.add(i);
            int moveVal = think(board, depth - 1, rival, moves);
            if (colour == 'W'){
                if (moveVal > bestVal){
                    bestMove = new int[] {i[0], i[1]};
                    bestVal = moveVal;
                }
            }else {
                if (moveVal < bestVal){
                    bestMove = new int[] {i[0], i[1]};
                    bestVal = moveVal;
                }
            }

            board.setBoard(copy);
        }
        return bestMove;
    }

    //calculate the score of the board
    public int score(Board board, char colour){
        int sum = 0;
        for (int i = 0; i < 8 ; i++){
            for (int j = 0 ; j < 8 ; j++){
                if (board.getPiece(new int[] {i, j}) != null){
                    if (board.getPiece(new int[] {i, j}).getColour() == 'W'){
                        sum+=scoreTable[i][j];
                    }else {
                        sum-=scoreTable[i][j];
                    }
                }
            }
        }
        return sum;
    }
}
