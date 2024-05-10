package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class KingMovesCalculator {

    private static final Collection<ChessMove> kingMoves = new ArrayList<>();

    public static Collection<ChessMove> validKingMoves(ChessBoard board, ChessPosition position) {
        kingMoves.clear();
        //creating array of all possible moves regardless of board space
        int[][] possMoves = {{1,1},{1,-1},{1,0},{0,1},{0,-1},{-1,0},{-1,-1},{-1,1}};

        ChessPiece piece = board.getPiece(position);

        int x = position.getRow();
        int y = position.getColumn();

        // Finding possible moves for King and adding to collection
        for(int[] i: possMoves) {
            if (i[0]+x <= 8 && i[1]+y <= 8 && i[0]+x >= 1 && i[1]+y >= 1) {
                ChessPosition newPos = new ChessPosition(i[0] + x, i[1] + y);
                ChessMove move;
                if (board.getPiece(newPos) != null && (board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) ) {
                    move = new ChessMove(position, newPos, null);
                    kingMoves.add(move);
                } else if (board.getPiece(newPos) == null) {
                    move = new ChessMove(position, newPos, null);
                    kingMoves.add(move);
                }
            } else {
                break;
            }
        }

        return kingMoves;
    }
}
