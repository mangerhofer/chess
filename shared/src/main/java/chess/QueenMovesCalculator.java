package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator {
    private static final Collection<ChessMove> QUEENMOVES = new ArrayList<>();

    public static Collection<ChessMove> validQueenMoves(ChessBoard board, ChessPosition myPos) {
        QUEENMOVES.clear();

        QUEENMOVES.addAll(BishopMovesCalculator.validBishopMoves(board, myPos));
        QUEENMOVES.addAll(RookMovesCalculator.validRookMoves(board, myPos));

        return QUEENMOVES;
    }
}
