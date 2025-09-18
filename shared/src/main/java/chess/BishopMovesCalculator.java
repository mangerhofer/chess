package chess;

import java.util.Collection;
import java.util.ArrayList;

public class BishopMovesCalculator {

    private static final Collection<ChessMove> bishopMoves = new ArrayList<>();

    public static Collection<ChessMove> validBishopMoves(ChessBoard board, ChessPosition myPos) {
        bishopMoves.clear();
        int x = myPos.getRow();
        int y = myPos.getColumn();

        int bottomLeft = Math.min(x, y);
        int bottomRight = Math.max(x, 7-y);
        int topLeft = Math.min(7-x, y);
        int topRight = Math.max(7-x, 7-y);

        for (int i = 1; i < bottomLeft; i++) {
            ChessPosition newPos = new ChessPosition(x-i, y-i);
            if (newPos.getRow() >= 1 && newPos.getColumn() >= 1) {
                if (board.getPiece(newPos) == null) {
                    getBishopMoves(board, myPos, newPos);
                } else if (board.getPiece(newPos) != null) {
                    getBishopMoves(board, myPos, newPos);
                    break;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < bottomRight+1; i++) {
            ChessPosition newPos = new ChessPosition(x-i, y+i);
            if (newPos.getRow() >= 1 && newPos.getColumn() <= 8) {
                if (board.getPiece(newPos) == null) {
                    getBishopMoves(board, myPos, newPos);
                } else if (board.getPiece(newPos) != null) {
                    getBishopMoves(board, myPos, newPos);
                    break;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < topLeft+2; i++) {
            ChessPosition newPos = new ChessPosition(x+i, y-i);
            if (newPos.getRow() <= 8 && newPos.getColumn() >= 1) {
                if (board.getPiece(newPos) == null) {
                    getBishopMoves(board, myPos, newPos);
                } else if (board.getPiece(newPos) != null) {
                    getBishopMoves(board, myPos, newPos);
                    break;
                } else {
                    break;
                }
            }
        }

        for (int i = 1; i < topRight+2; i++) {
            ChessPosition newPos = new ChessPosition(x+i, y+i);
            if (newPos.getRow() <= 8 && newPos.getColumn() <= 8) {
                if (board.getPiece(newPos) == null) {
                    getBishopMoves(board, myPos, newPos);
                } else if (board.getPiece(newPos) != null) {
                    getBishopMoves(board, myPos, newPos);
                    break;
                } else {
                    break;
                }
            }
        }

        return bishopMoves;
    }

    public static void getBishopMoves(ChessBoard board, ChessPosition myPos, ChessPosition newPos) {
        ChessPiece piece = board.getPiece(myPos);

        if (board.getPiece(newPos) == null) {
            bishopMoves.add(new ChessMove(myPos, newPos, null));
        } else if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) {
            bishopMoves.add(new ChessMove(myPos, newPos, null));
        }
    }

}