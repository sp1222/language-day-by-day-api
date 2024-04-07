package com.sp1222.language.daybyday.api.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MisMatchedIdException extends RuntimeException {
    /**
     * Id of the Patron attempting to update.
     */
    private final Long id;

    /**
     * Patron Id containing updated information.
     */
    private final Long entityId;

    public MisMatchedIdException(Long id, Long entityId) {
        this.id = id;
        this.entityId = entityId;
    }
}
