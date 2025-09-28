package chess;

import java.util.Collection;
import java.util.ArrayList;

public class BishopMovesCalculator {

    private static final Collection<ChessMove> bishopMoves = new ArrayList<>();

    public static Collection<ChessMove> validBishopMoves(ChessBoard board, ChessPosition myPos) {
        bishopMoves.clear();
        int x = myPos.getRow();
        int y = myPos.getColumn();

        int bottomLeft = Math.min(x, y);
        int bottomRight = Math.max(x, 7-y);
        int topLeft = Math.min(7-x, y);
        int topRight = Math.max(7-x, 7-y);

        //bottomLeft
        bishopMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, 1, bottomLeft, -1, -1, x, y));
        //bottomRight
        bishopMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, 1, bottomRight+1, -1, 1, x, y));
        //topLeft
        bishopMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, 1, topLeft+2, 1, -1, x, y));
        //topRight
        bishopMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, 1, topRight+2, 1, 1, x, y));

        return bishopMoves;
    }
}