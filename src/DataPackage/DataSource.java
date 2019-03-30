package DataPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource{

    public static final String SQCONN = "jdbc:sqlite:DatabaseLibrary.sqlite";
    public static Connection getConnection() throws SQLException {

        try {
            return DriverManager.getConnection(SQCONN);

        } catch(Exception e){
            e.printStackTrace();

        }
        return null;
    }


}



