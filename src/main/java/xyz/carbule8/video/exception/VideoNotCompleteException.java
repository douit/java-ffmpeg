package xyz.carbule8.video.exception;

public class VideoNotCompleteException extends VideoException {
    private static final long serialVersionUID = -2874997987209276225L;

    public VideoNotCompleteException() {
    }

    public VideoNotCompleteException(String message) {
        super(message);
    }

    public VideoNotCompleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoNotCompleteException(Throwable cause) {
        super(cause);
    }

    public VideoNotCompleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
