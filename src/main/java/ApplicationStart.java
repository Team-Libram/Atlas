import consts.UserType;
import entities.identity.Account;
import globals.Globals;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

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

        Globals.getEntityManager().getTransaction().begin();

        Account u = new Account();
        u.setUsername("admin");
        u.setType(UserType.Administrator);
        u.setPassword("admin");

        Globals.getEntityManager().persist(u);

        Globals.getEntityManager().getTransaction().commit();

        Globals.getEntityManager().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}