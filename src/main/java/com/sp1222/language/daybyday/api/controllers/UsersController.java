//package com.sp1222.language.daybyday.api.controllers;
//
//import com.sp1222.language.daybyday.api.entities.user.Users;
//import com.sp1222.language.daybyday.api.exceptions.DuplicatePropertyException;
//import com.sp1222.language.daybyday.api.exceptions.MisMatchedIdException;
//import com.sp1222.language.daybyday.api.exceptions.NotFoundByIdException;
//import com.sp1222.language.daybyday.api.exceptions.NotFoundByUsernameException;
//import com.sp1222.language.daybyday.api.services.UsersService;
//import jakarta.validation.Valid;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Users rest controller.
// */
//@Controller
//@RequestMapping("/language-day-by-day/api/users")
//public class UsersController {
//
//    /**
//     * Users service bean.
//     */
//    private final UsersService usersService;
//
//    /**
//     * Constructor.
//     * @param usersService     Users service bean.
//     */
//    public UsersController(UsersService usersService) {
//        this.usersService = usersService;
//    }
//
//    /**
//     * Get a Users given an Id.
//     * @param id                Users's Id to query by.
//     * @return                  Response Entity of type Users.
//     */
//    @GetMapping("/id/{id}")
//    public ResponseEntity<Users> getUsers(@PathVariable long id) throws NotFoundByIdException {
//        return ResponseEntity.ok(usersService.getUsers(id));
//    }
//
//    /**
//     * Get a Users given an Username.
//     * @param username          Users's Username to query by.
//     * @return                  Response Entity of type Users.
//     */
//    @GetMapping("/username/{username}")
//    public ResponseEntity<Users> getUsers(@PathVariable String username) throws NotFoundByUsernameException {
//        return ResponseEntity.ok(usersService.getUsers(username));
//    }
//
//    /**
//     * Create Users with POST.
//     * @param users            Users to create.
//     * @return                  Creation result.
//     */
//    @PostMapping
//    public ResponseEntity<Users> createUsers(@RequestBody @Valid Users users) {
//        Users createdUsers = usersService.createUsers(users);
//        return ResponseEntity.ok(createdUsers);
//    }
//
//    /**
//     * Update Users with PUT.
//     * @param id                Users Id.
//     * @param users            Updated Users properties.
//     * @return                  Update result.
//     */
//    @PutMapping("/id/{id}")
//    public ResponseEntity<Users> updateUsers(@PathVariable long id, @RequestBody @Valid Users users)
//            throws MisMatchedIdException, NotFoundByIdException, DuplicatePropertyException {
//        Users updatedUsers = usersService.updateUsers(id, users);
//        return ResponseEntity.ok(updatedUsers);
//    }
//
//    /**
//     * Delete Users with DELETE.
//     * @param id                Id of Users to delete.
//     * @return                  Delete result.
//     */
//    @DeleteMapping("/id/{id}")
//    public ResponseEntity<Void> deleteUsers(@PathVariable long id) throws NotFoundByIdException {
//        usersService.deleteUsers(id);
//        return ResponseEntity.noContent().build();
//    }
//}
