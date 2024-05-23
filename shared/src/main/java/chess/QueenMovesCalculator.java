package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalculator {
    private final Collection<ChessMove> queenMoves = new ArrayList<>();
    private BishopMovesCalculator bishopMoves = new BishopMovesCalculator();
    private RookMovesCalculator rookMoves = new RookMovesCalculator();

    public Collection<ChessMove> validQueenMoves(ChessBoard board, ChessPosition position) {
        queenMoves.clear();

        queenMoves.addAll(bishopMoves.validBishopMoves(board, position));
        queenMoves.addAll(rookMoves.validRookMoves(board, position));

        return queenMoves;
    }

}
