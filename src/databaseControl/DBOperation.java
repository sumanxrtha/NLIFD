package databaseControl;

import java.sql.*;

public class DBOperation {

    public static boolean insert(String tableName, String value) {
        Connection connection = DatabaseHandler.GetDatabaseConnection();
        boolean status = false;
//        Statement statement = null;
        PreparedStatement preparedStatement = null;


        String insertQuery = "Insert into "+tableName+" values ("+value+ ")";

        try {
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.executeUpdate();
//            statement = connection.createStatement();
//            status = statement.execute(insertQuery);
//            preparedStatement = connection.prepareStatement("INSERT into synonym(sname,scolumn,stable) VALUES(?,?,?)");
//            preparedStatement.setString(1,synonymName);
//            preparedStatement.setString(2,columnName);
//            preparedStatement.setString(3,tableName);
//            preparedStatement.executeQuery();
//            status = preparedStatement.execute();
//            preparedStatement.close();
            status = true;

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