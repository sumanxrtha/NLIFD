package controller;

import application.ChangeSynFunLayout;
import application.GotoHome;
import application.Synonym;
import com.jfoenix.controls.JFXButton;
import database.DatabaseHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

    private ObservableList<Synonym> sdata;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton addButton;

    public static final String GET_SYNONYM_QUERY = "SELECT syname,scolumn,stable from synonym";

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
        sdata = FXCollections.observableArrayList();
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
                sdata.add(new Synonym(rs.getString(1),rs.getString(2), rs.getString(3)));

            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        syTable.setItems(sdata);
        DatabaseHandler.CloseConnection(con);
    }

    public void exitBtnAction(ActionEvent actionEvent) {
        Platform.exit();
        // close all running application
    }

    public void homeBtnAction(ActionEvent actionEvent) throws Exception {
        homeBtn.getScene().getWindow().hide();

        GotoHome change = new GotoHome();
        change.HomeSection();

//        Stage dashboardStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("../views/Dashboard.fxml"));
//        Scene scene = new Scene(root);
//        dashboardStage.setScene(scene);
//        dashboardStage.show();
    }

    public void addBtnAction(ActionEvent actionEvent) throws Exception {

//        Stage dashboardStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("../views/addSynonym.fxml"));
////        Scene scene = new Scene(root);
//        dashboardStage.setScene(new Scene(root));
//        dashboardStage.show();
        ChangeSynFunLayout changeSyn = new ChangeSynFunLayout();
        changeSyn.ChangeSyn();

    }
}
