package by.f1oating.exception;

import by.f1oating.validator.Error;

import java.util.List;

public class ValidationException extends RuntimeException{

    private final List<Error> errors;

    public ValidationException(List<Error> errors){
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }
}
