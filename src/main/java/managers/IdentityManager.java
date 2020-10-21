package managers;

import entities.identity.Account;
import entities.identity.Session;
import models.ApplicationUser;
import models.IdentityResult;

import java.util.List;

public class IdentityManager {
    private List<Session> sessionStore;

    public IdentityResult create(ApplicationUser user, String password) {
        Account account = new Account();

    }

    private IdentityResult validateAccount(ApplicationUser user) {
        if (user == null) {
            return IdentityResult.Failure<-1>("The provided account object is null");
        }
    }
}
