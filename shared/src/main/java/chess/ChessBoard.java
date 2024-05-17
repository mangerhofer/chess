package chess;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Cloneable {

    private final ChessPiece[][] board = new ChessPiece[8][8];
    private static final Collection<ChessMove> validMoves = new ArrayList<>();
    private static final Collection<ChessMove> validWhiteMoves = new ArrayList<>();
    private static final Collection<ChessMove> allMoves = new ArrayList<>();
    private static final Collection<ChessMove> allWhiteMoves = new ArrayList<>();
    private static final Collection<ChessMove> allBlackMoves = new ArrayList<>();


    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        //Setting black pieces back
        board[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        board[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        board[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        board[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        for (int col = 0; col < 8; col++) {
            board[6][col] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }

        // Setting white pieces back
        board[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        board[0][1] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[0][2] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        board[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        board[0][5] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[0][6] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[0][7] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        for (int col = 0; col < 8; col++) {
            board[1][col] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

        for (int col = 0; col < 8; col++) {
            for (int row = 2; row < 6; row++) {
                board[row][col] = null;
            }
        }

    }

    public static Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessBoard board = ChessGame.getBoard();
        ChessPiece piece = board.getPiece(startPosition);
        ChessGame.TeamColor teamColor = piece.getTeamColor();
        validMoves.clear();

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition newPosition = new ChessPosition(i, j);
                if (board.getPiece(newPosition) != null) {
                    //getting black piece moves
                    if (board.getPiece(newPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
                        allBlackMoves.addAll(getAllMoves(newPosition));
                    }
                    //getting white piece moves
                    else if (board.getPiece(newPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
                        allWhiteMoves.addAll(getAllMoves(newPosition));
                    }
                }
            }
        }

        if (teamColor == ChessGame.TeamColor.BLACK) {
            if (!board.isInCheck(teamColor)) {
//                for (ChessMove move : allBlackMoves) {
//                    validMoves.add(move);
//                }
//            } else {
                validMoves.addAll(allBlackMoves);
            }
        }
        else if (teamColor == ChessGame.TeamColor.WHITE) {
            if (!board.isInCheck(teamColor)) {
//                for (ChessMove move : allWhiteMoves) {
//                    validMoves.add(move);
//                }
//            } else {
                validMoves.addAll(allWhiteMoves);
            }
        }


        return validMoves;
    }

    public static Collection<ChessMove> getAllMoves(ChessPosition startPosition) {
        allMoves.clear();
        ChessBoard board = ChessGame.getBoard();
        ChessPiece piece = board.getPiece(startPosition);

        if (piece.getPieceType() == ChessPiece.PieceType.KING)  {
            allMoves.addAll(KingMovesCalculator.validKingMoves(board,startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.QUEEN)  {
            allMoves.addAll(QueenMovesCalculator.validQueenMoves(board,startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            allMoves.addAll(BishopMovesCalculator.validBishopMoves(board,startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            allMoves.addAll(KnightMovesCalculator.validKnightMoves(board,startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            allMoves.addAll(RookMovesCalculator.validRookMoves(board,startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            allMoves.addAll(PawnMovesCalculator.validPawnMoves(board,startPosition));
        }

        return allMoves;
    }

    public static void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece.PieceType promoPiece = move.getPromotionPiece();

        ChessBoard board = ChessGame.getBoard();
        validMoves(startPosition);

        if (validMoves.contains(move)) {
            ChessPiece piece = board.getPiece(startPosition);
            board.addPiece(endPosition,piece);
            board.addPiece(startPosition,null);
        }
    }

    public boolean isInCheck(ChessGame.TeamColor teamColor) {
        boolean inCheck = false;

        ChessBoard board = ChessGame.getBoard();
        ChessPosition kingPos = ChessPosition.findKing(board);

        if (teamColor == ChessGame.TeamColor.BLACK) {
            for (ChessMove move : allWhiteMoves) {
                if (move.getEndPosition() == kingPos) {
                    inCheck = true;
                    break;
                }
            }
        }
        else if (teamColor == ChessGame.TeamColor.WHITE) {
            for (ChessMove move : allBlackMoves) {
                if (move.getEndPosition() == kingPos) {
                    inCheck = true;
                    break;
                }
            }
        }
        return inCheck;
    }

    public boolean isInCheckmate(ChessGame.TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    public boolean isInStalemate(ChessGame.TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "board=" + Arrays.toString(board) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

//    @Override
//    public ChessBoard clone() throws CloneNotSupportedException {
//        ChessBoard clone = (ChessBoard) super.clone();
//
//        ChessBoard clonedBoard = (ChessBoard) ChessGame.getBoard().clone();
//        ChessGame.setBoard(clonedBoard);
//
//        return clone;
//    }
}