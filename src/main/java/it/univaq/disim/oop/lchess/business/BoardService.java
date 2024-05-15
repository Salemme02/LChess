package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.Board;
import it.univaq.disim.oop.lchess.domain.Move;
import it.univaq.disim.oop.lchess.domain.Piece;
import it.univaq.disim.oop.lchess.domain.PieceColor;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface BoardService {

    public Board initializeBoard();

    public Move move(Board board, Move move);

    public List<Pair<Integer, Integer>> getAllPositions(Board board, PieceColor color);

    public Pair<Integer, Integer> getKingPosition(Board board, PieceColor pieceColor);

    public boolean isKingInCheck(Board board, PieceColor kingColor);

    public boolean isCheckMate(Board board, PieceColor color);

    public void undoMove(Board board, Move move);


//    private Piece putPiece(Integer row, Integer column, PieceColor pieceColor);

}
