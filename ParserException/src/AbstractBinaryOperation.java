public abstract class AbstractBinaryOperation implements TripleExpression {
    private TripleExpression firstExpression, secondExpression;

    public AbstractBinaryOperation(TripleExpression firstExpression, TripleExpression secondExpression) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
    }

    protected abstract int calculate(int x, int y) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int first = firstExpression.evaluate(x, y, z);
        int second = secondExpression.evaluate(x, y, z);
        check(first, second);
        return calculate(first, second);
    }

    protected abstract void check(int x, int y) throws EvaluatingException;

}
