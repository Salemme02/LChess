package it.univaq.disim.oop.lchess.domain;

public class Bishop extends Piece {
    private static final int VALUE = 3;

    public Bishop(PieceColor color) {
        this.setColor(color);
    }

    @Override
    public String toString() {
        return ((getColor().equals(PieceColor.WHITE) ? "WB" : "BB"));
    }

    @Override
    public int getValue() {
        return VALUE;
    }
}
