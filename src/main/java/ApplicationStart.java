import consts.Genre;
import consts.UserType;
import globals.Globals;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import managers.BookManager;
import managers.IdentityManager;
import models.ApplicationUser;
import models.AtlasResult;
import models.BookModel;
import models.IdentityResult;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ApplicationStart extends Application {
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
        Globals.entityManager = emf.createEntityManager();
        System.out.println("Hibernate initialization successful.");

        System.out.println("Started initial seed...");
        ApplicationUser user = new ApplicationUser("admin", null, 21L, UserType.Administrator);
        //BookModel bookModel = new BookModel("Flight 19", "Grant Finnegan", Genre.Fiction);

        IdentityManager identityManager = new IdentityManager();

        IdentityResult result = identityManager.create(user, "admin");
        if (!result.succeeded) {
            System.out.println(result);
        }

        System.out.println("Initial seed successful.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}