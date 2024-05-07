package chess;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class BishopMovesCalculator {

    private static final Collection<ChessMove> bishopMoves = new ArrayList<>();

    public BishopMovesCalculator() {

    }

    public static Collection<ChessMove> validBishopMoves(ChessBoard board, ChessPosition position) {
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
                bishopMoves.add(move);
            }
        }

        for (int i = 1; i <= topRight; i++) {
            ChessPosition point = new ChessPosition(x - i, y + i);
            if (board.getPiece(point) == null || (board.getPiece(point) != null && board.getPiece(point).getTeamColor()!= piece.getTeamColor())) {
                ChessMove move = new ChessMove(position, point, null);
                bishopMoves.add(move);
            }
        }
        for (int i = 1; i <= bottomLeft; i++) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BishopMovesCalculator that = (BishopMovesCalculator) o;
        return Objects.equals(bishopMoves, that.bishopMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bishopMoves);
    }


}
