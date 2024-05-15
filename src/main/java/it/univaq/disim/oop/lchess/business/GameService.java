package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.domain.Board;
import it.univaq.disim.oop.lchess.domain.Game;
import it.univaq.disim.oop.lchess.domain.Player;

import java.util.List;

public interface GameService {

    public Game createGame (Player player1, Player player2) throws BusinessException;

    public void saveGame(Game game) throws BusinessException;

    public Game loadGame(String fileName) throws BusinessException;

    public List<Game> showSavedGames(Player player) throws BusinessException;

    public void switchCurrentPlayer(Game game);

    public String getRelativeFileName(Game game);

    public Game goBack(Game game, int numberOfMoves);

    public boolean isDrawn(Game game);

}
