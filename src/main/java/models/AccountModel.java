package models;

import consts.UserType;
import entities.identity.Account;

public class AccountModel {
    private int id;
    private String username;
    private String name;
    private Long age;
    private UserType type;

    public AccountModel(String username, String name, Long age, UserType type) {
        this.username = username;
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public AccountModel(int id, String username, String name, Long age, UserType type) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public static AccountModel from(Account account) {
        return new AccountModel(account.getId(), account.getUsername(), account.getName(), account.getAge(), account.getType());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
