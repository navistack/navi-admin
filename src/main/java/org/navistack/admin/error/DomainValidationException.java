package org.navistack.admin.error;

import org.navistack.framework.core.error.DomainException;

public class DomainValidationException extends DomainException {
    public DomainValidationException() {
        super();
    }

    public DomainValidationException(String message) {
        super(message);
    }

    public DomainValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainValidationException(Throwable cause) {
        super(cause);
    }

    protected DomainValidationException(
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
