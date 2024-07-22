package com.sp1222.language.daybyday.api.configurations

import org.springframework.core.env.Environment
import spock.lang.Specification

class PasswordEncoderConfigSpec extends Specification{

    PasswordEncoderConfig passwordEncoderConfig

    Environment environment

    def setup() {
        environment = Mock(Environment)
        passwordEncoderConfig = new PasswordEncoderConfig(environment)
    }

    def 'Get a salt value that contains alphanumeric characters with the default length'() {
        when:
        def salt = passwordEncoderConfig.getSalt();

        then:
        salt.length() == Integer.parseInt(passwordEncoderConfig.SALT_LENGTH_DEFAULT)
        salt =~ /^[a-zA-Z0-9]+$/
    }

    def 'Get a salt value that contains alphanumeric characters with the salt length set by environment variable'() {
        setup:
        def saltLength = "5"
        environment.getProperty(_ as String) >> saltLength

        when:
        def salt = passwordEncoderConfig.getSalt();

        then:
        salt.length() == Integer.parseInt(saltLength)
        salt =~ /^[a-zA-Z0-9]+$/
    }

    def 'Validate that the argon2 encoder is initialized using default values'() {
        when:
        def encoder = passwordEncoderConfig.getPasswordEncoder()

        then:
        encoder.saltGenerator.keyLength == Integer.parseInt(passwordEncoderConfig.SALT_LENGTH_DEFAULT)
        encoder.hashLength == Integer.parseInt(passwordEncoderConfig.HASH_LENGTH_DEFAULT)
        encoder.parallelism == Integer.parseInt(passwordEncoderConfig.PARALLELISM_DEFAULT)
        encoder.memory == Integer.parseInt(passwordEncoderConfig.MEMORY_DEFAULT)
        encoder.iterations == Integer.parseInt(passwordEncoderConfig.ITERATIONS_DEFAULT)
    }

//    def 'Validate that the argon2 encoder is initialized using property values'() {
//        setup:
//        passwordEncoderConfig.getPropertyAsInt("argon2.salt-length", _ as String) >> 5
//        passwordEncoderConfig.getPropertyAsInt("argon2.hash-length", _ as String) >> 48
//        passwordEncoderConfig.getPropertyAsInt("argon2.parallelism", _ as String) >> 1
//        passwordEncoderConfig.getPropertyAsInt("argon2.memory", _ as String) >> 40000
//        passwordEncoderConfig.getPropertyAsInt("argon2.iterations", _ as String) >> 2
//
//        when:
//        def encoder = passwordEncoderConfig.getPasswordEncoder()
//
//        then:
//        encoder.saltGenerator.keyLength == 5
//        encoder.hashLength == 48
//        encoder.parallelism == 1
//        encoder.memory == 40000
//        encoder.iterations == 2
//    }
}
