package SignupScreen;

import DataPackage.DataSource;
import DataPackage.Student;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXPasswordField PasswordField;

    @FXML
    private JFXPasswordField repeatPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private JFXTextField UsernameField;

    @FXML
    private Label errorLabel;

    @FXML
    void handleSignUp(ActionEvent event) {

        try {

            //If not the same password then stop the operation
            if(!(repeatPasswordField.getText().equals(PasswordField.getText()))){

                errorLabel.setText("Passwords are not matching");
                errorLabel.setOpacity(0.4);
                return;

            }

            Student insertStudent = new Student();
            insertStudent.setUserName(UsernameField.getText());
            insertStudent.setPassword(PasswordField.getText());
            insertStudent.setEmail(emailField.getText());
            insertStudent.setFirstName(firstNameField.getText());
            insertStudent.setLastName(lastNameField.getText());

            Connection conn = DataSource.getConnection();
            ArrayList<Student> students = new ArrayList<>();

            //Copying the database table members into a list
            PreparedStatement statementRet = conn.prepareStatement("SELECT * FROM Students");
            ResultSet resultsRet = statementRet.executeQuery();

            while(resultsRet.next()){

                Student temp = new Student();

                temp.setUserName(resultsRet.getString(1));
                temp.setFirstName(resultsRet.getString(2));
                temp.setLastName(resultsRet.getString(3));
                temp.setEmail(resultsRet.getString(4));
                temp.setPassword(resultsRet.getString(5));

                students.add(temp);
            }

            //checking for duplications of username
            for(Student stu: students){

                if(insertStudent.getUserName().equals(stu.getUserName())){
                   errorLabel.setText("Username already exists");
                   errorLabel.setOpacity(0.4);
                   return;
                }

            }

            PreparedStatement insertStatment =
                    conn.prepareStatement
                            ("INSERT INTO Students(Username,FirstName,LastName,Email,Password) VALUES(?,?,?,?,?) ");

            insertStatment.setString(1,insertStudent.getUserName());
            insertStatment.setString(2,insertStudent.getFirstName());
            insertStatment.setString(3,insertStudent.getLastName());
            insertStatment.setString(4,insertStudent.getEmail());
            insertStatment.setString(5,insertStudent.getPassword());
            insertStatment.executeUpdate();

            insertStatment.close();
            statementRet.close();
            resultsRet.close();

            Pane root = FXMLLoader.load(getClass().getResource("/LoginScreen/LoginGUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            //closing old scene, opening new scene
            Stage Currentstage = (Stage)signUpButton.getScene().getWindow();
            Currentstage.close();

            stage.setTitle("Login");
            stage.setResizable(false);
            stage.show();




        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
