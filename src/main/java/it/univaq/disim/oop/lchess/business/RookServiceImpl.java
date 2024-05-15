package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.Board;
import it.univaq.disim.oop.lchess.domain.Move;
import it.univaq.disim.oop.lchess.domain.Piece;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class RookServiceImpl implements SpecificPieceService {

    @Override
    public List<Move> calculateLegalMoves(Board board, Pair<Integer, Integer> from, Piece piece) {


        List<Move> legalMovesList = new ArrayList<>();

        Integer startRow = from.getLeft();
        Integer startColumn = from.getRight();

        int[][] directions = { {1, 0}, {-1, 0}, {0, 1}, {0, -1} };

        for (int[] dir : directions) {
            for (int i = 1; i <= 7; i++) {
                int endRow = startRow + dir[0] * i;
                int endColumn = startColumn + dir[1] * i;

                if (isValidMove(from, Pair.of(endRow, endColumn), board)) {
                    legalMovesList.add(new Move(from, Pair.of(endRow, endColumn), piece));
                }

            }
        }

        return legalMovesList;
    }

    @Override
    public boolean isValidMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board) {
        int currentRow = from.getLeft();
        int currentColumn = from.getRight();
        int destinationRow = to.getLeft();
        int destinationColumn = to.getRight();

        return destinationRow >= 1 && destinationRow <= 8 && destinationColumn >= 1
                && destinationColumn <= 8 && !isPathBlocked(currentRow, currentColumn, destinationRow, destinationColumn, board)
                && isValidCaptureMove(from, to, board);
    }

    @Override
    public boolean isValidCaptureMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board) {
        Piece takenPiece = board.getChessBoard().get(to.getLeft(), to.getRight());
        return (takenPiece == null) || board.getChessBoard().get(to.getLeft(), to.getRight()).getColor() != board.getChessBoard().get(from.getLeft(), from.getRight()).getColor();

    }

    private boolean isPathBlocked(int startRow, int startColumn, int endRow, int endColumn, Board board) {
        int rowDirection = Integer.compare(endRow, startRow);
        int colDirection = Integer.compare(endColumn, startColumn);

        int currentRow = startRow + rowDirection;
        int currentColumn = startColumn + colDirection;

        while (currentRow != endRow || currentColumn != endColumn) {
            if (board.getChessBoard().contains(currentRow, currentColumn)) {
                return true;
            }
            currentRow += rowDirection;
            currentColumn += colDirection;
        }

        return false;
    }
}