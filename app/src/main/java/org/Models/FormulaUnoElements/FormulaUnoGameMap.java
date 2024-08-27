package org.Models.FormulaUnoElements;

import java.util.ArrayList;
import java.util.List;

import org.Models.GameElements.*;

public class FormulaUnoGameMap {

    private List<Section2D> sections;
    private int count;
    private boolean editable;
    private Segment2D start;
    private Segment2D last;
    private double dimension;

    public FormulaUnoGameMap(){
        sections = new ArrayList<>();
        count = 0;
        editable = true;
        dimension = 0;
    }

    public int getSectionsCount(){
        return count;
    }

    public boolean isCircle(){
        return count > 0 && last.equals(start);
    }

    public void addCheck(Coordinate2D left, Coordinate2D right){
        if(!editable)
            return;
        Segment2D segment = new Segment2D(left,right);
        if(start == null){
            start = segment;
        }else{
            Section2D section = new Section2D(count++, last, segment);
            sections.add(section);
            dimension += section.getDistance();
        }
        last = segment;
    }

    public void setNotEditable(){
        if(this.editable){
            this.editable = false;
            if(isCircle()){
                sections.add(sections.get(0).clone(count++));
            }else{
                sections.add(sections.get(count - 1).getNext(count++));
            }
        }
    }

    public double getDimension(){
        return dimension;
    }

    public Section2D getSection(int index){
        return sections.get(index).clone(index);
    }

    public int getSectionFromCoordinate(int last, Coordinate2D c){
        List<Integer> list = new ArrayList<>();
        if(last > -1)
            list.add(last);
        if(last + 1 < count)
            list.add(last + 1);
        if(last > 0)
            list.add(last - 1);
        list.add(0);
        for(int i : list){
            if(getSection(i).isIntoSection(c)){
                return i;
            }
        }
        return -1;
    }

    public boolean checkIfOutOfRoad(int last, Coordinate2D from, Vector2D speed){
        while(!speed.equals(new Vector2D())){
            int x = speed.getX() > 0 ? 1 : speed.getX() < 0 ? -1 : 0;
            int y = speed.getY() > 0 ? 1 : speed.getY() < 0 ? -1 : 0;
            Vector2D s = new Vector2D(x, y);
            from = s.sum(from);
            if(getSectionFromCoordinate(last, from) == -1)
                return true;
            speed = s.reverse().sum(speed);
        }
        return false;
    }

    public double getPercentFromCoordinate(int last, Coordinate2D c){
        int index = getSectionFromCoordinate(last, c);
        if(index < 0) return 0;
        if(index == count - 1) return 100;
        double count = 0;
        for(int i = 0; i < index; i++){
            count += getSection(i).getDistance();
        }
        count += getSection(index).getSegmentStart()
                                  .getCoordinateMiddle()
                                  .getDistance(c);
        return (count * 100) / getDimension();
    }

}
