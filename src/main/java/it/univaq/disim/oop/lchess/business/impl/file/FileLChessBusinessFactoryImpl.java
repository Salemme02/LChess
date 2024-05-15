package it.univaq.disim.oop.lchess.business.impl.file;

import it.univaq.disim.oop.lchess.business.*;

import java.io.File;

public class FileLChessBusinessFactoryImpl extends LChessBusinessFactory {

    private final HumanPlayerService humanPlayerService;

    private final CpuPlayerService cpuPlayerService;

    private final GameService gameService;

    private final BoardService boardService;

    private final PieceService bishopService;

    private final PieceService kingService;

    private final PieceService knightService;

    private final PieceService pawnService;

    private final PieceService queenService;

    private final PieceService rookService;
    
    private final PieceService pieceService;

    private static final String REPOSITORY_BASE = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "data";

    private static final String PLAYER_FILE_NAME = REPOSITORY_BASE + File.separator + "players.json";

    private static final String GAME_FILE_DIRECTORY = REPOSITORY_BASE + File.separator + "games" + File.separator;

    private static final String GAME_COUNTER_FILE = REPOSITORY_BASE + File.separator + "game_counter.txt";

    public FileLChessBusinessFactoryImpl() {
        bishopService = new BishopServiceImpl();
        kingService = new KingServiceImpl();
        knightService = new KnightServiceImpl();
        pawnService = new PawnServiceImpl();
        queenService = new QueenServiceImpl();
        rookService = new RookServiceImpl();
        pieceService = new PieceServiceImpl(bishopService, kingService, knightService, pawnService, queenService, rookService);
        boardService = new BoardServiceImpl(pieceService);
        gameService = new FileGameServiceImpl(GAME_COUNTER_FILE, GAME_FILE_DIRECTORY, boardService);
        humanPlayerService = new FileHumanPlayerServiceImpl(PLAYER_FILE_NAME, pieceService);
        cpuPlayerService = new CpuPlayerServiceImpl(pieceService, boardService);
    }

    @Override
    public HumanPlayerService getHumanPlayerService() {
        return humanPlayerService;
    }

    public CpuPlayerService getCpuPlayerService() { return cpuPlayerService; }

    @Override
    public GameService getGameService() {
        return gameService;
    }

    @Override
    public BoardService getBoardService() { return boardService; }

    @Override
    public PieceService getBishopService() {
        return bishopService;
    }

    @Override
    public PieceService getKnightService() {
        return knightService;
    }

    @Override
    public PieceService getPawnService() {
        return pawnService;
    }

    @Override
    public PieceService getQueenService() {
        return queenService;
    }

    @Override
    public PieceService getRookService() {
        return rookService;
    }

    @Override
    public PieceService getKingService() {
        return kingService;
    }

    @Override
    public PieceService getPieceService() {return pieceService;}
}
