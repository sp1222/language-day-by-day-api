package com.sp1222.language.daybyday.api.services

import com.sp1222.language.daybyday.api.configurations.PasswordEncoderConfig
import com.sp1222.language.daybyday.api.dto.UserDto
import com.sp1222.language.daybyday.api.entities.user.UserConfidential
import com.sp1222.language.daybyday.api.entities.user.UserInsensitive
import com.sp1222.language.daybyday.api.entities.user.UserSensitive
import com.sp1222.language.daybyday.api.exceptions.DuplicatePropertyException
import com.sp1222.language.daybyday.api.repositories.UserConfidentialRepository
import com.sp1222.language.daybyday.api.repositories.UserInsensitiveRepository
import com.sp1222.language.daybyday.api.repositories.UserSensitiveRepository
import spock.lang.Specification

class UserServiceSpec extends Specification {

    UserInsensitiveRepository userInsensitiveRepository
    UserSensitiveRepository userSensitiveRepository
    UserConfidentialRepository userConfidentialRepository
    PasswordEncoderConfig passwordEncoderConfig
    UserService userService

    def setup() {
        userInsensitiveRepository = Mock(UserInsensitiveRepository)
        userSensitiveRepository = Mock(UserSensitiveRepository)
        userConfidentialRepository = Mock(UserConfidentialRepository)
        passwordEncoderConfig = Mock(PasswordEncoderConfig)
        userService = new UserService(userInsensitiveRepository, userSensitiveRepository, userConfidentialRepository, passwordEncoderConfig)
    }

    def 'Create user happy path'() {
        setup:
        def id = 1
        def username = "username"
        def firstname = "firstname"
        def email = "test@test.com"
        userInsensitiveRepository.existsByUsername(username) >> false
        userSensitiveRepository.existsByEmail(email) >> false
        userInsensitiveRepository.save(_ as UserInsensitive) >> new UserInsensitive(id: id, firstname: firstname, username: username)
        userSensitiveRepository.save(_ as UserSensitive) >> new UserSensitive(id: id, email: email, username: username)

        when:
        def userDto = UserDto.builder()
            .firstname("firstname")
            .email("test@test.com")
            .username("username")
            .build()
        def result = userService.createUser(userDto)

        then:
        result.id == id
        result.firstname == firstname
        result.email == email
        result.username == username
    }

    def 'Create user invalid username'() {
        setup:
        def username = "username"
        userInsensitiveRepository.existsByUsername(username) >> true

        when:
        def userDto = UserDto.builder()
            .firstname("firstname")
            .email("test@test.com")
            .username("username")
            .build()
        userService.createUser(userDto)

        then:
        thrown(DuplicatePropertyException)
    }

    def 'Create user invalid email'() {
        setup:
        def username = "username"
        def email = "test@test.com"
        userInsensitiveRepository.existsByUsername(username) >> false
        userSensitiveRepository.existsByEmail(email) >> true

        when:
        def userDto = UserDto.builder()
            .firstname("firstname")
            .email("test@test.com")
            .username("username")
            .build()
        userService.createUser(userDto)

        then:
        thrown(DuplicatePropertyException)
    }

    def 'Set first name happy path'() {
        setup:
        def id = 1
        def firstname = "firstname"
        def newFirstname = "newfirstname"
        userInsensitiveRepository.existsById(id) >> true
        userInsensitiveRepository.findById(id) >> new UserInsensitive(id: id, firstname: firstname)
        userInsensitiveRepository.save(_ as UserInsensitive) >> new UserInsensitive(id: id, firstname: firstname)

        when:
        def userDto = UserDto.builder()
                .id(id)
                .firstname(newFirstname)
                .build()
        def result = userService.setFirstName(userDto)

        then:
        result == true
    }

    def 'Set password happy path'() {
        setup:
        def id = 1
        def username = "username"
        def hashed = "hashed"
        def newHashed = "newHashed"
        def salt = "salt"
        userConfidentialRepository.existsByUsername(username) >> true
        userConfidentialRepository.findByUsername(_ as String) >> new UserConfidential(id: id, username: username, salt: salt, hashed: hashed)
        userConfidentialRepository.save(_ as UserConfidential) >> new UserConfidential(id: id, username: username, salt: salt, hashed: newHashed)

        when:
        def userDto = UserDto.builder()
            .username(username)
            .password(newHashed)
            .build()
        def result = userService.setPassword(userDto)

        then:
        result
    }
}
