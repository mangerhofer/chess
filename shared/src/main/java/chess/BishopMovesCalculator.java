package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class BishopMovesCalculator {

    private Collection<ChessMove> moves = new ArrayList<ChessMove>();
    private ChessPosition position;

    public BishopMovesCalculator() {

    }

    public Collection<ChessMove> validBishopMoves(ChessBoard board, ChessPosition position) {
        if (Math.abs(this.position.getRow() - position.getRow()) == Math.abs(this.position.getColumn() - position.getColumn())) {

        }
//        if (move.getEndPosition() != null) {
//            ChessPosition position = move.getEndPosition();
//            ChessPiece otherPiece = ChessBoard  .getPiece(position);
////            ChessGame.TeamColor other = piece.getTeamColor();
//            if (other != piece.getTeamColor()) {
//
//            }
        ChessPiece piece = board.getPiece(position);
//        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BishopMovesCalculator that = (BishopMovesCalculator) o;
        return Objects.equals(moves, that.moves);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(moves);
    }


}
