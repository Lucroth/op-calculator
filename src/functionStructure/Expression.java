package functionStructure;

import inputParsing.InputParsing;

import java.util.ArrayList;
/**
 * Created by Maciej on 2016-11-01.
 */
public class Expression implements IEvaluable {
    protected String input;
    protected ArrayList<Expression> expressions;
    protected ArrayList<Operation> operations;

    public Expression(String input) {
        this.expressions = InputParsing.detectExpressions(input);
        if(expressions == null)
            this.input = trimInput(input);
        else
            this.input = input;
        this.operations = InputParsing.getOperationListExpr(input, expressions);
    }

    private String trimInput(String input) {
        while(input.charAt(0) == '(')
            input = input.substring(1);
        while(input.charAt(input.length() - 1) == ')')
            input = input.substring(input.length() - 1);

        return input;
    }

    protected double Calculate(ArrayList<Operation> operations) {
        boolean operationDone = false;

        while(operations.size() > 1) {

            for(int i = 0; i < operations.size(); i++) {
                operationDone = false;
                if ((operations.get(i).getOperationType() == OperationType.Multiplication) || (operations.get(i).getOperationType() == OperationType.Division)) {
                    preformCalculations(operations, i);
                    operations.remove(i);
                    operationDone = true;
                    break;
                }
            }

            if(!operationDone) {
                for(int i = 0; i < operations.size(); i++) {
                    if ((operations.get(i).getOperationType() == OperationType.Addition) || (operations.get(i).getOperationType() == OperationType.Subtraction)) {
                        preformCalculations(operations, i);
                        operations.remove(i);
                        break;
                    }
                }
            }
        }

        return operations.get(0).getValue();
    }

    private void preformCalculations(ArrayList<Operation> operations, int i) {
        Expression expr = new Expression(Double.toString(operations.get(i).getValue()));

        if (i == 0) {
            operations.get(i + 1).setLeftOperator(expr);
        } else if (i > 0 && i < operations.size() - 1) {
            operations.get(i - 1).setRightOperator(expr);
            operations.get(i + 1).setLeftOperator(expr);
        } else if (i == operations.size() - 1) {
            operations.get(i - 1).setRightOperator(expr);
        }
    }

    public String toString() {
        return input;
    }

    public double getValue() {
        if(expressions == null && input.equals("pi"))
            return Math.PI;
        else if(expressions == null && input.equals("-pi"))
            return -Math.PI;
        else if(expressions == null && input.equals("e"))
            return Math.E;
        else if(expressions == null && input.equals("-e"))
            return -Math.E;
        else if(expressions == null)
            return Double.parseDouble(input);
        else if (expressions.size() == 1)
            return expressions.get(0).getValue();
        else
            return Calculate(operations);
    }
}
