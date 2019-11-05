public class StringParser {
    private Token token;
    private String name;
    private int value;
    private int index;
    private String expression;
    private int balance;

    public StringParser(String expression) {
        token = Token.BEGIN;
        value = 0;
        index = 0;
        balance = 0;
        this.expression = expression;
    }

    public Token getToken() {
        return token;
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    protected void next() throws ParsingException, EvaluatingException {
        while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
            index++;
        }
        if (index == expression.length()) {
            if (balance > 0) {
                throw new ExtraOpenBracketException(index);
            }
                token = Token.END;
            return;
        }
        char symbol = expression.charAt(index);
        if (symbol == '+') {
            checkForOperand(index);
            token = Token.ADD;
        } else if (symbol == '-') {
            if (token == Token.CONST || token == Token.VARIABLE || token == Token.CLOSEDBRACKET) {
                token = Token.SUBTRACT;
            } else {
                index++;
                if (index == expression.length()) {
                    throw new MissingOperandException(index);
                }
                while (index < expression.length() && Character.isWhitespace(expression.charAt(index))) {
                    index++;
                }
                if (Character.isDigit(expression.charAt(index))) {
                    int beginOfNumber = index;
                    while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                        index++;
                    }
                    int endOfNumber = index;
                    String stringNumber = expression.substring(beginOfNumber, endOfNumber);
                    try {
                        value = Integer.parseInt("-" + stringNumber);
                    } catch (NumberFormatException e) {
                        throw new IllegalConstantException("-" + stringNumber, index);
                    }
                    token = Token.CONST;
                } else {
                    token = Token.UNARYMINUS;
                }
                index--;
            }
        } else if (symbol == '*') {
            checkForOperand(index);
            token = Token.MULTIPLY;
        } else if (symbol == '/') {
            checkForOperand(index);
            token = Token.DIVIDE;
        } else if (Character.isDigit(symbol)) {
            checkForOperation(index);
            int left = index;
            while (index < expression.length() && Character.isDigit(expression.charAt(index))) {
                index++;
            }
            int right = index;
            value = Integer.parseInt(expression.substring(left, right));
            token = Token.CONST;
            index--;
        } else if (symbol == 'x' || symbol == 'y' || symbol == 'z') {
            name = "";
            name += symbol;
            token = Token.VARIABLE;
        } else if (symbol == '(') {
            checkForOperation(index);
            balance++;
            token = Token.OPENEDBRACKET;
        } else if (symbol == ')') {
            balance--;
            token = Token.CLOSEDBRACKET;
            if (balance < 0) {
                throw new ExtraCloseBracketException(index);
            }
        } else if (index + 3 < expression.length() && expression.substring(index, index + 3).equals("max")) {
            checkForOperand(index);
            token = Token.MAX;
            checkNextPosition(index + 3);
            index += 2;
        } else if (index + 3 < expression.length() && expression.substring(index, index + 3).equals("min")) {
            checkForOperand(index);
            token = Token.MIN;
            checkNextPosition(index + 3);
            index += 2;
        } else if (index + 3 < expression.length() && expression.substring(index, index + 3).equals("abs")) {
            token = Token.ABS;
            checkNextPosition(index + 3);
            index += 2;
        } else if (index + 4 < expression.length() && expression.substring(index, index + 4).equals("sqrt")) {
            token = Token.SQRT;
            checkNextPosition(index + 4);
            index += 3;
        } else {
            throw new UnknownIdentifierException(index);
        }
        index++;
    }

    private void checkForOperand(int index) throws MissingOperandException {
        if (token != Token.CONST && token != Token.VARIABLE && token != Token.CLOSEDBRACKET) {
            throw new MissingOperandException(index);
        }
    }

    private void checkForOperation(int index) throws MissingOperationException {
        if (token == Token.CLOSEDBRACKET || token == Token.VARIABLE || token == Token.CONST) {
            throw new MissingOperationException(index);
        }
    }

    private void checkNextPosition(int index) throws IncorrectExpressionException {
        if (expression.charAt(index) != '-' && expression.charAt(index) != ' ' && expression.charAt(index) != '(') {
            throw new IncorrectExpressionException("" + index);
        }
    }

}
