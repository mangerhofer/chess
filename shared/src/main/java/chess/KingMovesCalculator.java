package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalculator {

    private Collection<ChessMove> kingMoves = new ArrayList<ChessMove>();

    public KingMovesCalculator() {
        super();
    }

    public Collection<ChessMove> validKingMoves(ChessBoard board, ChessPosition position) {
        int[][] possMoves = {{1,1},{1,-1},{1,0},{0,1},{0,-1},{-1,0},{-1,-1},{-1,1}};

        ChessPiece piece = board.getPiece(position);

        int x = position.getRow();
        int y = position.getColumn();

        for(int[] i: possMoves) {
            ChessPosition newPos = new ChessPosition(i[0] + x, i[1] + y);
            ChessMove move;
            if (board.getPiece(newPos) != null && (board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) ) {
                move = new ChessMove(position, newPos, null);
                kingMoves.add(move);
            } else if (board.getPiece(newPos) == null) {
                move = new ChessMove(position, newPos, null);
                kingMoves.add(move);
            }
        }

        return kingMoves;
    }
}
