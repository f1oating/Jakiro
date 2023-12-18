package by.f1oating.entity;

import java.util.Objects;

public class User {

    private Long user_id;
    private String user_name;
    private String user_login;
    private String user_password;
    private String user_privilege;

    public User() {
    }

    public User(String user_name, String user_login, String user_password, String user_privilege) {
        this.user_name = user_name;
        this.user_login = user_login;
        this.user_password = user_password;
        this.user_privilege = user_privilege;
    }

    public User(Long user_id, String user_name, String user_login, String user_password, String user_privilege) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_login = user_login;
        this.user_password = user_password;
        this.user_privilege = user_privilege;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_privilege() {
        return user_privilege;
    }

    public void setUser_privilege(String user_privilege) {
        this.user_privilege = user_privilege;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(user_id, user.user_id) && Objects.equals(user_name, user.user_name) && Objects.equals(user_login, user.user_login) && Objects.equals(user_password, user.user_password) && Objects.equals(user_privilege, user.user_privilege);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, user_name, user_login, user_password, user_privilege);
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_login='" + user_login + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_privilege=" + user_privilege +
                '}';
    }
}
