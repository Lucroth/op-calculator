package functionStructure;

public class TrigonometricFunction extends Expression implements IEvaluable {
    private FunctionType functionType;
    private String function;

    public TrigonometricFunction(String function, String input) {
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
        double result;

        if(function.charAt(0) == '-') {
            switch (functionType) {
                case Sin:
                    result = -Math.sin(value);
                    if(Math.abs(result) < 1E-10) return 0;
                    else return result;
                case Cos:
                    result = -Math.cos(value);
                    if(Math.abs(result) < 1E-10) return 0;
                    else return result;
                case Tan:
                    result = -Math.tan(value);
                    if(Math.abs(result) < 1E-10) return 0;
                    else return result;
                default:
                    if(Math.abs(-value) < 1E-10) return 0;
                    else return -value;
            }
        } else {
            switch (functionType) {
                case Sin:
                    result = Math.sin(value);
                    if(Math.abs(result) < 1E-10) return 0;
                    else return result;
                case Cos:
                    result = Math.cos(value);
                    if(Math.abs(result) < 1E-10) return 0;
                    else return result;
                case Tan:
                    result = Math.tan(value);
                    if(Math.abs(result) < 1E-10) return 0;
                    else return result;
                default:
                    if(Math.abs(value) < 1E-10) return 0;
                    else return value;
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
