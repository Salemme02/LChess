package it.univaq.disim.oop.lchess.business.impl.file;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import it.univaq.disim.oop.lchess.business.BoardService;
import it.univaq.disim.oop.lchess.business.BusinessException;
import it.univaq.disim.oop.lchess.business.GameService;
import it.univaq.disim.oop.lchess.domain.*;

import java.io.*;
import java.util.*;

public class FileGameServiceImpl implements GameService {

    private final String gameCounterFile;
    private final String gamesFilesDirectory;
    private final BoardService boardService;

    public FileGameServiceImpl(String gameCounterFile, String gamesFilesDirectory, BoardService boardService) {
        this.gameCounterFile = gameCounterFile;
        this.gamesFilesDirectory = gamesFilesDirectory;
        this.boardService = boardService;
    }

    public int getGameCounter() throws BusinessException {
        int gameCounter;
        try {
            Path filePath = Paths.get(gameCounterFile);

            if (Files.exists(filePath)) {
                String content = new String(Files.readAllBytes(filePath));
                if (!content.isEmpty()) {
                    gameCounter = Integer.parseInt(content);
                } else {
                    gameCounter = 0;
                }
            } else {
                gameCounter = 0;
            }

            gameCounter++;

            // Sovrascrivi il valore nel file
            Files.write(filePath, Integer.toString(gameCounter).getBytes());

        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("Non e' stato possibile accedere al game counter");
        }
        return gameCounter;
    }

    @Override
    public Game createGame(Player player1, Player player2) throws BusinessException {
        Game game;
        if (new Random().nextBoolean()) {
            game = new Game(getGameCounter(), false, player1, player2, PieceColor.WHITE, PieceColor.BLACK, player1, boardService.initializeBoard(), new Stack<Move>());
        } else {
            game = new Game(getGameCounter(), false, player1, player2, PieceColor.BLACK, PieceColor.WHITE, player2, boardService.initializeBoard(), new Stack<Move>());
        }
        File file = new File(getAbsoluteFileName(game));
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("Non e' stato possibile creare il file");
        }
        saveGame(game);
        return game;
    }

    // Restituisce il nome del file relativo ad una partita
    @Override
    public String getRelativeFileName(Game game) {
        return (game.getPlayer1() + "_" + game.getPlayer2() + "_" + game.getGameID());
    }

    // Restituisce il percorso completo del file di una partita
    public String getAbsoluteFileName(Game game) {
        return (gamesFilesDirectory + getRelativeFileName(game) + ".txt");
    }


    // Salva una partita in un file
    @Override
    public void saveGame(Game game) throws BusinessException {
        String fileName = getAbsoluteFileName(game);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {

            outputStream.writeObject(game);

        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("Non e' stato possibile salvare la partita");
        }
    }

    // Carica e restituisce una lista di partite salvate per un giocatore.
    @Override
    public List<Game> showSavedGames(Player player) throws BusinessException {

        List<Game> games = new LinkedList<>();
        File directory = new File(gamesFilesDirectory);
        File[] gamesFiles = directory.listFiles((gamesFilesDirectory, fileName) -> {

            StringBuilder player1 = new StringBuilder(), player2 = new StringBuilder();

            int i = 0;
            for (; fileName.charAt(i) != '_'; i++)
                player1.append(fileName.charAt(i));
            i++;
            for (; fileName.charAt(i) != '_'; i++)
                player2.append(fileName.charAt(i));

            return player.getName().contentEquals(player1) || player.getName().contentEquals(player2);});

        try {
            if (gamesFiles != null) {

                for (File gameFile : gamesFiles) {
                    Game game = loadGame(gameFile.getName());
                    if (!game.isFinished()) {
                        games.add(game);
                    }
                }
            }
            return games;
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new BusinessException("Non e' stato possibile leggere le partite");
        }
    }

    // Carica una partita da un file
    @Override
    public Game loadGame(String fileName) throws BusinessException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(gamesFilesDirectory + fileName))) {

            return (Game) inputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new BusinessException("Non e' stato possibile caricare la partita");
        }
    }

    @Override
    public void switchCurrentPlayer(Game game) {
        if (game.getCurrentPlayer() == game.getPlayer1()) game.setCurrentPlayer(game.getPlayer2());
        else game.setCurrentPlayer(game.getPlayer1());
    }

    // Torna indietro nel tempo della partita, annullando le mosse precedenti fatte da entrambi i giocatori
    @Override
    public Game goBack(Game game, int numberOfMoves) {

        for (; numberOfMoves >= 1; numberOfMoves--) {
            Move moveToBeRemoved = game.getMoves().pop();
            boardService.undoMove(game.getBoard(), moveToBeRemoved);
            switchCurrentPlayer(game);
        }
        return game;
    }

    @Override
    public boolean isDrawn(Game game) {
        Move lastMove = game.getMoves().peek();

        if (lastMove.getPiece() instanceof Pawn || lastMove.getTakenPiece() != null) game.setDrawCounter(0);
        else game.setDrawCounter(game.getDrawCounter()+1);
        return game.getDrawCounter() == 50;
    }


}

