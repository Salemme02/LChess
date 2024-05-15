package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.Board;
import it.univaq.disim.oop.lchess.domain.Move;
import it.univaq.disim.oop.lchess.domain.Piece;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface PieceService {

    public List<Move> calculateLegalMoves(Board board, Pair<Integer, Integer> from, Piece piece);


}
