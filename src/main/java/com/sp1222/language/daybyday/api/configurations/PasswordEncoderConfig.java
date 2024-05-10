package com.sp1222.language.daybyday.api.configurations;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.security.SecureRandom;

/**
 * Configuration for Argon2 encoding and salting.
 */
@Configuration
@AllArgsConstructor
public class PasswordEncoderConfig {

    /**
     * Environment bean to access environment variables by.
     */
    private final Environment environment;

    /**
     * Default salt length.
     */
    private final String SALT_LENGTH_DEFAULT = "4";

    /**
     * Default hash length.
     */
    private final String HASH_LENGTH_DEFAULT = "64";

    /**
     * Default parallelism value.
     */
    private final String PARALLELISM_DEFAULT = "1";

    /**
     * Default memory value.
     */
    private final String MEMORY_DEFAULT = "32768";

    /**
     * Default iterations value
     */
    private final String ITERATIONS_DEFAULT = "8";

    /**
     * Configures an Argon2PasswordEncoder using environment variables or a default value if one is not set.
     *
     * @return      Defined Argon2PasswordEncoder object.
     */
    public Argon2PasswordEncoder getPasswordEncoder() {
        int saltLength = getPropertyAsInt("argon2.salt-length", SALT_LENGTH_DEFAULT);
        int hashLength = getPropertyAsInt("argon2.hash-length", HASH_LENGTH_DEFAULT);
        int parallelism = getPropertyAsInt("argon2.parallelism", PARALLELISM_DEFAULT);
        int memory = getPropertyAsInt("argon2.memory", MEMORY_DEFAULT);
        int iterations = getPropertyAsInt("argon2.iterations", ITERATIONS_DEFAULT);
        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }

    /**
     * Generate a salt value for password hashing.
     *
     * @return      Generated string value for salting passwords.
     */
    public String getSalt() {
        int saltLength = getPropertyAsInt("argon2.salt-length", SALT_LENGTH_DEFAULT);
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(saltLength);
        for (int i = 0; i < saltLength; i++) {
            int randomInt = secureRandom.nextInt(62); // range 0-61 for alphanumeric characters
            char randomChar = randomInt < 26 ? (char) ('a' + randomInt) : (char) ('A' + randomInt - 26);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    /**
     * Get an integer value either from an environment property or using a default value.
     *
     * @param property          Property to get the value from.
     * @param defaultValue      Default value if property does not exist.
     * @return                  Non-null integer value.
     */
    private int getPropertyAsInt(String property, String defaultValue) {
        return Integer.parseInt(environment.getProperty(property, defaultValue));
    }
}
