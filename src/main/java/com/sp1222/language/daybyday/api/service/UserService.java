package com.sp1222.language.daybyday.api.service;

import com.sp1222.language.daybyday.api.dto.UserDto;
import com.sp1222.language.daybyday.api.entities.user.UserInsensitive;
import com.sp1222.language.daybyday.api.entities.user.UserSensitive;
import com.sp1222.language.daybyday.api.exceptions.DuplicatePropertyException;
import com.sp1222.language.daybyday.api.repositories.UserConfidentialRepository;
import com.sp1222.language.daybyday.api.repositories.UserInsensitiveRepository;
import com.sp1222.language.daybyday.api.repositories.UserSensitiveRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {

    /**
     * Repository to user insensitive data.
     */
    UserInsensitiveRepository userInsensitiveRepository;

    /**
     * Repository to user sensitive data.
     */
    UserSensitiveRepository userSensitiveRepository;

    /**
     * Repository to user confidential data.
     */
    UserConfidentialRepository userConfidentialRepository;

    public UserDto createUser(UserDto newUser) {
        if (userInsensitiveRepository.existsByUsername(newUser.username)) {
            throw new DuplicatePropertyException(newUser.username);
        }
        if (userSensitiveRepository.existsByEmail(newUser.email)) {
            throw new DuplicatePropertyException(newUser.email);
        }

        UserInsensitive userInsensitive = new UserInsensitive(newUser.firstname);
        try {
            userInsensitive = userInsensitiveRepository.save(userInsensitive);
        } catch (Exception e) {
            // TODO
        }
        UserSensitive userSensitive = new UserSensitive(newUser.email);
        userSensitive.setId(userInsensitive.getId());
        try {
            userSensitive = userSensitiveRepository.save(userSensitive);
        } catch (Exception e) {
            // TODO
        }
        newUser.id = userSensitive.getId();
        return newUser;
    }

    public UserDto updateUserFirstname() {

        return null;
    }
}
