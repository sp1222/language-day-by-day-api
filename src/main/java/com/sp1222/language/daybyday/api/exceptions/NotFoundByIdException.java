package com.sp1222.language.daybyday.api.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NotFoundByIdException extends RuntimeException {
    /**
     * Id of the Entity not found
     */
    private final Long id;

    public NotFoundByIdException(Long id){
        this.id = id;
    }
}
