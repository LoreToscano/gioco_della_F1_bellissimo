package org.Controller.IngameLogic.DifficultMover;

import java.util.List;

import org.Models.FormulaUnoElements.FormulaUnoGameMap;
import org.Models.FormulaUnoElements.FormulaUnoMove;
import org.Models.FormulaUnoElements.FormulaUnoPlayer;
import org.Models.GameElements.Coordinate2D;
import org.Models.GameElements.Vector2D;

public class FormulaUnoMediumMoveCalculator extends FormulaUnoEasyMoveCalculator {

    public FormulaUnoMediumMoveCalculator(FormulaUnoGameMap gameMap) {
        super(gameMap);
    }

    @Override
    public FormulaUnoMove findNextMove(FormulaUnoPlayer player, List<FormulaUnoMove> moves) {
        List<FormulaUnoMove> limitedMoves = moves.stream().filter(m ->{
            Vector2D speed = m.getNewSpeed();
            int x = Math.abs(speed.getX());
            int y = Math.abs(speed.getY());
            Coordinate2D futureCoor = speed.sum(speed.sum(speed.sum(m.getEndPosition())));
            boolean safe = gameMap.getSectionFromCoordinate(player.getSection(), futureCoor) != -1;
            return x < 3 && y < 3 && safe;
        }).toList();
        if(!limitedMoves.isEmpty())
            return limitedMoves.get(0);
        return super.findNextMove(player, moves);
    }
    
}
