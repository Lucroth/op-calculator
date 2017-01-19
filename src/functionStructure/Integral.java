package functionStructure;

import inputParsing.InputParsing;
import inputParsing.DoublePoint;
import java.util.ArrayList;

/**
 * Created by Maciej on 2017-01-19.
 */
public class Integral extends Expression implements IEvaluable {

    private double lowBorder;
    private double highBorder;
    private int amount;
    private double delta;

    public Integral(String borders, String input) {
        super(input);
        this.lowBorder = Double.parseDouble(borders.split("\\,")[0]);
        this.highBorder = Double.parseDouble(borders.split("\\,")[1]);
        this.amount = 1000;
        this.delta = (this.highBorder - this.lowBorder) / this.amount;

    }

    public double getValue() {
        double sum = 0;

        ArrayList<DoublePoint> dpList = InputParsing.getChartPoints(new Expression(input), amount, lowBorder, highBorder);

        for(int i = 0; i < dpList.size() - 1; i++) {
            sum += (dpList.get(i).getY() + dpList.get(i + 1).getY()) / 2;
        }

        return sum * delta;
    }
}
