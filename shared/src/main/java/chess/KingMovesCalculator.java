package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KingMovesCalculator {
    private static final Collection<ChessMove> KINGMOVES = new ArrayList<>();

    public static Collection<ChessMove> validKingMoves(ChessBoard board, ChessPosition myPos) {
        KINGMOVES.clear();
        int[][] possMoves = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,-1},{-1,1},{1,-1}};

        KINGMOVES.addAll(MovesCalculator.calculateKMoves(board, myPos, possMoves));

        return KINGMOVES;
    }
}
