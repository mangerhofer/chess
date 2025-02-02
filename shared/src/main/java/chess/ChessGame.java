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

    private TeamColor team = null;
    private ChessBoard board;

    private final Collection<ChessMove> validPieceMoves = new ArrayList<>();
    private final Collection<ChessMove> validMoves = new ArrayList<>();
    private final Collection<ChessMove> validBlackMoves = new ArrayList<>();
    private final Collection<ChessMove> validWhiteMoves = new ArrayList<>();
    private final Collection<ChessMove> allBlackMoves = new ArrayList<>();
    private final Collection<ChessMove> allWhiteMoves = new ArrayList<>();
    private final Collection<ChessMove> allMoves = new ArrayList<>();
    private final Collection<ChessMove> allValidMovesArray = new ArrayList<>();
    private final Collection<ChessMove> allValidMovesArray2 = new ArrayList<>();
    private final Collection<ChessMove> checkValidMovesArray = new ArrayList<>();
    Collection<ChessMove> checkmateWhiteMoves = new ArrayList<>();
    Collection<ChessMove> checkmateBlackMoves = new ArrayList<>();

    private int validMovesCounter = 0;
    private int turnTracker = 0;

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
        TeamColor firstTurn = TeamColor.WHITE;
        if (team == null) {
            this.team = firstTurn;
        } else {
            if (turnTracker%2 == 0) {
                this.team = TeamColor.WHITE;
            } else {
                this.team = TeamColor.BLACK;
            }
        }
        return team;
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

            if (teamColor == ChessGame.TeamColor.BLACK) {
                allBlackMoves.addAll(pieceMoves(startPosition));
                validPieceMoves.addAll(checkValidMoves(board, teamColor, allBlackMoves));
            } else if (teamColor == ChessGame.TeamColor.WHITE) {
                allWhiteMoves.addAll(pieceMoves(startPosition));
                validPieceMoves.addAll(checkValidMoves(board, teamColor, allWhiteMoves));
            }
        }

        return validPieceMoves;
    }

    public void allValidMoves() {
        allValidMovesArray.clear();
        ChessBoard board = getBoard();

        if (board != null) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition newPos = new ChessPosition(i, j);
                    if (board.getPiece(newPos) != null) {
                        TeamColor teamColor = board.getPiece(newPos).getTeamColor();
                        if (teamColor == ChessGame.TeamColor.BLACK) {
                            allValidMovesArray.addAll(pieceMoves(newPos));
                        } else if (teamColor == ChessGame.TeamColor.WHITE) {
                            allValidMovesArray.addAll(pieceMoves(newPos));
                        }
                    }
                }
            }
        }
    }

    public Collection<ChessMove> pieceMoves(ChessPosition startPosition) {
        allMoves.clear();
        ChessBoard board = getBoard();
        ChessPiece piece = board.getPiece(startPosition);

        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            allMoves.addAll(KingMovesCalculator.validKingMoves(board, startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            allMoves.addAll(QueenMovesCalculator.validQueenMoves(board, startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            allMoves.addAll(BishopMovesCalculator.validBishopMoves(board, startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            allMoves.addAll(KnightMovesCalculator.validKnightMoves(board, startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            allMoves.addAll(RookMovesCalculator.validRookMoves(board, startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            allMoves.addAll(PawnMovesCalculator.validPawnMoves(board, startPosition));
        }

        return allMoves;
    }

    public Collection<ChessMove> checkValidMoves(ChessBoard board, TeamColor teamColor, Collection<ChessMove> possMoves) {
        validMoves.clear();

        if (isInCheck(teamColor)) {
            ChessBoard copyBoard = new ChessBoard(board);
            for (ChessMove move : possMoves) {
                try {
                    makeMove(move);
                } catch (InvalidMoveException e) {
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
                    } else {
                        if (!validMoves.contains(move)) {
                            validMoves.add(move);
                            board.copyBoard(copyBoard);
                        }
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
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece.PieceType promoPiece = move.getPromotionPiece();
        int xEnd = endPosition.getRow();
        int yEnd = endPosition.getColumn();
        allValidMovesArray.clear();

        if (getBoard() != null && getBoard().getPiece(startPosition) != null) {
            if (xEnd <= 8 && xEnd >= 1 && yEnd <= 8 && yEnd >= 1) {
                if (!allValidMovesArray.contains(move)) {
                    if (validMovesCounter >= 1) {
                        allValidMoves();
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
            ChessPiece piece = board.getPiece(startPosition);

            if (promoPiece != null){
                TeamColor teamColor = piece.getTeamColor();
                ChessPiece promPiece = new ChessPiece(teamColor, promoPiece);
                board.addPiece(endPosition, promPiece);
            } else {
                board.addPiece(endPosition, piece);
            }

            board.addPiece(startPosition, null);
            turnTracker ++;
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
        allValidMoves();
        ChessPosition findKing = new ChessPosition(0, 0);

        ChessBoard board = getBoard();
        ChessPosition kingWhitePos = findKing.findBWKing(board, TeamColor.WHITE);
        ChessPosition kingBlackPos = findKing.findBWKing(board, TeamColor.BLACK);

        if (teamColor == ChessGame.TeamColor.BLACK) {
            inCheck = teamInCheck(kingBlackPos, allValidMovesArray);
        } else if (teamColor == ChessGame.TeamColor.WHITE) {
            inCheck = teamInCheck(kingWhitePos, allValidMovesArray);
        }

        return inCheck;
    }

    public boolean teamInCheck(ChessPosition kingPos, Collection<ChessMove> validMoves) {
        boolean inCheck = false;

        for (ChessMove move : validMoves) {
            ChessPosition endPosition = move.getEndPosition();
            int xEnd = endPosition.getRow();
            int yEnd = endPosition.getColumn();
            if (kingPos != null) {
                int xKing = kingPos.getRow();
                int yKing = kingPos.getColumn();
                if (xEnd == xKing && yEnd == yKing) {
                    inCheck = true;
                    break;
                }
            }
        }

        return inCheck;
    }

    public Collection<ChessMove> checkMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece.PieceType promoPiece = move.getPromotionPiece();
        int xEnd = endPosition.getRow();
        int yEnd = endPosition.getColumn();
        Collection<ChessMove> newValidMoves;
        checkValidMovesArray.clear();
        TeamColor teamColor = getBoard().getPiece(startPosition).getTeamColor();

        if (getBoard() != null && getBoard().getPiece(startPosition) != null) {
            if (xEnd <= 8 && xEnd >= 1 && yEnd <= 8 && yEnd >= 1) {
                if (!allValidMovesArray.contains(move)) {
                    checkValidMovesArray.addAll(pieceMoves(startPosition));
                }
            }
        } else {
            throw new InvalidMoveException();
        }

        if (checkValidMovesArray.contains(move)) {
            newValidMoves = new ArrayList<>(checkPromotion(startPosition, endPosition, promoPiece, teamColor));
            if (promoPiece == null) {
                if (turnTracker%2 == 0 && getBoard().getPiece(startPosition).getTeamColor() != TeamColor.WHITE) {
                    if (getBoard().getPiece(startPosition).getTeamColor() == getTeamTurn()) {
                        throw new InvalidMoveException();
                    }
                } else if (turnTracker%2 == 1 && getBoard().getPiece(startPosition).getTeamColor() != TeamColor.BLACK) {
                    if (getBoard().getPiece(startPosition).getTeamColor() == getTeamTurn()) {
                        throw new InvalidMoveException();
                    }
                }
            }
        } else {
            throw new InvalidMoveException();
        }

        return newValidMoves;
    }

    public Collection<ChessMove> checkPromotion(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promoPiece, TeamColor teamColor) {
        Collection<ChessMove> newValidMoves = new ArrayList<>();
        ChessBoard copy = new ChessBoard(board);

        for (ChessMove checkMove : checkValidMovesArray) {
            ChessBoard board = getBoard();
            ChessPiece piece = board.getPiece(startPosition);

            if (promoPiece != null) {
                ChessPiece prPiece = new ChessPiece(teamColor, promoPiece);
                board.addPiece(endPosition, prPiece);
            } else {
                board.addPiece(endPosition, piece);
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
        allValidMoves();

        return checkCheckmate(allValidMovesArray, teamColor) || checkCheckmate(allValidMovesArray, teamColor);
    }

    public Collection<ChessMove> checkTeamCheckmate(ChessMove move, ChessPosition kingPos) {
        Collection<ChessMove> pieceMoves = new ArrayList<>();
        ChessBoard copy = new ChessBoard(board);
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece piece = board.getPiece(startPosition);
        TeamColor teamColor = piece.getTeamColor();
        ChessPiece kingPiece = board.getPiece(kingPos);
        TeamColor kingColor = kingPiece.getTeamColor();

        board.addPiece(endPosition, piece);
        board.addPiece(startPosition, null);

        allValidMoves2();

        if (teamColor != kingColor) {
            if (teamInCheck(kingPos, allValidMovesArray2)) {
                board.copyBoard(copy);
            }
        } else {
            if (teamInCheck(kingPos, allValidMovesArray2)) {
                board.copyBoard(copy);
            } else {
                board.copyBoard(copy);
                pieceMoves.add(move);
            }
        }

        return pieceMoves;
    }

    public void allValidMoves2() {
        allValidMovesArray2.clear();
        ChessBoard board = getBoard();

        if (board != null) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition newPos = new ChessPosition(i, j);
                    if (board.getPiece(newPos) != null) {
                        allValidMovesArray2.addAll(pieceMoves(newPos));
                    }
                }
            }
        }
    }

    public boolean checkCheckmate(Collection<ChessMove> validMovesArray, TeamColor teamColor) {
        boolean inCheckmate = false;
        ChessPosition findKing = new ChessPosition(0, 0);
        ChessPosition kingWhitePos = findKing.findBWKing(board, TeamColor.WHITE);
        ChessPosition kingBlackPos = findKing.findBWKing(board, TeamColor.BLACK);

        for (ChessMove move : validMovesArray) {
            ChessPosition startPosition = move.getStartPosition();
            ChessPiece piece = getBoard().getPiece(startPosition);
            if (piece != null) {
                if (piece.getTeamColor() == TeamColor.WHITE) {
                    if (teamInCheck(kingWhitePos, allValidMovesArray)) {
                        whiteInCheck = true;
                        checkmateWhiteMoves.addAll(checkTeamCheckmate(move, kingWhitePos));
                    } else {
                        checkmateWhiteMoves.add(move);
                    }
                }
                if (piece.getTeamColor() == TeamColor.BLACK) {
                    if (teamInCheck(kingBlackPos, allValidMovesArray)) {
                        blackInCheck = true;
                        checkmateBlackMoves.addAll(checkTeamCheckmate(move, kingBlackPos));
                    } else {
                        checkmateBlackMoves.add(move);
                    }
                }
            }
        }

        if (teamColor == TeamColor.WHITE && checkmateWhiteMoves.isEmpty() && whiteInCheck) {
            inCheckmate = true;
        } else if (teamColor == TeamColor.BLACK && checkmateBlackMoves.isEmpty() && blackInCheck) {
            inCheckmate = true;
        }

        return inCheckmate;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean inCheck = false;
        boolean noMoves = false;
        ChessPosition findKing = new ChessPosition(0, 0);
        ChessPosition whiteKing = findKing.findBWKing(getBoard(), TeamColor.WHITE);
        ChessPosition blackKing = findKing.findBWKing(getBoard(), TeamColor.BLACK);

        allValidMoves();
        checkValidMoves2(getBoard(), allValidMovesArray);

        if (teamColor == TeamColor.WHITE) {
            if (teamInCheck(whiteKing, allValidMovesArray)) {
                inCheck = true;
            } else {
                if (validWhiteMoves.isEmpty()) {
                    noMoves = true;
                }
            }
        } else if (teamColor == TeamColor.BLACK) {
            if (teamInCheck(blackKing, allValidMovesArray)) {
                inCheck = true;
            } else {
                if (validBlackMoves.isEmpty()) {
                    noMoves = true;
                }
            }
        }

        return !inCheck && noMoves;
    }

    public void checkValidMoves2(ChessBoard board, Collection<ChessMove> possMoves) {
        validBlackMoves.clear();
        validWhiteMoves.clear();
        ChessPosition findKing = new ChessPosition(0, 0);
        ChessPosition whiteKing = findKing.findBWKing(getBoard(), TeamColor.WHITE);
        ChessPosition blackKing = findKing.findBWKing(getBoard(), TeamColor.BLACK);
        ChessBoard copy = new ChessBoard(board);

        for (ChessMove move : possMoves) {
            ChessPosition startPosition = move.getStartPosition();
            ChessPosition endPosition = move.getEndPosition();
            ChessPiece piece = board.getPiece(startPosition);

            if (piece.getTeamColor() == TeamColor.WHITE) {
                if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                    tryMove(move, whiteKing, startPosition, endPosition, piece);
                    if (teamInCheck(endPosition, allValidMovesArray)) {
                        board.copyBoard(copy);
                    } else {
                        board.copyBoard(copy);
                        if (!validWhiteMoves.contains(move)) {
                            validWhiteMoves.add(move);
                        }
                    }
                } else {
                    if (teamInCheck(whiteKing, allValidMovesArray)) {
                        tryMove(move, whiteKing, startPosition, endPosition, piece);
                    } else {
                        tryMove(move, whiteKing, startPosition, endPosition, piece);
                        if (piece.getTeamColor() == TeamColor.WHITE) {
                            if (!validWhiteMoves.contains(move)) {
                                validWhiteMoves.add(move);
                            }
                        }
                    }
                }
            } else if (piece.getTeamColor() == TeamColor.BLACK) {
                if (piece.getPieceType() == ChessPiece.PieceType.KING) {
                    tryMove(move, blackKing, startPosition, endPosition, piece);
                    if (teamInCheck(endPosition, allValidMovesArray)) {
                        board.copyBoard(copy);
                    } else {
                        board.copyBoard(copy);
                        if (!validBlackMoves.contains(move)) {
                            validBlackMoves.add(move);
                        }
                    }
                } else {
                    if (teamInCheck(blackKing, allValidMovesArray)) {
                        tryMove(move, blackKing, startPosition, endPosition, piece);
                    } else {
                        tryMove(move, blackKing, startPosition, endPosition, piece);
                        if (!validBlackMoves.contains(move)) {
                            validBlackMoves.add(move);
                        }
                    }
                }
            }
        }
    }

    public void tryMove(ChessMove move, ChessPosition kingPos, ChessPosition startPosition, ChessPosition endPosition, ChessPiece piece) {
        ChessBoard copy = new ChessBoard(board);

        board.addPiece(endPosition, piece);
        board.addPiece(startPosition, null);

        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            if (teamInCheck(endPosition, allValidMovesArray)) {
                board.copyBoard(copy);
            } else {
                board.copyBoard(copy);
                if(piece.getTeamColor() == TeamColor.WHITE) {
                    validWhiteMoves.add(move);
                } else {
                    validBlackMoves.add(move);
                }
            }
        } else {
            if (teamInCheck(kingPos, allValidMovesArray)) {
                board.copyBoard(copy);
            } else {
                board.copyBoard(copy);
                if (piece.getTeamColor() == TeamColor.WHITE) {
                    validWhiteMoves.add(move);
                } else {
                    validBlackMoves.add(move);
                }
            }
        }
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
