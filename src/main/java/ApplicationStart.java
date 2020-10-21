import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

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
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("library-persistence-unit");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//
//        User u = new User();
//
//        u.setName("Nikolay");
//        u.setAge(69L);
//        em.persist(u);
//        u = null;
//
//        em.getTransaction().commit();
//
//        System.out.println(ApplicationStart.getUser(em));
//
//        em.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}