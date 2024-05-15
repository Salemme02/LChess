package it.univaq.disim.oop.lchess.domain;

import java.io.Serializable;
import java.util.*;


public class Game implements Serializable {

    private int gameID;
    private boolean isFinished;
    private Player player1;
    private Player player2;
    private final PieceColor colorP1;
    private final PieceColor colorP2;
    private Player currentPlayer;
    private Board board;
    private Stack<Move> moves;
    private int drawCounter;

    public Game(int gameID, boolean isFinished, Player player1, Player player2, PieceColor colorP1, PieceColor colorP2, Player currentPlayer, Board board, Stack<Move> moves) {
        this.gameID = gameID;
        this.isFinished = isFinished;
        this.player1 = player1;
        this.player2 = player2;
        this.colorP1 = colorP1;
        this.colorP2 = colorP2;
        this.currentPlayer = currentPlayer;
        this.board = board;
        this.moves = moves;
        this.drawCounter = 0;
    }

    public int getGameID() {
        return gameID;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public PieceColor getColorP1() {
        return colorP1;
    }

    public PieceColor getColorP2() {
        return colorP2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public PieceColor getCurrentPlayerColor() {
        return (currentPlayer == player1) ? colorP1 : colorP2;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Stack<Move> getMoves() {
        return moves;
    }

    public void setMoves(Stack<Move> moves) {
        this.moves = moves;
    }

    public void addMove(Move move) {
        this.moves.push(move);
    }

    public int getPiecesNumber() {
        return board.getPiecesNumber();
    }

    public int getPiecesValue() {
        return board.getPiecesValue();
    }

    public int getDrawCounter() {
        return drawCounter;
    }

    public void setDrawCounter(int drawCounter) {
        this.drawCounter = drawCounter;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameID=" + gameID +
                ", isFinished=" + isFinished +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", colorP1=" + colorP1 +
                ", colorP2=" + colorP2 +
                ", currentPlayer=" + currentPlayer +
                ", board=" + board +
                ", moves=" + moves +
                '}';
    }

}

