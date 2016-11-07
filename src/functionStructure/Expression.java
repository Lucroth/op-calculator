package functionStructure;

import inputParsing.InputParsing;
import mainMechanism.mainMechanism;

import java.util.ArrayList;
/**
 * Created by Maciej on 2016-11-01.
 */
public class Expression implements IEvaluable {
    private String input;
    private ArrayList<Expression> expressions;
    private ArrayList<Operation> operations;

    public Expression(String input) {
        this.input = InputParsing.cropExpression(input);
        this.expressions = InputParsing.detectExpressions(input);
        this.operations = InputParsing.getOperationListExpr(input);
    }

    public double getValue() {
        if(expressions == null)
            return Double.parseDouble(input);
        else
            return mainMechanism.Calculate(operations);
    }

    public String toString() {
        return input;
    }
}
