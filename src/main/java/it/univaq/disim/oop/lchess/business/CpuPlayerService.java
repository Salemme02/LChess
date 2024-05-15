package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.Board;
import it.univaq.disim.oop.lchess.domain.Move;
import it.univaq.disim.oop.lchess.domain.PieceColor;

public interface CpuPlayerService {

    public Move makeMove(Board board, PieceColor currentPlayerColor);
}
