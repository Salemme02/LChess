// Questa classe astratta rappresenta una factory per la creazione di servizi per l'intera applicazione

package it.univaq.disim.oop.lchess.business;

import it.univaq.disim.oop.lchess.business.impl.file.FileLChessBusinessFactoryImpl;

public abstract class LChessBusinessFactory {

    // Unica istanza della factory, creata utilizzando l'implementazione file
    private final static LChessBusinessFactory factory = new FileLChessBusinessFactoryImpl();

    public static LChessBusinessFactory getInstance() {
        return factory;
    }

    public abstract HumanPlayerService getHumanPlayerService();

    public abstract CpuPlayerService getCpuPlayerService();

    public abstract GameService getGameService();

    public abstract BoardService getBoardService();

    public abstract PieceService getBishopService();

    public abstract PieceService getKingService();

    public abstract PieceService getKnightService();

    public abstract PieceService getPawnService();

    public abstract PieceService getQueenService();

    public abstract PieceService getRookService();

    public abstract PieceService getPieceService();
}
