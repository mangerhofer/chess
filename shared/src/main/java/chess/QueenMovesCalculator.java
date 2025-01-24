package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator {
    private static final Collection<ChessMove> queenMoves = new ArrayList<>();

    public static Collection<ChessMove> validQueenMoves(ChessBoard board, ChessPosition myPos) {
        queenMoves.clear();

        queenMoves.addAll(BishopMovesCalculator.validBishopMoves(board, myPos));
        queenMoves.addAll(RookMovesCalculator.validRookMoves(board, myPos));

        return queenMoves;
    }
}
