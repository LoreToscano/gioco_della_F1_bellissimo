package org.View;

import java.util.ArrayList;
import java.util.List;

import org.Models.GameElements.Coordinate2D;

public class FormulaUnoStandardGameMapBuilder {

    public List<Coordinate2D[]> getStandardMapFromId(int id){
        if(id == 0)
            return getStandardOpenMap();
        if(id == 1)
            return getStandardClosedMap();
        if(id == 2)
            return getStandardHardMap();
        return null;
    }
    
    public List<Coordinate2D[]> getStandardOpenMap(){
        List<Coordinate2D[]> list = new ArrayList<>();
        list.add(new Coordinate2D[]{new Coordinate2D(0, 0), new Coordinate2D(5, 0)});
        list.add(new Coordinate2D[]{new Coordinate2D(0, 30), new Coordinate2D(5, 25)});
        list.add(new Coordinate2D[]{new Coordinate2D(25, 30), new Coordinate2D(30, 25)});
        list.add(new Coordinate2D[]{new Coordinate2D(25, 55), new Coordinate2D(30, 60)});
        list.add(new Coordinate2D[]{new Coordinate2D(0, 55), new Coordinate2D(0, 60)});
        return list;
    }

    public List<Coordinate2D[]> getStandardClosedMap(){
        List<Coordinate2D[]> list = new ArrayList<>();
        list.add(new Coordinate2D[]{new Coordinate2D(0, 0), new Coordinate2D(5, 5)});
        list.add(new Coordinate2D[]{new Coordinate2D(0, 30), new Coordinate2D(5, 25)});
        list.add(new Coordinate2D[]{new Coordinate2D(30, 30), new Coordinate2D(25, 25)});
        list.add(new Coordinate2D[]{new Coordinate2D(30, 0), new Coordinate2D(25, 5)});
        list.add(new Coordinate2D[]{new Coordinate2D(0, 0), new Coordinate2D(5, 5)});
        return list;
    }

    public List<Coordinate2D[]> getStandardHardMap(){
        List<Coordinate2D[]> list = new ArrayList<>();
        list.add(new Coordinate2D[]{new Coordinate2D(0, 0), new Coordinate2D(5, 10)});
        list.add(new Coordinate2D[]{new Coordinate2D(0, 50), new Coordinate2D(5, 45)});
        list.add(new Coordinate2D[]{new Coordinate2D(15, 50), new Coordinate2D(10, 45)});
        list.add(new Coordinate2D[]{new Coordinate2D(15, 5), new Coordinate2D(10, 0)});
        list.add(new Coordinate2D[]{new Coordinate2D(40, 5), new Coordinate2D(45, 0)});
        list.add(new Coordinate2D[]{new Coordinate2D(40, 15), new Coordinate2D(45, 20)});
        list.add(new Coordinate2D[]{new Coordinate2D(25, 15), new Coordinate2D(30, 20)});
        list.add(new Coordinate2D[]{new Coordinate2D(25, 40), new Coordinate2D(30, 35)});
        list.add(new Coordinate2D[]{new Coordinate2D(50, 50), new Coordinate2D(45, 40)});
        list.add(new Coordinate2D[]{new Coordinate2D(70, 15), new Coordinate2D(55, 20)});
        list.add(new Coordinate2D[]{new Coordinate2D(0, 0), new Coordinate2D(5, 10)});
        return list;
    }

}
