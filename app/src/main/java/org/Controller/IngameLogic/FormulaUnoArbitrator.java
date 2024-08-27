package org.Controller.IngameLogic;

import java.util.List;
import java.util.stream.IntStream;

import org.Models.FormulaUnoElements.FormulaUnoGameMap;
import org.Models.FormulaUnoElements.FormulaUnoGameSettings;
import org.Models.FormulaUnoElements.FormulaUnoMove;
import org.Models.FormulaUnoElements.FormulaUnoPlayer;
import org.Models.GameElements.Coordinate2D;
import org.Models.GameElements.Round;
import org.Models.GameElements.Vector2D;

public class FormulaUnoArbitrator {

    private FormulaUnoGameMap gameMap;
    private Coordinate2D[] players;
    private Round round;
    private boolean veiclesCanOverlap, dontEliminateIfExit;
    private Vector2D[] OneMoveVectors;
    private final int lapsToDo;
    private int winnerPositionCount;

    public FormulaUnoArbitrator(FormulaUnoGameMap gameMap, Coordinate2D[] players, FormulaUnoGameSettings settings, Round round) {
        this.gameMap = gameMap;
        this.players = players;
        this.round = round;
        this.veiclesCanOverlap = settings.isVeiclesCanOverlap();
        this.dontEliminateIfExit = settings.isDontEliminateIfExit();
        this.OneMoveVectors = new Vector2D[9];
        int count = 0;
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                this.OneMoveVectors[count++] = new Vector2D(i, j);
            }
        }
        this.lapsToDo = settings.getLaps();
        this.winnerPositionCount = 1;
    }

    public List<FormulaUnoMove> getAcceptableMoves(FormulaUnoPlayer player){
        Vector2D speed = player.getSpeed();
        boolean isFirst = player.doFirstMove();
        return IntStream.range(0, 9).boxed()
                        .map(i -> {
                            Vector2D s = speed.sum(this.OneMoveVectors[i]);
                            FormulaUnoMove move = new FormulaUnoMove();
                            move.setStartPosition(player.getCoordinate());
                            move.setEndPosition(s.sum(player.getCoordinate()));
                            move.setLastSpeed(speed);
                            move.setChosedMove(this.OneMoveVectors[i]);
                            move.setNewSpeed(s);
                            move.setLastSection(player.getSection());
                            move.setNewSection(this.gameMap.getSectionFromCoordinate(player.getSection(), move.getEndPosition()));
                            move.setOutOfRoad(this.gameMap.checkIfOutOfRoad(player.getSection(), move.getStartPosition(), move.getNewSpeed()));
                            move.setWinTheGame(move.getNewSection() == gameMap.getSectionsCount() -1);
                            return move;
                        }).filter(m -> veiclesCanOverlap || freePosition(m.getEndPosition()))
                        .filter(m -> !(isFirst && m.getChosedMove().equals(new Vector2D())))
                        .toList();
    }

    private boolean freePosition(Coordinate2D coordinate){
        for(Coordinate2D c : players){
            if(c.getCoordinate().equals(coordinate))
                return false;
        }
        return true;
    }



    public void applyMove(FormulaUnoPlayer player, FormulaUnoMove move) {
        if(move.isWinTheGame()){
            if(player.getLaps() + 1 < lapsToDo){
                move.setNewSection(0);
                player.setLaps(player.getLaps() + 1);
            } else {
                player.setEndRoad(true);
                round.deleteRound(player.getPlayerId());
                player.setWinnerPosition(winnerPositionCount++);
                player.setWinnerTime(this.round.getTime());
            }
        }
        player.setNotIntoRoad(move.isOutOfRoad());
        if(move.isOutOfRoad()){
            if(!dontEliminateIfExit){
                round.deleteRound(player.getPlayerId());
                player.setWinnerTime(this.round.getTime());
            }
        }
        player.applyNewSpeed(move.getChosedMove());
        player.setSection(move.getNewSection());
        round.nextRound();
    }
    
}
