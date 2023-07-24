package org.navistack.admin.modules.identity.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.admin.modules.identity.enums.Gender;
import org.navistack.admin.modules.identity.enums.UserStatus;
import org.navistack.framework.batis.entity.IdentifyingEntity;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends IdentifyingEntity<Long> {
    private String nickName;

    private String avatarUrl;

    private Gender gender;

    private LocalDate birthday;

    private String loginName;

    /**
     * aka international mobile dial codes
     *
     * @see <a href="https://en.wikipedia.org/wiki/E.164">E.164</a>
     */
    private String mobileNumber;

    private String emailAddress;

    /**
     * BCrypt hashed password
     */
    private String password;

    private UserStatus status;
}
