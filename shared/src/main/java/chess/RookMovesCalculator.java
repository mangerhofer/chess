package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator {

    private static final Collection<ChessMove> rookMoves = new ArrayList<>();

    public static Collection<ChessMove> validRookMoves(ChessBoard board, ChessPosition position) {
        ChessPiece piece = board.getPiece(position);
        int x = position.getRow();
        int y = position.getColumn();

        // checking horizontal moves
        for (int i = 0; i < 8; i++) {
            if (y == i && x != i) {
                ChessPosition nextPosition = new ChessPosition(x, i);
                if (board.getPiece(nextPosition) == null || (board.getPiece(nextPosition) != null && board.getPiece(nextPosition).getTeamColor() != piece.getTeamColor())) {
                    ChessMove move = new ChessMove(position, nextPosition, null);
                    rookMoves.add(move);
                }
            }
        }

        // checking vertical moves
        for (int i = 0; i < 8; i++) {
            if (x == i && y != i) {
                ChessPosition nextPosition = new ChessPosition(x, i);
                if (board.getPiece(nextPosition) == null || (board.getPiece(nextPosition) != null && board.getPiece(nextPosition).getTeamColor() != piece.getTeamColor())) {
                    ChessMove move = new ChessMove(position, nextPosition, null);
                    rookMoves.add(move);
                }
            }
        }

        return rookMoves;
    }

}
