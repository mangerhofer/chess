package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KnightMovesCalculator {
    private static final Collection<ChessMove> KnightMoves = new ArrayList<>();

    public static Collection<ChessMove> validKnightMoves(ChessBoard board, ChessPosition myPos) {
        KnightMoves.clear();
        int[][] possMoves = {{1,2},{2,1},{1,-2},{2,-1},{-1,2},{-2,1},{-1,-2},{-2,-1}};

        KnightMoves.addAll(MovesCalculator.calculateKMoves(board, myPos, possMoves));

        return KnightMoves;
    }
}
