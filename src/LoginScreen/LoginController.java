package LoginScreen;

import DataPackage.DataSource;
import StudentScreen.StudentScreenController;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class LoginController {
    @FXML
    protected JFXTextField userField;

    @FXML
    private JFXPasswordField PasswordField;

    @FXML
    private Button LoginButton;

    @FXML
    private Label invalidLabel;

    @FXML
    void handLogin(ActionEvent event) {
        try{
            //SQLITE DATABASE actions
            Connection conn = DataSource.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM Members WHERE Members.Name = ? and Members.Password = ?");


            //Getting names and password of the user passed
            String name = userField.getText();
            String pass = PasswordField.getText();
            StudentScreenController.setName(name);


            statement.setString(1,name);
            statement.setString(2,pass);

            ResultSet results = statement.executeQuery();

            //Checking in students table
            ResultSet resultsStudents = conn.prepareStatement("SELECT * FROM Students").executeQuery();
            while(resultsStudents.next()){

                if(name.equals(resultsStudents.getString(1)) &&
                        pass.equals(resultsStudents.getString(5))){
                    //New scene
                    Pane root = FXMLLoader.load(getClass().getResource("/StudentScreen/StudentScreenGUI.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);

                    //closing old Scene and opening the new Scene
                    Stage currentStage = (Stage)LoginButton.getScene().getWindow();
                    currentStage.close();

                    stage.setTitle("Student Page");
                    stage.setResizable(false);
                    stage.getIcons().add(new Image("/book.png"));
                    stage.show();
                    resultsStudents.close();
                    return;
                }

            }

            if(!results.next()){
                System.out.println("Undefined User");
                invalidLabel.setOpacity(0.5);
                return;
            }

            else{
                System.out.println("Acess Granted");
                if(pass.equals("admin")){

                    Pane root = FXMLLoader.load(getClass().getResource("/AdminScreen/AdminScreenGUI.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);

                    //closing old scene, opening new scene
                    Stage Currentstage = (Stage)LoginButton.getScene().getWindow();
                    Currentstage.close();

                    stage.setResizable(false);
                    stage.setTitle("Admin Page");
                    stage.getIcons().add(new Image("/book.png"));
                    stage.show();

                }
                else {

                    //New scene
                    Pane root = FXMLLoader.load(getClass().getResource("/StudentScreen/StudentScreenGUI.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);

                    //closing old Scene and opening the new Scene
                    Stage currentStage = (Stage)LoginButton.getScene().getWindow();
                    currentStage.close();

                    stage.setTitle("Student Page");
                    stage.setResizable(false);
                    stage.getIcons().add(new Image("/book.png"));
                    stage.show();
                }
                statement.close();
                results.close();
                return;
            }






        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    void handleSignUp(ActionEvent event) {
        try{
            Pane root = FXMLLoader.load(getClass().getResource("/SignupScreen/SignupGUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            //closing old Scene and opening the new Scene
            Stage currentStage = (Stage)LoginButton.getScene().getWindow();
            currentStage.close();
            stage.setResizable(false);
            stage.setTitle("Signup Page");
            stage.getIcons().add(new Image("/book.png"));
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();

        }
    }
}
