package functionStructure;

public class OtherFunction extends Expression implements IEvaluable {
    private String function;
    private FunctionType functionType;

    public OtherFunction(String function, String input) {
        super(input);
        this.function = function;
        this.functionType = getFunctionType(function);
    }

    private FunctionType getFunctionType(String function) {
        switch(function) {
            case "-exp":
            case "exp":
                return FunctionType.Exp;
            case "-sqr":
            case "sqr":
                return FunctionType.Sqr;
            default:
                return FunctionType.Exp;
        }
    }

    private double getFunctionValue(double value) {
        double result;

        if(function.charAt(0) == '-') {
            switch (functionType) {
                case Exp:
                    result = -Math.exp(value);
                    if(Math.abs(result) < 1E-10) return 0;
                    else return result;
                case Sqr:
                    result = -Math.sqrt(value);
                    if(Math.abs(result) < 1E-10) return 0;
                    else return result;
                default:
                    if(Math.abs(-value) < 1E-10) return 0;
                    else return -value;
            }
        } else {
            switch (functionType) {
                case Exp:
                    result = Math.exp(value);
                    if(Math.abs(result) < 1E-10) return 0;
                    else return result;
                case Sqr:
                    result = Math.sqrt(value);
                    if(Math.abs(result) < 1E-10) return 0;
                    else return result;
                default:
                    if(Math.abs(value) < 1E-10) return 0;
                    else return value;
            }
        }
    }

    @Override
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
