public class CheckedAdd extends AbstractBinaryOperation {
    public CheckedAdd(TripleExpression firstExpression, TripleExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    protected int calculate(int x, int y) {
        return x + y;
    }

    protected void check(int x, int y) throws EvaluatingException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException("Add");
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException("Add");
        }
    }
}
