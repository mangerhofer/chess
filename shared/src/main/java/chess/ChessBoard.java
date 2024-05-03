package chess;
import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private string[][] chessBoard;

    public ChessBoard() {
        chessBoard = new string[8][8];
    }

    public string[] getChessBoard() {
        return chessBoard;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {

//        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        chessBoard [0][0] = "ROOK";
        chessBoard [7][0] = "ROOK";
        chessBoard [0][1] = "KNIGHT";
        chessBoard [7][1] = "KNIGHT";
        chessBoard [0][2] = "BISHOP";
        chessBoard [7][2] = "BISHOP";
        chessBoard [0][3] = "KING";
        chessBoard [7][4] = "KING";
        chessBoard [0][4] = "QUEEN";
        chessBoard [7][3] = "QUEEN";
        chessBoard [0][5] = "BISHOP";
        chessBoard [7][5] = "BISHOP";
        chessBoard [0][6] = "KNIGHT";
        chessBoard [7][6] = "KNIGHT";
        chessBoard [0][7] = "ROOK";
        chessBoard [7][7] = "ROOK";
        Arrays.fill(chessBoard[1], "PAWN");
        Arrays.fill(chessBoard[6], "PAWN");

//        throw new RuntimeException("Not implemented");
    }
}
