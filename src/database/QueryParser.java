package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryParser {

    public static ArrayList<String> GetSynonyms(String col) {
        ArrayList<String> temp = new ArrayList<String>();
        Connection conn = DatabaseHandler.GetDatabaseConnection();
        final String QUERY = "select scolumn, stable from synonym where sname like '%"+col.trim()+"%'";
//        System.out.println(QUERY);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(QUERY);
            rs = ps.executeQuery();
            while (rs.next()) {
                temp.add(rs.getString("scolumn"));
                temp.add(rs.getString("stable"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
        }
        return temp;
    }

    public static String GetFunOperator(String col) {
        String temp ="";  // initizlize string data = null;
        Connection conn = DatabaseHandler.GetDatabaseConnection();
        PreparedStatement ps = null;
        // always constant query using final keyword
        final String OPERATOR_QUERY = "select operator from function where fname like '%"+col.trim()+"%'";
        // try resource pani garna milxa !!(Exception handling)
        try {
            ps = conn.prepareStatement(OPERATOR_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                temp = rs.getString(2); // 2 => operator
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return temp; // string value
    }

    public static String ClauseSelection(String col) {
        String temp = "";
//        int count
        String value = "";  // value check -- SELECT or WHERE
        final String TYPE_SELECTION_QUERY = "Select ftype from function where fname like '%"+col.trim()+"%'";
//        System.out.println(TYPE_SELECTION_QUERY);
        try(Connection conn = DatabaseHandler.GetDatabaseConnection()) {
            PreparedStatement ps = conn.prepareStatement(TYPE_SELECTION_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                value = rs.getString("ftype");
            }
            // for select clause
            if (value.equals("SELECT")) {
                temp = "sel"; // select
            } else {
                temp = "whe"; // where
            }
//            else if (value.equalsIgnoreCase("where")) {
//                temp = "whe";
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp; // either select or where clause
    }

//    public static String

}
