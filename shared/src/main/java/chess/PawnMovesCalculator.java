package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator {

    private final Collection<ChessMove> pawnMoves = new ArrayList<>();

    public PawnMovesCalculator() {

    }

    public static Collection<ChessMove> validPawnMoves(ChessBoard board, ChessPosition position) {
        ChessPiece piece = board.getPiece(position);
        ChessMove move;

        int x = position.getColumn();
        int y = position.getRow();

        //getting list of possible valid moves for black pawns
        if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            //first move pawn can move 1 or 2 spaces forward
            if (position.getRow() == 2) {
                ChessPosition newPos = new ChessPosition(x, y+2);
                move = new ChessMove(position, newPos, null);
                pawnMoves.add(move);

                newPos = new ChessPosition(x, y+1);
                move = new ChessMove(position, newPos, null);
                pawnMoves.add(move);
            }
            // getting possible moves for a black pawn
            ChessPosition newPos = new ChessPosition(x, y+1);
            ChessPosition newPos1 = new ChessPosition(x-1, y+1);
            ChessPosition newPos2 = new ChessPosition(x+1, y+1);
            getPawnMoves(board, position, newPos, newPos1, newPos2);
        }

        //getting list of possible valid moves for white pawns
        else if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            //first move pawn can move 1 or 2 spaces forward
            if (position.getRow() == 7) {
                ChessPosition newPos = new ChessPosition(x, y-2);
                move = new ChessMove(position, newPos, null);
                pawnMoves.add(move);

                newPos = new ChessPosition(x, y-1);
                move = new ChessMove(position, newPos, null);
                pawnMoves.add(move);
            }

            //getting possible moves for white pawn
            ChessPosition newPos = new ChessPosition(x, y-1);
            ChessPosition newPos1 = new ChessPosition(x-1, y-1);
            ChessPosition newPos2 = new ChessPosition(x+1, y-1);
            getPawnMoves(board, position, newPos, newPos1, newPos2);
        }

        return pawnMoves;
    }

    //creating method to avoid duplicating code for getting most common pawn moves
    public void getPawnMoves(ChessBoard board, ChessPosition pos, ChessPosition pos1, ChessPosition pos2, ChessPosition pos3) {
        ChessMove move;
        ChessPiece piece = board.getPiece(pos);
        if (board.getPiece(pos1) == null) {
            move = new ChessMove(pos, pos1, null);
            pawnMoves.add(move);
            if (board.getPiece(pos2) != null && board.getPiece(pos2).getTeamColor() != piece.getTeamColor()) {
                move = new ChessMove(pos, pos2, null);
                pawnMoves.add(move);
            } else if (board.getPiece(pos3) != null && board.getPiece(pos3).getTeamColor() != piece.getTeamColor()) {
                move = new ChessMove(pos, pos3, null);
                pawnMoves.add(move);
            }
        }
        //getting code for promotion pieces for pawns
        else if (piece.getTeamColor() == ChessGame.TeamColor.BLACK && pos.getRow() == 8) {
            pawnMoves.addAll(KnightMovesCalculator.validKnightMoves(board, pos));
            pawnMoves.addAll(BishopMovesCalculator.validBishopMoves(board,pos));
        } else if (piece.getTeamColor() == ChessGame.TeamColor.WHITE && pos.getRow() == 1) {
            pawnMoves.addAll(KnightMovesCalculator.validKnightMoves(board, pos));
            pawnMoves.addAll(BishopMovesCalculator.validBishopMoves(board,pos));
        }
    }
}
