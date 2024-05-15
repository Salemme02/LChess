package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.Board;
import it.univaq.disim.oop.lchess.domain.Move;
import it.univaq.disim.oop.lchess.domain.Piece;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class PieceServiceImpl implements PieceService {

    private PieceService bishopService;
    private PieceService kingService;
    private PieceService knightService;
    private PieceService queenService;
    private PieceService pawnService;
    private PieceService rookService;

    public PieceServiceImpl(PieceService bishopService, PieceService kingService, PieceService knightService,
                            PieceService pawnService, PieceService queenService, PieceService rookService) {
        this.bishopService = bishopService;
        this.kingService = kingService;
        this.knightService = knightService;
        this.pawnService = pawnService;
        this.queenService = queenService;
        this.rookService = rookService;
    }

// Chiama il metodo, della corretta classe della pedina, che calcola tutte le possibili mosse per quella pedina
    @Override
    public List<Move> calculateLegalMoves(Board board, Pair<Integer, Integer> from, Piece piece) {
        return selectPieceService(piece).calculateLegalMoves(board, from, piece);
    }

// Ritorna il corrispondente oggetto Service, dato un oggetto Piece
    private PieceService selectPieceService(Piece piece) {

        switch (piece.getClass().getSimpleName()) {

            case "Bishop":
                return bishopService;
            case "King":
                return kingService;
            case "Knight":
                return knightService;
            case "Pawn":
                return pawnService;
            case "Rook":
                return rookService;
            case "Queen":
                return queenService;

        }
        return null;
    }

}
