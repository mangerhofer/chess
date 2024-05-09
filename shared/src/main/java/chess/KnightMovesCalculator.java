package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalculator {

    private static final Collection<ChessMove> knightMoves = new ArrayList<>();

    public KnightMovesCalculator() {

    }

    public static Collection<ChessMove> validKnightMoves(ChessBoard board, ChessPosition position) {
        knightMoves.clear();
        //creating array of all possible moves regardless of board space
        int[][] possMoves = {{1,2},{1,-2},{-1,2},{-1,-2},{2,-1},{-2,-1},{-2,1},{2,1}};

        ChessPiece piece = board.getPiece(position);

        int x = position.getRow();
        int y = position.getColumn();

        // Finding possible moves for Knight and adding to collection
        for(int[] i: possMoves) {
            if (i[0]+x <= 8 && i[1]+y <= 8 && i[0]+x >= 1 && i[1]+y >= 1) {
                ChessPosition newPos = new ChessPosition(i[0] + x, i[1] + y);
                ChessMove move;
                if (board.getPiece(newPos) != null && (board.getPiece(newPos).getTeamColor() != piece.getTeamColor())) {
                    move = new ChessMove(position, newPos, null);
                    knightMoves.add(move);
                } else if (board.getPiece(newPos) == null) {
                    move = new ChessMove(position, newPos, null);
                    knightMoves.add(move);
                }
            }
        }

        return knightMoves;
    }

    @Override
    public String toString() {
        return "KnightMovesCalculator{" +
                "knightMoves=" + knightMoves +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
