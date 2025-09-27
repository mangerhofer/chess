package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator {
    private static final Collection<ChessMove> kingMoves = new ArrayList<>();

    public static Collection<ChessMove> validKingMoves(ChessBoard board, ChessPosition myPos) {
        kingMoves.clear();
        int[][] possMoves = {{0,1}, {0,-1},{1,0},{-1,0},{1,1},{-1,-1},{-1,1},{1,-1}};

        kingMoves.addAll(MovesCalculator.calculateKMoves(board, myPos, possMoves));

        return kingMoves;
    }
}
