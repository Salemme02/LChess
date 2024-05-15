package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.Board;
import it.univaq.disim.oop.lchess.domain.HumanPlayer;
import it.univaq.disim.oop.lchess.domain.Move;
import it.univaq.disim.oop.lchess.domain.PieceColor;
import org.apache.commons.lang3.tuple.Pair;

public interface HumanPlayerService {

    public HumanPlayer createPlayer(String name) throws BusinessException;

    public HumanPlayer authenticate(String name) throws BusinessException;

    public Move makeMove(Board board, PieceColor currentPlayerColor, Pair<Integer, Integer> from, Pair<Integer, Integer> to);


}
