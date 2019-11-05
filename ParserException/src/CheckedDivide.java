public class CheckedDivide extends AbstractBinaryOperation {
    public CheckedDivide(TripleExpression firstExpression, TripleExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    protected int calculate(int x, int y) {
        return x / y;
    }

    protected void check(int x, int y) throws EvaluatingException {
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException("Divide");
        }
    }
}
