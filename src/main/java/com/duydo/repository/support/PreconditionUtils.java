package com.duydo.repository.support;

/**
 * The
 * @author duydo
 */
public class PreconditionUtils {
    private PreconditionUtils() {
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method/constructors is not null.
     * @param reference an object reference
     * @return the non-null reference that was validated
     */
    public final static <T> T checkNotNull(T reference) {
        if (reference  == null) {
            throw new NullPointerException();
        }
        return reference;
    }

}
