package managers;

import consts.NotAuthorizedException;
import consts.StatusCode;
import consts.UserType;
import consts.Utils;
import entities.identity.Account;
import javassist.NotFoundException;
import models.AccountModel;
import results.IdentityResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IdentityManager {
    private static Map<String, AccountModel> sessionStore;
    private final DbManager dbManager;

    public IdentityManager(DbManager dbManager) {
        IdentityManager.sessionStore = new HashMap<>();
        this.dbManager = dbManager;
    }

    public IdentityResult create(String session, AccountModel user, String password) throws NotAuthorizedException {
        if (user.getType() == UserType.Operator) {
            IdentityManager.authorize(session, UserType.Administrator);
        } else {
            IdentityManager.authorize(session);
        }

        System.out.println("Started creation of account...");

        IdentityResult isAccountValid = validateAccount(user);
        IdentityResult isPasswordValid = validatePassword(password);

        if (!isAccountValid.succeeded) {
            return isAccountValid;
        }

        if (!isPasswordValid.succeeded) {
            return isPasswordValid;
        }

        Account account = new Account(user.getUsername(), Utils.getSHA256SecurePassword(password), user.getName(), user.getAge(), user.getType());

        try {
            this.dbManager.insertEntity(account);

            System.out.println("Account creation successful.");
            return IdentityResult.Success();
        } catch (Exception e) {
            return IdentityResult.Failure(StatusCode.UnexpectedError, e.getLocalizedMessage());
        }
    }

    public void removeAccount(String session, int userId) throws NotFoundException, NotAuthorizedException {
        IdentityManager.authorize(session, UserType.Operator);

        System.out.println("Started deletion of account...");

        Account accountToDelete = dbManager.getUserById(userId);
        if (accountToDelete == null) {
            throw new NotFoundException("No user with the provided ID was found.");
        }

        this.dbManager.removeEntity(accountToDelete);
    }

    public IdentityResult signIn(String username, String password) {
        try {
            System.out.println("Attempted sign in of account " + username + "...");

            AccountModel user = this.dbManager.getUserByUsername(username);
            if (user == null) {
                return IdentityResult.Failure(StatusCode.NoSuchUserError);
            }

            if (this.checkPassword(user, password)) {
                String sessionId = UUID.randomUUID().toString();
                sessionStore.put(sessionId, user);

                System.out.println("Login of account " + username + " successful.");
                return IdentityResult.Success(sessionId);
            } else {
                return IdentityResult.Failure(StatusCode.InvalidPasswordError);
            }
        } catch (Exception e) {
            return IdentityResult.Failure(StatusCode.UnexpectedError, e.getLocalizedMessage());
        }
    }

    public void signOut(String session) {
        sessionStore.remove(session);
    }

    public static void authorize(String session) throws NotAuthorizedException {
        if (session.equals("seed")) return;

        boolean isUserAuthorized = sessionStore.containsKey(session);
        if (!isUserAuthorized) {
            throw new NotAuthorizedException();
        }
    }

    public static void authorize(String session, UserType role) throws NotAuthorizedException {
        if (session.equals("seed")) return;

        boolean isUserAuthorized = sessionStore.containsKey(session) && sessionStore.get(session).getType().compareTo(role) >= 0;
        if (!isUserAuthorized) {
            throw new NotAuthorizedException();
        }
    }

    private IdentityResult validateAccount(AccountModel user) {
        if (user == null) {
            return IdentityResult.Failure(StatusCode.InvalidAccountError, "The provided account object is null");
        } else if (user.getUsername() == null || user.getUsername().length() < 4) {
            return IdentityResult.Failure(StatusCode.InvalidAccountError, "The provided account username must be at least 4 characters long");
        } else if (user.getType() == null || !UserType.contains(user.getType().name())) {
            return IdentityResult.Failure(StatusCode.InvalidAccountError, "The provided account type is invalid");
        }

        return IdentityResult.Success();
    }

    private IdentityResult validatePassword(String password) {
        if (password == null) {
            return IdentityResult.Failure(StatusCode.InvalidPasswordError, "The provided password is null");
        } else if (password.length() < 5) {
            return IdentityResult.Failure(StatusCode.InvalidPasswordError, "The provided password must be at least 5 characters long");
        }

        return IdentityResult.Success();
    }

    private boolean checkPassword(AccountModel user, String password) {
        if (user == null || password == null) {
            return false;
        }

        Account account = this.dbManager.getUserById(user.getId());
        String hashedPassword = Utils.getSHA256SecurePassword(password);

        return account.getPassword().equals(hashedPassword);
    }
}