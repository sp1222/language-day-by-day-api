package com.sp1222.language.daybyday.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    public Long id;
    public String username;
    public String email;
    public String firstname;
    public String password;
}
