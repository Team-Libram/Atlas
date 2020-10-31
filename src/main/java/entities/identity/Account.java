package entities.identity;

import consts.UserType;
import consts.Utils;

import javax.persistence.*;

@Entity
@Table
public class Account implements IEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column
    private Long age;

    @Column(nullable = false)
    private UserType type;

    public Account() {
    }

    public Account(String username, String password, String name, Long age, UserType type) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.type = type;
    }

    public String getId() {
        return id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Utils.getSHA256SecurePassword(password);
    }
}