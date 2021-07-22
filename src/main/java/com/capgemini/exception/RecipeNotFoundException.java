package com.capgemini.exception;

/**
 * Is thrown when a call to the Database fails.
 */
public class RecipeNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RecipeNotFoundException(String msg) {
        super(msg);
    }
}
