package org.Controller;

import java.nio.file.FileSystems;
import java.util.List;
import java.util.Map;

import org.Controller.Parsing.MapLoader;
import org.Controller.Parsing.MapSemanticParser;
import org.Controller.Parsing.MapSyntacticParser;
import org.Controller.Parsing.SettingsLoader;
import org.Controller.Parsing.SettingsParser;
import org.Exception.ParsingException;
import org.Models.DTO.FormulaUnoGameInfoDTO;
import org.Models.DTO.TwoDimensionGameMapDTO;
import org.Models.FormulaUnoElements.FormulaUnoCommand;
import org.Models.FormulaUnoElements.FormulaUnoGameMap;
import org.Models.FormulaUnoElements.FormulaUnoGameSettings;
import org.Models.GameElements.Coordinate2D;
import org.Models.GameElements.GameSettings;

/**
 * Classe che funge da controller principale.
 * Implementa GameController definendo i tipi con cui lavora e 
 * i metodi degli oggetti figli da chiamare
 */
public class FormulaUnoController implements GameController<TwoDimensionGameMapDTO, FormulaUnoCommand, FormulaUnoGameInfoDTO>{

    private MapLoader mapLoader;
    private MapSemanticParser mapSemParser;
    private MapSyntacticParser mapSynParser;
    private SettingsParser settingsParser;
    private SettingsLoader settingsLoader;
    private FormulaUnoGameMap gameMap;
    private GameSettings gameSettings;
    private FormulaUnoGameHandler gameHandler;

    /**
     * Costruttore del controller
     * Inizializza oggetti figli
     */
    public FormulaUnoController() {
        this.mapLoader=new MapLoader();
        this.settingsParser = new SettingsParser();
        this.settingsLoader = new SettingsLoader();
        this.mapSemParser = new MapSemanticParser();
    }

    @Override
    public void loadGameMap(String pathOfMap) throws Exception {
        pathOfMap = getAbsolutePath(pathOfMap);
        if(mapSynParser == null)
            throw new Exception("Caricare le impostazioni prima della mappa.");
        mapSemParser.parseMap(pathOfMap);
        this.gameMap = mapLoader.LoadGameMap(pathOfMap);
        mapSynParser.parseMap(gameMap);
    }

    @Override
    public void loadGameMap(List<Coordinate2D[]> map) throws ParsingException {
        if(mapSynParser == null)
            throw new IllegalArgumentException("Caricare le impostazioni prima della mappa.");
        mapSemParser.parseMap(map);
        this.gameMap = mapLoader.LoadGameMap(map);
        mapSynParser.parseMap(gameMap);
    }

    @Override
    public void applySettingsGame(String pathOfSetting) throws Exception {
        pathOfSetting = getAbsolutePath(pathOfSetting);
        settingsParser.parseSettingsFile(pathOfSetting);
        this.gameSettings = settingsLoader.LoadSettings(pathOfSetting);
        this.mapSynParser = new MapSyntacticParser(this.gameSettings.getSettingPositiveNumber(FormulaUnoGameSettings.NUMBERPLAYER_NAME, 4));
    }

    @Override
    public void applySettingsGame(Map<String,String> settings){
        this.gameSettings = new GameSettings(settings);
        this.mapSynParser = new MapSyntacticParser(this.gameSettings.getSettingPositiveNumber(FormulaUnoGameSettings.NUMBERPLAYER_NAME, 4));
    }

    /**
     * Metodo di utilità per ricavare il path assoluto da quello relativo
     * @param relativePath path relativo di partenza
     * @return path assoluto
     */
    private String getAbsolutePath(String relativePath){
        return FileSystems.getDefault().getPath(relativePath).normalize().toAbsolutePath().toString();
    }

    @Override
    public void startGame() {
        this.gameHandler = new FormulaUnoGameHandler(gameMap, gameSettings);
    }

    @Override
    public FormulaUnoGameInfoDTO getInfo() {
        return this.gameHandler.getPlayersInfo();
    }

    @Override
    public TwoDimensionGameMapDTO getGameMap() {
        return this.gameHandler.getMapInfo();
    }

    @Override
    public boolean executeMove(FormulaUnoCommand command) {
        return this.gameHandler.executeMove(command);
    }

    @Override
    public boolean executeMove() {
        return this.gameHandler.executeMove();
    }

}
