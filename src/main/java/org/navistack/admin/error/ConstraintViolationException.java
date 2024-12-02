package org.navistack.admin.error;

import org.navistack.framework.core.error.AbstractUserException;

public class ConstraintViolationException extends AbstractUserException {
    public ConstraintViolationException() {
        super();
    }

    public ConstraintViolationException(String message) {
        super(message);
    }

    public ConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstraintViolationException(Throwable cause) {
        super(cause);
    }

    protected ConstraintViolationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int getSubErrorCode() {
        return UserErrors.CONSTRAINT_VIOLATION;
    }
}
