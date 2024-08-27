package org.Controller.IngameLogic;

import java.util.List;

import org.Models.FormulaUnoElements.FormulaUnoGameMap;
import org.Models.FormulaUnoElements.FormulaUnoGameSettings;
import org.Models.GameElements.Coordinate2D;
import org.Models.GameElements.Section2D;

public class FormulaUnoStartingPositioner {

    private Section2D startSection, firstSection;
    private int playerNumber;
    private boolean rightStartingPositioning;

    public FormulaUnoStartingPositioner(FormulaUnoGameMap map, FormulaUnoGameSettings settings) {
        this.startSection = map.getSection(0);
        this.firstSection = map.getSection(1);
        this.playerNumber = settings.getNumberPlayer();
        this.rightStartingPositioning = settings.isRightStartingPositioning();
    }

    public Coordinate2D[] getPositions() {
        List<List<Coordinate2D>> positions = startSection.getIntoSectionPositions();
        int firstLine = getFirstLine(positions);
        if(rightStartingPositioning){
            List<Coordinate2D> line = positions.get(firstLine);
            Coordinate2D startLine = line.get(0), 
                         endLine = line.get(line.size() - 1),
                         target = firstSection.getSegmentEnd().getCoordinateMiddle();
            boolean reverse = startLine.getDistance(target) < endLine.getDistance(target);
            return getPositions(positions, reverse, firstLine);
        }
        return getPositions(positions, false, firstLine);
    }

    private Coordinate2D[] getPositions(List<List<Coordinate2D>> positions, boolean reverseOrder, int firstLine) {
        Coordinate2D[] coors = new Coordinate2D[this.playerNumber];
        int count = 0;
        for(int j = firstLine; j < positions.size(); j++){
            List<Coordinate2D> pos = positions.get(j);
            for(int i = reverseOrder ? pos.size() - 1 : 0; (reverseOrder && i >= 0) || ((!reverseOrder) && i < pos.size()); i += reverseOrder ? -1 : +1){
                coors[count++] = pos.get(i);
                if(count >= this.playerNumber)
                    return coors;
            }
        }
        return coors;
    }

    private int getFirstLine(List<List<Coordinate2D>> positions) {
        int count = playerNumber;
        for(int i = positions.size() - 1; i >= 0; i--){
            count -= positions.get(i).size();
            if(count < 1) {
                return i;
            }
        }
        throw new IllegalArgumentException("Impossibile partire da questa sezione con questi giocatori. Posti non sufficienti.");
    }

}
