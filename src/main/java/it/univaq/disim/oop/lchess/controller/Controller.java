package it.univaq.disim.oop.lchess.controller;

import it.univaq.disim.oop.lchess.business.*;
import it.univaq.disim.oop.lchess.domain.*;
import org.apache.commons.lang3.tuple.Pair;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Controller {

    private final HumanPlayerService humanPlayerService;
    private final CpuPlayerService cpuPlayerService;
    private final GameService gameService;
    private final BoardService boardService;

    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal terminal = textIO.getTextTerminal();


    public Controller() {
        LChessBusinessFactory factory = LChessBusinessFactory.getInstance();
        humanPlayerService = factory.getHumanPlayerService();
        cpuPlayerService = factory.getCpuPlayerService();
        gameService = factory.getGameService();
        boardService = factory.getBoardService();
    }

    public void start() {
        try {
            while (true) {
                terminal.println("\nBENVENUTO IN LCHESS !!!\n");
                String user;
                do {
                    user = textIO.newStringInputReader().read("Username");
                    if (!(user.equals("BOT") || user.contains("_"))) break;
                    terminal.println("Inserisci un nome diverso da BOT o che non contenga il simbolo \"_\" ");
                } while (true);
                HumanPlayer humanPlayer = humanPlayerService.authenticate(user);
                loggedIn(humanPlayer);
            }
        } catch(BusinessException b) {
            b.printStackTrace();
        }
    }

// Gestisce il flusso del gioco per un utente autenticato
        public void loggedIn (HumanPlayer player){
            try {
                loop:
                while (true) {
// Scelta tra nuova partita, partita iniziata o abbandono

                    terminal.println("\nDesideri: \n" +
                            " (1) Giocare una nuova partita\n" +
                            " (2) Recuperare una partita gia' iniziata\n" +
                            " (3) Abbandonare");
                    int typeOfGameChoice = textIO.newIntInputReader().withMinVal(1).withMaxVal(3).read("Inserisci il numero corrispondente alla tua scelta");
                    switch (typeOfGameChoice) {
                        case 1:

                            terminal.println("\nDesideri: \n" +
                                    " (1) Giocare contro un'altro giocatore\n" +
                                    " (2) Giocare contro un bot\n" +
                                    " (3) Tornare indietro");
                            int opponentChoice = textIO.newIntInputReader().withMinVal(1).withMaxVal(3).read("Inserisci il numero corrispondente alla tua scelta");
                            Game game;
                            switch (opponentChoice) {
                                case 1:
                                    terminal.println();
                                    String opponentUser = textIO.newStringInputReader().read("Con chi vuoi giocare?");
                                    HumanPlayer player2 = humanPlayerService.authenticate(opponentUser);
                                    game = gameService.createGame(player, player2);
                                    play(game);
                                    break;
                                case 2:
                                    game = gameService.createGame(player, new CpuPlayer());
                                    play(game);
                                    break;
                                case 3:
                                    continue loop;
                            }
                            break;
                        case 2:
                            savedGameSelection(player);
                            break;
                        case 3:
                            break loop;
                    }
                }

            } catch (BusinessException b) {
                b.printStackTrace();
            }
        }

// Gestisce la scelta e selezione di una partita salvata
        public void savedGameSelection (HumanPlayer player){
            try {

                List<Game> games = gameService.showSavedGames(player);
                terminal.println();

                if (displayGames(games, null)) {
                    terminal.println("\nQueste sono le tue partite salvate.");

                    loop:
                    while (true) {
// Ordinamento delle partite in base a vari criteri

                        terminal.println("\nPreferisci ordinarle in base ad un criterio? Scegli se vuoi \n" +
                                " (1) Ordinarle in base al numero di mosse effettuate nella partita (ordine crescente); \n" +
                                " (2) Ordinarle in base al numero complessivo di pezzi sulla scacchiera (ordine crescente); \n" +
                                " (3) Ordinarle in base al valore complessivo dei pezzi sulla scacchiera (ordine decrescente); \n" +
                                " (4) Continuare con la scelta della partita salvata.\n");

                        int orderChoice = textIO.newIntInputReader().withMinVal(1).withMaxVal(4).read("Inserisci il numero corrispondente alla tua scelta");

                        switch (orderChoice) {
                            case 1:
                                terminal.println();
                                displayGames(games, Comparator.comparing((Game game) -> Integer.valueOf(game.getMoves().size())));
                                break;
                            case 2:
                                terminal.println();
                                displayGames(games, Comparator.comparing((Game game) -> Integer.valueOf(game.getPiecesNumber())));
                                break;
                            case 3:
                                terminal.println();
                                displayGames(games, Comparator.comparing((Game game) -> Integer.valueOf(game.getPiecesValue())));
                                break;
                            case 4:
                                break loop;
                        }
                    }

                    int gameOrOrderChoice = textIO.newIntInputReader().withMinVal(1).withMaxVal(games.size()).read("Scegli la " +
                            "partita che vuoi recuperare. Inserisci il numero corrispondente alla tua scelta");

                    play(games.get(gameOrOrderChoice - 1));
                }

            } catch (BusinessException b) {
                b.printStackTrace();
            }
        }

// Questo metodo mostra la lista delle partite all'utente, ordinandole se un comparatore è fornito.
// Restituisce true se ci sono partite da mostrare.
        public boolean displayGames (List < Game > games, Comparator < Game > comparator){

            if (comparator != null) {
                Collections.sort(games, comparator);
            }
            if (games.isEmpty()) {
                terminal.println("Non sono presenti partite salvate");
                return false;
            }
            int index = 1;
            for (Game game : games) {
                terminal.println("(" + index++ + ") " + gameService.getRelativeFileName(game));
            }
            return true;
        }

        public void play (Game game){
            terminal.println();
            while (!game.isFinished()) {
                displayBoard(game.getBoard());
                String colore = game.getCurrentPlayerColor() == PieceColor.BLACK ? "nere" : "bianche";
                terminal.println("E' il turno di " + game.getCurrentPlayer().getName() + " (pedine " + colore + ") \n");

                Move move;
                if (game.getCurrentPlayer() instanceof CpuPlayer) {
// Se il giocatore attuale è una CPU, fa una mossa automatica.
                    do {
                        move = cpuPlayerService.makeMove(game.getBoard(), game.getCurrentPlayerColor());
                    } while (move == null || ((move = boardService.move(game.getBoard(), move)) == null));
                } else {
// Chiede all'utente di inserire una mossa.
                    boolean isValid = false;
                    do {
                        terminal.println("Inserisci la mossa che vuoi fare, scegliendo prima la posizione di partenza e " +
                                "poi quella di arrivo (esempio E3), \naltrimenti \"options\" per aprire le opzioni\n");
                        String fromChoice = textIO.newStringInputReader().withPattern("^[a-hA-H]{1}[1-8]{1}?|options|OPTIONS").read("Casella di partenza: ");
                        fromChoice = fromChoice.toUpperCase();
                        if (fromChoice.equalsIgnoreCase("options")) {
                            userOptions(game);
                            return;
                        } else {

                            String toChoice = textIO.newStringInputReader().withPattern("^[a-hA-H]{1}[1-8]{1}?").read("Casella di arrivo: ");
                            terminal.println();
                            toChoice = toChoice.toUpperCase();
                            Pair<Integer, Integer> from = Pair.of(((Integer.valueOf(fromChoice.substring(1, 2)))), ((fromChoice.charAt(0) - 64)));
                            Pair<Integer, Integer> to = Pair.of(((Integer.valueOf(toChoice.substring(1, 2)))), ((toChoice.charAt(0) - 64)));
                            if ((move = humanPlayerService.makeMove(game.getBoard(), game.getCurrentPlayerColor(), from, to)) == null) {
                                terminal.println("Mossa non valida, scegli una mossa valida per le tue pedine");
                                continue;
                            }
                            if ((move = boardService.move(game.getBoard(), move)) == null) {
                                terminal.println("Attenzione! Con questa mossa il tuo Re e' sotto scacco\n");
                                continue;
                            }
                            isValid = true;
                        }
                    }
                    while (!isValid);
                }

                game.addMove(move);
                PieceColor opponentColor = game.getCurrentPlayerColor() == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;

                if (boardService.isCheckMate(game.getBoard(), opponentColor)) {
                    game.setFinished(true);
                    displayBoard(game.getBoard());
                    terminal.println();
                    terminal.println("Partita finita: " + game.getCurrentPlayer().getName() + " ha vinto!");
                    continue;
                }

// Controlla se la partita è finita in pareggio.
                if (gameService.isDrawn(game)) {
                    game.setFinished(true);
                    displayBoard(game.getBoard());
                    terminal.println();
                    terminal.println("Partita finita in pareggio per la regola delle 50 mosse! ");
                    continue;
                }
                gameService.switchCurrentPlayer(game);
            }
            try {
                gameService.saveGame(game);
            } catch (BusinessException b) {
                b.printStackTrace();
            }
        }

        public void userOptions (Game game){
            try {
                terminal.println();
                terminal.println("Desideri:\n" +
                        " (1) Salvare la partita;\n" +
                        " (2) Abbandonare la partita;\n" +
                        " (3) Arrenderti;\n" +
                        " (4) Tornare indietro di qualche mossa;\n" +
                        " (5) Scegliere la prossima mossa.\n");
                int optionsChoice = textIO.newIntInputReader().withMinVal(1).withMaxVal(5).read("Inserisci il numero corrispondente alla tua scelta");

                switch (optionsChoice) {
                    case 1: //SALVARE
                        gameService.saveGame(game);
                        play(game);
                        break;
                    case 2: // ABBANDONARE
                        gameService.saveGame(game);
                        break;
                    case 3: // ARRENDITI
                        game.setFinished(true);
                        terminal.println("\nPartita finita: " + game.getCurrentPlayer().getName() + " si e' arreso!");
                        gameService.saveGame(game);
                        break;
                    case 4: // TORNA INDIETRO DI QUALCHE MOSSA
                        terminal.println();
                        int numberOfMoves = textIO.newIntInputReader().withMinVal(1).withMaxVal(Integer.min(5, game.getMoves().size())).read("Di quante mosse vuoi tornare indietro? ");
                        Game modifiedGame = gameService.goBack(game, numberOfMoves);  //game viene modificato
                        play(modifiedGame);
                        break;
                    case 5: // TORNA INDIETRO A FARE LA MOSSA
                        play(game);
                        break;
                }
            } catch (BusinessException b) {
                b.printStackTrace();
            }
        }
// Stampa la scacchiera sul terminale
        public void displayBoard (Board board){
            terminal.printf("          -------------------------------------------%n");
            for (int i = 8; i >= 1; i--) {
                terminal.print("          " + String.valueOf(i) + " ");
                for (int j = 1; j <= 8; j++) {
                    try {
                        terminal.printf("| %-3s", board.getChessBoard().get(i, j).toString());
                    } catch (NullPointerException e) {
                        terminal.printf("|%-4s", " ");
                    }
                }
                terminal.printf("|%n");
                terminal.printf("          -------------------------------------------%n");
            }
            terminal.printf("            | %-2s | %-2s | %-2s | %-2s | %-2s | %-2s | %-2s | %-2s |%n", "A", "B", "C", "D", "E", "F", "G", "H");

            terminal.println();
        }

    }

