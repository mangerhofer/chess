package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator {
    private static final Collection<ChessMove> rookMoves = new ArrayList<>();

    public static Collection<ChessMove> validRookMoves(ChessBoard board, ChessPosition myPos) {
        rookMoves.clear();
        int x = myPos.getRow();
        int y = myPos.getColumn();

        //Checking east
        for (int i = y-1; i >= 1; i--) {
            ChessPosition newPos = new ChessPosition(x, i);
            if (newPos.getColumn() >= 1) {
                if (board.getPiece(newPos) == null) {
                    rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, newPos));
                } else if (board.getPiece(newPos) != null) {
                    rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, newPos));
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
                    rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, newPos));
                } else if (board.getPiece(newPos) != null) {
                    rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, newPos));
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
                if(board.getPiece(newPos) == null) {
                    rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, newPos));
                } else if (board.getPiece(newPos) != null) {
                    rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, newPos));
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
                    rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, newPos));
                } else if (board.getPiece(newPos) != null) {
                    rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, newPos));
                    break;
                } else {
                    break;
                }
            }
        }

        return rookMoves;
    }
}
