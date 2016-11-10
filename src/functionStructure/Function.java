package functionStructure;

/**
 * Created by Maciej on 2016-11-01.
 */
public class Function extends Expression implements IEvaluable {
    private FunctionType functionType;
    private String function;

    public Function(String function, String input) {
        super(input);
        this.function = function;
        this.functionType = getFunctionType(function);
    }

    private FunctionType getFunctionType(String function) {
        switch(function) {
            case "-sin":
            case "sin":
                return FunctionType.Sin;
            case "-cos":
            case "cos":
                return FunctionType.Cos;
            case "-tan":
            case "tan":
                return FunctionType.Tan;
            default:
                return FunctionType.Sin;
        }
    }

    private double getFunctionValue(double value) {
        if(function.charAt(0) == '-') {
            switch (functionType) {
                case Sin:
                    return -Math.sin(value);
                case Cos:
                    return -Math.cos(value);
                case Tan:
                    return -Math.tan(value);
                default:
                    return -value;
            }
        } else {
            switch (functionType) {
                case Sin:
                    return Math.sin(value);
                case Cos:
                    return Math.cos(value);
                case Tan:
                    return Math.tan(value);
                default:
                    return value;
            }
        }
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
