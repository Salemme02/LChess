package it.univaq.disim.oop.lchess.business;

import com.google.common.collect.Table;
import it.univaq.disim.oop.lchess.domain.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CpuPlayerServiceImpl implements CpuPlayerService {

    private final PieceService pieceService;
    private final BoardService boardService;

    public CpuPlayerServiceImpl(PieceService pieceService, BoardService boardService) {
        this.pieceService = pieceService;
        this.boardService = boardService;
    }

    // Metodo per effettuare la mossa del giocatore CPU
    @Override
    public Move makeMove(Board board, PieceColor currentPlayerColor) {

        // Ottiene la lista delle posizioni dei pezzi CPU sulla scacchiera.
        List<Pair<Integer, Integer>> cpuPiecesPositionsList;
        cpuPiecesPositionsList = boardService.getAllPositions(board, currentPlayerColor);

        // Seleziona casualmente una posizione tra le possibili.
        Piece p;
        Pair<Integer, Integer> position = cpuPiecesPositionsList.get(new Random().nextInt(cpuPiecesPositionsList.size()));
        p = board.getChessBoard().get(position.getLeft(), position.getRight());

        // Calcola le mosse legali per il pezzo selezionato.
        List<Move> movesList = pieceService.calculateLegalMoves(board, board.getPiecePosition(p), p);

        // Se non ci sono mosse legali, restituisce null.
        if (movesList.isEmpty()) return null;

        return movesList.get(new Random().nextInt(movesList.size()));
    }
}
