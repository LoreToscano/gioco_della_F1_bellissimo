package org.Models.GameElements;

public class MobileCoordinate2D extends Coordinate2D {

    public MobileCoordinate2D(int x, int y) {
        super(x, y);
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setCoordiante(Coordinate2D c){
        this.x = c.x;
        this.y = c.y;
    }

    public void applyVector(Vector2D vector){
        this.x += vector.x;
        this.y += vector.y;
    }
    
}
