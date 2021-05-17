package database;

//import com.sun.org.apache.xml.internal.security.signature.reference.ReferenceSubTreeData;
//import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
//
//import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class QueryParser {

    // list out matched database table name or ocolumn name
    public static ArrayList<String> ListMatchedStems(String stem) {
        ArrayList<String> matchItem = new ArrayList<>();
        Connection conn = DatabaseHandler.GetDatabaseConnection();
        String query = "Select table_name, column_name from information_schema.columns where column_name like '%" +stem.trim() +"%' and table_schema='finalproject'";
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

    public static boolean ChkStatus(String table, String col) {
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
        String query = "select count(*) from information_schema.columns where column_name like '%" + col.trim()+"%'" + " and table_name like '%" +table.trim()+"%' and table_schema = 'finalproject'";
// status for true or false
//        System.out.println(query);
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

    // checking operator status for given operator form database
    public static boolean ChkOperatorStatus(String o) {
        boolean status = false;
        PreparedStatement ps =null;
        ResultSet rs = null;
        int count =0;
        Connection c = DatabaseHandler.GetDatabaseConnection();
        String query = "select count(*) from function_tbl where fsynonym like '% " + o.trim() + " %' ";

        try {
            ps = c.prepareStatement(query);
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
            if (count>0) {
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            status =false;
        }
        return status;
    }

    // getting select and where type of functions
    public static String SelectWhere(String col) {
        String temp = ""; // this is for return type in this method

        String name = ""; // yesmaa ftype basxa ra yeslai equal garney clause anusaar
        Connection connection = DatabaseHandler.GetDatabaseConnection();
        String query = "select ftype from function_tbl where fsynonym like '%" +col.trim() +"%'";
        System.out.println("Function Type: "+query);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString(1);
            }
            if (name.equals("SELECT")) {
                temp = "SELECT";
            }
            else if (name.equals("WHERE")) {
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
        String query = " SELECT data_type FROM INFORMATION_SCHEMA.columns WHERE column_name like '%"+columnName.trim()+"%' AND table_schema = 'finalproject'";
        System.out.println("DataType: " +query);
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
        ArrayList<String> temp = new ArrayList<>();
        Connection conn = DatabaseHandler.GetDatabaseConnection();
        final String QUERY = "select scolumn, stable from synonym where syname like '%"+col.trim()+"%'";
        System.out.println("GetSynonyms: "+QUERY);
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

    public static String getFunctionOperator(String col) {
        String temp ="";  // initizlize string data = null;
        Connection conn = DatabaseHandler.GetDatabaseConnection();
        PreparedStatement ps = null;
        // always constant query using final keyword
        final String OPERATOR_QUERY = "select foperator from function_tbl where fsynonym like '%"+col.trim()+"%'";
        // try resource pani garna milxa !!(Exception handling)
        System.out.println("FunOperator: " +OPERATOR_QUERY);
        try {
            ps = conn.prepareStatement(OPERATOR_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                temp = rs.getString(1); // always follows wrt query from above execution
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
//        final String TYPE_SELECTION_QUERY = "Select ftype from function_tbl where fname like '%"+col.trim()+"%'";
////        System.out.println(TYPE_SELECTION_QUERY);
//        try(Connection conn = DatabaseHandler.GetDatabaseConnection()) {
//            PreparedStatement ps = conn.prepareStatement(TYPE_SELECTION_QUERY);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                value = rs.getString("ftype");
//            }
//            // for select clause
//            if (value.equals("SELECT")) {
//                temp = "select"; // select
//            } else {
//                temp = "where"; // where
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
        String query = "SELECT count(*) FROM INFORMATION_SCHEMA.columns WHERE column_name like '%"+col.trim()+"%' AND table_schema = 'finalproject'";
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
//    public static void main(String[] args) {
//        System.out.println(checkColumnStatus("employee"));
//        System.out.println(getFunctionOperator("total"));
//        ArrayList<String> lists = GetSynonyms("name");
//        for (String temp: lists) {
//            System.out.println(temp);
//        }
//    }

    // resultant output of the given query
    public static ArrayList<ArrayList<String>> getQueryResult(String url, ArrayList<String> head) {
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        Connection c = DatabaseHandler.GetDatabaseConnection();
        PreparedStatement ps = null;
        ResultSet rs =null;

        try {
            ps = c.prepareStatement(url);
            rs = ps.executeQuery();
            while (rs.next()) {
                ArrayList<String> temp = new ArrayList<>();
                for (int i =1; i<=head.size(); i++) {
                    temp.add(rs.getString(i));
                }
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list;
    }

    public static ArrayList<String> getRightOfWhere(String stem) {
        ArrayList<String> temp = new ArrayList<>();

        Connection connection = DatabaseHandler.GetDatabaseConnection();
        String query = "SELECT table_name,column_name FROM INFORMATION_SCHEMA.columns WHERE column_name like '%"+stem+"%' AND table_schema = 'finalproject'";
        PreparedStatement ps = null;
        System.out.println(query);
        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String table = rs.getString("table_name");
                String tableCol = rs.getString("column_name");
                temp.add(table);
                temp.add(tableCol);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    // resultant ouptut ko laagi
//    public static void resultantOutput(String select, String from, String where) {
//        Connection con = DatabaseHandler.GetDatabaseConnection();
//        String getAllQuery = select+" "+from + " "+where+" ";
//        Statement stmt = null;
//        ResultSet rs = null;
//        try {
//            stmt = con.createStatement();
////            stmt.execute(getAllQuery);
//            rs = stmt.executeQuery(getAllQuery);
//            while (rs.next()) {
//                System.out.println(rs.getString(1));
////                System.out.println(rs.getString(2));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


    public static ArrayList<String> GetForignKey(ArrayList<String> list) {
        ArrayList<String> key = new ArrayList<>();
        Connection connection = DatabaseHandler.GetDatabaseConnection();

        PreparedStatement ps = null;


       /* String query = "select table_name, column_name, referenced_table,name," +
                " referenced_column_name from key_column_usage where table_schema = " +
                " finalproject and (table_name = ' get(0) or table_name=get(1) and " +
                " (referenced_table_name = get(0) or referenced_table_name = get(1) and " +
                " referenced_column_name is not null";*/

        String query ="SELECT table_name, column_name, referenced_table_name, referenced_column_name FROM  KEY_COLUMN_USAGE WHERE table_schema =  'finalproject' AND ( table_name =  '"+list.get(0)+"' OR table_name =  '"+list.get(1)+"') AND ( referenced_table_name =  '"+list.get(0)+"' OR referenced_table_name =  '"+list.get(1)+"') AND referenced_column_name IS NOT NULL ";


//        System.out.println(query);

        try {
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String tbl = rs.getString("table_name");
                String col = rs.getString("column_name");
                String reftable = rs.getString("referenced_table_name");
                String refcolumn = rs.getString("referenced_column_name");

                key.add(tbl);
                key.add(col);
                key.add(reftable);
                key.add(refcolumn);
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return key;
    }
    
}
