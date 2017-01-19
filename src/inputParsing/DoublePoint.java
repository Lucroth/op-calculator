package inputParsing;

/**
 * Created by Maciej on 2017-01-15.
 */
public class DoublePoint {
    private double x;
    private double y;

    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }

    @Override
    public String toString() {
        return "X: " + this.x + " Y: " + this.y;
    }
}
