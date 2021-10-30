package com.sysouq.sysouqapi.exceptions;


public class UserExistException extends Exception {

    /**
     * Constructs a new UserExistException with {@code null} as its detail message.
     * <p>
     * The cause is if we try to add user and the user already exist in our database
     */
    public UserExistException() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     * <p>
     * The cause is if we try to add user and the user already exist in our database
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public UserExistException(String message) {
        super(message);
    }
}
