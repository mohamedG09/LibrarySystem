package BookViewer;

import DataPackage.Book;
import DataPackage.DataSource;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;


public class BookViewController implements Initializable {
    private static String bookISBN;

    public static String getBookISBN() {
        return bookISBN;
    }

    public static void setBookISBN(String bookISBN) {
        BookViewController.bookISBN = bookISBN;
    }

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

    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{


            Book temp = new Book();
            PreparedStatement statement = DataSource.getConnection()
                    .prepareStatement("SELECT * FROM Books WHERE Books.ISBN = ?");

            statement.setString(1,bookISBN);
            ResultSet results = statement.executeQuery();

            temp.setTitle(results.getString(2));
            temp.setIsbn(results.getInt(1));
            temp.setStudentBor(results.getString(5));
            temp.setAuthor(results.getString(3));
            temp.setPicLink(results.getString(4));

            fieldAuthor.setText(temp.getAuthor());
            fieldISBN.setText(temp.getIsbn()+"");
            fieldTitle.setText(temp.getTitle());
            fieldImageURL.setText(temp.getPicLink());

            Image img = new Image(temp.getPicLink());
            imageBox.setImage(img);

            statement.close();
            results.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
