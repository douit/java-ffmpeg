package xyz.carbule8.video.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TranscodingException extends VideoException {
    private static final long serialVersionUID = 4050056083141596200L;

    public TranscodingException() {
    }

    public TranscodingException(String message) {
        super(message);
    }

    public TranscodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public TranscodingException(Throwable cause) {
        super(cause);
    }

    public TranscodingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
