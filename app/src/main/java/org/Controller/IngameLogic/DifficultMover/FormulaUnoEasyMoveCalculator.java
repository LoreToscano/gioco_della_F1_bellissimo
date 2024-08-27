package org.Controller.IngameLogic.DifficultMover;

import java.util.List;

import org.Models.FormulaUnoElements.FormulaUnoGameMap;
import org.Models.FormulaUnoElements.FormulaUnoMove;
import org.Models.FormulaUnoElements.FormulaUnoPlayer;
import org.Models.GameElements.Vector2D;

public class FormulaUnoEasyMoveCalculator extends FormulaUnoDifficultMoveCalculator{

    public FormulaUnoEasyMoveCalculator(FormulaUnoGameMap gameMap) {
        super(gameMap);
    }

    @Override
    public FormulaUnoMove findNextMove(FormulaUnoPlayer player, List<FormulaUnoMove> moves) {
        List<FormulaUnoMove> limitedMoves = moves.stream().filter(m ->{
            Vector2D speed = m.getNewSpeed();
            int x = Math.abs(speed.getX());
            int y = Math.abs(speed.getY());
            return x < 2 && y < 2;
        }).toList();
        if(!limitedMoves.isEmpty())
            return limitedMoves.get(0);
        int min = moves.stream().map(m ->{
            Vector2D speed = m.getNewSpeed();
            int x = Math.abs(speed.getX());
            int y = Math.abs(speed.getY());
            return x + y;
        }).reduce(10, (a,b) -> a < b ? a : b);
        return moves.stream().filter(m ->{
            Vector2D speed = m.getNewSpeed();
            int x = Math.abs(speed.getX());
            int y = Math.abs(speed.getY());
            return x + y == min;
        }).findFirst().get();
    }
    
}
