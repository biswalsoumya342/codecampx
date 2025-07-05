package com.codecampx.codecampx.exception;

public class ResorceNotFoundException extends RuntimeException{
    private String resource;
    private String field;
    private String value;

    public ResorceNotFoundException(String resource, String field, String value) {
        super(String.format("% Not Found With %s : %s",resource,field,value));
        this.resource = resource;
        this.field = field;
        this.value = value;
    }
}
