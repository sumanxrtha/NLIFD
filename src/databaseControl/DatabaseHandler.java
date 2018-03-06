package databaseControl;

import javafx.scene.control.Alert;
import java.sql.*;

public class DatabaseHandler {

    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_NAME = "majorproject";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
    public static final String DB_USER = "root";
    public static final String DB_PASS = "sujan";

//    public static final String LOGIN_USER_CHECK_QUERY = "select *from registeredUser where user = ? and pass = ?";

    public static Connection GetDatabaseConnection() {
        Connection connection = null;
//    Connection connection;

        try {
//            driver setup for database
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

//            System.out.println("connection successful");

        } catch (ClassNotFoundException e) {
//            e.getLocalizedMessage();
            e.printStackTrace();
            e.getMessage();

        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Database Connection Error");
            alert.setHeaderText("Database cannot connection in your system");
            alert.setContentText("Error in database connection");
            alert.show();
            e.printStackTrace();
//            e.getLocalizedMessage();
        }

        return connection;
    }

    // close database connection
// always close database connecton after connecting sessions
    public static void CloseConnection(Connection con) {
        try {
            if (con!=null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // is the use is valid or not checking valid user on database

    public static boolean CheckLoginUser(String uname, String pass) {
        Connection connection = GetDatabaseConnection();
//        String checkQuery = "select * from registeredUser where user = ' "+uname+" ' and pass = ' "+pass+" ' ";
        final String checkQuery = "select *from validuser where user = ? and pass = ? ";
        PreparedStatement preparedStatement;
        boolean status = false; //initially false

        try {
            preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setString(1, uname); // dynamically sending username
            preparedStatement.setString(2, pass); // sending password to checkquery statement
            ResultSet resultSet = preparedStatement.executeQuery();

            /* while (resultSet.next()) {
                return status;
            } */

            status = resultSet.next();
            preparedStatement.close();
            return status;
//            DatabaseHandler.CloseConnection(connection);

        } catch (SQLException e) {
//            e.getLocalizedMessage();
            e.printStackTrace();
        }
        return status;

        // closing db connection here
//        connection.close();
    }

}
