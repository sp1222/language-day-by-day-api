//package com.sp1222.language.daybyday.api.entities.user;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.Email;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * Sensitive User entity for handling sensitive data.
// */
//@Builder
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "users", uniqueConstraints = @UniqueConstraint(name = "unique_email", columnNames = {"email"}))
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Valid
//public class UserSensitive extends BaseUser {
//
//    /**
//     * Id for entities.
//     */
////    @Id
////    @OneToOne
////    private BaseUser baseUser;
//
//    /**
//     * Email.
//     */
//    @Column(name = "email", unique = true, nullable = false)
//    @Email(message = "Invalid email format", regexp = "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-za-z]{2,3}")
//    private String email;
//}
