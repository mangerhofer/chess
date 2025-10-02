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
}
