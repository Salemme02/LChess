package it.univaq.disim.oop.lchess.domain;

public class Pawn extends Piece{
	private static final int VALUE = 1;

	public Pawn(PieceColor color) {
		this.setColor(color);
	}

	public String toString() {
		return ((getColor().equals(PieceColor.WHITE) ? "WP" : "BP"));
	}

	@Override
	public int getValue() {
		return VALUE;
	}
}
