package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor team = TeamColor.WHITE;
    private ChessBoard board;

    private final Collection<ChessMove> validPieceMoves = new ArrayList<>();
    private final Collection<ChessMove> validMoves = new ArrayList<>();
    private final Collection<ChessMove> validBlackMoves = new ArrayList<>();
    private final Collection<ChessMove> validWhiteMoves = new ArrayList<>();
    private final Collection<ChessMove> allBlackMoves = new ArrayList<>();
    private final Collection<ChessMove> allWhiteMoves = new ArrayList<>();
    private final Collection<ChessMove> allValidMovesArray = new ArrayList<>();
    private final Collection<ChessMove> allValidMovesArray2 = new ArrayList<>();
    private final Collection<ChessMove> checkValidMovesArray = new ArrayList<>();
    Collection<ChessMove> checkmateWhiteMoves = new ArrayList<>();
    Collection<ChessMove> checkmateBlackMoves = new ArrayList<>();

    private int validMovesCounter = 0;

    private boolean whiteInCheck = false;
    private boolean blackInCheck = false;

    public ChessGame() {

    }
    public ChessGame(ChessBoard board) {
        this.board = board;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    public void updateTeamTurn() {
        if (team == TeamColor.BLACK) {
            team = TeamColor.WHITE;
        } else {
            team = TeamColor.BLACK;
        }
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        validPieceMoves.clear();
        allBlackMoves.clear();
        allWhiteMoves.clear();
        ChessBoard board = getBoard();

        validMovesCounter++;

        if (board != null) {
            ChessPiece piece = board.getPiece(startPosition);
            ChessGame.TeamColor teamColor = piece.getTeamColor();

            if (teamColor == TeamColor.BLACK) {
                allBlackMoves.addAll(AllValidMovesCalculator.pieceMoves(startPosition, piece, getBoard()));
                validPieceMoves.addAll(checkValidMoves(board, teamColor, allBlackMoves));
            } else if (teamColor == TeamColor.WHITE) {
                allWhiteMoves.addAll(AllValidMovesCalculator.pieceMoves(startPosition, piece, getBoard()));
                validPieceMoves.addAll(checkValidMoves(board, teamColor, allWhiteMoves));
            }
        }

        return validPieceMoves;
    }

    public Collection<ChessMove> checkValidMoves(ChessBoard board, TeamColor teamColor, Collection<ChessMove> possMoves) {
        validMoves.clear();

        if (isInCheck(teamColor)) {
            ChessBoard copyBoard = new ChessBoard(board);
            for (ChessMove move : possMoves) {
                try {
                    makeMove(move);
                } catch (InvalidMoveException e){
                    throw new RuntimeException(e);
                }
                if (isInCheck(teamColor)) {
                    board.copyBoard(copyBoard);
                } else {
                    if (!validMoves.contains(move)) {
                        validMoves.add(move);
                        board.copyBoard(copyBoard);
                    }
                }
            }
        } else {
            ChessBoard copyBoard = new ChessBoard(board);
            for (ChessMove move : possMoves) {
                try {
                    makeMove(move);
                    if (isInCheck(teamColor)) {
                        board.copyBoard(copyBoard);
                    } else if (!isInCheck(teamColor) && !validMoves.contains(move)) {
                        validMoves.add(move);
                        board.copyBoard(copyBoard);
                    }
                } catch (InvalidMoveException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPos = move.getStartPosition();
        ChessPosition endPos = move.getEndPosition();
        ChessPiece.PieceType promoPiece = move.getPromotionPiece();
        int xEnd = endPos.getRow();
        int yEnd = endPos.getColumn();
        allValidMovesArray.clear();

        if (getBoard() != null && getBoard().getPiece(startPos) != null) {
            if (xEnd <= 8 && xEnd >= 1 && yEnd <=8 && yEnd >= 1) {
                if (!allValidMovesArray.contains(move)) {
                    if (validMovesCounter >= 1) {
                        allValidMovesArray.addAll(AllValidMovesCalculator.allValidMoves(getBoard()));
                    } else {
                        allValidMovesArray.addAll(checkMove(move));
                    }
                }
            }
        } else {
            throw new InvalidMoveException();
        }

        if (allValidMovesArray.contains(move)) {
            ChessBoard board = getBoard();
            ChessPiece piece = board.getPiece(startPos);

            if (promoPiece != null) {
                TeamColor teamColor = piece.getTeamColor();
                ChessPiece promPiece = new ChessPiece(teamColor, promoPiece);
                board.addPiece(endPos, promPiece);
            } else {
                board.addPiece(endPos, piece);
            }

            board.addPiece(startPos, null);
            updateTeamTurn();
        } else {
            throw new InvalidMoveException();
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> checkMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not Implemented");
    }

    public Collection<ChessMove> checkPromotion(ChessPosition startPos, ChessPosition endPos, ChessPiece.PieceType promoPiece, TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> checkTeamCheckmate(ChessMove move, ChessPosition kingPos) {
        throw new RuntimeException("Not Implemented");
    }

    public boolean checkCheckmate(Collection<ChessMove> validMovesArray, TeamColor teamColor) {
        throw new RuntimeException("Not Implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    public void checkValidMoves2(ChessBoard board, Collection<ChessMove> possMoves) {
        throw new RuntimeException("Not Implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        ChessBoard copy = new ChessBoard();
        if (board == null) {
            copy.resetBoard();
            this.board = copy;
        }
        return board;
    }
}
