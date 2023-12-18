package by.f1oating.validator;

import by.f1oating.dto.UserDto;

public class UserDtoValidator implements Validator<UserDto>{

    private final static UserDtoValidator INSTANCE = new UserDtoValidator();

    @Override
    public ValidationResult isValid(UserDto dto) {
        var validationResult = new ValidationResult();
        if(dto.user_name().isEmpty()){
            validationResult.add(Error.of("invalid.name", "Name is empty!"));
        }
        if(dto.user_login().isEmpty()){
            validationResult.add(Error.of("invalid.login", "Login is empty!"));
        }
        if(dto.user_password().isEmpty()){
            validationResult.add(Error.of("invalid.password", "Password is empty!"));
        }
        return validationResult;
    }

    public static UserDtoValidator getInstance(){
        return INSTANCE;
    }

    private UserDtoValidator(){}
}
