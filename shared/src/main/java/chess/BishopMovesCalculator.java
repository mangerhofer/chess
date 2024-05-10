package chess;

import java.util.ArrayList;
import java.util.Collection;

public class BishopMovesCalculator {

    private static final Collection<ChessMove> bishopMoves = new ArrayList<>();

    public BishopMovesCalculator() {

    }

    public static Collection<ChessMove> validBishopMoves(ChessBoard board, ChessPosition position) {
        bishopMoves.clear();
        int x = position.getRow();
        int y = position.getColumn();

        int bottomLeft = Math.min(x,y);
        int bottomRight = Math.max(x, 7-y);
        int topLeft = Math.min(7-x, y);
        int topRight = Math.max(7-x, 7-y);


        for (int i = 1; i <= topRight+1; i++) {
            ChessPosition point = new ChessPosition(x + i, y + i);
            if (point.getRow() <= 8 && point.getColumn() <= 8) {
                if (board.getPiece(point) == null) {
                    getBishopMoves(board, position, point);
                } else if (board.getPiece(point) != null) {
                    getBishopMoves(board, position, point);
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = 1; i <= topLeft+2; i++) {
            ChessPosition point = new ChessPosition(x - i, y + i);
            if (point.getRow() >= 1 && point.getColumn() <= 8) {
                if (board.getPiece(point) == null) {
                    getBishopMoves(board, position, point);
                } else if (board.getPiece(point) != null) {
                    getBishopMoves(board, position, point);
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = 1; i < bottomLeft; i++) {
            ChessPosition point = new ChessPosition(x - i, y - i);
            if (point.getRow() <= 8 && point.getColumn() <= 8) {
                if (board.getPiece(point) == null) {
                    getBishopMoves(board, position, point);
                } else if (board.getPiece(point) != null) {
                    getBishopMoves(board, position, point);
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = 1; i < bottomRight-1; i++) {
            ChessPosition point = new ChessPosition(x + i, y - i);
            if (point.getRow() <= 8 && point.getColumn() >= 1) {
                if (board.getPiece(point) == null) {
                    getBishopMoves(board, position, point);
                } else if (board.getPiece(point) != null) {
                    getBishopMoves(board, position, point);
                    break;
                }
            } else {
                break;
            }
        }

        return bishopMoves;
    }

    public static void getBishopMoves(ChessBoard board, ChessPosition position, ChessPosition point) {
        ChessPiece piece = board.getPiece(position);
        if (board.getPiece(point) == null) {
            ChessMove move = new ChessMove(position, point, null);
            bishopMoves.add(move);
        } else if (board.getPiece(point) != null && board.getPiece(point).getTeamColor()!= piece.getTeamColor()) {
            ChessMove move = new ChessMove(position, point, null);
            bishopMoves.add(move);
        }
    }

}