package by.f1oating.exception;

import by.f1oating.validator.Error;

import java.util.ArrayList;
import java.util.List;

public class DataBaseException extends RuntimeException{

    private final List<Error> errors;

    public DataBaseException(List<Error> errors){
        this.errors = errors;
    }

    public List<Error> getErrors() {
        return errors;
    }


}
