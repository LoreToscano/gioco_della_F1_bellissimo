package org.Controller.Parsing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.Exception.ParsingException;
import org.Models.GameElements.Coordinate2D;

public class MapSemanticParser {

    public void parseMap(List<Coordinate2D[]> map){
        if(map == null)
            throw new NullPointerException("La lista di coordinate è nulla");
        for(int i = 0; i < map.size(); i++){
            if(map.get(i) == null || map.get(i).length != 2)
                throw new IllegalArgumentException("Array nullo o coordinate di numero diverso da 2 a riga " + i);
        }
    }

    public void parseMap(String path) throws Exception{
        List<String> lines = Files.readAllLines(Paths.get(path));
        for(int i = 0; i < lines.size() ;i++){
            String line = lines.get(i);
            String[] coors = line.split(" ");
            if(coors.length != 2)
                throw new ParsingException(i);
            parseCoordinates(coors, i);
        }
    }

    private void parseCoordinates(String[] coors, int line) throws ParsingException{
        for(String coor : coors){
            String[] nums = coor.split("\\.");
            if(nums.length != 2)
                throw new ParsingException(line);
            parseNums(nums, line);
        }
    }

    private void parseNums(String[] nums, int line) throws ParsingException{
        for(String num : nums){
            if(num.charAt(0) == '-')
                num = num.replace("-", "");
            for(char c : num.toCharArray()){
                if(c < '0' || c > '9')
                    throw new ParsingException(line);
            }
        }
    }
    
}
