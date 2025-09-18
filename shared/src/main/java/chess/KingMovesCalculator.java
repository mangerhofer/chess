package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator {
    private static final Collection<ChessMove> kingMoves = new ArrayList<>();

    public static Collection<ChessMove> validKingMoves(ChessBoard board, ChessPosition myPos) {
        kingMoves.clear();
        ChessPiece piece = board.getPiece(myPos);
        int x = myPos.getRow();
        int y = myPos.getColumn();
        int[][] possMoves = {{0,1}, {0,-1},{1,0},{-1,0},{1,1},{-1,-1},{-1,1},{1,-1}};

        for (int[] move : possMoves) {
            if (move[0]+x <= 8 && move[1]+y <= 8 && move[0]+x >= 1 && move[1]+y >= 1) {
                ChessPosition newPos = new ChessPosition(move[0]+x, move[1]+y);
                ChessMove newMove;
                if (board.getPiece(newPos) == null) {
                    newMove = new ChessMove(myPos, newPos, null);
                    kingMoves.add(newMove);
                } else if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) {
                    newMove = new ChessMove(myPos, newPos, null);
                    kingMoves.add(newMove);
                }
            }
        }

        return kingMoves;
    }
}
