package chess;

import java.util.Collection;
import java.util.ArrayList;

public class PawnMovesCalculator {
    private static final Collection<ChessMove> pawnMoves = new ArrayList<>();

    public static Collection<ChessMove> validPawnMoves(ChessBoard board, ChessPosition myPos) {
        pawnMoves.clear();
        ChessPiece piece = board.getPiece(myPos);
        int x = myPos.getRow();
        int y = myPos.getColumn();
        int[][] possWhiteMoves = {{1, 0}, {1, 1}, {1, -1}};
        int[][] possBlackMoves = {{-1, 0}, {-1, 1}, {-1, -1}};

        //White Pawn
        if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (myPos.getRow() == 2) {
                for (int[] move : possWhiteMoves) {
                    ChessPosition newPos = new ChessPosition(move[0] + x, move[1] + y);
                    if (newPos.getRow() >= 1 && newPos.getRow() <= 8 && newPos.getColumn() >= 1 && newPos.getColumn() <= 8) {
                        if (move[1] == 0) {
                            if (board.getPiece(newPos) == null) {
                                ChessMove newMove = new ChessMove(myPos, newPos, null);
                                pawnMoves.add(newMove);

                                newPos = new ChessPosition(x + 2, y);
                                if (board.getPiece(newPos) == null) {
                                    newMove = new ChessMove(myPos, newPos, null);
                                    pawnMoves.add(newMove);
                                }
                            }
                        } else {
                            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) {
                                ChessMove newMove = new ChessMove(myPos, newPos, null);
                                pawnMoves.add(newMove);
                            }
                        }
                    }
                }
            } else if (myPos.getRow() <= 7) {
                ChessPosition pos1 = new ChessPosition(x+1, y);
                ChessPosition pos2 = new ChessPosition(x+1, y+1);
                ChessPosition pos3 = new ChessPosition(x+1, y-1);
                if (myPos.getColumn() > 1 && myPos.getColumn() < 8) {
                    if (myPos.getRow() < 7) {
                        getPawnMoves(board, myPos, pos1, pos2, pos3);
                    } else if (myPos.getRow() == 7) {
                        getPawnPromo(board, myPos, pos1, pos2, pos3);
                    }
                } else if (myPos.getColumn() == 1) {
                    if (myPos.getRow() < 7) {
                        getPawnMoves(board, myPos, pos1, pos2, pos1);
                    } else if (myPos.getRow() == 7) {
                        getPawnPromo(board, myPos, pos1, pos2, pos1);
                    }
                } else if (myPos.getColumn() == 8) {
                    if (myPos.getRow() < 7) {
                        getPawnMoves(board, myPos, pos1, pos1, pos3);
                    } else if (myPos.getRow() == 7) {
                        getPawnPromo(board, myPos, pos1, pos1, pos3);
                    }
                }
            }
        }

        //Black Pawn
        if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (myPos.getRow() == 7) {
                for (int[] move: possBlackMoves) {
                    ChessPosition newPos = new ChessPosition(move[0]+x, move[1]+y);
                    if (newPos.getRow() >= 1 && newPos.getRow() <= 8 && newPos.getColumn() >= 1 && newPos.getColumn() <= 8) {
                        if (move[1] == 0) {
                            if (board.getPiece(newPos) == null) {
                                ChessMove newMove = new ChessMove(myPos, newPos, null);
                                pawnMoves.add(newMove);

                                newPos = new ChessPosition(x - 2, y);
                                if (board.getPiece(newPos) == null) {
                                    newMove = new ChessMove(myPos, newPos, null);
                                    pawnMoves.add(newMove);
                                }
                            }
                        } else {
                            if (board.getPiece(newPos) != null && board.getPiece(newPos).getTeamColor() != piece.getTeamColor()) {
                                ChessMove newMove = new ChessMove(myPos, newPos, null);
                                pawnMoves.add(newMove);
                            }
                        }
                    }
                }
            } else if (myPos.getRow() >= 2) {
                ChessPosition pos1 = new ChessPosition(x-1, y);
                ChessPosition pos2 = new ChessPosition(x-1, y+1);
                ChessPosition pos3 = new ChessPosition(x-1, y-1);
                if (myPos.getColumn() > 1 && myPos.getColumn() < 8) {
                    if (myPos.getRow() > 2) {
                        getPawnMoves(board, myPos, pos1, pos2, pos3);
                    } else if (myPos.getRow() == 2) {
                        getPawnPromo(board, myPos, pos1, pos2, pos3);
                    }
                } else if (myPos.getColumn() == 1) {
                    if (myPos.getRow() > 2) {
                        getPawnMoves(board, myPos, pos1, pos2, pos1);
                    } else if (myPos.getRow() == 2) {
                        getPawnPromo(board, myPos, pos1, pos2, pos1);
                    }
                } else if (myPos.getColumn() == 8) {
                    if (myPos.getRow() > 2) {
                        getPawnMoves(board, myPos, pos1, pos1, pos3);
                    } else if (myPos.getRow() == 2) {
                        getPawnPromo(board, myPos, pos1, pos1, pos3);
                    }
                }
            }
        }

        return pawnMoves;
    }

    public static void getPawnMoves(ChessBoard board, ChessPosition myPos, ChessPosition pos1, ChessPosition pos2, ChessPosition pos3) {
        ChessPiece piece = board.getPiece(myPos);
        if (board.getPiece(pos1) == null) {
            ChessMove move = new ChessMove(myPos, pos1, null);
            pawnMoves.add(move);
        }
        if (board.getPiece(pos2) != null && board.getPiece(pos2).getTeamColor() != piece.getTeamColor()) {
            ChessMove move = new ChessMove(myPos, pos2, null);
            pawnMoves.add(move);
        }
        if (board.getPiece(pos3) != null && board.getPiece(pos3).getTeamColor() != piece.getTeamColor()) {
            ChessMove move = new ChessMove(myPos, pos3, null);
            pawnMoves.add(move);
        }
    }

    public static void getPawnPromo(ChessBoard board, ChessPosition myPos, ChessPosition pos1, ChessPosition pos2, ChessPosition pos3) {
        ChessPiece piece = board.getPiece(myPos);
        if (board.getPiece(pos1) == null) {
            possiblePawnMoves(board, myPos, pos1);
        }
        if (board.getPiece(pos2) != null && board.getPiece(pos2).getTeamColor() != piece.getTeamColor()) {
            possiblePawnMoves(board, myPos, pos2);
        }
        if (board.getPiece(pos3) != null && board.getPiece(pos3).getTeamColor() != piece.getTeamColor()) {
            possiblePawnMoves(board, myPos, pos3);
        }
    }

    public static void possiblePawnMoves(ChessBoard board, ChessPosition myPos, ChessPosition newPos) {
        ChessMove move = new ChessMove(myPos, newPos, ChessPiece.PieceType.QUEEN);
        pawnMoves.add(move);
        move = new ChessMove(myPos, newPos, ChessPiece.PieceType.KNIGHT);
        pawnMoves.add(move);
        move = new ChessMove(myPos, newPos, ChessPiece.PieceType.BISHOP);
        pawnMoves.add(move);
        move = new ChessMove(myPos, newPos, ChessPiece.PieceType.ROOK);
        pawnMoves.add(move);
    }
}
