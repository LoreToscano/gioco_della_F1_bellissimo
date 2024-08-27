package org.Models.FormulaUnoElements;

import org.Models.GameElements.Coordinate2D;
import org.apache.commons.lang3.NotImplementedException;

public class FormulaUnoCommand {

    private Coordinate2D target;

    public FormulaUnoCommand(Coordinate2D target) {
        this.target = target;
    }
    
    public Coordinate2D move(){
        throw new NotImplementedException("I giocatori reali non sono stati implementati");
    }

}
