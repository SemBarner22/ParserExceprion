public class MissingOperationException extends ParsingException {
    public MissingOperationException(int index) {
        super("Missing operation " + index);
    }
}