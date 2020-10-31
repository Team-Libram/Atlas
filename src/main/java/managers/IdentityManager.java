package managers;

import consts.StatusCode;
import consts.UserType;
import consts.Utils;
import entities.identity.Account;
import models.ApplicationUser;
import models.IdentityResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IdentityManager {
    public final Map<String, String> sessionStore;
    private final DbManager dbManager;

    public IdentityManager(DbManager dbManager) {
        this.sessionStore = new HashMap<>();
        this.dbManager = dbManager;
    }

    public IdentityResult create(ApplicationUser user, String password) {
        System.out.println("Started creation of account...");

        IdentityResult isAccountValid = validateAccount(user);
        IdentityResult isPasswordValid = validatePassword(password);
        if (!isAccountValid.succeeded) {
            return isAccountValid;
        }

        if (!isPasswordValid.succeeded) {
            return isPasswordValid;
        }

        Account account = new Account(user.getUsername(), password, user.getName(), user.getAge(), user.getType());

        try {
            this.dbManager.insertEntity(account);

            System.out.println("Account creation successful.");
            return IdentityResult.Success();
        } catch (Exception e) {
            return IdentityResult.Failure(StatusCode.UnexpectedError, e.getLocalizedMessage());
        }
    }

    public IdentityResult login(String username, String password) {
        try {
            System.out.println("Attempted login of account " + username + "...");

            ApplicationUser user = this.dbManager.getUserByUsername(username);
            if (user == null) {
                return IdentityResult.Failure(StatusCode.NoSuchUserError);
            }

            if (this.checkPassword(user, password)) {
                this.signIn(user);

                System.out.println("Login of account " + username + " successful.");
                return IdentityResult.Success();
            } else {
                return IdentityResult.Failure(StatusCode.InvalidPasswordError);
            }
        } catch (Exception e) {
            return IdentityResult.Failure(StatusCode.UnexpectedError, e.getLocalizedMessage());
        }
    }

    private IdentityResult validateAccount(ApplicationUser user) {
        if (user == null) {
            return IdentityResult.Failure(StatusCode.InvalidAccountError, "The provided account object is null");
        } else if (user.getUsername() == null || user.getUsername().length() < 4) {
            return IdentityResult.Failure(StatusCode.InvalidAccountError, "The provided account username must be at least 4 characters long");
        } else if (user.getType() == null || !UserType.contains(user.getType().name())) {
            return IdentityResult.Failure(StatusCode.InvalidAccountError, "The provided account type is invalid");
        }

        return IdentityResult.Success();
    }

    private void signIn(ApplicationUser user) {
        this.sessionStore.put(UUID.randomUUID().toString(), user.getId());
    }

    private IdentityResult validatePassword(String password) {
        if (password == null) {
            return IdentityResult.Failure(StatusCode.InvalidPasswordError, "The provided password is null");
        } else if (password.length() < 5) {
            return IdentityResult.Failure(StatusCode.InvalidPasswordError, "The provided password must be at least 5 characters long");
        }

        return IdentityResult.Success();
    }

    private boolean checkPassword(ApplicationUser user, String password) {
        if (user == null || password == null) {
            return false;
        }

        Account account = this.dbManager.getUserById(user.getId());
        String hashedPassword = Utils.getSHA256SecurePassword(password);

        return account.getPassword().equals(hashedPassword);
    }
}