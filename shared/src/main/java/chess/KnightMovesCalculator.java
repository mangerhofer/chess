package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator {

    private final Collection<ChessMove> knightMoves = new ArrayList<>();
    private MovesCalculator movesCalculator = new MovesCalculator();

    public Collection<ChessMove> validKnightMoves(ChessBoard board, ChessPosition position) {
        knightMoves.clear();
        //creating array of all possible moves regardless of board space
        int[][] possMoves = {{1,2},{1,-2},{-1,2},{-1,-2},{2,-1},{-2,-1},{-2,1},{2,1}};

        knightMoves.addAll(movesCalculator.kingAndKnightMoves(board, position, possMoves));

        return knightMoves;
    }
}
