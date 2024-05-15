package it.univaq.disim.oop.lchess.domain;

public class Queen extends Piece {
    private static final int VALUE = 9;

    public Queen(PieceColor color) {
        this.setColor(color);
    }

    public String toString() {
        return ((getColor().equals(PieceColor.WHITE) ? "WQ" : "BQ"));
    }

    @Override
    public int getValue() {
        return VALUE;
    }
}
