package inputParsing;

import java.util.ArrayList;
import functionStructure.Operation;
import functionStructure.Expression;
import functionStructure.Function;
import functionStructure.OperationType;

/**
 * Created by Maciej on 2016-11-02.
 */
public class InputParsing {
    private static String removeWhitespaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    private static String lowerCase(String input) { return input.toLowerCase(); }

    public static String prepareString(String input) { return removeWhitespaces(lowerCase(input)); }

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
        return input.length();
    }

    private static boolean containsOperators(String input) {
        boolean flag = false;

        for(int i = 0; i < input.length(); i++) {
            if(!(input.charAt(i) == '(') && !(input.charAt(i) == '-') && !flag)
                flag = true;
            if (isOperator(input.charAt(i)) && flag)
                return true;
        }
        return false;
    }

    private static boolean isFunction(String input) {
        if(input.equals("sin") || input.equals("cos") || input.equals("tan"))
            return true;
        return false;
    }

    private static boolean isOperator(char character) {
        return character == '+' || character == '-' || character == '*' || character == '/';
    }

    public static ArrayList<Operation> getOperationListExpr(String input, ArrayList<Expression> expressionList) {
        ArrayList<Operation> operationList = new ArrayList<>();
        boolean flag = false;

        if(!(expressionList == null)) {
            for (int i = 0; i < input.length(); i++) {
                if(input.charAt(i) != '(' && input.charAt(i) != '-' && !flag)
                    flag = true;
                if (input.charAt(i) == '(')
                    i = findCorrespondingBracket(input, i);
                else if (isOperator(input.charAt(i)) && flag) {
                    operationList.add(new Operation(input.charAt(i), expressionList.get(operationList.size()), expressionList.get(operationList.size() + 1)));
                }
            }
        }

        return operationList;
    }

    public static boolean containsFunctions(String input) {
        for(int i = 0; i < input.length() - 3; i++) {
            if(isFunction(Character.toString(input.charAt(i)).concat(Character.toString(input.charAt(i + 1)).concat(Character.toString(input.charAt(i + 2))))))
                return true;
        }
        return false;
    }

    public static ArrayList<Expression> detectExpressions(String input) {
        ArrayList<Expression> expressionList = new ArrayList<>();
        int endIndex;

        if(!containsOperators(input) && !containsFunctions(input))
            return null;
        else {
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == '(') {
                    endIndex = findCorrespondingBracket(input, i) + 1;
                    expressionList.add(new Expression(input.substring(i + 1, endIndex - 1)));
                    i = endIndex;
                } else {
                    for (int j = i; j < input.length(); j++) {
                        if(j + 3 < input.length() && isFunction(input.substring(j, j + 3))) {
                            if (input.charAt(j + 3) == '(') {
                                endIndex = findCorrespondingBracket(input, j + 4) + 1;
                                expressionList.add(new Function(input.substring(i, j + 3), input.substring(j + 4, endIndex - 1)));
                                i = endIndex;
                                break;
                            }
                        } else if (j == 0 && i == 0)
                            continue;
                        else if (InputParsing.isOperator(input.charAt(j))) {
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
