package org.navistack.admin.modules.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.navistack.admin.modules.common.enums.Gender;
import org.navistack.admin.modules.common.enums.UserStatus;
import org.navistack.admin.support.entity.IdEntity;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends IdEntity<Long> {
    private Long id;

    private String nickName;

    private String avatarUrl;

    private Gender gender;

    private LocalDate birthday;

    private String loginName;

    /**
     * aka international mobile dial codes
     * @see <a href="https://en.wikipedia.org/wiki/E.164">E.164</a>
     */
    private String mobilePrefix;

    private String mobileNumber;

    private String emailAddress;

    /**
     * BCrypt hashed password
     */
    private String password;

    private UserStatus status;
}
