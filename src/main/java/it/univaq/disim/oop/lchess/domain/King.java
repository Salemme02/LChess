package it.univaq.disim.oop.lchess.domain;

public class King extends Piece {

    public static int VALUE = 0;

    public King(PieceColor color) {
        this.setColor(color);
    }

    public String toString() {
        return ((getColor().equals(PieceColor.WHITE) ? "WK" : "BK"));
    }

    @Override
    public int getValue() {
        return VALUE;
    }
}
