package by.f1oating.service;

import by.f1oating.dao.UserDao;
import by.f1oating.dto.UserDto;
import by.f1oating.dto.UserFilter;
import by.f1oating.entity.User;
import by.f1oating.exception.ValidationException;
import by.f1oating.validator.UserDtoValidator;
import by.f1oating.validator.ValidationResult;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final static UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final UserDtoValidator userDtoValidator = UserDtoValidator.getInstance();

    public Optional<UserDto> save(UserDto userDto){

        var validationResult = userDtoValidator.isValid(userDto);

        if(validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        Optional<User> user = userDao.save(new User(
                userDto.user_name(),
                userDto.user_login(),
                userDto.user_password(),
                userDto.user_privilege()
        ));
        if(user.get() != null){
            return Optional.of(toUserDto(user.get()));
        }else{
            return Optional.ofNullable(null);
        }
    }

    public boolean delete(Long id){
        return userDao.delete(id);
    }

    public Optional<UserDto> findById(Long id){
        Optional<User> user = userDao.findById(id);
        if(user.get() != null){
            return Optional.of(toUserDto(user.get()));
        }else{
            return Optional.ofNullable(null);
        }
    }

    public Optional<UserDto> findByLogin(String login){
        UserFilter filter = new UserFilter(null, login, null, null);
        List<Optional<User>> user = userDao.findAll(filter);
        if(user.isEmpty()){
            return Optional.ofNullable(null);
        }else{
            return Optional.of(toUserDto(user.stream().findFirst().get().get()));
        }
    }

    private UserService() {
    }

    public static UserService getInstance(){
        return INSTANCE;
    }

    private UserDto toUserDto(User user){
        return new UserDto(user.getUser_id(),
                           user.getUser_name(),
                            user.getUser_login(),
                            user.getUser_password(),
                            user.getUser_privilege());
    }
}
