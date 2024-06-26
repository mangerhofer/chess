package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType type;
    private PawnMovesCalculator pawnMoves = new PawnMovesCalculator();
    private BishopMovesCalculator bishopMoves = new BishopMovesCalculator();
    private KnightMovesCalculator knightMoves = new KnightMovesCalculator();
    private RookMovesCalculator rookMoves = new RookMovesCalculator();
    private QueenMovesCalculator queenMoves = new QueenMovesCalculator();
    private KingMovesCalculator kingMoves = new KingMovesCalculator();

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        Collection<ChessMove> moves = new ArrayList<>();
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            moves = kingMoves.validKingMoves(board, myPosition);
        } else if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            moves = queenMoves.validQueenMoves(board, myPosition);
        } else if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            moves = bishopMoves.validBishopMoves(board, myPosition);
        } else if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            moves = knightMoves.validKnightMoves(board, myPosition);
        } else if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            moves = rookMoves.validRookMoves(board, myPosition);
        } else if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            moves = pawnMoves.validPawnMoves(board,myPosition);
        }
        return moves;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }
}
