package it.univaq.disim.oop.lchess.domain;

import java.io.Serializable;

public abstract class Piece implements Serializable {

    private PieceColor color;

    public PieceColor getColor() {
        return color;
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }

    public abstract int getValue();
}

