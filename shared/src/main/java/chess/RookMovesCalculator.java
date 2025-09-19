package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator {
    private static Collection<ChessMove> rookMoves = new ArrayList<>();

    public static Collection<ChessMove> validRookMoves(ChessBoard board, ChessPosition myPos) {
        rookMoves.clear();
        int x = myPos.getRow();
        int y = myPos.getColumn();

        //Checking East
        for (int i = y-1; i >= 1; i--) {
            ChessPosition newPos = new ChessPosition(x, i);
            if (newPos.getColumn() >= 1) {
                if (board.getPiece(newPos) == null) {
                    getRookMoves(board, myPos, newPos);
                } else if (board.getPiece(newPos) != null) {
                    getRookMoves(board, myPos, newPos);
                    break;
                } else {
                    break;
                }
            }
        }
        //Checking West
        for (int i = y+1; i <= 8; i++) {
            ChessPosition newPos = new ChessPosition(x, i);
            if (newPos.getColumn() <= 8) {
                if (board.getPiece(newPos) == null) {
                    getRookMoves(board, myPos, newPos);
                } else if (board.getPiece(newPos) != null) {
                    getRookMoves(board, myPos, newPos);
                    break;
                } else {
                    break;
                }
            }
        }
        //Checking South
        for (int i = x-1; i >= 1; i--) {
            ChessPosition newPos = new ChessPosition(i, y);
            if (newPos.getRow() >= 1) {
                if (board.getPiece(newPos) == null) {
                    getRookMoves(board, myPos, newPos);
                } else if (board.getPiece(newPos) != null) {
                    getRookMoves(board, myPos, newPos);
                    break;
                } else {
                    break;
                }
            }
        }
        //Checking North
        for (int i = x+1; i <= 8; i++) {
            ChessPosition newPos = new ChessPosition(i, y);
            if (newPos.getRow() <= 8) {
                if (board.getPiece(newPos) == null) {
                    getRookMoves(board, myPos, newPos);
                } else if (board.getPiece(newPos) != null) {
                    getRookMoves(board, myPos, newPos);
                    break;
                } else {
                    break;
                }
            }
        }

        return rookMoves;
    }

    public static void getRookMoves(ChessBoard board, ChessPosition myPos, ChessPosition newPos) {
        ChessPiece piece = board.getPiece(myPos);

        if (board.getPiece(newPos) == null) {
            rookMoves.add(new ChessMove(myPos, newPos, null));
        } else if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) {
            rookMoves.add(new ChessMove(myPos, newPos, null));
        }
    }
}
