package InsertBookScreen;

import DataPackage.Book;
import DataPackage.DataSource;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InsertBookController  {


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
    private Label errorLabel;

    @FXML
    void handleImagePreview(ActionEvent event) {

        String link = fieldImageURL.getText();
        Image img = new Image(link);

        imageBox.setImage(img);


    }

    @FXML
    void handleInsertBook(ActionEvent event) {
        try {
            //This will copy data from database
            ArrayList<Integer> isbnList = new ArrayList<>();
            ResultSet booksResult = DataSource.getConnection().prepareStatement("SELECT * FROM Books").executeQuery();

            while (booksResult.next()) {

                isbnList.add(booksResult.getInt(1));

            }

            //Checking if anything is empty
            if (
                    fieldAuthor.getText().equals("") ||
                            fieldImageURL.getText().equals("") ||
                            fieldISBN.getText().equals("") ||
                            fieldTitle.getText().equals("")
            ) {

                errorLabel.setText("Field/s left empty");
                errorLabel.setStyle("-fx-background-color: #cf0000; -fx-opacity: 0.4;");
                return;

            }


            //This block will handle the input
            Book temp = new Book();
            temp.setAuthor(fieldAuthor.getText());
            try{
                temp.setIsbn(Integer.parseInt(fieldISBN.getText() + ""));
            }
            catch(Exception e){
                errorLabel.setText("Illegal ISBN");
                errorLabel.setStyle("-fx-background-color: #cf0000; -fx-opacity: 0.4;");
                return;
            }
            temp.setPicLink(fieldImageURL.getText());
            temp.setTitle(fieldTitle.getText());

            booksResult.close();


            //Checking if the book already exist by ISBN
            for (Integer x : isbnList) {
                if (x == temp.getIsbn()) {
                    errorLabel.setText("ISBN already Exists");
                    errorLabel.setStyle("-fx-background-color: #cf0000; -fx-opacity: 0.4;");
                    return;
                }

            }

            //After all checks update database
            PreparedStatement statment = DataSource.getConnection().prepareStatement("INSERT INTO books(ISBN,Title,Author,PicLink) VALUES(?,?,?,?)");
            statment.setInt(1, temp.getIsbn());
            statment.setString(2, temp.getTitle());
            statment.setString(3, temp.getAuthor());
            statment.setString(4, temp.getPicLink());
            statment.executeUpdate();

            errorLabel.setText("Book Inserted Successfully");
            errorLabel.setStyle("-fx-background-color: #00ff7f; -fx-opacity: 0.4;");



            statment.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
