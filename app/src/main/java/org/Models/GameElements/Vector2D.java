package org.Models.GameElements;

public class Vector2D extends Coordinate2D {

    public Vector2D(int x, int y) {
        super(x, y);
    }

    public Vector2D(){
        this(0, 0);
    }
    public Vector2D(Direction2D direction, int mul){
        this(direction == Direction2D.RIGHT ? mul : direction == Direction2D.LELF ? -mul : 0,
             direction == Direction2D.UP ? mul : direction == Direction2D.DOWN ? -mul : 0);
    }

    public Vector2D reverse(){
        return new Vector2D(x*-1, y*-1);
    }

    public Vector2D sum(Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public Coordinate2D sum(Coordinate2D other) {
        return new Coordinate2D(x + other.x, y + other.y);
    }

    public Direction2D getDirection(){
        if(this.x == 0 && this.y == 0)
            return Direction2D.NONE;
        int absX = Math.abs(this.x), absY = Math.abs(this.y);
        if(absX > absY) {
            if(this.x > 0) return Direction2D.RIGHT;
            else return Direction2D.LELF;
        } else {
            if(this.y > 0) return Direction2D.UP;
            else return Direction2D.DOWN;
        }
    }
    
}
