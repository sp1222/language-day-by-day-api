package com.sp1222.language.daybyday.api.repositories

import com.sp1222.language.daybyday.api.entities.user.UserSensitive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration
@DataJpaTest
class UserSensitiveRepositorySpec extends Specification{

    @Autowired
    UserSensitiveRepository userSensitiveRepository

    def setup() {
        def existingUser = new UserSensitive(username: "user1", email: "first1@test.com")
        userSensitiveRepository.save(existingUser)
        existingUser = new UserSensitive(username: "user2", email: "first2@test.com")
        userSensitiveRepository.save(existingUser)
    }

    def cleanup() {
        userSensitiveRepository.deleteAll()
    }

    @DirtiesContext
    def 'Get an existing Users from the database by username'() {
        when:
        def user = userSensitiveRepository.findByUsername("user1")

        then:
        user.id == 1
        user.username == "user1"
        user.email == "first1@test.com"
    }

    @DirtiesContext
    def 'Get a non-existing Users from the database with a username, expect a null user'() {
        when:
        def user = userSensitiveRepository.findByUsername("defunctuser")

        then:
        user == null
    }

    @DirtiesContext
    def 'Get an existing User from the database with an Id'() {
        when:
        def user = userSensitiveRepository.findById(1)

        then:
        user.id == 1
        user.username == "user1"
        user.email == "first1@test.com"
    }

    @DirtiesContext
    def 'Get a non-existing Users from the database with an Id, expect a null Users'() {
        when:
        def user = userSensitiveRepository.findById(99)

        then:
        user == null
    }

    @DirtiesContext
    def 'Get an existing User from the database with an email'() {
        when:
        def user = userSensitiveRepository.findByEmail("first1@test.com")

        then:
        user.id == 1
        user.username == "user1"
        user.email == "first1@test.com"
    }

    @DirtiesContext
    def 'Get a non-existing Users from the database with an Id, expect a null Users'() {
        when:
        def user = userSensitiveRepository.findByEmail("defunct@test.com")

        then:
        user == null
    }

    @DirtiesContext
    def 'Create a new User'() {
        setup:
        def newUser = new UserSensitive(username: "user3", email: "first3@test.com")

        when:
        def user = userSensitiveRepository.save(newUser)

        then:
        user.id == 3
        user.username == newUser.username
        user.email == newUser.email
    }

    @DirtiesContext
    def 'Delete an existing User'() {
        when:
        userSensitiveRepository.deleteById(1)
        def result = userSensitiveRepository.findById(1)

        then:
        result == null
    }

    @DirtiesContext
    @Unroll
    def 'Determine if user exists by email'() {
        when:
        def result = userSensitiveRepository.existsByEmail(email)

        then:
        result == expected

        where:
        email                   | expected
        "first1@test.com"       | true
        "defunct@test.com"      | false
    }
}
