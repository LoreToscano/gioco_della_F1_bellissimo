package org.Controller.Parsing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.Models.FormulaUnoElements.FormulaUnoGameMap;
import org.Models.GameElements.Coordinate2D;

public class MapLoader {

    public FormulaUnoGameMap LoadGameMap(List<Coordinate2D[]> map){
        FormulaUnoGameMap gameMap = new FormulaUnoGameMap();
        map.forEach(x -> gameMap.addCheck(x[0], x[1]));
        gameMap.setNotEditable();
        return gameMap;
    }

    public FormulaUnoGameMap LoadGameMap(String path) throws Exception {
        FormulaUnoGameMap gameMap = new FormulaUnoGameMap();
        Files.readAllLines(Paths.get(path))
             .forEach(x -> {
                String[] coors = x.split(" ");
                Coordinate2D left = getCoordinateFromString(coors[0]);
                Coordinate2D right = getCoordinateFromString(coors[1]);
                gameMap.addCheck(left, right);
             });
        gameMap.setNotEditable();
        return gameMap;
    }

    private Coordinate2D getCoordinateFromString(String coor){
        String[] nums = coor.split("\\.");
        return new Coordinate2D(Integer.parseInt(nums[0]), Integer.parseInt(nums[1]));
    }
    
}
