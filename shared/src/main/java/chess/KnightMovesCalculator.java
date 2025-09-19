package chess;

import java.util.Collection;
import java.util.ArrayList;

public class KnightMovesCalculator {
    private static final Collection<ChessMove> knightMoves = new ArrayList<>();

    public static Collection<ChessMove> validKnightMoves(ChessBoard board, ChessPosition myPos) {
        knightMoves.clear();
        ChessPiece piece = board.getPiece(myPos);
        int x = myPos.getRow();
        int y = myPos.getColumn();
        int[][] possMoves = {{1,2}, {2,1}, {1,-2}, {2,-1}, {-1,2}, {-2,1}, {-1,-2}, {-2,-1}};

        for (int[] move: possMoves) {
            if (move[0]+x <= 8 && move[1]+y <= 8 && move[0]+x >= 1 && move[1]+y >= 1) {
                ChessPosition newPos = new ChessPosition(move[0]+x, move[1]+y);
                ChessMove newMove;
                if (board.getPiece(newPos) == null) {
                    newMove = new ChessMove(myPos, newPos, null);
                    knightMoves.add(newMove);
                } else if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) {
                    newMove = new ChessMove(myPos, newPos, null);
                    knightMoves.add(newMove);
                }
            }
        }
        return knightMoves;
    }
}
