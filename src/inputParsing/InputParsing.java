package inputParsing;

import java.util.ArrayList;
import functionStructure.Operation;
import functionStructure.Expression;
import functionStructure.OperationType;

/**
 * Created by Maciej on 2016-11-02.
 */
public class InputParsing {
    public static String removeWhitespaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    private static int findCorrespondingBracket(String input, int index) {
        int counter = 0;
        for(int i = index + 1; i < input.length(); i++) {
            if(input.charAt(i) == '(')
                counter++;
            else if(input.charAt(i) == ')' && counter == 0)
                return i;
            else if(input.charAt(i) == ')') {
                counter--;
            }
        }
        return input.length() - 1;
    }

    private static boolean containsOperators(String input) {
        for(int i = 0; i < input.length(); i++)
            if(isOperator(input.charAt(i)))
                return true;
        return false;
    }

    private static boolean isOperator(char character) {
        return character == '+' || character == '-' || character == '*' || character == '/';
    }

    public static ArrayList<Operation> getOperationListExpr(String input) {
        ArrayList<Operation> operationList = new ArrayList<>();
        ArrayList<Expression> expressionList = detectExpressions(input);

        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == '(')
                i = findCorrespondingBracket(input, i);
            else if(isOperator(input.charAt(i))) {
                operationList.add(new Operation(input.charAt(i), expressionList.get(operationList.size()), expressionList.get(operationList.size() + 1)));
            }
        }

        return operationList;
    }

    public static String cropExpression(String input) {
        while(input.charAt(0) == '(' && input.charAt(input.length() - 1) == ')')
            input = input.substring(1, input.length() - 1);
        return input;
    }

    public static ArrayList<Expression> detectExpressions(String input) {
        ArrayList<Expression> expressionList = new ArrayList<>();
        int endIndex;

        if(!containsOperators(input))
            return null;
        else {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '(') {
                    endIndex = findCorrespondingBracket(input, i) + 1;
                    expressionList.add(new Expression(input.substring(i + 1, endIndex - 1)));
                    i = endIndex;
                } else {
                    for (int j = i; j < input.length(); j++) {
                        if (InputParsing.isOperator(input.charAt(j))) {
                            expressionList.add(new Expression(input.substring(i, j)));
                            i = j;
                            break;
                        } else if (j == input.length() - 1) {
                            expressionList.add(new Expression(input.substring(i)));
                            i = j;
                        }
                    }
                }
            }
        }

        return expressionList;
    }

    public static OperationType setOperationType(char input) {
        if(input == '*')
            return OperationType.Multiplication;
        else if (input == '/')
            return OperationType.Division;
        else if(input == '+')
            return OperationType.Addition;
        else
            return OperationType.Subtraction;
    }
}
