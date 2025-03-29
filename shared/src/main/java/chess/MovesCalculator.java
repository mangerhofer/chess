package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MovesCalculator {
    private static final Collection<ChessMove> PIECEMOVES = new ArrayList<>();

    public static Collection<ChessMove> calculateKMoves(ChessBoard board, ChessPosition myPos, int[][] possMoves) {
        PIECEMOVES.clear();
        int x = myPos.getRow();
        int y = myPos.getColumn();

        for (int[] move : possMoves) {
            if (move[0]+x <= 8 && move[1]+y <=8 && move[0]+x >= 1 && move[1]+y >=1) {
                ChessPosition newPos = new ChessPosition(move[0]+x, move[1]+y);
                getPieceMoves(board, myPos, newPos);
            }
        }

        return PIECEMOVES;
    }

    public static Collection<ChessMove> calculateBRMoves(ChessBoard board, ChessPosition myPos, int start, int stop, int newX, int newY, int x, int y) {
        PIECEMOVES.clear();
        int step = 1;

        if (start > stop) {
            step = -1;
        }

        for (int i = start; i != stop; i += step) {
            ChessPosition newPos = new ChessPosition(x + i * newX, y + i * newY);
            if (newPos.getRow() >= 1 && newPos.getRow() <= 8 && newPos.getColumn() >= 1 && newPos.getColumn() <= 8) {
                if (board.getPiece(newPos) == null) {
                    getPieceMoves(board, myPos, newPos);
                } else if (board.getPiece(newPos) != null) {
                    getPieceMoves(board, myPos, newPos);
                    break;
                }
            }
        }

        return PIECEMOVES;
    }

    public static void getPieceMoves(ChessBoard board, ChessPosition myPos, ChessPosition newPos) {
        ChessPiece piece = board.getPiece(myPos);
        ChessMove move;

        if (board.getPiece(newPos) == null) {
            move = new ChessMove(myPos, newPos, null);
            PIECEMOVES.add(move);
        } else if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) {
            move = new ChessMove(myPos, newPos, null);
            PIECEMOVES.add(move);
        }
    }
}
