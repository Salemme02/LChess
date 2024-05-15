package it.univaq.disim.oop.lchess.domain;

import com.google.gson.Gson;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.common.graph.ImmutableNetwork;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.CheckForNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Board implements Serializable {
    private Table<Integer, Integer, Piece> board;

    public Board(Table<Integer, Integer, Piece> board) {
        this.board = board;
    }

    public Board(Board board) {
        Table<Integer, Integer, Piece> copyChessBoard = TreeBasedTable.create();
        for (int row = 1; row <= 8; row++) {
            for (int column = 1; column <= 8; column++) {
                if (board.getChessBoard().get(row, column) != null) {
                    copyChessBoard.put(row, column, board.getChessBoard().get(row, column));
                }
            }
        }
        this.board = copyChessBoard;
    }

    public Table<Integer, Integer, Piece> getChessBoard() {
        return board;
    }

    public void setBoard(Table<Integer, Integer, Piece> board) {
        this.board = board;
    }

    public void addPiece(Pair<Integer, Integer> position, Piece piece) {
        board.put(position.getLeft(), position.getRight(), piece);
    }

    public Piece removePiece(Pair<Integer, Integer> position) {
        return board.remove(position.getLeft(), position.getRight());
    }

    public Pair<Integer, Integer> getPiecePosition(Piece piece) {
        Pair<Integer, Integer> position;
        for (Table.Cell<Integer, Integer, Piece> pieceCell : this.board.cellSet()) {
            if (piece == pieceCell.getValue()) {
                return Pair.of(pieceCell.getRowKey(), pieceCell.getColumnKey());
            }
        }
        return null;
    }
    
    public  int getPiecesNumber() {
        int counter = 0; 
        for(int row = 1; row <= 8; row++) {
            for(int column = 1; column <= 8; column++) {
                if(getChessBoard().get(row, column) != null) counter++;
            }
        }
        return counter;
    }

    public  int getPiecesValue() {
        int counter = 0;
        for(int row = 1; row <= 8; row++) {
            for(int column = 1; column <= 8; column++) {
                Piece piece = getChessBoard().get(row, column);
                if(piece != null) counter += piece.getValue();
            }
        }
        return counter;
    }
    

    @Override
    public String toString() {
        return "Board{" +
                "board=" + board +
                '}';
    }
}
