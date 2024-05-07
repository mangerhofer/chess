package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator {

    private static final Collection<ChessMove> knightMoves = new ArrayList<>();

    public static Collection<ChessMove> validKnightMoves(ChessBoard board, ChessPosition position) {
        //creating array of all possible moves regardless of board space
        int[][] possMoves = {{1,2},{1,-2},{-1,2},{-1,-2},{2,-1},{-2,-1},{-2,1},{2,1}};

        ChessPiece piece = board.getPiece(position);

        int x = position.getRow();
        int y = position.getColumn();

        // Finding possible moves for Knight and adding to collection
        for(int[] i: possMoves) {
            ChessPosition newPos = new ChessPosition(i[0] + x, i[1] + y);
            ChessMove move;
            if (board.getPiece(newPos) != null && (board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) ) {
                move = new ChessMove(position, newPos, null);
                knightMoves.add(move);
            } else if (board.getPiece(newPos) == null) {
                move = new ChessMove(position, newPos, null);
                knightMoves.add(move);
            }
        }

        return knightMoves;
    }

}
