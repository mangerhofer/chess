package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator {

    private static final Collection<ChessMove> bishopMoves = new ArrayList<>();

    public BishopMovesCalculator() {

    }

    public static Collection<ChessMove> validBishopMoves(ChessBoard board, ChessPosition position) {
        ChessPiece piece = board.getPiece(position);
        int x = position.getRow();
        int y = position.getColumn();

        int topLeft = Math.min(x,y);
        int topRight = Math.max(x, 7-y);
        int bottomLeft = Math.min(7-x, y);
        int bottomRight = Math.max(7-x, 7-y);

        for (int i = 1; i < topLeft; i++) {
            ChessPosition point = new ChessPosition(x - i, y - i);
            if (board.getPiece(point) == null || (board.getPiece(point) != null && board.getPiece(point).getTeamColor()!= piece.getTeamColor())) {
                ChessMove move = new ChessMove(position, point, null);
                bishopMoves.add(move);

            }
        }
        for (int i = 1; i <= bottomLeft+2; i++) {
            ChessPosition point = new ChessPosition(x - i, y + i);
            if (board.getPiece(point) == null || (board.getPiece(point) != null && board.getPiece(point).getTeamColor()!= piece.getTeamColor())) {
                ChessMove move = new ChessMove(position, point, null);
                bishopMoves.add(move);
            }
        }
        for (int i = 1; i < topRight-1; i++) {
            ChessPosition point = new ChessPosition(x + i, y - i);
            if (board.getPiece(point) == null || (board.getPiece(point) != null && board.getPiece(point).getTeamColor()!= piece.getTeamColor())) {
                ChessMove move = new ChessMove(position, point, null);
                bishopMoves.add(move);
            }
        }
        for (int i = 1; i <= bottomRight; i++) {
            ChessPosition point = new ChessPosition(x + i, y + i);
            if (board.getPiece(point) == null || (board.getPiece(point) != null && board.getPiece(point).getTeamColor()!= piece.getTeamColor())) {
                ChessMove move = new ChessMove(position, point, null);
                bishopMoves.add(move);
            }
        }

        return bishopMoves;
    }

    @Override
    public String toString() {
        return "BishopMovesCalculator{" +
                "bishopMoves=" + bishopMoves +
                '}';
    }

}