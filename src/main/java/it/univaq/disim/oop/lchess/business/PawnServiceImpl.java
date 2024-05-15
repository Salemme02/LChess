package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class PawnServiceImpl implements SpecificPieceService {

    @Override
    public List<Move> calculateLegalMoves(Board board, Pair<Integer, Integer> from, Piece piece) {
        int direction = (piece.getColor() == PieceColor.WHITE) ? 1 : -1;
        List<Move> possibleMoves = new ArrayList<>();

        // Mossa normale avanti
        if (isValidMove(from, Pair.of(from.getLeft() + direction, from.getRight()), board)) {
            possibleMoves.add(new Move(from, Pair.of(from.getLeft() + direction, from.getRight()), piece));
        }

        // Mossa doppia avanti
        if ((from.getLeft() == (piece.getColor() == PieceColor.WHITE ? 2 : 7) &&
                !(board.getChessBoard().contains(from.getLeft() + direction, from.getRight())))) {
            if (isValidMove(from, Pair.of(from.getLeft() + 2 * direction, from.getRight()), board)) {
                possibleMoves.add(new Move(from, Pair.of(from.getLeft() + 2 * direction, from.getRight()), piece));
            }
        }

        // Cattura pedina avanti destra
        if (isValidCaptureMove(from, Pair.of(from.getLeft() + direction, from.getRight() + 1), board)) {
            possibleMoves.add(new Move(from, Pair.of(from.getLeft() + direction, from.getRight() + 1), piece));
        }

        // Cattura pedina avanti sinistra
        if (isValidCaptureMove(from, Pair.of(from.getLeft() + direction, from.getRight() - 1), board)) {
            possibleMoves.add(new Move(from, Pair.of(from.getLeft() + direction, from.getRight() - 1), piece));
        }
        return possibleMoves;
    }

    @Override
    public boolean isValidMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board) {
        Integer destinationRow = to.getLeft();
        Integer destinationColumn = to.getRight();

        if (destinationRow < 1 || destinationRow > 8 || destinationColumn < 1 || destinationColumn > 8) {
            return false; // La mossa è fuori dai limiti della scacchiera
        }

        return !(board.getChessBoard().contains(destinationRow, destinationColumn));
    }

    @Override
    public boolean isValidCaptureMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board) {
        Integer currentRow = from.getLeft();
        Integer currentColumn = from.getRight();
        Integer destinationRow = to.getLeft();
        Integer destinationColumn = to.getRight();

        if (destinationRow < 1 || destinationRow > 8 || destinationColumn < 1 || destinationColumn > 8) {
            return false; // La mossa è fuori dai limiti della scacchiera
        }

        return board.getChessBoard().contains(destinationRow, destinationColumn) &&
                board.getChessBoard().get(destinationRow, destinationColumn).getColor() != board.getChessBoard().get(currentRow, currentColumn).getColor();
    }

}