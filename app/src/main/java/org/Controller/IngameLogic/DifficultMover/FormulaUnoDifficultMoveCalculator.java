package org.Controller.IngameLogic.DifficultMover;

import java.util.List;

import org.Models.FormulaUnoElements.FormulaUnoGameMap;
import org.Models.FormulaUnoElements.FormulaUnoMove;
import org.Models.FormulaUnoElements.FormulaUnoPlayer;

public abstract class FormulaUnoDifficultMoveCalculator {

    protected FormulaUnoGameMap gameMap;

    public FormulaUnoDifficultMoveCalculator(FormulaUnoGameMap gameMap) {
        this.gameMap = gameMap;
    }

    public abstract FormulaUnoMove findNextMove(FormulaUnoPlayer player, List<FormulaUnoMove> moves);
}
