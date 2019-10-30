package xyz.carbule8.video.exception;

public class VideoException extends Exception {
    private static final long serialVersionUID = -4980389659279292080L;

    public VideoException() {
    }

    public VideoException(String message) {
        super(message);
    }

    public VideoException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoException(Throwable cause) {
        super(cause);
    }

    public VideoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
