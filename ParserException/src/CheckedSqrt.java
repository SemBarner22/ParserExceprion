public class CheckedSqrt extends AbstractUnaryOperation {
    public CheckedSqrt(TripleExpression x) {
        super(x);
    }

    protected int calculate(int x) {
        int left = 0;
        int right = 46341;
        while (right - left > 1) {
            int middle = (left + right) / 2;
            if (middle * middle > x) {
                right = middle;
            } else {
                left = middle;
            }
        }
        return left;
    }

    protected void check(int x) throws EvaluatingException {
        if (x < 0) {
            throw new IllegalOperationException("Sqrt from negative number");
        }
    }
}
