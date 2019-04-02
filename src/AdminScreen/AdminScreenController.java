package AdminScreen;



import DataPackage.DataSource;
import DataPackage.Student;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminScreenController implements Initializable {

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private JFXTabPane TabPane;

    @FXML
    private TableColumn<Student, String> userCol;

    @FXML
    private TableColumn<Student, String> firstNameCol;

    @FXML
    private TableColumn<Student, String> LastNameCol;

    @FXML
    private TableColumn<Student, String> EmailCol;

    ObservableList<Student> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            Connection conn = DataSource.getConnection();
            ResultSet resultSet = conn.prepareStatement("SELECT * FROM Students").executeQuery();

            while(resultSet.next()){

                Student temp = new Student();
                temp.setUserName(resultSet.getString(1));
                temp.setFirstName(resultSet.getString(2));
                temp.setLastName(resultSet.getString(3));
                temp.setEmail(resultSet.getString(4));

                oblist.add(temp);

            }


            userCol.setCellValueFactory(new PropertyValueFactory<>("UserName"));
            firstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
            LastNameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
            EmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));


            studentTable.setItems(oblist);





            resultSet.close();
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
}
