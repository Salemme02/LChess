package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.Board;
import org.apache.commons.lang3.tuple.Pair;

// Interfaccia per i metodi implementati in modo specifico dalle singole pedine, che verranno chiamati da PieceServiceImpl
public interface SpecificPieceService extends PieceService{

// Gestisce i controlli relativi alle regole di scacchi della singola pedina che viene mossa
    public boolean isValidMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board);

// Controlla se la casella di destinazione è occupata da una pedina avversaria
    public boolean isValidCaptureMove(Pair<Integer, Integer> from, Pair<Integer, Integer> to, Board board);
}
