package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalculator {
    private static final Collection<ChessMove> PAWNMOVES = new ArrayList<>();

    public static Collection<ChessMove> validPawnMoves(ChessBoard board, ChessPosition myPos) {
        PAWNMOVES.clear();
        ChessPiece piece = board.getPiece(myPos);
        int x = myPos.getRow();
        int y = myPos.getColumn();
        int[][] possWhiteMoves = {{1,0}, {1,1}, {1, -1}};
        int[][] possBlackMoves = {{-1,0}, {-1,1}, {-1,-1}};

        //White Pawn
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE && x == 2) {
            for (int[] move: possWhiteMoves) {
                ChessPosition newPos = new ChessPosition(move[0] + x, move[1] + y);
                if (newPos.getRow() >= 1 && newPos.getRow() <= 8 && newPos.getColumn() >= 1 && newPos.getColumn() <= 8) {
                    if (move[1] == 0) {
                        addPawnMovesPos0(board, myPos, newPos, x+2, y);
                    } else {
                        addPawnMoves(board, myPos, newPos, piece);
                    }
                }
            }
        } else if (piece.getTeamColor() == ChessGame.TeamColor.WHITE && x <= 7) {
            ChessPosition pos1 = new ChessPosition(x+1, y);
            ChessPosition pos2 = new ChessPosition(x+1, y+1);
            ChessPosition pos3 = new ChessPosition(x+1, y-1);
            if (y > 1 && y < 8) {
                if (x < 7) {
                    getPawnMoves(board, myPos, pos1, pos2, pos3);
                } else {
                    getPawnPromo(board, myPos,pos1, pos2, pos3);
                }
            } else if (y == 1) {
                if (x < 7) {
                    getPawnMoves(board, myPos, pos1, pos2, pos1);
                } else {
                    getPawnPromo(board, myPos, pos1, pos2, pos1);
                }
            } else {
                if (x < 7) {
                    getPawnMoves(board, myPos, pos1, pos1, pos3);
                } else {
                    getPawnPromo(board, myPos, pos1, pos1, pos3);
                }
            }
        }
        //Black Pawn
        if (piece.getTeamColor() == ChessGame.TeamColor.BLACK && x == 7) {
            for (int[] move : possBlackMoves) {
                ChessPosition newPos = new ChessPosition(move[0]+x, move[1]+y);
                if (newPos.getRow() >= 1 && newPos.getRow() <= 8 && newPos.getColumn() >= 1 && newPos.getColumn() <= 8) {
                    if (move[1] == 0) {
                        addPawnMovesPos0(board, myPos, newPos, x-2, y);
                    } else {
                        addPawnMoves(board, myPos, newPos, piece);
                    }
                }
            }
        } else if (piece.getTeamColor() == ChessGame.TeamColor.BLACK && x >= 2) {
            ChessPosition pos1 = new ChessPosition(x-1, y);
            ChessPosition pos2 = new ChessPosition(x-1, y+1);
            ChessPosition pos3 = new ChessPosition(x-1, y-1);
            if (y > 1 && y < 8) {
                if (x > 2) {
                    getPawnMoves(board, myPos, pos1, pos2, pos3);
                } else {
                    getPawnPromo(board, myPos, pos1, pos2, pos3);
                }
            } else if (y == 1) {
                if (x > 2) {
                    getPawnMoves(board, myPos, pos1, pos2, pos1);
                } else {
                    getPawnPromo(board, myPos, pos1, pos2, pos1);
                }
            } else {
                if (x > 2) {
                    getPawnMoves(board, myPos, pos1, pos1, pos3);
                } else {
                    getPawnPromo(board, myPos, pos1, pos1, pos3);
                }
            }
        }

        return PAWNMOVES;
    }

    public static void addPawnMoves(ChessBoard board, ChessPosition myPos, ChessPosition newPos, ChessPiece piece) {
        if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) {
            PAWNMOVES.add(new ChessMove(myPos, newPos, null));
        }
    }

    public static void addPawnMovesPos0(ChessBoard board, ChessPosition myPos, ChessPosition newPos, int x, int y) {
        if (board.getPiece(newPos) == null) {
            PAWNMOVES.add(new ChessMove(myPos, newPos, null));

            newPos = new ChessPosition(x, y);
            if (board.getPiece(newPos) == null) {
                PAWNMOVES.add(new ChessMove(myPos, newPos, null));
            }
        }
    }

    public static void getPawnMoves(ChessBoard board, ChessPosition myPos, ChessPosition pos1, ChessPosition pos2, ChessPosition pos3) {
        ChessPiece piece = board.getPiece(myPos);
        if (board.getPiece(pos1) == null) {
            PAWNMOVES.add(new ChessMove(myPos, pos1, null));
        }
        if (board.getPiece(pos2) != null && board.getPiece(pos2).getTeamColor() != piece.getTeamColor()) {
            PAWNMOVES.add(new ChessMove(myPos, pos2, null));
        }
        if (board.getPiece(pos3) != null && board.getPiece(pos3).getTeamColor() != piece.getTeamColor()) {
            PAWNMOVES.add(new ChessMove(myPos, pos3, null));
        }
    }

    public static void getPawnPromo(ChessBoard board, ChessPosition myPos, ChessPosition pos1, ChessPosition pos2, ChessPosition pos3) {
        ChessPiece piece = board.getPiece(myPos);
        if (board.getPiece(pos1) == null) {
            possiblePawnMoves(myPos, pos1);
        }
        if (board.getPiece(pos2) != null && board.getPiece(pos2).getTeamColor() != piece.getTeamColor()) {
            possiblePawnMoves(myPos, pos2);
        }
        if (board.getPiece(pos3) != null && board.getPiece(pos3).getTeamColor() != piece.getTeamColor()) {
            possiblePawnMoves(myPos, pos3);
        }
    }

    public static void possiblePawnMoves(ChessPosition myPos, ChessPosition newPos) {
        PAWNMOVES.add(new ChessMove(myPos, newPos, ChessPiece.PieceType.QUEEN));
        PAWNMOVES.add(new ChessMove(myPos, newPos, ChessPiece.PieceType.KNIGHT));
        PAWNMOVES.add(new ChessMove(myPos, newPos, ChessPiece.PieceType.BISHOP));
        PAWNMOVES.add(new ChessMove(myPos, newPos, ChessPiece.PieceType.ROOK));
    }
}