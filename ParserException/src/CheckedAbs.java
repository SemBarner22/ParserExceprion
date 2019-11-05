public class CheckedAbs extends AbstractUnaryOperation {
    public CheckedAbs(TripleExpression x) {
        super(x);
    }

    protected int calculate(int x) {
        if (x < 0) {
            return -x;
        } else {
            return x;
        }
    }

    protected void check(int x) throws EvaluatingException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Abs");
        }
    }
}
