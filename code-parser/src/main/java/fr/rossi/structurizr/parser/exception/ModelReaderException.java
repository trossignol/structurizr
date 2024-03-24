package fr.rossi.structurizr.parser.exception;

public class ModelReaderException extends RuntimeException {

    public ModelReaderException(String msg) {
        super(msg);
    }

    public ModelReaderException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public static void assertTrue(boolean test, String msg) {
        if (!test) throw new ModelReaderException(msg);
    }

    public static void assertFalse(boolean test, String msg) {
        assertTrue(!test, msg);
    }
}
