package by.f1oating.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);

}
