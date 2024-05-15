package it.univaq.disim.oop.lchess.domain;

public class Knight extends Piece {
    private static final int VALUE = 3;

    public Knight(PieceColor color) {
        this.setColor(color);
    }

    public String toString() {
        return ((getColor().equals(PieceColor.WHITE) ? "WN" : "BN"));
    }

    @Override
    public int getValue() {
        return VALUE;
    }

}
