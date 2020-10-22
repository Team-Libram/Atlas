import consts.UserType;
import globals.Globals;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import managers.IdentityManager;
import models.ApplicationUser;
import models.IdentityResult;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ApplicationStart extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Layout.fxml"));
        primaryStage.setTitle("Atlas library");

        primaryStage.setMaximized(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        ApplicationStart.initializeHibernate();
    }

    private static void initializeHibernate() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("library-persistence-unit");
        Globals.setEntityManager(emf.createEntityManager());

        ApplicationUser user = new ApplicationUser();
        user.setUsername("admin");
        user.setName("Peter");
        user.setAge(21L);
        user.setType(UserType.Administrator);


        IdentityManager identityManager = new IdentityManager();
        IdentityResult result = identityManager.create(user, "admin");
    }

    public static void main(String[] args) {
        launch(args);
    }
}