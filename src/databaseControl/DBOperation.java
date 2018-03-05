package databaseControl;

import java.sql.*;

public class DBOperation {
    //
    // applying below trick for insertion of synonyms and functions on database table in one function
    /* insert into synonym table for field1=sname, field2=scolumn, field3=stable continuously
     * similarly for function table for field1=funname, field2=operator, field3=type(either 'select' or 'where'
     * clause continuously.
     * */

    public static boolean insert(String tableName, String field1, String field2, String field3) {

        Connection connection = DatabaseHandler.GetDatabaseConnection();
        boolean status = false;
//        Statement statement = null;
        PreparedStatement preparedStatement = null;
//        final String insertQuery = "Insert into "+tableName+" values ("+value+ ")"; // bad way

        try {
            preparedStatement = connection.prepareStatement("INSERT into ? VALUES (?,?,?)");
//            preparedStatement.executeUpdate();
//            statement = connection.createStatement();

            preparedStatement.setString(1, tableName);
            preparedStatement.setString(2, field1);
            preparedStatement.setString(3, field2);
            preparedStatement.setString(4, field3);

            status = preparedStatement.execute();
            preparedStatement.close();

            // checking incoming content for database
            System.out.println(tableName + " and " + field1 + " and " + field2 + " and " + field3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;

    }

    public static boolean update(String table, String mode, String column) {
        Connection connection = DatabaseHandler.GetDatabaseConnection();
        boolean status = false;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE ? SET ? WHERE ?");
//            System.out.println(preparedStatement);
            preparedStatement.setString(1,table);
            preparedStatement.setString(2,mode);
            preparedStatement.setString(3,column);
            int count = preparedStatement.executeUpdate();
            if (count > 0 ) {
                status = true;
            }
//            status = preparedStatement.execute();
//            return status;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    // deleleting row from table
    public static boolean DeleteRow(String table, String url) {
        boolean status = false;
        Connection connection = DatabaseHandler.GetDatabaseConnection();
        String query = "delete from " +table + " where " + url;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            int row = 0;
            row = preparedStatement.executeUpdate();
            if (row>0) {
                status = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

}