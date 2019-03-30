module LibrarySystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.swing;
    requires com.jfoenix;
    requires java.sql;

    opens AdminScreen;
    opens DataPackage;
    opens LoginScreen;
    //opens SignupScreen;
    opens StudentScreen;

}