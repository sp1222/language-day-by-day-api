package com.sp1222.language.daybyday.api.repositories;

import com.sp1222.language.daybyday.api.entities.user.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
//
//    /**
//     * Returns a Users given its username.
//     *
//     * @param username      Username to query by.
//     * @return              User with matching username.
//     */
//    BaseUser findByUsername(String username);
//
//    /**
//     * Returns a Users given its Id.
//     *
//     * @param id            Id to query by.
//     * @return              User with matching Id.
//     */
//    BaseUser findById(long id);
//
//    /**
//     * Returns if a Users exists given a username.
//     * @param username      Username to query by.
//     * @return              Boolean if User exists given username.
//     */
//    boolean existsByUsername(String username);
//
//    /**
//     * Returns if a Users exists given an Id.
//     * @param id            Id to query by.
//     * @return              Boolean if User exists given username.
//     */
//    boolean existsById(long id);
}