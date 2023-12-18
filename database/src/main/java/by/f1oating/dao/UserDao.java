package by.f1oating.dao;

import by.f1oating.dto.UserFilter;
import by.f1oating.entity.User;
import by.f1oating.exception.DataBaseException;
import by.f1oating.util.ConnectionManager;
import by.f1oating.validator.Error;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDao implements Dao<User, UserFilter>{

    private final static UserDao INSTANCE = new UserDao();
    private final static String SAVE_SQL = """
            INSERT INTO data.users(user_name, user_login, user_password, user_privilege)
            VALUES(?, ?, ?, (SELECT privilege_id FROM data.privileges WHERE privilege_name=?))
            """;
    private final static String DELETE_SQL = """
            DELETE FROM data.users WHERE user_id=?
            """;
    private final static String UPDATE_SQL = """
            UPDATE data.users
            """;
    private final static String FIND_BY_ID_SQL = """
            SELECT user_id, user_name, user_login, user_password,
            (SELECT privilege_name FROM data.privileges WHERE privilege_id=user_privilege) as user_privilege
            FROM data.users WHERE user_id=?
            """;
    private final static String FIND_ALL_SQL = """
            SELECT user_id, user_name, user_login, user_password,
            (SELECT privilege_name FROM data.privileges WHERE privilege_id=user_privilege) as user_privilege
            FROM data.users
            """;

    @Override
    public Optional<User> save(User entity){
        try(var connection = ConnectionManager.get();
            var statement = connection.getConnection().prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getUser_name());
            statement.setString(2, entity.getUser_login());
            statement.setString(3, entity.getUser_password());
            statement.setString(4, entity.getUser_privilege());
            statement.execute();

            var keys = statement.getGeneratedKeys();
            if(keys.next()){
                entity.setUser_id(keys.getLong("user_id"));
            }

            return Optional.of(entity);
        } catch (SQLException e) {
            List<Error> erros = new ArrayList<>();
            erros.add(Error.of("login.already.exist", "Login already exist!"));
            throw new DataBaseException(erros);
        }
    }

    @Override
    public boolean delete(Long id) {
        try(var connection = ConnectionManager.get();
            var statement = connection.getConnection().prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean update(UserFilter filter, Long id) {
        List<String> parameters = new ArrayList<>();
        List<String> setSql = new ArrayList<>();

        if(filter.user_name() != null){
            parameters.add(filter.user_name());
            setSql.add("user_name=?");
        }
        if(filter.user_login() != null){
            parameters.add(filter.user_login());
            setSql.add("user_login=?");
        }
        if(filter.user_password() != null){
            parameters.add(filter.user_password());
            setSql.add("user_password=?");
        }
        if(filter.user_privilege() != null){
            parameters.add(filter.user_privilege());
            setSql.add("user_privilege=(SELECT privilege_id FROM data.privileges WHERE privilege_name=?)");
        }

        if(parameters.isEmpty()){
            return true;
        }

        var req = setSql.stream().collect(Collectors.joining(
                ", ",
                " SET ",
                " WHERE user_id=?"
        ));

        String sql = UPDATE_SQL + req;

        try(var connection = ConnectionManager.get();
            var statement = connection.getConnection().prepareStatement(sql)) {

            for(int i = 0; i < parameters.size(); i++){
                statement.setString(i + 1, parameters.get(i));
            }
            statement.setLong(parameters.size() + 1, id);
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try(var connection = ConnectionManager.get(); var statement = connection.getConnection().prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            var res = statement.executeQuery();
            User user = null;
            if(res.next()){
                user = new User(res.getLong("user_id"),
                        res.getString("user_name"),
                        res.getString("user_login"),
                        res.getString("user_password"),
                        res.getString("user_privilege"));
            }

            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Optional<User>> findAll(UserFilter filter) {
        List<String> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        List<Optional<User>> users = new ArrayList<>();

        if(filter.user_name() != null){
            parameters.add(filter.user_name());
            whereSql.add("user_name=?");
        }
        if(filter.user_login() != null){
            parameters.add(filter.user_login());
            whereSql.add("user_login=?");
        }
        if(filter.user_password() != null){
            parameters.add(filter.user_password());
            whereSql.add("user_password=?");
        }
        if(filter.user_privilege() != null){
            parameters.add(filter.user_privilege());
            whereSql.add("user_privilege=(SELECT privilege_id FROM data.privileges WHERE privilege_name=?)");
        }
        if(parameters.size() < 1){
            return null;
        }

        var where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                " WHERE ",
                ""
        ));

        String sql = FIND_ALL_SQL + where;

        try(var connection = ConnectionManager.get(); var statement = connection.getConnection().prepareStatement(sql)) {

            for(int i = 0; i < parameters.size(); i++){
                statement.setString(i + 1, parameters.get(i));
            }

            var res = statement.executeQuery();

            while(res.next()){
                users.add(Optional.of(new User(res.getLong("user_id"),
                        res.getString("user_name"),
                        res.getString("user_login"),
                        res.getString("user_password"),
                        res.getString("user_privilege"))));
            }

            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    private UserDao() {
    }

    public static UserDao getInstance(){
        return INSTANCE;
    }

}
