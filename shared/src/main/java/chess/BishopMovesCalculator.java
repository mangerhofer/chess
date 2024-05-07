package chess;

import java.util.Collection;
import java.util.Objects;

public class BishopMovesCalculator {
    private Collection<ChessMove> moves;

    public BishopMovesCalculator(Collection<ChessMove> moves) {
        this.moves = moves;
    }

    public Collection<ChessMove> validBishopMoves(ChessPosition position) {
        //
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
