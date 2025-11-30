package exceptions;

public class MissingParamException extends RuntimeException {
    public MissingParamException(String message) {
        super(message);
    }
}
