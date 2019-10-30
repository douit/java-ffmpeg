package xyz.carbule8.video.exception;

public class UploadOSSException extends VideoException {
    private static final long serialVersionUID = -1393999788389903036L;

    public UploadOSSException() {
    }

    public UploadOSSException(String message) {
        super(message);
    }

    public UploadOSSException(String message, Throwable cause) {
        super(message, cause);
    }

    public UploadOSSException(Throwable cause) {
        super(cause);
    }

    public UploadOSSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
