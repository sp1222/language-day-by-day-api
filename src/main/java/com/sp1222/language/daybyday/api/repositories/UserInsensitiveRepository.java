package com.sp1222.language.daybyday.api.repositories;

import com.sp1222.language.daybyday.api.entities.user.UserInsensitive;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInsensitiveRepository extends BaseUserRepository<UserInsensitive> {
    UserInsensitive findById(long id);
}
