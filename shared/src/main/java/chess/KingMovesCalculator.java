package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator {

    private final Collection<ChessMove> kingMoves = new ArrayList<>();
    private MovesCalculator movesCalculator = new MovesCalculator();

    public Collection<ChessMove> validKingMoves(ChessBoard board, ChessPosition position) {
        kingMoves.clear();
        //creating array of all possible moves regardless of board space
        int[][] possMoves = {{1,1},{1,-1},{1,0},{0,1},{0,-1},{-1,0},{-1,-1},{-1,1}};

        kingMoves.addAll(movesCalculator.kingAndKnightMoves(board, position, possMoves));

        return kingMoves;
    }
}
