package xyz.carbule8.video.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NullUploadFileException extends Exception {
    private static final long serialVersionUID = 2967921264617396530L;

    public NullUploadFileException() {
    }

    public NullUploadFileException(String message) {
        super(message);
    }

    public NullUploadFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullUploadFileException(Throwable cause) {
        super(cause);
    }

    public NullUploadFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
