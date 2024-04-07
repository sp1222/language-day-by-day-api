package com.sp1222.language.daybyday.api.services;

import com.sp1222.language.daybyday.api.entities.Users;
import com.sp1222.language.daybyday.api.exceptions.DuplicatePropertyException;
import com.sp1222.language.daybyday.api.exceptions.MisMatchedIdException;
import com.sp1222.language.daybyday.api.exceptions.NotFoundByIdException;
import com.sp1222.language.daybyday.api.exceptions.NotFoundByUsernameException;
import com.sp1222.language.daybyday.api.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UsersService {

    /**
     * Repository bean for Users.
     */
    private final UsersRepository usersRepository;

    /**
     * Get Users by Id.
     * @param id    Users's Id in the database.
     * @return      Existing users.
     */
    public Users getUsers(long id) {
        if(!usersRepository.existsById(id)) {
            throw new NotFoundByIdException(id);
        }
        return usersRepository.findById(id);
    }

    /**
     * Get Users by username.
     * @param username  Users's username.
     * @return          Existing users.
     */
    public Users getUsers(String username) {
        if(!usersRepository.existsByUsername(username)) {
            throw new NotFoundByUsernameException(username);
        }
        return usersRepository.findByUsername(username);
    }

    /**
     * Create a new Users.
     * @param users    New users to create.
     * @return          Newly created users.
     */
    @Transactional
    public Users createUsers(Users users) {
        if(usersRepository.existsByEmail(users.getEmail())) {
            throw new DuplicatePropertyException(users.getEmail());
        }
        if(usersRepository.existsByUsername(users.getUsername())) {
            throw new DuplicatePropertyException(users.getUsername());
        }
        return usersRepository.save(users);
    }

    /**
     * Update a Users.
     * @param id        Id of Users to update.
     * @param updated   UsersUpdate object.
     * @return          Newly updated Users.
     */
    @Transactional
    public Users updateUsers(long id, Users updated) {
        if (id != updated.getId()) {
            throw new MisMatchedIdException(id, updated.getId());
        }
        if (!usersRepository.existsById(id)) {
            throw new NotFoundByIdException(id);
        }
        Users existing = usersRepository.findById(id);
        if (usersRepository.existsByEmail(updated.getEmail())
                && existing.getEmail() != updated.getEmail()) {
            throw new DuplicatePropertyException(updated.getEmail());
        }
        existing.setEmail(updated.getEmail());
        return usersRepository.save(existing);
    }

    /**
     * Delete an existing Users.
     * @param id    Id of Users to delete.
     */
    @Transactional
    public void deleteUsers(long id) {
        if(!usersRepository.existsById(id)) {
            throw new NotFoundByIdException(id);
        }
        usersRepository.deleteById(id);
    }
}
