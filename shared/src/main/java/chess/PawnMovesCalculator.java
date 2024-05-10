package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator {

    private static final Collection<ChessMove> pawnMoves = new ArrayList<>();

    public PawnMovesCalculator() {

    }

    public static Collection<ChessMove> validPawnMoves(ChessBoard board, ChessPosition position) {
        pawnMoves.clear();
        ChessPiece piece = board.getPiece(position);
        ChessMove move;

        int x = position.getRow();
        int y = position.getColumn();

        //getting list of possible valid moves for white pawns
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            //first move pawn can move 1 or 2 spaces forward
            if (position.getRow() == 2) {
                ChessPosition newPos = new ChessPosition(x + 1, y);
                if (board.getPiece(newPos) == null) {
                    move = new ChessMove(position, newPos, null);
                    pawnMoves.add(move);

                    newPos = new ChessPosition(x + 2, y);
                    if (board.getPiece(newPos) == null) {
                        move = new ChessMove(position, newPos, null);
                        pawnMoves.add(move);
                    }
                }
            } else if (position.getRow() < 7) {
                // getting possible moves for a white pawn
                ChessPosition newPos = new ChessPosition(x + 1, y);
                ChessPosition newPos1 = new ChessPosition(x + 1, y + 1);
                ChessPosition newPos2 = new ChessPosition(x + 1, y - 1);
                whitePawnMoves(board, position, newPos, newPos1, newPos2);
            } else if (position.getRow() == 7) {
                ChessPosition newPos = new ChessPosition(x + 1, y);
                ChessPosition newPos1 = new ChessPosition(x + 1, y + 1);
                ChessPosition newPos2 = new ChessPosition(x + 1, y - 1);
                getPromPiece(board, position, newPos, newPos1, newPos2);
            }
        }

        //getting list of possible valid moves for black pawns
        else if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            //first move pawn can move 1 or 2 spaces forward
            if (position.getRow() == 7) {
                ChessPosition newPos = new ChessPosition(x-1, y);
                if (board.getPiece(newPos) == null) {
                    move = new ChessMove(position, newPos, null);
                    pawnMoves.add(move);

                    newPos = new ChessPosition(x-2, y);
                    if (board.getPiece(newPos) == null) {
                        move = new ChessMove(position, newPos, null);
                        pawnMoves.add(move);
                    }
                }
            } else if (position.getRow() > 2) {
                //getting possible moves for black pawn
                ChessPosition newPos = new ChessPosition(x - 1, y);
                ChessPosition newPos1 = new ChessPosition(x - 1, y - 1);
                ChessPosition newPos2 = new ChessPosition(x - 1, y + 1);
                blackPawnMoves(board, position, newPos, newPos1, newPos2);
            } else if (position.getRow() == 2) {
                ChessPosition newPos = new ChessPosition(x - 1, y);
                ChessPosition newPos1 = new ChessPosition(x - 1, y - 1);
                ChessPosition newPos2 = new ChessPosition(x - 1, y + 1);
                getPromPiece(board, position, newPos, newPos1, newPos2);
            }
        }

        return pawnMoves;
    }

    public static void whitePawnMoves(ChessBoard board, ChessPosition pos, ChessPosition pos1, ChessPosition pos2, ChessPosition pos3) {
        ChessPiece piece = board.getPiece(pos);
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            getPawnMoves(board, pos, pos1, pos2, pos3);
        }

    }

    public static void blackPawnMoves(ChessBoard board, ChessPosition pos, ChessPosition pos1, ChessPosition pos2, ChessPosition pos3) {
        ChessPiece piece = board.getPiece(pos);
        if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            getPawnMoves(board, pos, pos1, pos2, pos3);
        }
    }

    //creating method to avoid duplicating code for getting most common pawn moves
    public static void getPawnMoves(ChessBoard board, ChessPosition pos, ChessPosition pos1, ChessPosition pos2, ChessPosition pos3) {
        ChessMove move;
        ChessPiece piece = board.getPiece(pos);

        if (board.getPiece(pos1) == null) {
            move = new ChessMove(pos, pos1, null);
            pawnMoves.add(move);
        }
        if (board.getPiece(pos2) != null && board.getPiece(pos2).getTeamColor() != piece.getTeamColor()) {
            move = new ChessMove(pos, pos2, null);
            pawnMoves.add(move);
        } else if (board.getPiece(pos3) != null && board.getPiece(pos3).getTeamColor() != piece.getTeamColor()) {
            move = new ChessMove(pos, pos3, null);
            pawnMoves.add(move);
        }
    }

    public static void getPromPiece(ChessBoard board, ChessPosition pos, ChessPosition pos1, ChessPosition pos2, ChessPosition pos3) {
        ChessMove move;
        ChessPiece piece = board.getPiece(pos);

        if (board.getPiece(pos1) == null) {
            move = new ChessMove(pos, pos1, ChessPiece.PieceType.QUEEN);
            pawnMoves.add(move);
            move = new ChessMove(pos, pos1, ChessPiece.PieceType.BISHOP);
            pawnMoves.add(move);
            move = new ChessMove(pos, pos1, ChessPiece.PieceType.KNIGHT);
            pawnMoves.add(move);
            move = new ChessMove(pos, pos1, ChessPiece.PieceType.ROOK);
            pawnMoves.add(move);
        }
        if (board.getPiece(pos2) != null && board.getPiece(pos2).getTeamColor() != piece.getTeamColor()) {
            move = new ChessMove(pos, pos2, ChessPiece.PieceType.QUEEN);
            pawnMoves.add(move);
            move = new ChessMove(pos, pos2, ChessPiece.PieceType.BISHOP);
            pawnMoves.add(move);
            move = new ChessMove(pos, pos2, ChessPiece.PieceType.KNIGHT);
            pawnMoves.add(move);
            move = new ChessMove(pos, pos2, ChessPiece.PieceType.ROOK);
            pawnMoves.add(move);
        } else if (board.getPiece(pos3) != null && board.getPiece(pos3).getTeamColor() != piece.getTeamColor()) {
            move = new ChessMove(pos, pos3, ChessPiece.PieceType.QUEEN);
            pawnMoves.add(move);
            move = new ChessMove(pos, pos3, ChessPiece.PieceType.BISHOP);
            pawnMoves.add(move);
            move = new ChessMove(pos, pos3, ChessPiece.PieceType.KNIGHT);
            pawnMoves.add(move);
            move = new ChessMove(pos, pos3, ChessPiece.PieceType.ROOK);
            pawnMoves.add(move);
        }
    }

    public static void getPromotionMoves(ChessBoard board, ChessPosition pos, ChessPiece piece) {
        switch (piece.getPieceType()) {
            case KNIGHT:
                pawnMoves.addAll(KnightMovesCalculator.validKnightMoves(board, pos));
            case BISHOP:
                pawnMoves.addAll(BishopMovesCalculator.validBishopMoves(board, pos));
            case ROOK:
                pawnMoves.addAll(RookMovesCalculator.validRookMoves(board, pos));
            case QUEEN:
                pawnMoves.addAll(QueenMovesCalculator.validQueenMoves(board, pos));
        }
    }

}
