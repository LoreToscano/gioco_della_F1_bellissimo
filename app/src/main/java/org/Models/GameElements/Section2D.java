package org.Models.GameElements;

import java.util.ArrayList;
import java.util.List;

public class Section2D {
    private int id;
    private Segment2D segmentStart, segmentEnd;
    
    public Segment2D getSegmentStart() {
        return segmentStart;
    }

    public Segment2D getSegmentEnd() {
        return segmentEnd;
    }

    public int getId(){
        return id;
    }
    

    public Section2D(int id, Segment2D segmentStart, Segment2D segmentEnd) {
        this.id = id;
        this.segmentStart = segmentStart;
        this.segmentEnd = segmentEnd;
    }

    public double getDistance(){
        Coordinate2D startMiddle = segmentStart.getCoordinateMiddle();
        Coordinate2D endMiddle = segmentEnd.getCoordinateMiddle();
        return startMiddle.getDistance(endMiddle);
    }

    public double getAreaSection(){
        return getArea(segmentStart.getCoordinateLeft(), segmentStart.getCoordinateRight(),
                        segmentEnd.getCoordinateRight(), segmentEnd.getCoordinateLeft());
    }

    public Section2D clone(int id){
        return new Section2D(id, this.segmentStart.clone(), this.segmentEnd.clone());
    }

    public Section2D getNext(int id){
        Coordinate2D left = segmentStart.getCoordinateLeft().getVector(segmentEnd.getCoordinateLeft()).sum(segmentEnd.getCoordinateLeft());
        Coordinate2D right = segmentStart.getCoordinateRight().getVector(segmentEnd.getCoordinateRight()).sum(segmentEnd.getCoordinateRight());
        return new Section2D(id, segmentEnd.clone(), new Segment2D(left, right));
    }

    public int getMinX(){
        return this.segmentStart.getMinX() < this.segmentEnd.getMinX() ? this.segmentStart.getMinX() : this.segmentEnd.getMinX();
    }

    public int getMinY(){
        return this.segmentStart.getMinY() < this.segmentEnd.getMinY() ? this.segmentStart.getMinY() : this.segmentEnd.getMinY();
    }

    public int getMaxX(){
        return this.segmentStart.getMaxX() > this.segmentEnd.getMaxX() ? this.segmentStart.getMaxX() : this.segmentEnd.getMaxX();
    }

    public int getMaxY(){
        return this.segmentStart.getMaxY() > this.segmentEnd.getMaxY() ? this.segmentStart.getMaxY() : this.segmentEnd.getMaxY();
    }

    public boolean isIntoSection(Coordinate2D coordinate){
        double areaABCD = getArea(segmentStart.getCoordinateLeft(), segmentStart.getCoordinateRight(), 
                                              segmentEnd.getCoordinateRight(), segmentEnd.getCoordinateLeft());
        double areaABE = getArea(segmentStart.getCoordinateLeft(), segmentStart.getCoordinateRight(), coordinate);
        double areaBCE = getArea(segmentStart.getCoordinateRight(), segmentEnd.getCoordinateRight(), coordinate);
        double areaCDE = getArea(segmentEnd.getCoordinateRight(), segmentEnd.getCoordinateLeft(), coordinate);
        double areaDAE = getArea(segmentEnd.getCoordinateLeft(), segmentStart.getCoordinateLeft(), coordinate);
        return Math.abs(areaABCD - (areaABE + areaBCE + areaCDE + areaDAE)) < 1e-6;
    }

    private double getArea(Coordinate2D A, Coordinate2D B, Coordinate2D C, Coordinate2D D) {
        return getArea(A, B, D) + getArea(B, C, D);
    }

    private double getArea(Coordinate2D A, Coordinate2D B, Coordinate2D C) {
        double a = A.getDistance(B);
        double b = B.getDistance(C);
        double c = C.getDistance(A);
        double p = (a + b + c) / 2.0;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public List<List<Coordinate2D>> getIntoSectionPositions(){
        Direction2D direction = this.segmentStart.getCoordinateMiddle()
                                    .getVector(this.segmentEnd.getCoordinateMiddle())
                                    .getDirection();
        if(direction == Direction2D.UP || direction == Direction2D.DOWN)
            return getPositionsUpDownDirection(direction);
        if(direction == Direction2D.RIGHT || direction == Direction2D.LELF)
            return getPositionsRightLeftDirection(direction);
        return null;
    }

    private List<List<Coordinate2D>> getPositionsUpDownDirection(Direction2D direction){
        List<List<Coordinate2D>> coors = new ArrayList<>();
        Coordinate2D start = this.segmentEnd.getCoordinateLeft(), end = this.segmentEnd.getCoordinateRight();
        boolean leftright = start.getX() < end.getX(), up = direction == Direction2D.UP;
        int Yend = up ? this.segmentStart.getMinY() : this.segmentStart.getMaxY();
        for(int j = start.getY(); (up && j >= Yend) || ((!up) && j <= Yend); j += up ? -1 : +1){
            List<Coordinate2D> coor = new ArrayList<>();
            for(int i = start.getX(); (leftright && i <= end.getX()) || ((!leftright) && i >= end.getX()); i += leftright ? +1 : -1){
                Coordinate2D coordinate = new Coordinate2D(i, j);
                if(isIntoSection(coordinate))
                    coor.add(coordinate);
            }
            coors.add(coor);
        }
        return coors;
    }

    private List<List<Coordinate2D>> getPositionsRightLeftDirection(Direction2D direction){
        List<List<Coordinate2D>> coors = new ArrayList<>();
        Coordinate2D start = this.segmentEnd.getCoordinateLeft(), end = this.segmentEnd.getCoordinateRight();
        boolean updown = start.getY() > end.getY(), right = direction == Direction2D.RIGHT;
        int Xend = right ? this.segmentStart.getMinX() : this.segmentStart.getMaxX();
        for(int j = start.getX(); (right && j >= Xend) || ((!right) && j <= Xend); j += right ? -1 : +1){
            List<Coordinate2D> coor = new ArrayList<>();
            for(int i = start.getY(); ((!updown) && i <= end.getY()) || (updown && i >= end.getY()); i += updown ? -1 : +1){
                Coordinate2D coordinate = new Coordinate2D(j, i);
                if(isIntoSection(coordinate))
                    coor.add(coordinate);
            }
            coors.add(coor);
        }
        return coors;
    }

    public Direction2D getDirection(){
        return getSegmentStart().getCoordinateMiddle()
                                .getVector(getSegmentEnd().getCoordinateMiddle())
                                .getDirection();
    }

    @Override
    public String toString() {
        return "{ 'segmentStart': " + segmentStart + ", 'segmentEnd': " + segmentEnd + " }";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((segmentStart == null) ? 0 : segmentStart.hashCode());
        result = prime * result + ((segmentEnd == null) ? 0 : segmentEnd.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Section2D other = (Section2D) obj;
        if (segmentStart == null) {
            if (other.segmentStart != null)
                return false;
        } else if (!segmentStart.equals(other.segmentStart))
            return false;
        if (segmentEnd == null) {
            if (other.segmentEnd != null)
                return false;
        } else if (!segmentEnd.equals(other.segmentEnd))
            return false;
        return true;
    }

    

    

}
