package com.sp1222.language.daybyday.api.services

import com.sp1222.language.daybyday.api.exceptions.NotFoundByIdException
import com.sp1222.language.daybyday.api.exceptions.NotFoundByUsernameException
import com.sp1222.language.daybyday.api.entities.Users
import com.sp1222.language.daybyday.api.repositories.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
@SpringBootTest
class UsersServiceSpec extends Specification {

    @Autowired
    UsersRepository usersRepository

    @Autowired
    UsersService usersService

    Users users

    def setup() {
        usersRepository = Mock()
        usersService = new UsersService(usersRepository)
        users = Users.builder()
            .id(1)
            .username("users1")
            .email("users1@test.com")
            .firstname("users1")
            .hashed("some hash")
            .build()
    }

    def 'Get an existing Users by username'() {
        setup:
        usersRepository.existsByUsername(users.username) >> true
        usersRepository.findByUsername(users.username) >> users

        when:
        def result = usersService.getUsers(users.username)

        then:
        result.id == users.id
        result.username == users.username
        result.email == users.email
        result.firstname == users.firstname
        result.hashed == users.hashed
    }

    def 'Get an existing Users by username with defunct username'() {
        setup:
        usersRepository.existsByUsername("defunct") >> false

        when:
        usersService.getUsers("defunct")

        then:
        thrown(NotFoundByUsernameException)
    }

    def 'Get an existing Users by id'() {
        setup:
        usersRepository.existsById(users.id) >> true
        usersRepository.findById(users.id) >> users

        when:
        def result = usersService.getUsers(users.id)

        then:
        result.id == users.id
        result.username == users.username
        result.email == users.email
        result.firstname == users.firstname
        result.hashed == users.hashed
    }

    def 'Get an existing Users by id with non-existing id'() {
        setup:
        usersRepository.existsById(users.id) >> false

        when:
        usersService.getUsers(99)

        then:
        thrown(NotFoundByIdException)
    }

    def 'Create a new Users to the database'() {
        setup:
        usersRepository.save(users) >> users

        when:
        def result = usersService.createUsers(users)

        then:
        result.id == users.id
        result.username == users.username
        result.email == users.email
        result.firstname == users.firstname
        result.hashed == users.hashed
    }

    def 'Update an existing Users'() {
        setup:
        def id = 1
        def updated = Users.builder()
                .id(id)
                .email("newemail@test.com")
                .firstname("users1")
                .hashed("some hash")
                .build()
        def updatedUsers = users
        updatedUsers.setEmail(updated.email)
        usersRepository.existsById(id) >> true
        usersRepository.findById(id) >> users
        usersRepository.save(updatedUsers) >> updatedUsers

        when:
        def result = usersService.updateUsers(users.id, updated)

        then:
        result.id == updated.id
        result.id == users.id
        result.email == updated.email
        result.firstname == users.firstname
        result.hashed == users.hashed
    }

    def 'Update an existing Users with defunct Id'() {
        setup:
        def updated = Users.builder()
                .id(99)
                .email("newemail@test.com")
                .firstname("users1")
                .hashed("some hash")
                .build()
        usersRepository.existsById(99) >> false

        when:
        usersService.updateUsers(updated.id, updated)

        then:
        thrown(NotFoundByIdException)
    }
}
