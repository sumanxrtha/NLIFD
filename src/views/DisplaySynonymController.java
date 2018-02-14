package views;

import Application.Synonym;
import databaseControl.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DisplaySynonymController implements Initializable {

    private ObservableList<Synonym> data;

    public static final String GET_SYNONYM_QUERY = "SELECT sname,scolumn,stable from synonym";

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    @FXML
    private TableView<Synonym> syTable;
    @FXML
    private TableColumn<?, ?> sname;
    @FXML
    private TableColumn<?, ?> scolumn;

    @FXML
    private TableColumn<?,?> stable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = DatabaseHandler.GetDatabaseConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
        LoadSynonymFromDatabase();
    }

    private void setCellTable() {
        sname.setCellValueFactory(new PropertyValueFactory<>("syName"));
        scolumn.setCellValueFactory(new PropertyValueFactory<>("syColumn"));
        stable.setCellValueFactory(new PropertyValueFactory<>("syTable"));
    }

    private void LoadSynonymFromDatabase() {

        try {
            ps = con.prepareStatement(GET_SYNONYM_QUERY);
            rs = ps.executeQuery();
            while (rs.next()) {
                data.add(new Synonym(rs.getString("sname"),rs.getString("scolumn"), rs.getString("stable")));

            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        syTable.setItems(data);
    }
}
