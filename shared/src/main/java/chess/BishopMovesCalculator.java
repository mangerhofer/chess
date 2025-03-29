package chess;

import java.util.Collection;
import java.util.ArrayList;

public class BishopMovesCalculator {

    private static final Collection<ChessMove> BishopMoves = new ArrayList<>();

    public static Collection<ChessMove> validBishopMoves(ChessBoard board, ChessPosition myPos) {
        BishopMoves.clear();
        int x = myPos.getRow();
        int y = myPos.getColumn();

        int bottomLeft = Math.min(x, y);
        int bottomRight = Math.max(x, 7-y);
        int topLeft = Math.min(7-x, y);
        int topRight = Math.max(7-x, 7-y);

        //bottomLeft
        BishopMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, 1, bottomLeft, -1, -1, x, y));
        //bottomRight
        BishopMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, 1, bottomRight+1, -1, 1, x, y));
        //topLeft
        BishopMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, 1, topLeft+2, 1, -1, x, y));
        //topRight
        BishopMoves.addAll(MovesCalculator.calculateBRMoves(board, myPos, 1, topRight+2, 1, 1, x, y));

        return BishopMoves;
    }

}
