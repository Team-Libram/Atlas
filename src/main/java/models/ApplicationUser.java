package models;

import consts.UserType;

public class ApplicationUser {
    private String id;
    private String username;
    private String name;
    private Long age;
    private UserType type;

    public ApplicationUser(String username, String name, Long age, UserType type) {
        this.username = username;
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public ApplicationUser(String id, String username, String name, Long age, UserType type) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
