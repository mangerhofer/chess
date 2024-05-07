package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class BishopMovesCalculator {

    private final Collection<ChessMove> bishopMoves = new ArrayList<>();
    private ChessPosition position;

    public BishopMovesCalculator() {

    }

    public static Collection<ChessMove> validBishopMoves(ChessBoard board, ChessPosition position) {
        if (Math.abs(this.position.getRow() - position.getRow()) == Math.abs(this.position.getColumn() - position.getColumn())) {

        }
        ChessPiece piece = board.getPiece(position);



        return bishopMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BishopMovesCalculator that = (BishopMovesCalculator) o;
        return Objects.equals(bishopMoves, that.bishopMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bishopMoves);
    }


}
