package com.sp1222.language.daybyday.api.repositories

import com.sp1222.language.daybyday.api.entities.Users
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration
@DataJpaTest
class UsersRepositorySpec extends Specification {

    @Autowired
    UsersRepository usersRepository

    def setup() {
        def existingUsers = Users.builder()
                .username("user1")
                .firstname("first1")
                .email("user1@test.com")
                .hashed("some hash")
                .build()
        usersRepository.save(existingUsers)
        existingUsers = Users.builder()
                .username("user2")
                .firstname("first2")
                .email("user2@test.com")
                .hashed("some hash")
                .build()
        usersRepository.save(existingUsers)
    }

    def cleanup() {
        usersRepository.deleteAll()
    }

    @DirtiesContext
    def 'Get an existing Users from the database by username'() {
        when:
        def user = usersRepository.findByUsername("user1")

        then:
        user.id == 1
        user.username == "user1"
        user.firstname == "first1"
        user.email == "user1@test.com"
        user.hashed == "some hash"
    }

    @DirtiesContext
    def 'Get a non-existing Users from the database with a username, expect a null user'() {
        when:
        def user = usersRepository.findByUsername("defunctuser")

        then:
        user == null
    }

    @DirtiesContext
    def 'Get an existing Users from the database with an Id'() {
        when:
        def user = usersRepository.findById(1)

        then:
        user.id == 1
        user.username == "user1"
        user.firstname == "first1"
        user.email == "user1@test.com"
        user.hashed == "some hash"
    }

    @DirtiesContext
    def 'Get a non-existing Users from the database with an Id, expect a null Users'() {
        when:
        def user = usersRepository.findById(99)

        then:
        user == null
    }

    @DirtiesContext
    def 'Create a new Users'() {
        setup:
        def newUser = Users.builder()
                .username("new user")
                .firstname("first3")
                .email("newUser@test.com")
                .hashed("some hash")
                .build()

        when:
        def user = usersRepository.save(newUser)

        then:
        user.id == 3
        user.firstname == newUser.firstname
        user.username == newUser.username
        user.email == newUser.email
        user.hashed == newUser.hashed
    }

    @DirtiesContext
    def 'Update email for existing Users'() {
        setup:
        def newEmail = "user1Updated@test.com"
        def user = usersRepository.findByUsername("user1")
        user.setEmail(newEmail)

        when:
        def updated = usersRepository.save(user)

        then:
        updated.email == newEmail
    }

    @DirtiesContext
    def 'Delete an existing Users'() {
        when:
        usersRepository.deleteById(1)
        def result = usersRepository.findById(1)

        then:
        result == null
    }
}
