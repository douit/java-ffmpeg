package xyz.carbule8.video.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FormatNotSupportedException extends VideoException {
    private static final long serialVersionUID = -3290429580601494311L;

    public FormatNotSupportedException() {
    }

    public FormatNotSupportedException(String message) {
        super(message);
    }

    public FormatNotSupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormatNotSupportedException(Throwable cause) {
        super(cause);
    }

    public FormatNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
