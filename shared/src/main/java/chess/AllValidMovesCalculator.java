package chess;

import java.util.ArrayList;
import java.util.Collection;

public class AllValidMovesCalculator {
    private static final Collection<ChessMove> allMoves = new ArrayList<>();
    private static final Collection<ChessMove> allValidMovesArray = new ArrayList<>();
    private static final Collection<ChessMove> allValidMovesArray2 = new ArrayList<>();
    private static final Collection<ChessMove> validWhiteMoves = new ArrayList<>();
    private static final Collection<ChessMove> validBlackMoves = new ArrayList<>();
    private static final Collection<ChessMove> validMoves = new ArrayList<>();

    public AllValidMovesCalculator() {

    }

    //getting all piece moves
    public static Collection<ChessMove> pieceMoves(ChessPosition startPos, ChessPiece piece, ChessBoard board) {
        allMoves.clear();

        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            allMoves.addAll(KingMovesCalculator.validKingMoves(board, startPos));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            allMoves.addAll(QueenMovesCalculator.validQueenMoves(board, startPos));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            allMoves.addAll(BishopMovesCalculator.validBishopMoves(board, startPos));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            allMoves.addAll(KnightMovesCalculator.validKnightMoves(board, startPos));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            allMoves.addAll(RookMovesCalculator.validRookMoves(board, startPos));
        }
        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            allMoves.addAll(PawnMovesCalculator.validPawnMoves(board, startPos));
        }
        return allMoves;
    }

    //Checking for valid moves
    public static Collection<ChessMove> allValidMoves(ChessBoard board) {
        allValidMovesArray.clear();

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

        return allValidMovesArray;
    }

    public static Collection<ChessMove> allValidMoves2(ChessBoard board) {
        allValidMovesArray2.clear();

        if (board != null) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition newPos = new ChessPosition(i, j);
                    ChessPiece piece = board.getPiece(newPos);
                    if (piece != null) {
                        allValidMovesArray2.addAll(pieceMoves(newPos, piece, board));
                    }
                }
            }
        }

        return allValidMovesArray2;
    }

    public static void colorMovesCheck(ChessBoard board, ChessPiece piece, ChessPosition newPos) {
        ChessGame.TeamColor teamColor = board.getPiece(newPos).getTeamColor();
        if (teamColor == ChessGame.TeamColor.BLACK) {
            allValidMovesArray.addAll(pieceMoves(newPos, piece, board));
        } else if (teamColor == ChessGame.TeamColor.WHITE) {
            allValidMovesArray.addAll(pieceMoves(newPos, piece, board));
        }
    }

    public static boolean teamInCheck(ChessPosition kingPos, Collection<ChessMove> chessMoves) {
        boolean inCheck = false;

        for (ChessMove move : validMoves) {
            ChessPosition endPos = move.getEndPosition();
            int xEnd = endPos.getRow();
            int yEnd = endPos.getColumn();
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
        ChessPosition startPos = move.getStartPosition();
        ChessPosition endPos = move.getEndPosition();
        ChessBoard copy = new ChessBoard(board);

        board.addPiece(endPos, piece);
        board.addPiece(startPos, null);

        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            if (AllValidMovesCalculator.teamInCheck(endPos, allValidMovesArray)) {
                board.copyBoard(copy);
            } else {
                board.copyBoard(copy);
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    validWhiteMoves.add(move);
                } else {
                    validBlackMoves.add(move);
                }
            }
        } else {
            if (AllValidMovesCalculator.teamInCheck(kingPos, allValidMovesArray)) {
                board.copyBoard(copy);
            } else {
                board.copyBoard(copy);
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    validWhiteMoves.add(move);
                } else {
                    validBlackMoves.add(move);
                }
            }
        }
    }

    public static void tryMoveHelper(ChessMove move, ChessPosition king, ChessPiece piece, ChessBoard board) {
        ChessBoard copy = new ChessBoard(board);
        ChessPosition endPos = move.getEndPosition();
        validMoves.clear();

        tryMove(move, king, piece, board);
        if (teamInCheck(endPos, allValidMovesArray)) {
            board.copyBoard(copy);
        } else {
            board.copyBoard(copy);
            if (!validMoves.contains(move)) {
                validMoves.add(move);
            }
        }
    }

    public static Collection<ChessMove> moveHelper2(ChessPiece piece, ChessMove move, ChessPosition king, ChessBoard board) {
        validMoves.clear();
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            tryMoveHelper(move, king, piece, board);
        } else {
            if (teamInCheck(king, allValidMovesArray)) {
                tryMove(move, king, piece, board);
            } else {
                tryMove(move, king, piece, board);
                if (!validMoves.contains(move)) {
                    validMoves.add(move);
                }
            }
        }
        return validMoves;
    }
}
