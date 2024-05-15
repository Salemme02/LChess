package it.univaq.disim.oop.lchess.domain;

import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;

public class Move implements Serializable {

    private Pair<Integer,Integer> from;
    private Pair<Integer,Integer> to;
    private Piece piece;
    private Piece takenPiece;

    public Move(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Piece piece, Piece takenPiece) {
        this.from = from;
        this.to = to;
        this.piece = piece;
        this.takenPiece = takenPiece;
    }

    public Move(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Piece piece) {
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    public void setTakenPiece(Piece takenPiece) {
        this.takenPiece = takenPiece;
    }

    public Piece getTakenPiece() {
        return takenPiece;
    }

    public Pair<Integer,Integer> getFrom() {
        return from;
    }

    public void setFrom(Pair<Integer, Integer> from) {
        this.from = from;
    }

    public Pair<Integer, Integer> getTo() {
        return to;
    }

    public void setTo(Pair<Integer, Integer> to) {
        this.to = to;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Move)) return false;
        Move move = (Move) obj;
        return this.from.equals(move.from) && this.to.equals(move.to) && this.piece.equals(move.piece);
    }

    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                ", piece=" + piece +
                ", takenPiece=" + takenPiece +
                '}';
    }
}
