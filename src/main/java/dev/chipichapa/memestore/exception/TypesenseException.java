package dev.chipichapa.memestore.exception;

public class TypesenseException extends AppException{
    public TypesenseException() {
        super();
    }

    public TypesenseException(String message) {
        super(message);
    }

    public TypesenseException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypesenseException(Throwable cause) {
        super(cause);
    }

    protected TypesenseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
