package it.univaq.disim.oop.lchess.business.impl.file;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.lchess.business.BusinessException;
import it.univaq.disim.oop.lchess.business.HumanPlayerService;
import it.univaq.disim.oop.lchess.business.PieceService;
import it.univaq.disim.oop.lchess.domain.*;
import org.apache.commons.lang3.tuple.Pair;

public class FileHumanPlayerServiceImpl implements HumanPlayerService {
    private final String playersFile;
    private final PieceService pieceService;

    public FileHumanPlayerServiceImpl(String playersFile, PieceService pieceService) {
        this.playersFile = playersFile;
        this.pieceService = pieceService;
    }

    public HumanPlayer createPlayer(String name) throws BusinessException {
        try {
            Gson gson = new Gson();
            List<HumanPlayer> players = new ArrayList<>();

            // Leggi i dati esistenti dal file JSON, se presenti
            try (BufferedReader reader = new BufferedReader(new FileReader(playersFile))) {
                Type listType = new TypeToken<List<HumanPlayer>>() {
                }.getType();
                players = gson.fromJson(reader, listType);
            } catch (FileNotFoundException ignored) {
            }

            if (players == null) {
                players = new ArrayList<>();
            }

            // Crea il nuovo giocatore
            HumanPlayer newPlayer = new HumanPlayer(name);
            players.add(newPlayer);

            // Scrivi tutti i dati nel file JSON
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(playersFile))) {
                gson.toJson(players, writer);
                writer.flush(); // Assicurati che i dati vengano scritti immediatamente
            } catch (FileNotFoundException ignored) {
            }


            return newPlayer;

        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("Errore durante la creazione del giocatore.");
        }
    }

// Gestisce l'input fornito dall'utente in fase di "login", creando un nuovo profilo se necessario
    public HumanPlayer authenticate(String name) throws BusinessException {
        try {
            Gson gson = new Gson();

            // Leggi i dati esistenti dal file JSON
            List<HumanPlayer> players = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(playersFile))) {
                Type listType = new TypeToken<List<HumanPlayer>>() {
                }.getType();
                players = gson.fromJson(reader, listType);
            } catch (FileNotFoundException ignored) {
                // Il file non esiste ancora
                throw new BusinessException("il file non esiste");
            }
            if (players != null) {

                // Cerca il giocatore con il nome specificato
                for (HumanPlayer player : players) {
                    if (player.getName().equals(name)) {
                        return player; // Restituisci il giocatore trovato
                    }
                }

                // Se il giocatore non è stato trovato, creiamo un giocatore
            }
            return createPlayer(name);

        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("Errore durante l'autenticazione del giocatore.");
        }
    }

    //Gestisce i controlli sulla mossa scelta dal giocatore umano
    public Move makeMove(Board board, PieceColor currentPlayerColor, Pair<Integer, Integer> from, Pair<Integer, Integer> to) {

        Piece p = board.getChessBoard().get(from.getLeft(), from.getRight());
        Move toReturn;
        List<Move> movesList;
        if (p != null && p.getColor().equals(currentPlayerColor)) {
            toReturn = new Move(from, to, p);
            movesList = pieceService.calculateLegalMoves(board, from, p);
            for (Move m : movesList) {
                if (m.equals(toReturn)) return toReturn;
            }
        }
        return null;
    }


}