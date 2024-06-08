package com.sp1222.language.daybyday.api.service;

import com.sp1222.language.daybyday.api.configurations.PasswordEncoderConfig;
import com.sp1222.language.daybyday.api.dto.UserDto;
import com.sp1222.language.daybyday.api.entities.user.UserConfidential;
import com.sp1222.language.daybyday.api.entities.user.UserInsensitive;
import com.sp1222.language.daybyday.api.entities.user.UserSensitive;
import com.sp1222.language.daybyday.api.exceptions.DuplicatePropertyException;
import com.sp1222.language.daybyday.api.exceptions.NotFoundByIdException;
import com.sp1222.language.daybyday.api.exceptions.NotFoundByUsernameException;
import com.sp1222.language.daybyday.api.repositories.UserConfidentialRepository;
import com.sp1222.language.daybyday.api.repositories.UserInsensitiveRepository;
import com.sp1222.language.daybyday.api.repositories.UserSensitiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// TODO
//  create user - done
//  set user password - done
//  salting - done
//  hashing - wip
//  update firstname?
//  update email?
//  JWT
//  handling exceptions

@Service
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

    /**
     * Configuration for Argon2 encoding.
     */
    PasswordEncoderConfig passwordEncoderConfig;

    /**
     * Create a new user given properties within a UserDto.
     * Initial user creation expects UserInsensitive and UserSensitive data.
     *
     * @param newUser   UserDto containing properties to create a new user by.
     * @return          A valid UserDto with an ID or invalid if ID is null.
     */
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

    /**
     * Sets a password for an existing user.
     * Expected to be done after a new user is created.
     *
     * @param userDto   UserDto containing properties to set a user's password.
     * @return          Boolean representing if a user's password was successfully created or not.
     */
    public boolean setPassword(UserDto userDto) {
        if (!userConfidentialRepository.existsByUsername(userDto.username)) {
            throw new NotFoundByUsernameException(userDto.username);
        }
        UserConfidential userConfidential = userConfidentialRepository.findByUsername(userDto.username);
        if (userConfidential.getSalt() == null || userConfidential.getSalt().isBlank()) {
            userConfidential.setSalt(initSalt());
        }
        userConfidential.setHashed(toHash(userDto.password.concat(userConfidential.getSalt())));
        try {
            userConfidentialRepository.save(userConfidential);
        } catch (Exception e) {
            // TODO
            return false;
        }
        return true;
    }

    /**
     *
     * @param userDto
     * @return
     */
    public boolean setFirstName(UserDto userDto) {
        if (!userInsensitiveRepository.existsById(userDto.id)) {
            throw new NotFoundByIdException(userDto.id);
        }
        UserInsensitive userInsensitive = userInsensitiveRepository.findById(userDto.id);
        userInsensitive.setFirstname(userDto.firstname);
        try {
            userInsensitiveRepository.save(userInsensitive);
        } catch (Exception e) {
            // TODO
        }
        return true;
    }

    /**
     * Retrieves a salt value from the PasswordEncoderConfig.
     *
     * @return      A generated salt value.
     */
    private String initSalt() {
        return passwordEncoderConfig.getSalt();
    }

    /**
     * Hashes a string.
     *
     * @param hashing   String being hashed.
     * @return          Hashed string.
     */
    private String toHash(String hashing) {
        return passwordEncoderConfig.getHashedPassword(hashing);
    }
}
