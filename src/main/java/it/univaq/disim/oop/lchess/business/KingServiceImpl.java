package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.Board;
import it.univaq.disim.oop.lchess.domain.Move;
import it.univaq.disim.oop.lchess.domain.Piece;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class KingServiceImpl implements SpecificPieceService {


    @Override
    public List<Move> calculateLegalMoves(Board board, Pair<Integer, Integer> from, Piece piece) {
        List<Move> legalMovesList = new ArrayList<>();

        int startRow = from.getLeft();
        int startColumn = from.getRight();

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            int endRow = startRow + dir[0];
            int endColumn = startColumn + dir[1];

            if (isValidMove(from, Pair.of(endRow, endColumn), board)) {
                legalMovesList.add(new Move(from, Pair.of(endRow, endColumn), piece));
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

        return destinationRow >= 1 && destinationRow <= 8
                && destinationColumn >= 1 && destinationColumn <= 8
                && isValidCaptureMove(from, to, board);
    }

    @Override
    public boolean isValidCaptureMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board) {
        Piece takenPiece = board.getChessBoard().get(to.getLeft(), to.getRight());
        return (takenPiece == null) || board.getChessBoard().get(to.getLeft(), to.getRight()).getColor() != board.getChessBoard().get(from.getLeft(), from.getRight()).getColor();
    }
}


