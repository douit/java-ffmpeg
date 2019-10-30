package xyz.carbule8.video.exception;

public class VideoNotFoundException extends VideoException {
    private static final long serialVersionUID = 6217703089145099438L;

    public VideoNotFoundException() {
    }

    public VideoNotFoundException(String message) {
        super(message);
    }

    public VideoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoNotFoundException(Throwable cause) {
        super(cause);
    }

    public VideoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
