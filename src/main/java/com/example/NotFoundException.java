package com.example;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6285867531715388561L;

    public NotFoundException(String id) {
        // output into console only
        super(String.format("No entry found with id: <%s>", id));
    }
}
