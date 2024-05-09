package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator {
    private static final Collection<ChessMove> queenMoves = new ArrayList<>();

    public static Collection<ChessMove> validQueenMoves(ChessBoard board, ChessPosition position) {

        queenMoves.addAll(BishopMovesCalculator.validBishopMoves(board, position));
        queenMoves.addAll(RookMovesCalculator.validRookMoves(board, position));

        return queenMoves;
    }

}
