public abstract class AbstractUnaryOperation implements TripleExpression {
    private TripleExpression expression;

    public AbstractUnaryOperation(TripleExpression expression) {
        this.expression = expression;
    }

    protected abstract int calculate(int x) throws EvaluatingException;

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        int evaluated = expression.evaluate(x, y, z);
        check(evaluated);
        return calculate(evaluated);
    }

    protected abstract void check(int x) throws EvaluatingException;

}
