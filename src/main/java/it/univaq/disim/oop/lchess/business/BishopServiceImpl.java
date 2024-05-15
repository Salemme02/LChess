package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class BishopServiceImpl implements SpecificPieceService {
    @Override
    public List<Move> calculateLegalMoves(Board board, Pair<Integer, Integer> from, Piece piece) {
        List<Move> legalMovesList = new ArrayList<>();

        for (int rowOffset = -7; rowOffset <= 7; rowOffset++) {
            for (int colOffset = -7; colOffset <= 7; colOffset++) {
                if (Math.abs(rowOffset) == Math.abs(colOffset) && rowOffset != 0) {
                    int newRow = from.getLeft() + rowOffset;
                    int newColumn = from.getRight() + colOffset;
                    Pair<Integer, Integer> to = Pair.of(newRow, newColumn);
                    if (isValidMove(from, to, board))
                        legalMovesList.add(new Move(from, to, piece));
                }
            }
        } return legalMovesList;
}

    @Override
    public boolean isValidMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board) {
        int currentRow = from.getLeft();
        int currentColumn = from.getRight();
        int destinationRow = to.getLeft();
        int destinationColumn = to.getRight();

        return  destinationRow >= 1 && destinationRow <= 8
                && destinationColumn >= 1 && destinationColumn <= 8
                && !isPathBlocked(currentRow, currentColumn, destinationRow, destinationColumn, board)
                && isValidCaptureMove(from, to, board);
    }

    @Override
    public boolean isValidCaptureMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board) {
        Piece takenPiece = board.getChessBoard().get(to.getLeft(), to.getRight());
        return (takenPiece == null) || board.getChessBoard().get(to.getLeft(), to.getRight()).getColor() != board.getChessBoard().get(from.getLeft(), from.getRight()).getColor();
    }

    private boolean isPathBlocked(int startRow, int startColumn, int endRow, int endColumn, Board board) {
        int rowDirection = (endRow > startRow) ? 1 : -1;
        int colDirection = (endColumn > startColumn) ? 1 : -1;

        int currentRow = startRow + rowDirection;
        int currentColumn = startColumn + colDirection;

        while (currentRow != endRow) {
            if (board.getChessBoard().contains(currentRow, currentColumn)) {
                return true;
            }
            currentRow += rowDirection;
            currentColumn += colDirection;
        }
        return false;
    }

}