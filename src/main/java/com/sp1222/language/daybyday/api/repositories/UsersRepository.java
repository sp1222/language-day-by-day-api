package com.sp1222.language.daybyday.api.repositories;

import com.sp1222.language.daybyday.api.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    /**
     * Returns a Users given its username.
     * @param username      Username to query by.
     * @return              Patron with matching username.
     */
    Users findByUsername(String username);

    /**
     * Returns a Users given its Id.
     * @param id            Id to query by.
     * @return              Patron with matching Id.
     */
    Users findById(long id);

    /**
     * Returns if a Users exists given a username.
     * @param username      Username to query by.
     * @return              Boolean if Patron exists given username.
     */
    boolean existsByUsername(String username);

    /**
     * Returns if a User exists given an email.
     * @param email
     * @return
     */
    boolean existsByEmail(String email);
}
