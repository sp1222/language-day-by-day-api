package com.sp1222.language.daybyday.api.repositories

import com.sp1222.language.daybyday.api.entities.user.UserConfidential
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
class UserConfidentialSpec extends Specification {

    @Autowired
    UserConfidentialRepository userConfidentialRepository

    def setup() {
        def existingUser = new UserConfidential(username: "user1", hashed: "hashed1")
        userConfidentialRepository.save(existingUser)
        existingUser = new UserConfidential(username: "user2", hashed: "hashed2")
        userConfidentialRepository.save(existingUser)
    }

    def cleanup() {
        userConfidentialRepository.deleteAll()
    }

    @DirtiesContext
    def 'Get an existing Users from the database by username'() {
        when:
        def user = userConfidentialRepository.findByUsername("user1")

        then:
        user.id == 1
        user.username == "user1"
        user.hashed == "hashed1"
    }

    @DirtiesContext
    def 'Get a non-existing Users from the database with a username, expect a null user'() {
        when:
        def user = userConfidentialRepository.findByUsername("defunctuser")

        then:
        user == null
    }

    @DirtiesContext
    def 'Get an existing User from the database with an Id'() {
        when:
        def user = userConfidentialRepository.findById(1)

        then:
        user.id == 1
        user.username == "user1"
        user.hashed == "hashed1"
    }

    @DirtiesContext
    def 'Get a non-existing Users from the database with an Id, expect a null Users'() {
        when:
        def user = userConfidentialRepository.findById(99)

        then:
        user == null
    }

    @DirtiesContext
    def 'Create a new User'() {
        setup:
        def newUser = new UserConfidential(username: "user3", hashed: "hashed3")

        when:
        def user = userConfidentialRepository.save(newUser)

        then:
        user.id == 3
        user.username == newUser.username
        user.hashed == newUser.hashed
    }

    @DirtiesContext
    def 'Delete an existing User'() {
        when:
        userConfidentialRepository.deleteById(1)
        def result = userConfidentialRepository.findById(1)

        then:
        result == null
    }
}
