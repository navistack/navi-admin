package org.navistack.admin.support.problems;

import org.navistack.framework.core.problem.ThrowableBizProblem;

public class EntityDuplicatedProblem extends ThrowableBizProblem {
    private static final String ERROR_CODE = "EntityDuplicated";

    public EntityDuplicatedProblem() {
        super(ERROR_CODE);
    }

    public EntityDuplicatedProblem(String message) {
        super(ERROR_CODE, message);
    }

    public EntityDuplicatedProblem(String message, Throwable cause) {
        super(ERROR_CODE, message, cause);
    }

    public EntityDuplicatedProblem(Throwable cause) {
        super(ERROR_CODE, cause);
    }

    public EntityDuplicatedProblem(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(ERROR_CODE, message, cause, enableSuppression, writableStackTrace);
    }
}
