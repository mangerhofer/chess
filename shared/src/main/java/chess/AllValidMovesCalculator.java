package chess;

import java.util.ArrayList;
import java.util.Collection;

public class AllValidMovesCalculator {
    private static final Collection<ChessMove> ALLMOVES = new ArrayList<>();
    private static final Collection<ChessMove> ALLVALIDMOVESARRAY = new ArrayList<>();
    private static final Collection<ChessMove> ALLVALIDMOVESARRAY2 = new ArrayList<>();
    private static final Collection<ChessMove> VALIDWHITEMOVES = new ArrayList<>();
    private static final Collection<ChessMove> VALIDBLACKMOVES = new ArrayList<>();
    private static final Collection<ChessMove> VALIDMOVES = new ArrayList<>();

    public AllValidMovesCalculator() {}

    //getting all Piece Moves
    public static Collection<ChessMove> pieceMoves(ChessPosition startPosition, ChessPiece piece, ChessBoard board) {
        ALLMOVES.clear();

        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            ALLMOVES.addAll(KingMovesCalculator.validKingMoves(board, startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            ALLMOVES.addAll(QueenMovesCalculator.validQueenMoves(board, startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            ALLMOVES.addAll(BishopMovesCalculator.validBishopMoves(board, startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            ALLMOVES.addAll(KnightMovesCalculator.validKnightMoves(board, startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            ALLMOVES.addAll(RookMovesCalculator.validRookMoves(board, startPosition));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            ALLMOVES.addAll(PawnMovesCalculator.validPawnMoves(board, startPosition));
        }

        return ALLMOVES;
    }

    //checking for valid moves
    public static Collection<ChessMove> allValidMoves(ChessBoard board) {
        ALLVALIDMOVESARRAY.clear();

        if (board != null) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition newPos = new ChessPosition(i, j);
                    ChessPiece piece = board.getPiece(newPos);
                    if (piece != null) {
                        colorMovesCheck(board, piece, newPos);
                    }
                }
            }
        }

        return ALLVALIDMOVESARRAY;
    }

    public static Collection<ChessMove> allValidMoves2(ChessBoard board) {
        ALLVALIDMOVESARRAY2.clear();

        if (board != null) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition newPos = new ChessPosition(i, j);
                    ChessPiece piece = board.getPiece(newPos);
                    if (piece != null) {
                        ALLVALIDMOVESARRAY2.addAll(pieceMoves(newPos, piece, board));
                    }
                }
            }
        }

        return ALLVALIDMOVESARRAY2;
    }

    public static void colorMovesCheck(ChessBoard board, ChessPiece piece, ChessPosition newPos) {
        ChessGame.TeamColor teamColor = board.getPiece(newPos).getTeamColor();
        if (teamColor == ChessGame.TeamColor.BLACK) {
            ALLVALIDMOVESARRAY.addAll(pieceMoves(newPos, piece, board));
        } else if (teamColor == ChessGame.TeamColor.WHITE) {
            ALLVALIDMOVESARRAY.addAll(pieceMoves(newPos, piece, board));
        }
    }

    public static boolean teamInCheck(ChessPosition kingPos, Collection<ChessMove> validMoves) {
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

    public static void tryMove(ChessMove move, ChessPosition kingPos, ChessPiece piece, ChessBoard board) {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessBoard copy = new ChessBoard(board);

        board.addPiece(endPosition, piece);
        board.addPiece(startPosition, null);

        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            if (AllValidMovesCalculator.teamInCheck(endPosition, ALLVALIDMOVESARRAY)) {
                board.copyBoard(copy);
            } else {
                board.copyBoard(copy);
                if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    VALIDWHITEMOVES.add(move);
                } else {
                    VALIDBLACKMOVES.add(move);
                }
            }
        } else {
            if (AllValidMovesCalculator.teamInCheck(kingPos, ALLVALIDMOVESARRAY)) {
                board.copyBoard(copy);
            } else {
                board.copyBoard(copy);
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    VALIDWHITEMOVES.add(move);
                } else {
                    VALIDBLACKMOVES.add(move);
                }
            }
        }
    }

    public static void tryMoveHelper(ChessMove move, ChessPosition king, ChessPiece piece, ChessBoard board) {
        ChessBoard copy = new ChessBoard(board);
        ChessPosition endPosition = move.getEndPosition();
        VALIDMOVES.clear();

        tryMove(move, king, piece, board);
        if (teamInCheck(endPosition, ALLVALIDMOVESARRAY)) {
            board.copyBoard(copy);
        } else {
            board.copyBoard(copy);
            if (!VALIDMOVES.contains(move)) {
                VALIDMOVES.add(move);
            }
        }
    }

    public static Collection<ChessMove> moveHelper2 (ChessPiece piece, ChessMove move, ChessPosition king, ChessBoard board) {
        VALIDMOVES.clear();
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            tryMoveHelper(move, king, piece, board);
        } else {
            if (teamInCheck(king, ALLVALIDMOVESARRAY)) {
                tryMove(move, king, piece, board);
            } else {
                tryMove(move, king, piece, board);
                if (!VALIDMOVES.contains(move)) {
                    VALIDMOVES.add(move);
                }
            }
        }
        return VALIDMOVES;
    }
}
