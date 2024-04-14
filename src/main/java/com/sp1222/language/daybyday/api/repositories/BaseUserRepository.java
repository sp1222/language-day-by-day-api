package com.sp1222.language.daybyday.api.repositories;

import com.sp1222.language.daybyday.api.entities.user.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseUserRepository<T extends BaseUser> extends JpaRepository<T, Long> {

    /**
     * Returns a Users given its username.
     *
     * @param username      Username to query by.
     * @return              User with matching username.
     */
    T findByUsername(String username);

    /**
     * Returns a Users given its Id.
     *
     * @param id            Id to query by.
     * @return              User with matching Id.
     */
    T findById(long id);

    /**
     * Returns if a Users exists given a username.
     * @param username      Username to query by.
     * @return              Boolean if User exists given username.
     */
    boolean existsByUsername(String username);

    /**
     * Returns if a Users exists given an Id.
     * @param id            Id to query by.
     * @return              Boolean if User exists given username.
     */
    boolean existsById(long id);
}
