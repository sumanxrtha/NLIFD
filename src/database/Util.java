package database;

public class Util {
    // database connection information
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_NAME = "finalproject";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false";
    public static final String DB_USER = "root";
    public static final String DB_PASS = "sujan";


    // Users database column details
    public static final String USER_TABLE = "validuser";
//    public static final String USER_ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

}
