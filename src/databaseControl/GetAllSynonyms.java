package databaseControl;

import Application.Synonym;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetAllSynonyms {
    public static final String GET_SYNONYM_QUERY = "SELECT sname,scolumn,stable from synonym";

    public static List<Synonym> getSynonymList() {

        List<Synonym> list = new ArrayList<>();

        try(Connection conn = DatabaseHandler.GetDatabaseConnection();
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(GET_SYNONYM_QUERY);

            while (rs.next()) {
                Synonym s = new Synonym(rs.getString("sname"),rs.getString("scolmn"),rs.getString("stable"));
                list.add(s);
    // return FXCollections.<Synonym>observableArrayList(rs.getString("sname"),rs.getString("scolumn"),rs.getString("stable"));
            }
// now closing the database conncection
            DatabaseHandler.CloseConnection(conn);

        } catch (SQLException e) {
            e.getMessage();
        }
        return list;
//        return null;
    }
}
