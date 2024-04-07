//package com.sp1222.language.daybyday.api.entities.user;
//
//import jakarta.persistence.*;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotBlank;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * Confidential User entity for handling password-related data.
// */
//@Builder
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "users")
//@Valid
//public class UserConfidential extends BaseUser {
//
//    /**
//     * Id for entities.
//     */
////    @Id
////    @OneToOne
////    private BaseUser baseUser;
//
//    /**
//     * Hashed password.
//     */
//    @Column(name = "hashed", nullable = false)
//    @NotBlank(message = "hashed must contain a value")
//    private String hashed;
//}
