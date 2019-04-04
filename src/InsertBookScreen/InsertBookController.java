package InsertBookScreen;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InsertBookController implements Initializable {


    @FXML
    private JFXTextField fieldISBN;

    @FXML
    private JFXTextField fieldTitle;

    @FXML
    private JFXTextField fieldAuthor;

    @FXML
    private JFXTextField fieldImageURL;

    @FXML
    private ImageView imageBox;

    @FXML
    void handleImagePreview(ActionEvent event) {

        String link = fieldImageURL.getText();
        Image img = new Image(link);

        imageBox.setImage(img);


    }

    @FXML
    void handleInsertBook(ActionEvent event) {

        try{

            Pane root = FXMLLoader.load(getClass().getResource("/InsertBookScreen/InsertBookGUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Insert A book");
            stage.setResizable(false);
            stage.show();

        }
        catch(Exception e){

            e.printStackTrace();

        }



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
