package inputParsing;

import java.util.ArrayList;
import functionStructure.*;
import java.util.ArrayList;
import java.util.EmptyStackException;

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
        if(input.equals("sin") || input.equals("cos") || input.equals("tan") || input.equals("log") || input.equals("pow") || input.equals("exp") || input.equals("sqr") || input.equals("int"))
            return true;
        return false;
    }

    private static String getFunctionType(String input) {
        if(input.equals("sin") || input.equals("cos") || input.equals("tan"))
            return "trigonometric";
        else if(input.equals("log"))
            return "logarithmic";
        else if(input.equals("sqr") || input.equals("exp"))
            return "otherFunction";
        else if(input.equals("int"))
            return "integral";
        else return "";
    }

    private static boolean isOperator(char character) {
        return character == '+' || character == '-' || character == '*' || character == '/' || character == '^';
    }

    public static ArrayList<Operation> getOperationListExpr(String input, ArrayList<Expression> expressionList) {
        ArrayList<Operation> operationList = new ArrayList<>();
        boolean flag = false;

        if(!(expressionList == null)) {
            for (int i = 0; i < input.length(); i++) {
                if(input.charAt(i) != '(' && input.charAt(i) != '-' && !flag)
                    flag = true;
                if (input.charAt(i) == '(')
                    i = findCorrespondingBracket(input, i) - 1;
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
                            if(getFunctionType(input.substring(j, j + 3)).equals("trigonometric")) {
                                if(input.charAt(j + 3) == '(') {
                                    endIndex = findCorrespondingBracket(input, j + 4) + 1;
                                    expressionList.add(new TrigonometricFunction(input.substring(i, j + 3), input.substring(j + 4, endIndex - 1)));
                                    i = endIndex;
                                    break;
                                }
                            } else if(getFunctionType(input.substring(j, j + 3)).equals("logarithmic")) {
                                if (input.charAt(j + 3) == '(') {
                                    endIndex = findCorrespondingBracket(input, j + 4) + 1;
                                    expressionList.add(new LogarithmicFunction("", input.substring(j + 4, endIndex - 1)));
                                    i = endIndex;
                                    break;
                                } else if (Character.isDigit(input.charAt(j + 3))) {
                                    int k = j + 3;
                                    while (Character.isDigit(input.charAt(k))) {
                                        k++;
                                    }
                                    if (input.charAt(k) == '(') {
                                        endIndex = findCorrespondingBracket(input, k + 1) + 1;
                                        i = endIndex;
                                        expressionList.add(new LogarithmicFunction(input.substring(j + 3, k), input.substring(k + 1, endIndex - 1)));
                                        break;
                                    }
                                }
                                break;
                            } else if(getFunctionType(input.substring(j, j + 3)).equals("integral")) {
                                if(input.charAt(j + 3) == '(') {
                                    int borderIndex = findCorrespondingBracket(input, j + 4) + 1;
                                    endIndex = findCorrespondingBracket(input, borderIndex + 1) + 1;
                                    i = endIndex;
                                    expressionList.add(new Integral(input.substring(j + 4, borderIndex - 1), input.substring(borderIndex + 1, endIndex - 1)));
                                    break;
                                } else {
                                    throw new EmptyStackException();
                                }
                            } else if(getFunctionType(input.substring(j, j + 3)).equals("otherFunction")) {
                                if(input.charAt(j + 3) == '(') {
                                    endIndex = findCorrespondingBracket(input, j + 4) + 1;
                                    expressionList.add(new OtherFunction(input.substring(i, j + 3), input.substring(j + 4, endIndex - 1)));
                                    i = endIndex;
                                    break;
                                }
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

    public static boolean isEquation(String input) {
        if(input.length() == 1) {
            if (Character.isAlphabetic(input.charAt(0)) && input.charAt(0) != 'e') {
                return true;
            }
        } else if (input.length() > 1) {
            for(int i = 1; i < input.length() - 1; i++) {
                if (Character.isAlphabetic(input.charAt(i)) && !Character.isAlphabetic(input.charAt(i - 1)) && !Character.isAlphabetic(input.charAt(i + 1))) {
                    return true;
                }
            }
            if (Character.isAlphabetic(input.charAt(0)) && input.charAt(0) != 'e' && !Character.isAlphabetic(input.charAt(1))) {
                return true;
            }
            if(Character.isAlphabetic(input.charAt(input.length() - 1)) && !Character.isAlphabetic(input.charAt(input.length() - 2)) && input.charAt(input.length() - 1) != 'e') {
                return true;
            }
        }
        return false;
    }

    public static String removeIntegrals(String input) {
        for(int i = 0; i < input.length() - 2; i++) {
            if(getFunctionType(input.substring(i, i + 3)).equals("integral")) {
                int borderIndex = findCorrespondingBracket(input, i + 4) + 1;
                int endIndex = findCorrespondingBracket(input, borderIndex + 1) + 1;
                input = input.substring(0, i) + input.substring(endIndex);
            }
        }

        return input;
    }

    public static ArrayList<Integer> getVariableIndexes(String input) {

        ArrayList<Integer> array = new ArrayList<>();
        if(input.length() == 1) {
            if (Character.isAlphabetic(input.charAt(0)) && input.charAt(0) != 'e') {
                array.add(0);
            }
        } else if (input.length() > 1) {
            for(int i = 1; i < input.length() - 1; i++) {
                if (Character.isAlphabetic(input.charAt(i)) && !Character.isAlphabetic(input.charAt(i - 1)) && !Character.isAlphabetic(input.charAt(i + 1))) {
                    array.add(i);
                }
            }

            if (Character.isAlphabetic(input.charAt(0)) && input.charAt(0) != 'e' && !Character.isAlphabetic(input.charAt(1))) {
                array.add(0);
            }

            if(Character.isAlphabetic(input.charAt(input.length() - 1)) && !Character.isAlphabetic(input.charAt(input.length() - 2)) && input.charAt(input.length() - 1) != 'e') {
                array.add(input.length() - 1);
            }
        }

        return array;
    }

    public static Expression substituteVariables(ArrayList<Integer> list, Expression expr, double nmb) {
        String input = expr.toString();
        int numberOfLoop = 0;

        for(int number : list) {
            input = input.substring(0, number + (Double.toString(nmb).length() - 1) * numberOfLoop) + Double.toString(nmb) + input.substring(number + 1 + (Double.toString(nmb).length() - 1) * numberOfLoop);
            numberOfLoop++;
        }

        return new Expression(input);
    }

    public static OperationType setOperationType(char input) {
        if(input == '*')
            return OperationType.Multiplication;
        else if (input == '/')
            return OperationType.Division;
        else if(input == '+')
            return OperationType.Addition;
        else if(input == '^')
            return OperationType.Power;
        else
            return OperationType.Subtraction;
    }

    public static ArrayList<DoublePoint> getChartPoints(Expression expr, int amount, double start, double end) {
        ArrayList<DoublePoint> dpTable = new ArrayList<>();
        double delta = (end - start) / amount;

        for(double i = start; i <= end; i += delta) {
            dpTable.add(new DoublePoint(i, new Expression(substituteVariables(getVariableIndexes(expr.toString()), expr, i).toString()).getValue()));
        }

        return dpTable;
    }
}
