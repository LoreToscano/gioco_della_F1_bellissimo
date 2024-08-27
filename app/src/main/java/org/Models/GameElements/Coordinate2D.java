package org.Models.GameElements;

public class Coordinate2D {
    protected int x;
    protected int y;

    public Coordinate2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate2D getCoordinate(){
        return new Coordinate2D(x, y);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
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
        Coordinate2D other = (Coordinate2D) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    public double getDistance(Coordinate2D other){
        double dimX = Math.abs(this.x - other.x);
        double dimY = Math.abs(this.y - other.y);
        dimX *= dimX;
        dimY *= dimY;
        return Math.sqrt( dimX + dimY );
    }

    public Vector2D getVector(Coordinate2D other){
        return new Vector2D(other.x - this.x, other.y - this.y);
    }

    public Coordinate2D getAverage(Coordinate2D other){
        Vector2D vector = getVector(other);
        vector = new Vector2D(vector.getX() / 2, vector.getY() / 2);
        return vector.sum(this);
    }

    @Override
    public String toString() {
        return "{ 'x': " + x + ", 'y': " + y + " }";
    }
    
}
