public class Main extends ExpressionParser {
    public static void main(String[] args) throws ParsingException, EvaluatingException {
        new Main();
    }

    protected Main() throws ParsingException, EvaluatingException {
        System.out.println(parse("9*9*9*9*9*9*9*9*9*9*9*9*9").evaluate(0, 0, 0));
    }
}
