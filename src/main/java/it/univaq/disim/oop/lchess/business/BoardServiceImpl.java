package it.univaq.disim.oop.lchess.business;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import it.univaq.disim.oop.lchess.business.BoardService;
import it.univaq.disim.oop.lchess.business.PieceService;
import it.univaq.disim.oop.lchess.domain.*;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class BoardServiceImpl implements BoardService {
    private final PieceService pieceService;

    public BoardServiceImpl(PieceService pieceService) {
        this.pieceService = pieceService;
    }


    // Inizializza la scacchiera secondo le regole ufficiali
    @Override
    public Board initializeBoard() {
        Table<Integer, Integer, Piece> table = TreeBasedTable.create(); // Crea una struttura dati per rappresentare la scacchiera usando le table Guava
        Board board;
        for (int column = 1; column <= 8; column++) {
            for (int row = 1; row <= 8; row++) {
                PieceColor pieceColor = (row <= 2) ? PieceColor.WHITE : PieceColor.BLACK;
                Piece piece = putPiece(column, row, pieceColor);
                if (piece == null) continue;
                table.put(row, column, piece);
            }
        }
        return board = new Board(table);
    }

    private Piece putPiece(Integer column, int row, PieceColor color) {
        Piece piece = null;

        if (color == PieceColor.WHITE) {
            if (row == 1) {
                switch (column) {
                    case 1:
                        piece = new Rook(color);
                        break;
                    case 2:
                        piece = new Knight(color);
                        break;
                    case 3:
                        piece = new Bishop(color);
                        break;
                    case 4:
                        piece = new Queen(color);
                        break;
                    case 5:
                        piece = new King(color);
                        break;
                    case 6:
                        piece = new Bishop(color);
                        break;
                    case 7:
                        piece = new Knight(color);
                        break;
                    case 8:
                        piece = new Rook(color);
                        break;
                }
            } else if (row == 2) {
                piece = new Pawn(color);
            }
        } else if (color == PieceColor.BLACK) {
            if (row == 8) {
                switch (column) {
                    case 1:
                        piece = new Rook(color);
                        break;
                    case 2:
                        piece = new Knight(color);
                        break;
                    case 3:
                        piece = new Bishop(color);
                        break;
                    case 4:
                        piece = new Queen(color);
                        break;
                    case 5:
                        piece = new King(color);
                        break;
                    case 6:
                        piece = new Bishop(color);
                        break;
                    case 7:
                        piece = new Knight(color);
                        break;
                    case 8:
                        piece = new Rook(color);
                        break;
                }
            } else if (row == 7) {
                piece = new Pawn(color);
            }
        }
        return piece;
    }

    // Prova ad eseguire la data mossa e poi controlla se eseguendola il proprio Re si trova sotto scacco; nel caso in cui lo sia, annulla la mossa
    @Override
    public Move move(Board board, Move move) {

        Piece takenPiece = board.getChessBoard().get(move.getTo().getLeft(), move.getTo().getRight());
        Board copyBoard = new Board(board);
        PieceColor pieceColor = move.getPiece().getColor();
        copyBoard.removePiece(move.getFrom());
        copyBoard.addPiece(move.getTo(), move.getPiece());

        if (isKingInCheck(copyBoard, pieceColor)) return null;

        if (takenPiece != null) {
            move.setTakenPiece(takenPiece);
        }
        board.removePiece(move.getFrom());
        board.addPiece(move.getTo(), move.getPiece());

        return move;
    }

    // Restituisce tutte le posizioni dei pezzi sulla scacchiera di uno specifico colore
    @Override
    public List<Pair<Integer, Integer>> getAllPositions(Board board, PieceColor color) {
        List<Pair<Integer, Integer>> piecesPositions = new ArrayList<>();
        for (int row = 1; row <= 8; row++) {
            for (int column = 1; column <= 8; column++) {
                Piece tempPiece = board.getChessBoard().get(row, column);
                if (tempPiece != null && tempPiece.getColor() == color)
                    piecesPositions.add(Pair.of(row, column));
            }
        }
        return piecesPositions;
    }


    // Restituisce la posizione del re nella scacchiera di uno specifico colore
    @Override
    public Pair<Integer, Integer> getKingPosition(Board board, PieceColor color) {
        Table<Integer, Integer, Piece> chessBoard = board.getChessBoard();
        for (int row = 1; row <= 8; row++) {
            for (int column = 1; column <= 8; column++) {
                Piece tempPiece = chessBoard.get(row, column);
                if (tempPiece instanceof King && tempPiece.getColor() == color)
                    return Pair.of(row, column);
            }
        }
        return null;
    }

    // Controlla se il re di uno specifico colore è sotto scacco, controllando se uno dei pezzi avversari ha (tra le sue mosse legali) una mossa che lo minaccia
    @Override
    public boolean isKingInCheck(Board board, PieceColor kingColor) {
        Pair<Integer, Integer> kingPosition = getKingPosition(board, kingColor);
        List<Pair<Integer, Integer>> opponentPiecesPositions = getAllPositions(board, (kingColor == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE));

        Piece tempPiece;
        List<Move> tempPieceMoves;
        for (Pair<Integer, Integer> position : opponentPiecesPositions) {
            tempPiece = board.getChessBoard().get(position.getLeft(), position.getRight());
            tempPieceMoves = pieceService.calculateLegalMoves(board, position, tempPiece);
            for (Move move : tempPieceMoves)
                if (move.getTo().equals(kingPosition)) {
                    return true;
                }
        }
        return false;
    }

    // Controlla se è scacco matto per uno specifico colore, verificando innanzitutto se è sotto scacco, e poi controllando se esiste una mossa per cui
    // il re non è più sotto scacco

    @Override
    public boolean isCheckMate(Board board, PieceColor color) {

            if (!isKingInCheck(board, color)) return false;
            List<Pair<Integer, Integer>> piecesPositions = getAllPositions(board, color);

            Piece tempPiece;
            List<Move> tempPieceMoves;
            for (Pair<Integer, Integer> position : piecesPositions) {
                tempPiece = board.getChessBoard().get(position.getLeft(), position.getRight());
                tempPieceMoves = pieceService.calculateLegalMoves(board, position, tempPiece);

                for (Move tempMove : tempPieceMoves) {
                    Board copyBoard = new Board(board); //esegue la mossa su una copia della scacchiera
                    copyBoard.removePiece(tempMove.getFrom());
                    copyBoard.addPiece(tempMove.getTo(), tempMove.getPiece());

                    if (!isKingInCheck(copyBoard, color)) return false;
                }
            }

        return true;
    }

    // Viene disfatta la mossa eseguendola ripristinando le posizioni e i potenziali pezzi mangiati
    @Override
    public void undoMove(Board board, Move move) {

        Pair<Integer, Integer> from = move.getTo();
        Pair<Integer, Integer> to = move.getFrom();
        Piece movedPiece = move.getPiece();
        Piece takenPiece = move.getTakenPiece();

        board.addPiece(to, movedPiece);
        if (takenPiece != null)
            board.addPiece(from, takenPiece);
        else {
            board.removePiece(from);
        }
    }

}
