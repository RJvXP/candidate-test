/*
 * Copyright 2019. National Association of Insurance Commissioners.
 */

package org.naic.exception;

public class UserProfileNotFoundException extends RuntimeException {

    public UserProfileNotFoundException() {
        super("User not found");
    }
}
