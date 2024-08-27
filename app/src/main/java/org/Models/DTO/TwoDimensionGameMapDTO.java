package org.Models.DTO;

import java.util.List;

import org.Models.GameElements.Section2D;

public class TwoDimensionGameMapDTO {

    private Section2D[] sections;
    private boolean isCircle;
    private int minX, maxX, minY, maxY;
    
    public TwoDimensionGameMapDTO(List<Section2D> list, boolean circle){
        this.isCircle = circle;
        this.sections = new Section2D[list.size() - 1];
        this.minX = list.get(0).getMinX();
        this.maxX = list.get(0).getMaxX();
        this.minY = list.get(0).getMinY();
        this.maxY = list.get(0).getMaxY();
        
        for(int i = 0; i < sections.length; i++){
            this.sections[i] = list.get(i);
            if(list.get(i).getMinX() < this.minX){
                this.minX = list.get(i).getMinX();
            }
            if(list.get(i).getMaxX() > this.maxX){
                this.maxX = list.get(i).getMaxX();
            }
            if(list.get(i).getMinY() < this.minY){
                this.minX = list.get(i).getMinY();
            }
            if(list.get(i).getMaxY() > this.maxY){
                this.maxY = list.get(i).getMaxY();
            }
        }
    }

    public Section2D[] getSections() {
        Section2D[] copy = new Section2D[sections.length];
        for(int i = 0; i < sections.length; i++){
            copy[i] = this.sections[i];
        }
        return sections;
    }

    public boolean isCircle() {
        return isCircle;
    }

    public int getSectionsCount() {
        return sections.length;
    }

    public int getMinX() {
        return minX;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    

}
