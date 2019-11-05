public class Const implements TripleExpression {
    private Number value;

    public Const(int value) {
        this.value = value;
    }

    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }

}
