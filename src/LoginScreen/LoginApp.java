package LoginScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginGui.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Login Form");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Login");
        stage.getIcons().add(new Image("/book.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
