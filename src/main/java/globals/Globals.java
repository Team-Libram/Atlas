package globals;

import javax.persistence.EntityManager;

public class Globals {
    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static void setEntityManager(EntityManager entityManager) {
        Globals.entityManager = entityManager;
    }
}
