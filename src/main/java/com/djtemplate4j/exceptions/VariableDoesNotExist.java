package com.djtemplate4j.exceptions;


public class VariableDoesNotExist extends RuntimeException {
    public VariableDoesNotExist() {
        super();
    }

    public VariableDoesNotExist(String variableName) {
        super("Variable '"+ variableName +"' does not exist.");
    }
}
