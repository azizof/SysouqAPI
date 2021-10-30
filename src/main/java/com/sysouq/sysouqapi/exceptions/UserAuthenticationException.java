package com.sysouq.sysouqapi.exceptions;

public class UserAuthenticationException extends Exception {

    /**
     * Constructs a new UserAuthenticationException with {@code null} as its detail message.
     * <p>
     * The cause is if user tried to login with a wrong email or password
     */
    public UserAuthenticationException() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     * <p>
     * The cause is if user tried to login with a wrong email or password
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UserAuthenticationException(String message) {
        super(message);
    }
}
