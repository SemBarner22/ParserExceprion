public class ExpressionParser implements Parser {
    StringParser stringParser;

    public TripleExpression parse(String s) throws ParsingException, EvaluatingException {
        stringParser = new StringParser(s);
        return maxOrMinOperations();
    }

    private TripleExpression maxOrMinOperations() throws ParsingException, EvaluatingException {
        TripleExpression current = addOrSubtractOperations();
        while (true) {
            if (stringParser.getToken() == Token.MAX) {
                current = new CheckedMax(current, addOrSubtractOperations());
            } else if (stringParser.getToken() == Token.MIN) {
                current = new CheckedMin(current, addOrSubtractOperations());
            } else {
                return current;
            }
        }
    }

    private TripleExpression addOrSubtractOperations() throws ParsingException, EvaluatingException {
        TripleExpression current = multiplyOrDivideOperations();
        while (true) {
            if (stringParser.getToken() == Token.ADD) {
                current = new CheckedAdd(current, multiplyOrDivideOperations());
            } else if (stringParser.getToken() == Token.SUBTRACT) {
                current = new CheckedSubtract(current, multiplyOrDivideOperations());
            } else {
                return current;
            }
        }
    }

    private TripleExpression multiplyOrDivideOperations() throws ParsingException, EvaluatingException {
        TripleExpression current = otherOperations();
        while (true) {
            if (stringParser.getToken() == Token.MULTIPLY) {
                current = new CheckedMultiply(current, otherOperations());
            } else if (stringParser.getToken() == Token.DIVIDE) {
                current = new CheckedDivide(current, otherOperations());
            } else {
                return current;
            }
        }
    }

    private TripleExpression otherOperations() throws ParsingException, EvaluatingException {
        stringParser.next();
        TripleExpression current;
        if (stringParser.getToken() == Token.CONST) {
            current = new Const(stringParser.getValue());
            stringParser.next();
        } else if (stringParser.getToken() == Token.VARIABLE) {
            current = new Variable(stringParser.getName());
            stringParser.next();
        } else if (stringParser.getToken() == Token.OPENEDBRACKET) {
            current = maxOrMinOperations();
            if (stringParser.getToken() != Token.CLOSEDBRACKET) {
                throw new ExtraOpenBracketException(stringParser.getIndex());
            }
            stringParser.next();
        } else if (stringParser.getToken() == Token.UNARYMINUS) {
            current = new CheckedNegate(otherOperations());
        } else if (stringParser.getToken() == Token.ABS) {
            current = new CheckedAbs(otherOperations());
        } else if (stringParser.getToken() == Token.SQRT) {
            current = new CheckedSqrt(otherOperations());
        } else {
            throw new UnknownIdentifierException(stringParser.getIndex());
        }
        return current;
    }

}
