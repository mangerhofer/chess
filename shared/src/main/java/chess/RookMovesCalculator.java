package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalculator {
    private static Collection<ChessMove> rookMoves = new ArrayList<>();

    public static Collection<ChessMove> validRookMoves(ChessBoard board, ChessPosition myPos) {
        rookMoves.clear();
        int x = myPos.getRow();
        int y = myPos.getColumn();

        //Checking East
        rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, y-1, 0, 0, 1, x, 0));
        //Checking West
        rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, y+1, 9, 0, 1, x, 0));
        //Checking South
        rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, x-1, 0, 1, 0, 0, y));
        //Checking North
        rookMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, x+1, 9, 1, 0, 0, y));

        return rookMoves;
    }
}
