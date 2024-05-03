package chess;

public class moveCalculator {
    public moveCalculator() {

    }

    public moveCalculator(ChessPiece.PieceType type) {

    }

    public string[] queenMoves() {
//        can move any direction but cannot pass over pieces of same color
        return null;
    }
    public string[] kingMoves() {
//        can only move 1 space in any direction as long as no spots have pieces on the same team
        return null;
    }
    public string[] bishopMoves() {
//        moves diagonally
        return null;
    }
    public string[] knightMoves() {
//        moves in "L" shape or 2 spaces in one direction and 1 space in another. CAN "jump" over pieces
        return null;
    }
    public string[] rookMoves() {
//        moves in a line
        return null;
    }
    public string[] pawnMoves() {
//        moves 1 space forward, except on first turn can move 2 spaces forward or when "killing" a piece, can move
//        diagonally. Once a pawn hits the opposite end of the board, must upgrade to a different piece
        return null;
    }

}