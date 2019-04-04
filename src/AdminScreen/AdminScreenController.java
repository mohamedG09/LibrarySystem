package AdminScreen;



import DataPackage.Book;
import DataPackage.DataSource;
import DataPackage.Student;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
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

    @FXML
    private TableView<Book> tableBooks;

    ObservableList<Book> obBook = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Book, String> colISBN;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colBorrower;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {


            //Copying from Database to Students Table
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

            //Copying from DB into books table

            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBorrower.setCellValueFactory(new PropertyValueFactory<>("studentBor"));
            colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));

            ResultSet booksRes = DataSource.getConnection().prepareStatement("SELECT * FROM Books").executeQuery();

            //Populating books table
            while(booksRes.next()){

                obBook.add(new Book(booksRes.getInt(1)
                        ,booksRes.getString(2)
                        , booksRes.getString(3)
                        , booksRes.getString(5)));


            }
            tableBooks.setItems(obBook);


            booksRes.close();
            resultSet.close();
            conn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }


    @FXML
    void handleInsertBook(ActionEvent event) {

        try {
            Pane root = FXMLLoader.load(getClass().getResource("/InsertBookScreen/InsertBookGUI.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Insert A book");
            stage.setResizable(false);
            stage.getIcons().add(new Image("/book.png"));
            stage.show();
        }
        catch(IOException e){

            e.printStackTrace();

        }


    }

    @FXML
    void handleRefreshButton(ActionEvent event) {
        tableBooks.getItems().clear();

        try{
            ResultSet booksRes = DataSource.getConnection().prepareStatement("SELECT * FROM Books").executeQuery();

            //Populating books table
            while(booksRes.next()){

                obBook.add(new Book(booksRes.getInt(1)
                        ,booksRes.getString(2)
                        , booksRes.getString(3)
                        , booksRes.getString(5)));


            }
            tableBooks.setItems(obBook);

            booksRes.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }









}
