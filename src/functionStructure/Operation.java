package functionStructure;

import inputParsing.InputParsing;

/**
 * Created by Maciej on 2016-11-03.
 */
public class Operation<T extends IEvaluable> implements IEvaluable {
    private char sign;
    private OperationType operation;
    private T leftOperator;
    private T rightOperator;

    public Operation(char sign, T leftOperator, T rightOperator) {
        this.sign = sign;
        operation = InputParsing.setOperationType(sign);
        this.leftOperator = leftOperator;
        this.rightOperator = rightOperator;
    }

    public double getValue() {
        switch(operation) {
            case Multiplication:
                return leftOperator.getValue() * rightOperator.getValue();
            case Division:
                return leftOperator.getValue() / rightOperator.getValue();
            case Addition:
                return leftOperator.getValue() + rightOperator.getValue();
            case Subtraction:
                return leftOperator.getValue() - rightOperator.getValue();
            default:
                return (Double) null;
        }
    }

    public void setLeftOperator(T leftOperator) {
        this.leftOperator = leftOperator;
    }

    public void setRightOperator(T rightOperator) {
        this.rightOperator = rightOperator;
    }

    public OperationType getOperationType() { return operation; }

    public String toString() {
        return Double.toString(leftOperator.getValue()) + sign + Double.toString(rightOperator.getValue());
    }
}
