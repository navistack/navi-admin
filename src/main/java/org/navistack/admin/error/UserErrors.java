package org.navistack.admin.error;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserErrors {
    public final int CONSTRAINT_VIOLATION = 0x001;
    public final int DOMAIN_VALIDATION = 0x002;
    public final int NO_SUCH_ENTITY = 0x003;
}
