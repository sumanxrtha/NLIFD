package testdemo;

import java.sql.*;
import java.util.*;
import javax.swing.*;


public class SqlLibrary {

	/**
	 * This class contains all the necessary functions to query the 
	 * Information schema and the database 
	 * All the functions are the static and mostly contains the select
	 * statements and more the one select statements.
	 */

	public static Connection GetConnection(String database){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database+"?useSSL=false","root","sujan");
			
		} catch (ClassNotFoundException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
			
		} catch (SQLException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return conn;
	}

	
	/**
	 * This function queries the information schema for the matched tables column
	 * of specified database as with the supplied argument stem
	 * The ResultSet is assumed to has one row in max and if has more than 
	 * that the natural join may exist in the query so further processing 
	 * is needed to be done for that.
	 * */
	public static ArrayList<String> ListMatchedStems(String stem){
		ArrayList<String>matchItem = new ArrayList<String>();
		Connection conn = SqlLibrary.GetConnection("information_schema");
		String query = "SELECT table_name,column_name"+
					   " FROM INFORMATION_SCHEMA.columns"+
					   " WHERE column_name like '%"+stem.trim()+"%'"+
					   " AND table_schema = 'finalproject'";
		System.out.println(query);
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs  = stmt.executeQuery();
			while(rs.next()){
			String table = rs.getString("table_name");
			String tableCol = rs.getString("column_name");
			matchItem.add(table);
			matchItem.add(tableCol);
			}
			
		} catch (SQLException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		return matchItem;
	}
	
	public static ArrayList<String> GetRightOfWhere(String stem){
		ArrayList<String> temp = new ArrayList<String>();
		Connection conn = SqlLibrary.GetConnection("finalproject");
		String query = "SELECT table_name,column_name"+
					   " FROM INFORMATION_SCHEMA.columns"+
					   " WHERE column_name like '%"+stem+"%'"+
					   " AND table_schema = 'finalproject'";
		PreparedStatement stmt = null;
		System.out.println(query);
		try {
			stmt = conn.prepareStatement(query);
			ResultSet rs  = stmt.executeQuery();
			while(rs.next()){
			String table = rs.getString("table_name");
			String tableCol = rs.getString("column_name");
			temp.add(table);
			temp.add(tableCol);
			break;
			}
			
		} catch (SQLException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return temp;
	}
	
	public static boolean ChkStatus(String table, String col){
		boolean status = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		//Check if synonym exists if exits replace it with synonym
		ArrayList<String>temp = SqlLibrary.GetSynonymn(col);
		if(temp.size() > 0){
			table = temp.get(0);
			col = temp.get(1);
		}
		
		Connection con = SqlLibrary.GetConnection("information_schema");
		String query = "SELECT count(*)"+
					   "FROM INFORMATION_SCHEMA.columns "+
					   "WHERE column_name like '%"+col.trim()+"%'"+
					   "AND table_name like '%"+table.trim()+"%'"+
					   "AND table_schema = 'finalproject'";
		try {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
			if(count > 0)
				status = true;
		} catch (SQLException e) {
			status = false;
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static String SelWhe(String col){
		String temp = "";
		int count = 0;
		Connection conn = SqlLibrary.GetConnection("finalproject");
		String query  = "SELECT type FROM funcop where fsynonym like '%"+col.trim()+"%'";
		System.out.println(query);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next()){
				count = rs.getInt(1);
			}
			if(count  == 11)
				temp = "sel";
			else if(count == 22)
				temp = "whe";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	public static String GetDataTypeOfColumn(String column){
		String datatype = "";
		ResultSet rs = null;
		Connection conn = SqlLibrary.GetConnection("information_schema");
		String query = " SELECT data_type FROM INFORMATION_SCHEMA.columns"+
					   " WHERE column_name like '%"+column.trim()+"%' " +
					   " AND table_schema = 'finalproject'";
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next()){
				datatype = rs.getString(1);
				break;
			}
		} catch (SQLException e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		return datatype;
	}
	
	public static String GetOperator(String col){
		String temp = "";
		Connection conn = SqlLibrary.GetConnection("finalproject");
		String query  = "SELECT operator FROM funcop WHERE fsynonym like '%"+col.trim()+"%'";
		System.out.println(query);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next()){
				temp = rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	public static ArrayList<String> GetSynonymn(String col){
		ArrayList<String> temp  = new ArrayList<String>(); 
		Connection conn = SqlLibrary.GetConnection("finalproject");
		String query  = "SELECT scolumn,stable FROM synonym WHERE syname like '%"+col.trim()+"%'";
		System.out.println(query);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next()){
				temp.add(rs.getString("scolumn"));
				temp.add(rs.getString("stable"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
		
	}
	
	public static boolean insert(String database, String table, String value){
		boolean status = false;
		Connection conn = SqlLibrary.GetConnection(database);
		PreparedStatement stmt = null;
		String query = "INSERT INTO "+table+" VALUES ("+value+" )";
		System.out.println(query);
		try {
			stmt = conn.prepareStatement(query);
			stmt.executeUpdate();
			status = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static ArrayList<ArrayList<Object>> selectTable(String url,String[] flds){
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();
		ResultSet rs = null;
		Connection con = SqlLibrary.GetConnection("finalproject");
		try {
			int j = 1;
			PreparedStatement stmt = con.prepareStatement(url);
			rs = stmt.executeQuery();
			//System.out.println(flds.length);
			while(rs.next()){
				ArrayList<Object> temp = new ArrayList<Object>();
				for(int i = 0; i <= flds.length; i++){
					if(i == 0)
						temp.add(j);
					else if(rs.getString(i).equals("11"))
						temp.add("select");
					else if(rs.getString(i).equals("22"))
						temp.add("where");
					else
						temp.add(rs.getString(i));
				}
				list.add(temp);
				j++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static boolean ChkColStatus(String col){
		boolean status = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		//Check if synonym exists if exits replace it with synonym
		ArrayList<String>temp = SqlLibrary.GetSynonymn(col);
		if(temp.size() > 0){
			col = temp.get(1);
			status = true;
			//return status;
		}
		
		Connection con = SqlLibrary.GetConnection("information_schema");
		String query = "SELECT count(*)"+
					   "FROM INFORMATION_SCHEMA.columns "+
					   "WHERE column_name like '%"+col.trim()+"%'"+
					   "AND table_schema = 'finalproject'";
		//System.out.println(query);
		try {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
			if(count > 0)
				status = true;
		} catch (SQLException e) {
			status = false;
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static ArrayList<String> GetForigenKey(ArrayList<String> list){
		ArrayList<String> key = new ArrayList<String>();
		Connection con = SqlLibrary.GetConnection("information_schema");
		PreparedStatement stmt = null;
		String query ="SELECT table_name, column_name, referenced_table_name, referenced_column_name"+
					  " FROM  KEY_COLUMN_USAGE"+
					  "	WHERE table_schema =  'finalproject'"+
					  " AND ( table_name =  '"+list.get(0)+"' "+
					  " OR table_name =  '"+list.get(1)+"')"+
					  " AND ( referenced_table_name =  '"+list.get(0)+"'"+
					  " OR referenced_table_name =  '"+list.get(1)+"')"+
					  " AND referenced_column_name IS NOT NULL ";

		System.out.println(query);

		try {
			stmt = con.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				String tbl = rs.getString("table_name");
				String col = rs.getString("column_name");
				String refTbl = rs.getString("referenced_table_name");
				String refCol = rs.getString("referenced_column_name");
				key.add(tbl);
				key.add(col);
				key.add(refTbl);
				key.add(refCol);
				break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}
	
	public static ArrayList<ArrayList<String>> GetQueryResult(String url, ArrayList<String> head){
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		Connection conn = SqlLibrary.GetConnection("finalproject");
		try {
			PreparedStatement stmt = conn.prepareStatement(url);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				ArrayList<String>temp = new ArrayList<String>();
				for(int i = 1;i <= head.size(); i++){
					temp.add(rs.getString(i));
				}
				list.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	

	public static boolean ChkOperatorStatus(String operator){
		boolean status = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		Connection con = SqlLibrary.GetConnection("finalproject");
		String query = "SELECT count(*)"+
					   "FROM funcop "+
					   "WHERE fsynonym like '%"+operator.trim()+"%'";
		try {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			rs.next();
			count = rs.getInt(1);
			if(count > 0)
				status = true;
		} catch (SQLException e) {
			status = false;
			e.getLocalizedMessage();
			e.printStackTrace();
		}
		
		return status;
	}
	
}