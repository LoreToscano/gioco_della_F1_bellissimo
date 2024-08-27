package org.Controller.IngameLogic;

import java.util.Comparator;
import java.util.List;

import org.Models.FormulaUnoElements.FormulaUnoGameMap;
import org.Models.FormulaUnoElements.FormulaUnoMove;
import org.Models.FormulaUnoElements.FormulaUnoPlayer;
import org.Models.FormulaUnoElements.PlayerType;
import org.Models.GameElements.Coordinate2D;
import org.Models.GameElements.Section2D;
import org.Controller.IngameLogic.DifficultMover.*;

public class FormulaUnoBotMoveCalculator {

    private FormulaUnoGameMap gameMap;
    private FormulaUnoDifficultMoveCalculator difficultCalculator;

    public FormulaUnoBotMoveCalculator(FormulaUnoGameMap gameMap) {
        this.gameMap = gameMap;
        this.difficultCalculator = new FormulaUnoMediumMoveCalculator(gameMap);
    }

    public void setDifficult(PlayerType playerType) {
        if(playerType == PlayerType.EASY)
            this.difficultCalculator = new FormulaUnoEasyMoveCalculator(gameMap);
        else if(playerType == PlayerType.MEDIUM)
            this.difficultCalculator = new FormulaUnoMediumMoveCalculator(gameMap);
        else if(playerType == PlayerType.HARD)
            this.difficultCalculator = new FormulaUnoHardMoveCalculator(gameMap);
        else if(playerType == PlayerType.CHAMPION)
            this.difficultCalculator = new FormulaUnoChampionMoveCalculator(gameMap);
        else throw new UnsupportedOperationException("Tipo di giocatore non supportato");
    }

    public FormulaUnoMove findNextMove(FormulaUnoPlayer player, List<FormulaUnoMove> moves) {
        if(moves.size() == 0)
            return null;
        if(moves.stream().anyMatch(m -> m.isWinTheGame()))
            return moves.stream().filter(m -> m.isWinTheGame()).findFirst().get();
        if(moves.stream().anyMatch(m -> !m.isOutOfRoad()))
            moves = moves.stream().filter(m -> !m.isOutOfRoad()).toList();
        Section2D section = gameMap.getSection(player.getSection());
        Coordinate2D nextTarget;
        Coordinate2D target = section.getSegmentEnd().getCoordinateMiddle();
        if(player.getSection() + 1 < gameMap.getSectionsCount())
            nextTarget = this.gameMap.getSection(player.getSection() + 1).getSegmentEnd().getCoordinateMiddle();
        else nextTarget = target.getCoordinate();
        Comparator<FormulaUnoMove> comparator = Comparator.comparingDouble(m -> {
            double dis = m.getStartPosition().getDistance(target);
            if(dis <= 3) dis = m.getEndPosition().getDistance(nextTarget);
            else dis = m.getEndPosition().getDistance(target);
            return dis;
        });
        moves = moves.stream().sorted(comparator).toList();
        return this.difficultCalculator.findNextMove(player, moves);
    }

    
    
}
