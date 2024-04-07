//package com.sp1222.language.daybyday.api.controllers
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.sp1222.language.daybyday.api.exceptions.ApiExceptionResponse
//import com.sp1222.language.daybyday.api.entities.user.Users
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.web.client.TestRestTemplate
//import org.springframework.boot.test.web.server.LocalServerPort
//import org.springframework.http.*
//import org.springframework.test.annotation.DirtiesContext
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.test.context.ContextConfiguration
//import spock.lang.Specification
//
//@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class UsersControllerSpec extends Specification {
//
//    @LocalServerPort
//    int port
//
//    @Autowired
//    UsersRepository usersRepository
//
//    @Autowired
//    TestRestTemplate restTemplate
//
//    ObjectMapper objectMapper
//
//    def setup() {
//        restTemplate = new TestRestTemplate()
//        objectMapper = new ObjectMapper()
//        def existingUsers = Users.builder()
//                .username("users1")
//                .email("users1@test.com")
//                .firstname("users1")
//                .hashed("some hash 1")
//                .build()
//        usersRepository.save(existingUsers)
//        existingUsers = Users.builder()
//                .username("users2")
//                .email("users2@test.com")
//                .firstname("users2")
//                .hashed("some hash 2")
//                .build()
//        usersRepository.save(existingUsers)
//    }
//
//    def cleanup() {
//        usersRepository.deleteAll()
//    }
//
//    @DirtiesContext
//    def 'Get a Users given its Id'() {
//        when:
//        def result = restTemplate.getForEntity(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users/id/1"),
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, Users.class)
//
//        then:
//        result.statusCode == HttpStatus.OK
//        body.id == 1
//        body.username == "users1"
//        body.email == "users1@test.com"
//        body.firstname == "users1"
//    }
//
//    @DirtiesContext
//    def 'Get a Users given a non-existing Id, expect a not found response'() {
//        when:
//        def result = restTemplate.getForEntity(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users/id/99"),
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, ApiExceptionResponse.class)
//
//        then:
//        result.statusCode == HttpStatus.NOT_FOUND
//        body.message == "Entity not found by Id: 99"
//    }
//
//    @DirtiesContext
//    def 'Get a Users given its Username'() {
//        when:
//        def result = restTemplate.getForEntity(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users/username/users1"),
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, Users.class)
//
//        then:
//        result.statusCode == HttpStatus.OK
//        body.id == 1
//        body.username == "users1"
//        body.email == "users1@test.com"
//        body.firstname == "users1"
//    }
//
//    @DirtiesContext
//    def 'Get a Users given a non-existing Username, expect a not found response'() {
//        when:
//        def result = restTemplate.getForEntity(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users/username/defunct"),
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, ApiExceptionResponse.class)
//
//        then:
//        result.statusCode == HttpStatus.NOT_FOUND
//        body.message == "Entity not found by Username: defunct"
//    }
//
//    @DirtiesContext
//    def 'Create a new Users'() {
//        setup:
//        def newUsers = Users.builder()
//                .username("fred")
//                .email("fred@test.com")
//                .build()
//
//        def headers = new HttpHeaders()
//        headers.setContentType(MediaType.APPLICATION_JSON)
//        HttpEntity<Users> request = new HttpEntity<>(newUsers, headers)
//
//        when:
//        def result = restTemplate.postForEntity(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users"),
//                request,
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, Users.class)
//
//        then:
//        result.statusCode == HttpStatus.OK
//        body.id == 3
//        body.username == newUsers.username
//        body.email == newUsers.email
//        body.firstname == newUsers.firstname
//    }
//
//    @DirtiesContext
//    def 'Create a new Users with an already existing email'() {
//        setup:
//        def newUsers = Users.builder()
//                .username("fred")
//                .email("users1@test.com")
//                .firstname("users1")
//                .hashed("some hash 1")
//                .build()
//
//        def headers = new HttpHeaders()
//        headers.setContentType(MediaType.APPLICATION_JSON)
//        HttpEntity<Users> request = new HttpEntity<>(newUsers, headers)
//
//        when:
//        def result = restTemplate.postForEntity(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users"),
//                request,
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, ApiExceptionResponse.class)
//
//        then:
//        result.statusCode == HttpStatus.BAD_REQUEST
//        body.message == "Entity property already exists: users1@test.com"
//    }
//
//    @DirtiesContext
//    def 'Create a new Users with an already existing username'() {
//        setup:
//        def newUsers = Users.builder()
//                .username("users1")
//                .email("anotheremail@test.com")
//                .firstname("users1")
//                .hashed("some hash 1")
//                .build()
//
//        def headers = new HttpHeaders()
//        headers.setContentType(MediaType.APPLICATION_JSON)
//        HttpEntity<Users> request = new HttpEntity<>(newUsers, headers)
//
//        when:
//        def result = restTemplate.postForEntity(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users"),
//                request,
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, ApiExceptionResponse.class)
//
//        then:
//        result.statusCode == HttpStatus.BAD_REQUEST
//        body.message == "Entity property already exists: users1"
//    }
//
//    @DirtiesContext
//    def 'Update an existing Users email with a valid email'() {
//        setup:
//        def existingUsers = usersRepository.findByUsername("users1")
//        existingUsers.setEmail("updatedEmail@test.com")
//
//        def headers = new HttpHeaders()
//        headers.setContentType(MediaType.APPLICATION_JSON)
//        HttpEntity<Users> request = new HttpEntity<>(existingUsers, headers)
//
//        when:
//        def result = restTemplate.exchange(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users/id/${existingUsers.id}"),
//                HttpMethod.PUT,
//                request,
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, Users.class)
//
//        then:
//        result.statusCode == HttpStatus.OK
//        body.id == existingUsers.id
//        body.username == existingUsers.username
//        body.email == existingUsers.email
//        body.firstname == existingUsers.firstname
//    }
//
//    @DirtiesContext
//    def 'Update an existing Users with an invalid email'() {
//        setup:
//        def existingUsers = usersRepository.findByUsername("users1")
//        existingUsers.setEmail("defunctEmail")
//
//        def headers = new HttpHeaders()
//        headers.setContentType(MediaType.APPLICATION_JSON)
//        HttpEntity<Users> request = new HttpEntity<>(existingUsers, headers)
//
//        when:
//        def result = restTemplate.exchange(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users/id/${existingUsers.id}"),
//                HttpMethod.PUT,
//                request,
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, ApiExceptionResponse.class)
//
//        then:
//        result.statusCode == HttpStatus.BAD_REQUEST
//        body.message == "Invalid email format: defunctEmail"
//    }
//
//    @DirtiesContext
//    def 'Update an existing Users with an already existing email, expect a property already exists exception'() {
//        setup:
//        def existingUsers = usersRepository.findByUsername("users2")
//        existingUsers.setEmail("users1@test.com")
//
//        def headers = new HttpHeaders()
//        headers.setContentType(MediaType.APPLICATION_JSON)
//        HttpEntity<Users> request = new HttpEntity<>(existingUsers, headers)
//
//        when:
//        def result = restTemplate.exchange(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users/id/${existingUsers.id}"),
//                HttpMethod.PUT,
//                request,
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, ApiExceptionResponse.class)
//
//        then:
//        result.statusCode == HttpStatus.BAD_REQUEST
//        body.message == "Entity property already exists: users1@test.com"
//    }
//
//    @DirtiesContext
//    def 'Delete an existing Users'() {
//        when:
//        def result = restTemplate.exchange(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users/id/1"),
//                HttpMethod.DELETE,
//                HttpEntity.EMPTY,
//                String.class
//        )
//
//        then:
//        result.statusCode == HttpStatus.NO_CONTENT
//    }
//
//    @DirtiesContext
//    def 'Delete a Users with non-existing Id'() {
//        when:
//        def result = restTemplate.exchange(
//                new URI("http://localhost:${port}/language-day-by-day" +
//                        "/api/users/id/99"),
//                HttpMethod.DELETE,
//                HttpEntity.EMPTY,
//                String.class
//        )
//        def body = objectMapper.readValue(result.body, ApiExceptionResponse.class)
//
//        then:
//        result.statusCode == HttpStatus.NOT_FOUND
//        body.message == "Entity not found by Id: 99"
//    }
//}