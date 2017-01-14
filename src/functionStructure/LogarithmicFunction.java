package functionStructure;

public class LogarithmicFunction extends Expression implements IEvaluable {
    private double base;

    public LogarithmicFunction(String base, String input) {
        super(input);
        if(base.equals(""))
            this.base = Math.E;
        else
            this.base = Double.parseDouble(base);
    }

    private double getFunctionValue(double value) {
        if(base == Math.E)
            return Math.log(value);
        else
            return Math.log(value) / Math.log(base);
    }

    public double getValue() {
        if(expressions == null && input.equals("pi"))
            return getFunctionValue(Math.PI);
        else if(expressions == null && input.equals("-pi"))
            return getFunctionValue(-Math.PI);
        else if(expressions == null && input.equals("e"))
            return getFunctionValue(Math.E);
        else if(expressions == null && input.equals("-e"))
            return getFunctionValue(-Math.E);
        else if(expressions == null)
            return getFunctionValue(Double.parseDouble(input));
        else if (expressions.size() == 1)
            return getFunctionValue(expressions.get(0).getValue());
        else
            return getFunctionValue(Calculate(operations));
    }
}
