package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
        boolean inCheck = false;
        allValidMovesArray.clear();
        allValidMovesArray.addAll(AllValidMovesCalculator.allValidMoves(getBoard()));
        ChessPosition findKing = new ChessPosition(0, 0);

        ChessBoard board = getBoard();
        ChessPosition kingWhitePos = findKing.findBWKing(board, TeamColor.WHITE);
        ChessPosition kingBlackPos = findKing.findBWKing(board, TeamColor.BLACK);

        if (teamColor == TeamColor.BLACK) {
            inCheck = AllValidMovesCalculator.teamInCheck(kingBlackPos, allValidMovesArray);
        } else if (teamColor == TeamColor.WHITE) {
            inCheck = AllValidMovesCalculator.teamInCheck(kingWhitePos, allValidMovesArray);
        }

        return inCheck;
    }

    public Collection<ChessMove> checkMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPos = move.getStartPosition();
        ChessPosition endPos = move.getEndPosition();
        ChessPiece.PieceType promoPiece = move.getPromotionPiece();
        int xEnd = endPos.getRow();
        int yEnd = endPos.getColumn();
        Collection<ChessMove> newValidMoves;
        checkValidMovesArray.clear();
        TeamColor teamColor = getBoard().getPiece(startPos).getTeamColor();
        ChessPiece piece = getBoard().getPiece(startPos);

        if (getBoard() != null && piece != null) {
            if (xEnd <= 8 && xEnd >= 1 && yEnd <= 8 && yEnd >= 1) {
                if (!allValidMovesArray.contains(move)) {
                    checkValidMovesArray.addAll(AllValidMovesCalculator.pieceMoves(startPos, piece, getBoard()));
                }
            }
        } else {
            throw new InvalidMoveException();
        }

        if (checkValidMovesArray.contains(move)) {
            newValidMoves = new ArrayList<>(checkPromotion(startPos, endPos, promoPiece, teamColor));
            if (promoPiece == null) {
                if (getBoard().getPiece(startPos).getTeamColor() != TeamColor.WHITE) {
                    if (getBoard().getPiece(startPos).getTeamColor() != getTeamTurn()) {
                        throw new InvalidMoveException();
                    }
                } else if (getBoard().getPiece(startPos).getTeamColor() != TeamColor.BLACK) {
                    if (getBoard().getPiece(startPos).getTeamColor() != getTeamTurn()) {
                        throw new InvalidMoveException();
                    }
                }
            }
        } else {
            throw new InvalidMoveException();
        }

        return newValidMoves;
    }

    public Collection<ChessMove> checkPromotion(ChessPosition startPos, ChessPosition endPos, ChessPiece.PieceType promoPiece, TeamColor teamColor) {
        Collection<ChessMove> newValidMoves = new ArrayList<>();
        ChessBoard copy = new ChessBoard(board);

        for (ChessMove checkMove : checkValidMovesArray) {
            ChessBoard board = getBoard();
            ChessPiece piece = board.getPiece(startPos);

            if (promoPiece != null) {
                ChessPiece prPiece = new ChessPiece(teamColor, promoPiece);
                board.addPiece(endPos, prPiece);
            } else {
                board.addPiece(endPos, piece);
            }
            if (isInCheck(piece.getTeamColor())) {
                board.copyBoard(copy);
            } else {
                newValidMoves.add(checkMove);
            }
        }

        return newValidMoves;
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

    @Override
    public String toString() {
        return "ChessGame{" +
                "team=" + team +
                ", board=" + board +
                ", validPieceMoves=" + validPieceMoves +
                ", validMoves=" + validMoves +
                ", validBlackMoves=" + validBlackMoves +
                ", validWhiteMoves=" + validWhiteMoves +
                ", allBlackMoves=" + allBlackMoves +
                ", allWhiteMoves=" + allWhiteMoves +
                ", allValidMovesArray=" + allValidMovesArray +
                ", allValidMovesArray2=" + allValidMovesArray2 +
                ", checkValidMovesArray=" + checkValidMovesArray +
                ", checkmateWhiteMoves=" + checkmateWhiteMoves +
                ", checkmateBlackMoves=" + checkmateBlackMoves +
                ", validMovesCounter=" + validMovesCounter +
                ", whiteInCheck=" + whiteInCheck +
                ", blackInCheck=" + blackInCheck +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return validMovesCounter == chessGame.validMovesCounter && whiteInCheck == chessGame.whiteInCheck && blackInCheck == chessGame.blackInCheck && team == chessGame.team && Objects.equals(board, chessGame.board) && Objects.equals(validPieceMoves, chessGame.validPieceMoves) && Objects.equals(validMoves, chessGame.validMoves) && Objects.equals(validBlackMoves, chessGame.validBlackMoves) && Objects.equals(validWhiteMoves, chessGame.validWhiteMoves) && Objects.equals(allBlackMoves, chessGame.allBlackMoves) && Objects.equals(allWhiteMoves, chessGame.allWhiteMoves) && Objects.equals(allValidMovesArray, chessGame.allValidMovesArray) && Objects.equals(allValidMovesArray2, chessGame.allValidMovesArray2) && Objects.equals(checkValidMovesArray, chessGame.checkValidMovesArray) && Objects.equals(checkmateWhiteMoves, chessGame.checkmateWhiteMoves) && Objects.equals(checkmateBlackMoves, chessGame.checkmateBlackMoves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, board, validPieceMoves, validMoves, validBlackMoves, validWhiteMoves, allBlackMoves, allWhiteMoves, allValidMovesArray, allValidMovesArray2, checkValidMovesArray, checkmateWhiteMoves, checkmateBlackMoves, validMovesCounter, whiteInCheck, blackInCheck);
    }
}
