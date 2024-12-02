package org.navistack.admin.error;

import org.navistack.framework.core.error.AbstractUserException;

public class NoSuchEntityException extends AbstractUserException {
    public NoSuchEntityException() {
        super();
    }

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }

    protected NoSuchEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int getSubErrorCode() {
        return UserErrors.NO_SUCH_ENTITY;
    }
}
