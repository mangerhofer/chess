package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator {

    private static final Collection<ChessMove> knightMoves = new ArrayList<>();

    public static Collection<ChessMove> validKnightMoves(ChessBoard board, ChessPosition position) {
        knightMoves.clear();
        //creating array of all possible moves regardless of board space
        int[][] possMoves = {{1,2},{1,-2},{-1,2},{-1,-2},{2,-1},{-2,-1},{-2,1},{2,1}};

        knightMoves.addAll(MovesCalculator.kingAndKnightMoves(board, position, possMoves));

        return knightMoves;
    }
}
