package com.codecampx.codecampx.exception;

public class DuplicateResourceEntryException extends RuntimeException {
    private String resource;

    public DuplicateResourceEntryException(String resource) {
        super(String.format("%s Alredy Exist Try With Another!",resource));
        this.resource = resource;
    }
}
