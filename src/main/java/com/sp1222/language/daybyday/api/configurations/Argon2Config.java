package com.sp1222.language.daybyday.api.configurations;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
@AllArgsConstructor
public class Argon2Config {

    private final Environment environment;

    public Argon2PasswordEncoder getPasswordEncoder() {
        int saltLength = Integer.parseInt(environment.getProperty("argon2.salt-length", "4"));
        int hashLength = Integer.parseInt(environment.getProperty("argon2.hash-length", "64"));
        int parallelism = Integer.parseInt(environment.getProperty("argon2.parallelism", "1"));
        int memory = Integer.parseInt(environment.getProperty("argon2.memory", "32768"));
        int iterations = Integer.parseInt(environment.getProperty("argon2.iterations", "8"));
        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }

    public int getSaltLength(){
        return Integer.parseInt(environment.getProperty("argon2.salt-length", "12"));
    }
}
