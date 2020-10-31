import consts.UserType;
import globals.Globals;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import managers.BookManager;
import managers.DbManager;
import managers.IdentityManager;
import models.ApplicationUser;
import models.IdentityResult;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ApplicationStart extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Started main process initialization...");

        Parent root = FXMLLoader.load(getClass().getResource("Layout.fxml"));
        primaryStage.setTitle("Library");

        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        Thread initiatorThread = new Thread(() -> {
            try {
                initializeHibernate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        initiatorThread.setName("Hibernate initiator thread");
        initiatorThread.start();

        System.out.println("Main process initialization successful.");
    }

    private void initializeHibernate() {
        System.out.println("Started initialization of hibernate...");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("library-persistence-unit");
        DbManager dbManager = new DbManager(emf.createEntityManager());
        Globals.identityManager = new IdentityManager(dbManager);
        Globals.bookManager = new BookManager(dbManager);

        dbManager.seed();

        System.out.println("Hibernate initialization successful.");
    }
}