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

    private TeamColor team;
    private ChessBoard board;
    private final Collection<ChessMove> validMoves = new ArrayList<>();
    private final Collection<ChessMove> allMoves = new ArrayList<>();
    private final Collection<ChessMove> allValidMovesArray = new ArrayList<>();
    private final Collection<ChessMove> checkValidMovesArray = new ArrayList<>();
    private int validMovesCounter = 0;
    private final Collection<ChessMove> allWhiteMoves = new ArrayList<>();
    private final Collection<ChessMove> allBlackMoves = new ArrayList<>();
    private final Collection<ChessMove> validPieceMoves = new ArrayList<>();

    private PawnMovesCalculator pawnMoves = new PawnMovesCalculator();
    private BishopMovesCalculator bishopMoves = new BishopMovesCalculator();
    private KnightMovesCalculator knightMoves = new KnightMovesCalculator();
    private RookMovesCalculator rookMoves = new RookMovesCalculator();
    private QueenMovesCalculator queenMoves = new QueenMovesCalculator();
    private KingMovesCalculator kingMoves = new KingMovesCalculator();

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
                    ChessPosition newPosition = new ChessPosition(i, j);
                    if (board.getPiece(newPosition) != null) {
                        //getting black piece moves
                        TeamColor teamColor = board.getPiece(newPosition).getTeamColor();
                        if (teamColor == ChessGame.TeamColor.BLACK) {
                            allValidMovesArray.addAll(pieceMoves(newPosition));
                        }
                        //getting white piece moves
                        else if (teamColor == ChessGame.TeamColor.WHITE) {
                            allValidMovesArray.addAll(pieceMoves(newPosition));
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

        if (piece.getPieceType() == ChessPiece.PieceType.KING)  {
            allMoves.addAll(kingMoves.validKingMoves(board,startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.QUEEN)  {
            allMoves.addAll(queenMoves.validQueenMoves(board,startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            allMoves.addAll(bishopMoves.validBishopMoves(board,startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            allMoves.addAll(knightMoves.validKnightMoves(board,startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            allMoves.addAll(rookMoves.validRookMoves(board,startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            allMoves.addAll(pawnMoves.validPawnMoves(board,startPosition));
        }

        return allMoves;
    }

    public Collection<ChessMove> checkValidMoves(ChessBoard board, ChessGame.TeamColor teamColor, Collection<ChessMove> possibleMoves) {
        validMoves.clear();

        if (isInCheck(teamColor)) {
            ChessBoard copyBoard = new ChessBoard(board);
            for (ChessMove move : possibleMoves) {
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
            for (ChessMove move : possibleMoves) {
                try {
                    makeMove(move);
                    if (isInCheck(teamColor)) {
                        board.copyBoard(copyBoard);
                    } else {
                        if (!validMoves.contains(move)) {
                        board.copyBoard(copyBoard);
                        validMoves.add(move);
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

        if (board != null && board.getPiece(startPosition) != null) {
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

            if (promoPiece != null) {
                TeamColor teamColor = piece.getTeamColor();
                ChessPiece promPiece = new ChessPiece(teamColor, promoPiece);
                board.addPiece(endPosition, promPiece);
            } else {
                board.addPiece(endPosition, piece);
            }
            board.addPiece(startPosition, null);
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
        ChessPosition findKing = new ChessPosition(0,0);

        ChessBoard board = getBoard();
        ChessPosition kingWhitePos = findKing.findWhiteKing(board);
        ChessPosition kingBlackPos = findKing.findBlackKing(board);

        if (teamColor == ChessGame.TeamColor.BLACK) {
            inCheck = teamInCheck(kingBlackPos);
        }
        else if (teamColor == ChessGame.TeamColor.WHITE) {
            inCheck = teamInCheck(kingWhitePos);
        }

        return inCheck;
    }

    //created to avoid duplicate code in isInCheck(teamColor)
    public boolean teamInCheck(ChessPosition kingPos) {
        boolean inCheck = false;
        for (ChessMove move : allValidMovesArray) {
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
        ChessBoard copyBoard = new ChessBoard(board);
        Collection<ChessMove> newValidMoves = new ArrayList<>();
        checkValidMovesArray.clear();

        if (board != null && board.getPiece(startPosition) != null) {
            if (xEnd <= 8 && xEnd >= 1 && yEnd <= 8 && yEnd >= 1) {
                if (!checkValidMovesArray.contains(move)) {
                    checkValidMovesArray.addAll(pieceMoves(startPosition));
                }
            }
        } else {
            throw new InvalidMoveException();
        }

        if (checkValidMovesArray.contains(move)) {
            for (ChessMove checkMove : checkValidMovesArray) {
                ChessBoard board = getBoard();
                ChessPiece piece = board.getPiece(startPosition);

                if (promoPiece != null) {
                    TeamColor teamColor = piece.getTeamColor();
                    ChessPiece promPiece = new ChessPiece(teamColor, promoPiece);
                    board.addPiece(endPosition, promPiece);
                    if (isInCheck(piece.getTeamColor())) {
                        board.copyBoard(copyBoard);
                    } else {
                        newValidMoves.add(checkMove);
                    }
                } else {
                    board.addPiece(endPosition, piece);
                    if (isInCheck(piece.getTeamColor())) {
                        board.copyBoard(copyBoard);
                    } else {
                        newValidMoves.add(checkMove);
                    }
                }
            }
        } else {
            throw new InvalidMoveException();
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
        return isInCheck(teamColor) && validMoves.isEmpty();
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return validMoves.isEmpty() && !isInCheck(teamColor);
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
        return board;
    }
}
