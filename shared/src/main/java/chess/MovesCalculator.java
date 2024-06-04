package chess;

import java.util.ArrayList;
import java.util.Collection;

public class MovesCalculator {

    private final Collection<ChessMove> pieceMoves = new ArrayList<>();

    public Collection<ChessMove> kingAndKnightMoves(ChessBoard board, ChessPosition pos, int[][] possMoves) {
        pieceMoves.clear();
        ChessMove move;
        ChessPiece piece = board.getPiece(pos);
        int x = pos.getRow();
        int y = pos.getColumn();

        for(int[] i: possMoves) {
            if (i[0]+x <= 8 && i[1]+y <= 8 && i[0]+x >= 1 && i[1]+y >= 1) {
                ChessPosition newPos = new ChessPosition(i[0] + x, i[1] + y);
                if (board.getPiece(newPos) != null && (board.getPiece(newPos).getTeamColor() != piece.getTeamColor())) {
                    move = new ChessMove(pos, newPos, null);
                    pieceMoves.add(move);
                } else if (board.getPiece(newPos) == null) {
                    move = new ChessMove(pos, newPos, null);
                    pieceMoves.add(move);
                }
            }
        }

        return pieceMoves;
    }

    public Collection<ChessMove> bishopAndRookMoves(ChessBoard board, ChessPosition pos, ChessPosition newPos) {
        pieceMoves.clear();
        ChessMove move;
        ChessPiece piece = board.getPiece(pos);
        if (board.getPiece(newPos) == null) {
            move = new ChessMove(pos, newPos, null);
            pieceMoves.add(move);
        } else if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor()!= piece.getTeamColor()) {
            move = new ChessMove(pos, newPos, null);
            pieceMoves.add(move);
        }

        return pieceMoves;
    }
}
