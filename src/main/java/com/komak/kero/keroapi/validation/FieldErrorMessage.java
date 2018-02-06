package com.komak.kero.keroapi.validation;

public class FieldErrorMessage {

    public static final String EMPTY = "EMPTY";
    public static final String INVALID_LENGTH = "INVALID_LENGTH";
    public static final String TOO_SHORT = "TOO_SHORT";
    public static final String TOO_LONG = "TOO_LONG";
    public static final String DUPLICATE = "DUPLICATE";

    private String field;
    private String error;

    public FieldErrorMessage() {
    }

    public FieldErrorMessage(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
