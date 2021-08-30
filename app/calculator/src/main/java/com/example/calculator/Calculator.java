package com.example.calculator;

public class Calculator {

    private static final int MAX_DIGITS = 15;

    StringBuilder screen = new StringBuilder();
    StringBuilder numberA = new StringBuilder();
    int decimalPointA = -1;
    boolean negativeA = false;
    StringBuilder operation = new StringBuilder();
    StringBuilder numberB = new StringBuilder();
    int decimalPointB = -1;
    boolean negativeB = false;
    boolean inputB = false;
    boolean done = false;
    Double result = new Double(0);

    public String clear() {
        numberA.setLength(0);
        decimalPointA = -1;
        negativeA = false;
        operation.setLength(0);
        numberB.setLength(0);
        decimalPointB = -1;
        negativeB = false;
        inputB = false;
        done = false;
        return showScreen();
    }

    public String inputDelete() {
        if (done) {
            done = false;
        } else if (inputB) {
            if (numberB.length() == 0) {
                inputB = false;
                operation.setLength(0);
            }
            decimalPointB = inputDelete(numberB, decimalPointB);
        } else {
            decimalPointA = inputDelete(numberA, decimalPointA);
        }
        return showScreen();
    }

    public String inputDigit(char digit) throws IllegalArgumentException {
        if ((digit < '0') || (digit > '9')) {
            throw new IllegalArgumentException("Неверная цифра");
        }
        if (!done) {
            if (inputB) {
                decimalPointB = inputDigit(numberB, digit, decimalPointB);
            } else {
                decimalPointA = inputDigit(numberA, digit, decimalPointA);
            }
        }
        return showScreen();
    }

    public String inputEqual() {
        if (inputB && !done) {
            if (numberB.length() == 0) {
                numberB.append('0');
            }
            Double a = convertNumber(numberA, decimalPointA, negativeA);
            Double b = convertNumber(numberB, decimalPointB, negativeB);
            switch (operation.charAt(0)) {
                case '+':
                    result = a + b;
                    break;
                case '-':
                    result = a - b;
                    break;
                case '*':
                    result = a * b;
                    break;
                case '/':
                    result = a / b;
                    break;
            }
            done = true;
        }
        return showScreen();
    }

    public String inputOperation(char op) throws IllegalArgumentException {
        switch (op) {
            case '+':
            case '-':
            case '*':
            case '/':
                break;
            default:
                throw new IllegalArgumentException("Неверная операция");
        }
        if (operation.length() == 0) {
            if (numberA.length() == 0) {
                numberA.append('0');
            }
            operation.append(op);
            inputB = true;
        }
        return showScreen();
    }

    public String inputPoint() {
        if (!done) {
            if (inputB) {
                decimalPointB = inputPoint(numberB, decimalPointB);
            } else {
                decimalPointA = inputPoint(numberA, decimalPointA);
            }
        }
        return showScreen();
    }

    public String inputPercent() {
        if (!done) {
            if (inputB) {
                Double b = convertNumber(numberB, decimalPointB, negativeB);
                switch (operation.charAt(0)) {
                    case '*':
                    case '/':
                        b /= 100;
                        break;
                    case '+':
                    case '-':
                        Double a = convertNumber(numberA, decimalPointA, negativeA);
                        b = a * b / 100;
                        break;
                }
                numberB.setLength(0);
                numberB.append(b.toString());
            } else {
                Double a = convertNumber(numberA, decimalPointA, negativeA) / 100;
                numberA.setLength(0);
                numberA.append(a.toString());
            }
        }
        return showScreen();
    }

    public String inputSign() {
        if (!done) {
            if (inputB) {
                negativeB = !negativeB;
            } else {
                negativeA = !negativeA;
            }
        }
        return showScreen();
    }

    Double convertNumber(StringBuilder number, int decimalPoint, boolean negative) {
        Double x = Double.parseDouble(number.toString());
        if (decimalPoint > 0) {
            x /= Math.pow(10, decimalPointA);
        }
        if (negativeA) {
            return x;
        } else {
            return x;
        }
     }

    int inputDelete(StringBuilder to, int decimalPoint) {
        if (to.length() > 0) {
            to.deleteCharAt(to.length() - 1);
        }
        if (decimalPoint >= 0) {
            return decimalPoint - 1;
        } else  {
            return decimalPoint;
        }
    }

    int inputDigit(StringBuilder to, char digit, int decimalPoint) {
        if ((to.length() == 0) && (digit == '0')) {
            return decimalPoint;
        }
        if (to.length() >= MAX_DIGITS) {
            return decimalPoint;
        }
        to.append(digit);
        if (decimalPoint >= 0) {
            return decimalPoint + 1;
        } else {
            return decimalPoint;
        }
    }

    int inputPoint(StringBuilder to, int decimalPoint) {
        if ((decimalPoint < 0) && (to.length() < MAX_DIGITS)) {
            if (to.length() == 0) {
                to.append('0');
            }
            return 0;
        } else {
            return decimalPoint;
        }
    }

    String showScreen() {
        screen.setLength(0);
        showNumber(screen, numberA, decimalPointA, negativeA);
        if (operation.length() > 0) {
            screen.append('\n');
            screen.append(operation);
            screen.append('\n');
            showNumber(screen, numberB, decimalPointB, negativeB);
        }
        if (done) {
            screen.append('\n');
            screen.append("=");
            screen.append('\n');
            screen.append(result.toString());
        }
        return screen.toString();
    }

    void showNumber(StringBuilder to, StringBuilder from, int decimalPoint, boolean negative) {
        if (negative) {
            to.append('-');
        }
        if (from.length() == 0) {
            to.append('0');
        } else if (decimalPoint < 0) {
            to.append(from);
        } else {
            to.append(from.substring(0, from.length() - decimalPoint));
            to.append('.');
            if (decimalPoint > 0) {
                to.append(from.substring(from.length() - decimalPoint));
            }
        }
    }

}
