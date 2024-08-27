package org.Controller;

import java.util.List;
import java.util.stream.IntStream;

import org.Models.DTO.TwoDimensionGameMapDTO;
import org.Controller.IngameLogic.FormulaUnoArbitrator;
import org.Controller.IngameLogic.FormulaUnoBotMoveCalculator;
import org.Controller.IngameLogic.FormulaUnoStartingPositioner;
import org.Models.DTO.FormulaUnoGameInfoDTO;
import org.Models.DTO.FormulaUnoPlayerInfoDTO;
import org.Models.FormulaUnoElements.FormulaUnoCommand;
import org.Models.FormulaUnoElements.FormulaUnoGameMap;
import org.Models.FormulaUnoElements.FormulaUnoGameSettings;
import org.Models.FormulaUnoElements.FormulaUnoGameState;
import org.Models.FormulaUnoElements.FormulaUnoMove;
import org.Models.FormulaUnoElements.FormulaUnoPlayer;
import org.Models.FormulaUnoElements.PlayerType;
import org.Models.GameElements.Coordinate2D;
import org.Models.GameElements.GameSettings;
import org.Models.GameElements.Round;
import org.Models.GameElements.Section2D;
import org.apache.commons.lang3.NotImplementedException;

public class FormulaUnoGameHandler {

    private FormulaUnoGameMap map;
    private FormulaUnoGameSettings settings;
    private FormulaUnoPlayer[] players;
    private Round round;
    private FormulaUnoArbitrator arbitrator;
    private FormulaUnoBotMoveCalculator botMover;
    private FormulaUnoStartingPositioner startingPositioner;
    
    public FormulaUnoGameHandler(FormulaUnoGameMap gameMap, GameSettings settings){
        this.map = gameMap;
        this.settings = new FormulaUnoGameSettings(settings, !gameMap.isCircle());
        this.round = new Round(this.settings.getNumberPlayer(), this.settings.isRandomOrderChange());
        this.players = new FormulaUnoPlayer[this.settings.getNumberPlayer()];
        this.arbitrator = new FormulaUnoArbitrator(gameMap, players, this.settings, this.round);
        this.botMover = new FormulaUnoBotMoveCalculator(gameMap);
        for(int i = 0; i < players.length; i++) {
            if(this.settings.getTypes()[i] == PlayerType.REAL)
                throw new NotImplementedException("I giocatori reali non sono stati implementati");
            players[i] = new FormulaUnoPlayer(0, 0, i, this.settings.getNames()[i], this.settings.getTypes()[i]);
        }
        this.startingPositioner = new FormulaUnoStartingPositioner(gameMap, this.settings);
        SetStartingPositions();
    }

    public void SetStartingPositions(){
        Coordinate2D[] startPositions = this.startingPositioner.getPositions();
        for(int i = 0; i < players.length; i++){
            players[i].setCoordiante(startPositions[i]);
        }
    }

    public TwoDimensionGameMapDTO getMapInfo(){
        List<Section2D> list = IntStream.range(0, map.getSectionsCount())
                                      .boxed().map(x -> this.map.getSection(x)).toList();
        return new TwoDimensionGameMapDTO(list, map.isCircle());
    }

    public FormulaUnoGameInfoDTO getPlayersInfo() {
        FormulaUnoPlayerInfoDTO[] array = new FormulaUnoPlayerInfoDTO[players.length];
        for(int i = 0; i < array.length; i++) {
            FormulaUnoGameState state = players[i].isNotIntoRoad() ? 
            (this.settings.isDontEliminateIfExit() ? FormulaUnoGameState.OUTROAD : FormulaUnoGameState.ELIMINATED) : 
            players[i].isEndRoad() ? FormulaUnoGameState.FINISHED : FormulaUnoGameState.ONROAD;
            array[i] = new FormulaUnoPlayerInfoDTO(players[i], state, 
                                                   this.map.getPercentFromCoordinate(players[i].getSection(), players[i].getCoordinate()));
        }
        FormulaUnoGameInfoDTO info = new FormulaUnoGameInfoDTO(array, this.round.getRound());
        return info;
    }

    public boolean executeMove(FormulaUnoCommand command){
        throw new NotImplementedException("I giocatori reali non sono stati implementati");
    }

    public boolean executeMove(){
        FormulaUnoPlayer player = this.players[this.round.getRound()];
        if(player.getPlayerType() == PlayerType.REAL)
            throw new IllegalArgumentException("Bisogna specificare un comando per un giocatore reale");
        this.botMover.setDifficult(player.getPlayerType());
        List<FormulaUnoMove> moves = this.arbitrator.getAcceptableMoves(player);
        FormulaUnoMove move = this.botMover.findNextMove(player, moves);
        if(move == null)
            return false;
        this.arbitrator.applyMove(player, move);
        return true;
    }

}
