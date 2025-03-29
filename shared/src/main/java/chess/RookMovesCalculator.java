package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator {
    private static final Collection<ChessMove> RookMoves = new ArrayList<>();

    public static Collection<ChessMove> validRookMoves(ChessBoard board, ChessPosition myPos) {
        RookMoves.clear();
        int x = myPos.getRow();
        int y = myPos.getColumn();

        //Checking East
        RookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, y-1, 0, 0, 1, x, 0));
        //Checking West
        RookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, y+1, 9, 0, 1, x, 0));
        //Checking South
        RookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, x-1, 0, 1, 0, 0, y));
        //Checking North
        RookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, x+1, 9, 1, 0, 0, y));

        return RookMoves;
    }
}
