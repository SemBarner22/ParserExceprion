public class CheckedMax extends AbstractBinaryOperation {
    public CheckedMax(TripleExpression firstExpression, TripleExpression secondExpression) {
        super(firstExpression, secondExpression);
    }

    protected int calculate(int x, int y) {
        if (x > y) {
            return x;
        } else {
            return y;
        }
    }

    protected void check(int x, int y) {
    }
}
