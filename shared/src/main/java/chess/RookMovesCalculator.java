package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator {

    private final Collection<ChessMove> rookMoves = new ArrayList<>();
    private MovesCalculator movesCalculator = new MovesCalculator();

    public Collection<ChessMove> validRookMoves(ChessBoard board, ChessPosition position) {
        rookMoves.clear();
        int x = position.getRow();
        int y = position.getColumn();

        // checking horizontal moves
        // west
        for (int i = y+1; i <= 8; i++) {
            ChessPosition nextPosition = new ChessPosition(x, i);
            if (nextPosition.getColumn() <= 8) {
                if (board.getPiece(nextPosition) == null) {
                    rookMoves.addAll(movesCalculator.bishopAndRookMoves(board,position,nextPosition));
                } else if (board.getPiece(position) != null) {
                    rookMoves.addAll(movesCalculator.bishopAndRookMoves(board,position,nextPosition));
                    break;
                }
            } else {
                break;
            }
        }
        // east
        for (int i = y-1; i >= 1; i--) {
            ChessPosition nextPosition = new ChessPosition(x, i);
            if (nextPosition.getColumn() >= 1) {
                if (board.getPiece(nextPosition) == null) {
                    rookMoves.addAll(movesCalculator.bishopAndRookMoves(board,position,nextPosition));
                } else if (board.getPiece(position) != null) {
                    rookMoves.addAll(movesCalculator.bishopAndRookMoves(board,position,nextPosition));
                    break;
                }
            } else {
                break;
            }
        }

//         checking vertical moves
        // north
        for (int i = x+1; i <= 8; i++) {
            ChessPosition nextPosition = new ChessPosition(i, y);
            if (nextPosition.getRow() <= 8) {
                if (board.getPiece(nextPosition) == null) {
                    rookMoves.addAll(movesCalculator.bishopAndRookMoves(board,position,nextPosition));
                } else if (board.getPiece(position) != null) {
                    rookMoves.addAll(movesCalculator.bishopAndRookMoves(board,position,nextPosition));
                    break;
                }
            } else {
                break;
            }
        }
        //south
        for (int i = x-1; i >= 1; i--) {
            ChessPosition nextPosition = new ChessPosition(i, y);
            if (nextPosition.getRow() >= 1) {
                if (board.getPiece(nextPosition) == null) {
                    rookMoves.addAll(movesCalculator.bishopAndRookMoves(board,position,nextPosition));
                } else if (board.getPiece(position) != null) {
                    rookMoves.addAll(movesCalculator.bishopAndRookMoves(board,position,nextPosition));
                    break;
                }
            } else {
                break;
            }
        }

        return rookMoves;
    }

}
