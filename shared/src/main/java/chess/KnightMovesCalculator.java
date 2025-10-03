package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KnightMovesCalculator {
    private static final Collection<ChessMove> KNIGHTMOVES = new ArrayList<>();

    public static Collection<ChessMove> validKnightMoves(ChessBoard board, ChessPosition myPos) {
        KNIGHTMOVES.clear();
        int[][] possMoves = {{1,2}, {2,1}, {1,-2}, {2,-1}, {-1,2}, {-2,1}, {-1,-2}, {-2,-1}};

        KNIGHTMOVES.addAll(MovesCalculator.calculateKMoves(board, myPos, possMoves));

        return KNIGHTMOVES;
    }
}
