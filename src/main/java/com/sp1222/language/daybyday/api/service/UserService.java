package com.sp1222.language.daybyday.api.service;

import com.sp1222.language.daybyday.api.dto.UserDto;
import com.sp1222.language.daybyday.api.entities.user.UserConfidential;
import com.sp1222.language.daybyday.api.entities.user.UserInsensitive;
import com.sp1222.language.daybyday.api.entities.user.UserSensitive;
import com.sp1222.language.daybyday.api.exceptions.DuplicatePropertyException;
import com.sp1222.language.daybyday.api.exceptions.NotFoundByUsernameException;
import com.sp1222.language.daybyday.api.repositories.UserConfidentialRepository;
import com.sp1222.language.daybyday.api.repositories.UserInsensitiveRepository;
import com.sp1222.language.daybyday.api.repositories.UserSensitiveRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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

    Argon2PasswordEncoder argon2PasswordEncoder;

    private int saltLength;

    public UserService(UserInsensitiveRepository userInsensitiveRepository,
                       UserSensitiveRepository userSensitiveRepository,
                       UserConfidentialRepository userConfidentialRepository,
                       Environment environment ) {
        this.userInsensitiveRepository = userInsensitiveRepository;
        this.userSensitiveRepository = userSensitiveRepository;
        this.userConfidentialRepository = userConfidentialRepository;

        this.saltLength = Integer.parseInt(environment.getProperty("argon2.salt-length", "12"));
        int hashLength = Integer.parseInt(environment.getProperty("argon2.hash-length", "128"));
        int parallelism = Integer.parseInt(environment.getProperty("argon2.parallelism", "1"));
        int memory = Integer.parseInt(environment.getProperty("argon2.memory", "60000"));
        int iterations = Integer.parseInt(environment.getProperty("argon2.iterations", "8"));
        argon2PasswordEncoder = new Argon2PasswordEncoder(this.saltLength,hashLength,parallelism,memory,iterations);
    }

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

    public boolean setUserPassword(UserDto userDto) {
        if (!userConfidentialRepository.existsByUsername(userDto.username)) {
            throw new NotFoundByUsernameException(userDto.username);
        }
        UserConfidential userConfidential = userConfidentialRepository.findByUsername(userDto.username);
        if (userConfidential.getSalt().isEmpty()) {
            userConfidential.setSalt(initSalt());
        }
        // TODO: consider transmitting password safely from front to back ends
        userConfidential.setHashed(toHash(userDto.password.concat(userConfidential.getSalt())));
        try {
            userConfidentialRepository.save(userConfidential);
        } catch (Exception e) {
            // TODO
            return false;
        }
        return true;
    }

    private String initSalt() {
        // TODO: return random characters of length this.saltLength
        return "implement me";
    }

    private String toHash(String hashing) {
        return argon2PasswordEncoder.encode(hashing);
    }

    public UserDto updateUserFirstname() {
        return null;
    }
}
