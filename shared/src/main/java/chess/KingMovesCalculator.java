package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class KingMovesCalculator {

    private static final Collection<ChessMove> kingMoves = new ArrayList<>();

    public KingMovesCalculator() {
        super();
    }

    public static Collection<ChessMove> validKingMoves(ChessBoard board, ChessPosition position) {
        //creating array of all possible moves regardless of board space
        int[][] possMoves = {{1,1},{1,-1},{1,0},{0,1},{0,-1},{-1,0},{-1,-1},{-1,1}};

        ChessPiece piece = board.getPiece(position);

        int x = position.getColumn();
        int y = position.getRow();

        // Finding possible moves for King and adding to collection
        for(int[] i: possMoves) {
            ChessPosition newPos = new ChessPosition(i[0] + x-1, i[1] + y-1);
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

    @Override
    public String toString() {
        return "KingMovesCalculator{" +
                "kingMoves=" + kingMoves +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KingMovesCalculator that = (KingMovesCalculator) o;
        return Objects.equals(kingMoves, kingMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(kingMoves);
    }
}
