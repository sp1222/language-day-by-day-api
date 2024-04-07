package com.sp1222.language.daybyday.api.repositories

import com.sp1222.language.daybyday.api.entities.user.BaseUser
import com.sp1222.language.daybyday.api.entities.user.UserInsensitive
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
class UserInsensitiveRepositorySpec extends Specification {

    @Autowired
    UserInsensitiveRepository userInsensitiveRepository

    def setup() {
        def existingUser = new UserInsensitive(username: "user1", firstname: "first1")
        userInsensitiveRepository.save(existingUser)
        existingUser = new UserInsensitive(username: "user2", firstname: "first2")
        userInsensitiveRepository.save(existingUser)
    }

    def cleanup() {
        userInsensitiveRepository.deleteAll()
    }

    @DirtiesContext
    def 'Get an existing Users from the database by username'() {
        when:
        def user = userInsensitiveRepository.findByUsername("user1")

        then:
        user.id == 1
        user.username == "user1"
        user.firstname == "first1"
    }

    @DirtiesContext
    def 'Get a non-existing Users from the database with a username, expect a null user'() {
        when:
        def user = userInsensitiveRepository.findByUsername("defunctuser")

        then:
        user == null
    }

    @DirtiesContext
    def 'Get an existing User from the database with an Id'() {
        when:
        def user = userInsensitiveRepository.findById(1)

        then:
        user.id == 1
        user.username == "user1"
        user.firstname == "first1"
    }

    @DirtiesContext
    def 'Get a non-existing Users from the database with an Id, expect a null Users'() {
        when:
        def user = userInsensitiveRepository.findById(99)

        then:
        user == null
    }

    @DirtiesContext
    def 'Create a new User'() {
        setup:
        def newUser = new UserInsensitive(username: "user3", firstname: "first3")

        when:
        def user = userInsensitiveRepository.save(newUser)

        then:
        user.id == 3
        user.username == newUser.username
        user.firstname == newUser.firstname
    }

    @DirtiesContext
    def 'Delete an existing User'() {
        when:
        userInsensitiveRepository.deleteById(1)
        def result = userInsensitiveRepository.findById(1)

        then:
        result == null
    }
}
