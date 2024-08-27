package org.Controller.Parsing;

import org.Exception.ParsingException;
import org.Models.FormulaUnoElements.FormulaUnoGameMap;
import org.Models.GameElements.Coordinate2D;
import org.Models.GameElements.Section2D;
import org.Models.GameElements.Segment2D;

public class MapSyntacticParser {

    private int playerNumber;

    public MapSyntacticParser(int playerNum){
        this.playerNumber = playerNum;
    }

    public void parseMap(FormulaUnoGameMap map) throws ParsingException{
        if(map.getSectionsCount() < 3)
            throw new IllegalArgumentException("Pista troppo corta");
        if(map.getSection(0).getAreaSection() <= this.playerNumber * 3)
            throw new IllegalArgumentException("Sezione iniziale troppo piccola per il numero di giocatori");
        analyzeSegment(map.getSection(0).getSegmentStart(), 0);
        for(int i = 0; i < map.getSectionsCount(); i++){
            analyzeSection(map.getSection(i));
        }
    }

    private void analyzeSection(Section2D section) throws ParsingException{
        analyzeSegment(section.getSegmentEnd(), section.getId() + 1);
        doSectionOverlap(section);
    }


    private void analyzeSegment(Segment2D segment, int line) throws ParsingException{
        if(segment.getCoordinateLeft().getDistance(segment.getCoordinateRight()) < 2)
            throw new ParsingException(line);
    }
    
    private void doSectionOverlap(Section2D section) throws ParsingException{
        Coordinate2D p1 = section.getSegmentStart().getCoordinateLeft(), 
                     q1 = section.getSegmentEnd().getCoordinateLeft(),
                     p2 = section.getSegmentStart().getCoordinateRight(), 
                     q2 = section.getSegmentEnd().getCoordinateRight();
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4) {
            throw new ParsingException(section.getId() + 1);
        }
    }

    public int orientation(Coordinate2D p, Coordinate2D q, Coordinate2D r) {
        double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());
        if (val == 0.0) return 0;
        return (val > 0) ? 1 : 2;
    }
    
}
