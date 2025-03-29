package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator {
    private static final Collection<ChessMove> QueenMoves = new ArrayList<>();

    public static Collection<ChessMove> validQueenMoves(ChessBoard board, ChessPosition myPos) {
        QueenMoves.clear();

        QueenMoves.addAll(BishopMovesCalculator.validBishopMoves(board, myPos));
        QueenMoves.addAll(RookMovesCalculator.validRookMoves(board, myPos));

        return QueenMoves;
    }
}
