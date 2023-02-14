/*
 In this lab, we want to generate an expression that approximates a set of input-output data. We will use expression trees to represent the expressions, where the nodes in the tree can be either a constant, a variable, or a binary operator (e.g., addition, subtraction, multiplication, division). We'll also add a new node type, called UnaryOpNode, that represents unary operators like sine, cosine, and absolute value.

Our first task is to write a method that generates a random expression tree of a given height (i.e., the maximum number of nodes from the root to a leaf). We'll then use this method to generate a bunch of random expressions.

Next, we need to evaluate how well each of these expressions approximates the input-output data. We'll use a Root Mean Square Error (RMSE) metric to compare the expression's output to the true output. Our goal is to find the expression that has the smallest RMSE on the input-output data.

Finally, we'll add a new node type, called UnaryOpNode, to represent unary operators like sine, cosine, and absolute value. We'll modify our random expression tree method to include these new nodes.
 */
import java.util.*;

public class RandomExpressions {
    public static void main(String[] args) {
        // generate some random expressions of different heights
        int numExpressions = 5;
        int maxHeight = 3;
        List<ExpNode> expressions = new ArrayList<>();
        for (int i = 0; i < numExpressions; i++) {
            expressions.add(randomExpression(maxHeight));
        }
        // print the expressions
        for (ExpNode exp : expressions) {
            System.out.println(exp.toString());
        }
    }

    //node classes
    static abstract class ExpNode {
        abstract double value(double x);
        abstract ExpNode copy();
    }

    static class ConstNode extends ExpNode {
        double value;
        ConstNode(double val) {
            value = val;
        }
        double value(double x) {
            return value;
        }
        ExpNode copy() {
            return new ConstNode(value);
        }
        public String toString() {
            return String.format("%.2f", value);
        }
    }

    static class VariableNode extends ExpNode {
        double value(double x) {
            return x;
        }
        ExpNode copy() {
            return new VariableNode();
        }
        public String toString() {
            return "x";
        }
    }

    static class BinaryOpNode extends ExpNode {
        char operator;
        ExpNode left, right;
        BinaryOpNode(char op, ExpNode l, ExpNode r) {
            operator = op;
            left = l;
            right = r;
        }
        double value(double x) {
            double leftVal = left.value(x);
            double rightVal = right.value(x);
            switch (operator) {
                case '+':
                    return leftVal + rightVal;
                case '-':
                    return leftVal - rightVal;
                case '*':
                    return leftVal * rightVal;
                case '/':
                    return leftVal / rightVal;
                case '^':
                    return Math.pow(leftVal, rightVal);
                default:
                    return Double.NaN;
            }
        }
        ExpNode copy() {
            return new BinaryOpNode(operator, left.copy(), right.copy());
        }
        public String toString() {
            String leftStr = left instanceof BinaryOpNode ? "(" + left.toString() + ")" : left.toString();
            String rightStr = right instanceof BinaryOpNode ? "(" + right.toString() + ")" : right.toString();
            return leftStr + " " + operator + " " + rightStr;
        }
    }

    static class UnaryOpNode extends ExpNode {
        int function;
        ExpNode child;

        UnaryOpNode(int func, ExpNode c) {
            function = func;
            child = c;
        }

        double value(double x) {
            double childVal = child.value(x);
            switch (function) {
                case 0:
                    return Math.sin(childVal);
                case 1:
                    return Math.cos(childVal);
                case 2:
                    return Math.exp(childVal);
                case 3:
                    return Math.abs(childVal);
                case 4:
                    return -childVal;
                default:
                    return Double.NaN;
            }
        }

        ExpNode copy() {
            return new UnaryOpNode(function, child.copy());
        }

        public String toString() {
            String childStr = child instanceof BinaryOpNode ? "(" + child.toString() + ")" : child.toString();
            switch (function) {
                case 0:
                    return "sin(" + childStr + ")";
                case 1:
                    return "cos(" + childStr + ")";
                case 2:
                    return "exp(" + childStr + ")";
                case 3:
                    return "abs(" + childStr + ")";
                case 4:
                    return "-" + childStr;
                default:
                    return "NaN";
            }
        }
    }
  

    // generate a random expression tree of a given height
    static ExpNode randomExpression(int height) {
        if (height == 0) {
            // base case: return a random constant or variable
            if (Math.random() < 0.5) {
                return new ConstNode(Math.random() * 10 - 5);
            } else {
                return new VariableNode();
            }
        } else {
            // recursive case: return a random binary operator with random children
            char[] operators = {'+', '-', '*', '/', '^'};
            char op = operators[(int) (Math.random() * operators.length)];
            ExpNode left = randomExpression(height - 1);
            ExpNode right = randomExpression(height - 1);
            return new BinaryOpNode(op, left, right);
        }
    }
}
