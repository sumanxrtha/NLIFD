package database;

import com.sun.org.apache.xml.internal.security.signature.reference.ReferenceSubTreeData;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryParser {

    // list out matched database table name or ocolumn name
    public static ArrayList<String> ListMatchedStems(String stem) {
        ArrayList<String> matchItem = new ArrayList<String>();
        Connection conn = DatabaseHandler.GetDatabaseConnection();
        String query = "Select table_name, column_name from information_schema.columns " +
                " where column_name like '%" +stem.trim() +"%'" + " and table_schema='finalproject'";
        System.out.println("This is my query: " +query);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String table = resultSet.getString("table_name");
                String tableCol = resultSet.getString("column_name");
                matchItem.add(table);
                matchItem.add(tableCol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchItem; // return available list from database, contains tables and columns
    }

    public static boolean CheckStatus(String table, String col) {
        boolean status = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        // check if synonym exists
        ArrayList<String> temp = QueryParser.GetSynonyms(col);
        if (temp.size()>0) {
            table = temp.get(0);
            col = temp.get(1);
        }

        Connection connection = DatabaseHandler.GetDatabaseConnection();
        String query = "select count(*) from information_schema.columns where column_name like '%"
                + col.trim()+"%'" + " and table_name like '%" +table.trim()+"%'"
                + " and table_schema = 'finalproject'";
        try {
            ps = connection.prepareCall(query);
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
            if (count>0)
                status =true;
        } catch (SQLException e) {
            status = false;
            e.printStackTrace();
        }
        return status;
    }

    // getting select and where type of functions
    public static String functionTypeSelectOrWhere(String col) {
        String temp = ""; // this is for return type in this method

        String name = ""; // yesmaa ftype basxa ra yeslai equal garney clause anusaar
        Connection connection = DatabaseHandler.GetDatabaseConnection();
        String query = "select ftype from function where fname like '%" +col.trim() +"%'";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("ftype");
            }
            if (name.equalsIgnoreCase("SELECT")) {
                temp = "SELECT";
            }
            else if (name.equalsIgnoreCase("WHERE")) {
                temp = "WHERE";
            }
            else {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp; // returning either select or where clause
    }

    // this methods gives data type of the given column name
    public static String getDataTypeForGivenColumn(String columnName) {
        String dataType = ""; // initially empty
        Connection connection = DatabaseHandler.GetDatabaseConnection();
        String query = "";
//        PreparedStatement preparedStatement;
//        ResultSet resultSet;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // return
                dataType = rs.getString("data_type"); // yo column label ma vayeko name execute gareko query anusaar hunxa database ko name anusaar hudaina !!
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return  dataType;
    }


    public static ArrayList<String> GetSynonyms(String col) {
        ArrayList<String> temp = new ArrayList<String>();
        Connection conn = DatabaseHandler.GetDatabaseConnection();
        final String QUERY = "select sycolumn, sytable from synonym where syname like '%"+col.trim()+"%'";
//        System.out.println(QUERY);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(QUERY);
            rs = ps.executeQuery();
            while (rs.next()) {
                temp.add(rs.getString("sycolumn"));
                temp.add(rs.getString("sytable"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return temp;
    }

    public static String getFunctionOperator(String col) {
        String temp ="";  // initizlize string data = null;
        Connection conn = DatabaseHandler.GetDatabaseConnection();
        PreparedStatement ps = null;
        // always constant query using final keyword
        final String OPERATOR_QUERY = "select foperator from function where fname like '%"+col.trim()+"%'";
        // try resource pani garna milxa !!(Exception handling)
        try {
            ps = conn.prepareStatement(OPERATOR_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                temp = rs.getString("foperator"); // always follows wrt query from above execution
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return temp; // string value
    }

//    public static String ClauseSelection(String col) {
//        String temp = "";
////        int count
//        String value = "";  // value check -- SELECT or WHERE
//        final String TYPE_SELECTION_QUERY = "Select ftype from function where fname like '%"+col.trim()+"%'";
////        System.out.println(TYPE_SELECTION_QUERY);
//        try(Connection conn = DatabaseHandler.GetDatabaseConnection()) {
//            PreparedStatement ps = conn.prepareStatement(TYPE_SELECTION_QUERY);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                value = rs.getString("ftype");
//            }
//            // for select clause
//            if (value.equals("SELECT")) {
//                temp = "sel"; // select
//            } else {
//                temp = "whe"; // where
//            }
////            else if (value.equalsIgnoreCase("where")) {
////                temp = "whe";
////            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return temp; // either select or where clause
//    }

//    public static String
    public static boolean checkColumnStatus(String col) {
        boolean status = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        // checking for synonym
        ArrayList<String> sy = GetSynonyms(col);
        if (sy.size()>0) {
            col = sy.get(1);
            status =true;
        }

        Connection con = DatabaseHandler.GetDatabaseConnection();
        String query = "select count(*) from information_schema.columns where column_name like '%" +col.trim() + "%' "
                + " and table_schema = 'finalproject'";
        try {
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
           rs.next();
           count = rs.getInt(1);
           if (count>0) {
               status=true;
           }
        } catch (SQLException e) {
//            status=false;
            e.getMessage();
            e.printStackTrace();
        }
        return status;
    }


    // checking output for above codes// demo test
    public static void main(String[] args) {
        System.out.println(checkColumnStatus("employee"));
        System.out.println(getFunctionOperator("total"));
        ArrayList<String> lists = GetSynonyms("name");
        for (String temp: lists) {
            System.out.println(temp);
        }
    }

}
