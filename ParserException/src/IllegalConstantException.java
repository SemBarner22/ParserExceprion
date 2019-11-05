public class IllegalConstantException extends EvaluatingException {
    public IllegalConstantException(String reason, int index) {
        super("Constant '" + reason + "' on position " + index + " is unsuitable for int");
    }
}