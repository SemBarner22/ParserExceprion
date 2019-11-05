public class CheckedSubtract extends AbstractBinaryOperation {
    public CheckedSubtract(TripleExpression firstExpression, TripleExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    protected int calculate(int x, int y) {
        return x - y;
    }

    protected void check(int x, int y) throws EvaluatingException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new OverflowException("Subtract");
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException("Subtract");
        }
    }
}
