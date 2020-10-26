package managers;

import annotations.Authorize;
import consts.StatusCodes;
import consts.UserType;
import consts.Utils;
import entities.identity.Account;
import globals.Globals;
import models.ApplicationUser;
import models.IdentityResult;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IdentityManager {
    public final Map<String, String> sessionStore;

    public IdentityManager() {
        this.sessionStore = new HashMap<>();
    }
    public IdentityResult create(ApplicationUser user, String password) {
        IdentityResult isAccountValid = validateAccount(user);
        IdentityResult isPasswordValid = validatePassword(password);
        if (!isAccountValid.succeeded) {
            return isAccountValid;
        }

        if (!isPasswordValid.succeeded) {
            return isPasswordValid;
        }

        Account account = new Account();
        account.setUsername(user.getUsername());
        account.setPassword(password);
        account.setType(user.getType());
        account.setAge(user.getAge());
        account.setName(user.getName());

        try {
            Globals.entityManager.getTransaction().begin();
            Globals.entityManager.persist(account);
            Globals.entityManager.getTransaction().commit();

            return IdentityResult.Success();
        } catch (Exception e) {
            return IdentityResult.Failure(StatusCodes.UnknownError, e.getLocalizedMessage());
        }
    }

    public IdentityResult login(String username, String password) {
        try {
            ApplicationUser user = this.getUserByUsername(username);
            if (user == null) {
                return IdentityResult.Failure(StatusCodes.NoSuchUserError, "User with the provided username was not found.");
            }

            if (this.checkPassword(user, password)) {
                this.signIn(user);
                return IdentityResult.Success();
            } else {
                return IdentityResult.Failure(StatusCodes.InvalidPasswordError, "Provided password is incorrect.");
            }
        } catch (Exception e) {
            return IdentityResult.Failure(StatusCodes.UnknownError, e.getLocalizedMessage());
        }
    }

    private IdentityResult validateAccount(ApplicationUser user) {
        if (user == null) {
            return IdentityResult.Failure(StatusCodes.InvalidAccountError, "The provided account object is null");
        } else if (user.getUsername() == null || user.getUsername().length() < 4) {
            return IdentityResult.Failure(StatusCodes.InvalidAccountError, "The provided account username must be at least 4 characters long");
        } else if (user.getType() == null || (user.getType() != UserType.Administrator && user.getType() != UserType.Operator && user.getType() != UserType.Reader)) {
            System.out.println(user.getType());
            System.out.println(UserType.Administrator);
            return IdentityResult.Failure(StatusCodes.InvalidAccountError, "The provided account type is invalid");
        }

        return IdentityResult.Success();
    }

    private void signIn(ApplicationUser user) {
        this.sessionStore.put(UUID.randomUUID().toString(), user.getId());
    }

    private IdentityResult validatePassword(String password) {
        if (password == null) {
            return IdentityResult.Failure(StatusCodes.InvalidPasswordError, "The provided password is null");
        } else if (password.length() < 5) {
            return IdentityResult.Failure(StatusCodes.InvalidPasswordError, "The provided password must be at least 5 characters long");
        }

        return IdentityResult.Success();
    }

    private Account getUserById(String id) {
        Query query = Globals.entityManager.createQuery("select account from Account account where account.id = :id");
        query.setParameter("id", Integer.parseInt(id));

        return (Account) query.getSingleResult();
    }

    private ApplicationUser getUserByUsername(String username) {
        Query query = Globals.entityManager.createQuery("select account from Account account where account.username = :username");
        query.setParameter("username", username);
        Account account = (Account) query.getSingleResult();
        if (account == null) {
            return null;
        }

        return new ApplicationUser(account.getId(), account.getUsername(), account.getName(), account.getAge(), account.getType());
    }

    private boolean checkPassword(ApplicationUser user, String password) {
        if (user == null || password == null) {
            return false;
        }

        Account account = this.getUserById(user.getId());
        String hashedPassword = Utils.getSHA256SecurePassword(password);

        return account.getPassword().equals(hashedPassword);
    }
}
