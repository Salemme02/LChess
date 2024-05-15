package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.Board;
import it.univaq.disim.oop.lchess.domain.Move;
import it.univaq.disim.oop.lchess.domain.Piece;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class KnightServiceImpl implements SpecificPieceService {

    @Override
    public List<Move> calculateLegalMoves(Board board, Pair<Integer, Integer> from, Piece piece) {

        List<Move> legalMoveslist = new ArrayList<>();

        //L lunga avanti sinistra
        if (isValidMove(from, Pair.of(from.getLeft() + 2, from.getRight() - 1), board)) {
            legalMoveslist.add(new Move(from, Pair.of(from.getLeft() + 2, from.getRight() - 1), piece));
        }
        //L lunga avanti destra
        if (isValidMove(from, Pair.of(from.getLeft() + 2, from.getRight() + 1), board)) {
            legalMoveslist.add(new Move(from, Pair.of(from.getLeft() + 2, from.getRight() + 1), piece));
        }
        //L corta avanti sinistra
        if (isValidMove(from, Pair.of(from.getLeft() + 1, from.getRight() - 2), board)) {
            legalMoveslist.add(new Move(from, Pair.of(from.getLeft() + 1, from.getRight() - 2), piece));
        }
        //L corta avanti destra
        if (isValidMove(from, Pair.of(from.getLeft() + 1, from.getRight() + 2), board)) {
            legalMoveslist.add(new Move(from, Pair.of(from.getLeft() + 1, from.getRight() + 2), piece));
        }
        //L lunga indietro sinistra
        if (isValidMove(from, Pair.of(from.getLeft() - 2, from.getRight() - 1), board)) {
            legalMoveslist.add(new Move(from, Pair.of(from.getLeft() - 2, from.getRight() - 1), piece));
        }
        //L lunga indietro destra
        if (isValidMove(from, Pair.of(from.getLeft() - 2, from.getRight() + 1), board)) {
            legalMoveslist.add(new Move(from, Pair.of(from.getLeft() - 2, from.getRight() + 1), piece));
        }
        //L corta indietro sinistra
        if (isValidMove(from, Pair.of(from.getLeft() - 1, from.getRight() - 2), board)) {
            legalMoveslist.add(new Move(from, Pair.of(from.getLeft() - 1, from.getRight() - 2), piece));
        }
        //L corta indietro destra
        if (isValidMove(from, Pair.of(from.getLeft() - 1, from.getRight() + 2), board)) {
            legalMoveslist.add(new Move(from, Pair.of(from.getLeft() - 1, from.getRight() + 2), piece));
        }
        return legalMoveslist;
    }

    @Override
    public boolean isValidMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board) {

        int fromRow = from.getLeft();
        int fromColumn = from.getRight();
        int toRow = to.getLeft();
        int toColumn = to.getRight();

        int rowDifference = Math.abs(toRow - fromRow);
        int colDifference = Math.abs(toColumn - fromColumn);

        return toRow >= 1 && toRow <= 8 && toColumn >= 1 && toColumn <= 8 && isValidCaptureMove(from, to, board);
    }

    @Override
    public boolean isValidCaptureMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board) {
        Piece takenPiece = board.getChessBoard().get(to.getLeft(), to.getRight());
        return (takenPiece == null) || board.getChessBoard().get(to.getLeft(), to.getRight()).getColor() != board.getChessBoard().get(from.getLeft(), from.getRight()).getColor();

    }
}
