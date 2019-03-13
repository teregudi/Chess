package board;

public class Square {
    
    private int x;
    private int y;
    
    public Square(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Square){
            if (this.x == ((Square)obj).getX() && this.y == ((Square)obj).getY()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getX() + ", " + this.getY();
    }
}
