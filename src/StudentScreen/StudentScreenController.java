package StudentScreen;


import DataPackage.Book;
import DataPackage.DataSource;
import LoginScreen.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private Label errorLabel;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    private MenuItem contextBorrow;

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


            int i=0;
            //Populating books table
            while(booksRes.next()){

                String isBorrowed = "True";
                if(booksRes.getString(5).equals("none")){
                    isBorrowed = "False";
                }
                obBook.add(new Book(booksRes.getInt(1)
                        ,booksRes.getString(2)
                        , booksRes.getString(3)
                        , isBorrowed));

                if(booksRes.getString(5).equals(name)){

                }

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

    @FXML
    void handleContextBorrow(ActionEvent event) {

        //Getting the row to be updated
        Book borBook = tableBooks.getSelectionModel().getSelectedItem();

        //Incase no one has borrowed the book
        if(borBook.getStudentBor().equals("False")){

            borBook.setStudentBor(name);

        }
        else{
            System.out.println("Book is already borrowed!");
            errorLabel.setText("Book is already borrowed");
            errorLabel.setOpacity(0.4);
            return;
        }

        //Updating Database
        try{

            //Retriving the count of students
            PreparedStatement studentStat = DataSource.getConnection()
                    .prepareStatement("SELECT * FROM Students WHERE Username=?");

            studentStat.setString(1,name);

            ResultSet studentResult = studentStat.executeQuery();

            int booksCountTemp = studentResult.getInt(6);
            System.out.println(studentResult.getInt(6));

            //Checking if more than three borrows
            if(studentResult.getInt(6)>=3){
                errorLabel.setText("Maximum of three borrows is allowed");
                errorLabel.setOpacity(0.4);
                studentStat.close();
                studentResult.close();
                return;
            }

            studentStat.close();
            //Updating number of borrows
            PreparedStatement updateCount = DataSource.getConnection()
                    .prepareStatement("UPDATE Students SET(countBooks)=(?) WHERE Students.Username=?");

            updateCount.setInt(1,booksCountTemp+1);
            updateCount.setString(2,name);
            updateCount.executeUpdate();
            updateCount.close();





            PreparedStatement statment
                    = DataSource.getConnection()
                    .prepareStatement("UPDATE Books SET(StudentBor)=(?) WHERE Books.ISBN=?");

            statment.setString(1,borBook.getStudentBor());
            statment.setInt(2,borBook.getIsbn());
            statment.executeUpdate();
            tableBooks.refresh();



            statment.close();
        }catch(Exception e){
            e.printStackTrace();
        }



    }









}

