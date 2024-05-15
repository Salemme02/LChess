package it.univaq.disim.oop.lchess.domain;

public class Rook extends Piece {
    private static final int VALUE = 5;

    public Rook(PieceColor color) {
        this.setColor(color);
    }

    public String toString() {
        return ((getColor().equals(PieceColor.WHITE) ? "WR" : "BR"));
    }

    @Override
    public int getValue() {
        return VALUE;
    }

}


