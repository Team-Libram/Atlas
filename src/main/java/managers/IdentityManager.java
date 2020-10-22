package managers;

import consts.UserType;
import entities.identity.Account;
import entities.identity.Session;
import globals.Globals;
import models.ApplicationUser;
import models.IdentityResult;

import java.util.List;

public class IdentityManager {
    private List<Session> sessionStore;

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
            Globals.getEntityManager().getTransaction().begin();
            Globals.getEntityManager().persist(account);
            Globals.getEntityManager().getTransaction().commit();

            return IdentityResult.Success();
        } catch (Exception e) {
            return IdentityResult.Failure(-1, e.getLocalizedMessage());
        }
    }

    private IdentityResult validateAccount(ApplicationUser user) {
        if (user == null) {
            return IdentityResult.Failure(-2, "The provided account object is null");
        } else if (user.getUsername() == null || user.getUsername().length() < 4) {
            return IdentityResult.Failure(-3, "The provided account username must be at least 4 characters long");
        } else if (user.getType() == null
                && user.getType() != UserType.Administrator
                && user.getType() != UserType.Operator
                && user.getType() != UserType.Reader) {
            System.out.println(user.getType());
            System.out.println(UserType.Administrator);
            return IdentityResult.Failure(-4, "The provided account type is invalid");
        }

        return IdentityResult.Success();
    }

    private IdentityResult validatePassword(String password) {
        if (password == null) {
            return IdentityResult.Failure(-5, "The provided password is null");
        } else if (password.length() < 5) {
            return IdentityResult.Failure(-6, "The provided password must be at least 5 characters long");
        }

        return IdentityResult.Success();
    }
}
