package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MovesCalculator {
    private static final Collection<ChessMove> pieceMoves = new ArrayList<>();

    public static Collection<ChessMove> calculateKMoves(ChessBoard board, ChessPosition myPos, int[][] possMoves) {
        pieceMoves.clear();
        int x = myPos.getRow();
        int y = myPos.getColumn();

        for (int[] move : possMoves) {
            if (move[0]+x <= 8 && move[1]+y <= 8 && move[0]+x >= 1 && move[1]+y >=1) {
                ChessPosition newPos = new ChessPosition(move[0]+x, move[1]+y);
                getPieceMoves(board, myPos, newPos);
            }
        }

        return pieceMoves;
    }

    public static void getPieceMoves(ChessBoard board, ChessPosition myPos, ChessPosition newPos) {
        ChessPiece piece = board.getPiece(myPos);

        if (board.getPiece(newPos) == null) {
            pieceMoves.add(new ChessMove(myPos, newPos, null));
        } else if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) {
            pieceMoves.add(new ChessMove(myPos, newPos, null));
        }
    }
}
