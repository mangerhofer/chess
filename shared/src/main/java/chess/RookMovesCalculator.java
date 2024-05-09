package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator {

    private static final Collection<ChessMove> rookMoves = new ArrayList<>();

    public static Collection<ChessMove> validRookMoves(ChessBoard board, ChessPosition position) {
        ChessPiece piece = board.getPiece(position);
        int x = position.getColumn();
        int y = position.getRow();

        int topLeft = Math.min(x,y);
        int topRight = Math.max(x, 7-y);
        int bottomLeft = Math.min(7-x, y);
        int bottomRight = Math.min(7-x, 7-y);

        for (int i = 1; i <= topLeft; i++) {
            ChessPosition point = new ChessPosition(x - i, y - i);
            if (board.getPiece(point) == null || (board.getPiece(point) != null && board.getPiece(point).getTeamColor()!= piece.getTeamColor())) {
                ChessMove move = new ChessMove(position, point, null);
                rookMoves.add(move);
            }
        }

        for (int i = 1; i <= topRight; i++) {
            ChessPosition point = new ChessPosition(x - i, y + i);
            if (board.getPiece(point) == null || (board.getPiece(point) != null && board.getPiece(point).getTeamColor()!= piece.getTeamColor())) {
                ChessMove move = new ChessMove(position, point, null);
                rookMoves.add(move);
            }
        }
        for (int i = 1; i <= bottomLeft; i++) {
            ChessPosition point = new ChessPosition(x + i, y - i);
            if (board.getPiece(point) == null || (board.getPiece(point) != null && board.getPiece(point).getTeamColor()!= piece.getTeamColor())) {
                ChessMove move = new ChessMove(position, point, null);
                rookMoves.add(move);
            }
        }
        for (int i = 1; i <= bottomRight; i++) {
            ChessPosition point = new ChessPosition(x + i, y + i);
            if (board.getPiece(point) == null || (board.getPiece(point) != null && board.getPiece(point).getTeamColor()!= piece.getTeamColor())) {
                ChessMove move = new ChessMove(position, point, null);
                rookMoves.add(move);
            }
        }



        return rookMoves;
    }

}
