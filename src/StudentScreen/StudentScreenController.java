package StudentScreen;


import DataPackage.Book;
import DataPackage.DataSource;
import LoginScreen.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentScreenController implements Initializable  {
    @FXML
    private Label LabelStudentName;

    @FXML
    private Label LabelUserName;

    @FXML
    private Label LabelEmail;

    protected static String name;

    @FXML
    private TableView<Book> tableBooks;

    @FXML
    private TableColumn<Book, String> colISBN;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colBorrower;

    ObservableList<Book> obBook = FXCollections.observableArrayList();

    public static void setName(String name_){
        name = name_;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            PreparedStatement stud = DataSource.getConnection()
                    .prepareStatement("SELECT * FROM Students WHERE Students.Username=?");

            //Obtaining details of logged in student
            stud.setString(1,name);
            ResultSet chosen = stud.executeQuery();

            //Updating labels
            LabelStudentName.setText(LabelStudentName.getText()+" "+chosen.getString(2)
                +" "+chosen.getString(3));
            LabelEmail.setText(LabelEmail.getText()+" "+chosen.getString(4));
            LabelUserName.setText(LabelUserName.getText()+" "+chosen.getString(1));


            //Copying books into table
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colBorrower.setCellValueFactory(new PropertyValueFactory<>("studentBor"));
            colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));

            ResultSet booksRes = DataSource.getConnection().prepareStatement("SELECT * FROM Books").executeQuery();

            //Populating books table
            while(booksRes.next()){

                String isBorrowed = "True";
                if(booksRes.getString(5)==null){
                    isBorrowed = "False";
                }
                obBook.add(new Book(booksRes.getInt(1)
                        ,booksRes.getString(2)
                        , booksRes.getString(3)
                        , isBorrowed));


            }
            tableBooks.setItems(obBook);


            booksRes.close();
            stud.close();
            chosen.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

//TODO Highlight books borrowed
