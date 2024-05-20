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
        salt.length() == Integer.parseInt(passwordEncoderConfig.getSaltLengthDefault())
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

    def 'Get hashed string using default parameters for password encoder'() {
        // TODO
        when:
        def hashed = passwordEncoderConfig.getHashedPassword("test")

        then:
        hashed.length() == passwordEncoderConfig.getHashLengthDefault()
    }
}
