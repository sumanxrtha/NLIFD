package controller;

import Application.Function;
import Application.GotoHome;
import com.jfoenix.controls.JFXButton;
import databaseControl.DatabaseHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DisplayFunctionController implements Initializable {

    private ObservableList<Function> fdata;

    @FXML
    private JFXButton homeBtn;

    @FXML
    private JFXButton addButton;

    public static final String GET_FUNCTION_QUERY = "SELECT fname,foperator,ftype from function";

    Connection con = null;
    PreparedStatement ps = null;
//    ResultSet rs = null;

    @FXML
    private TableView<Function> funTable;
    @FXML
    private TableColumn<?, ?> funName;
    @FXML
    private TableColumn<?, ?> funOperator;
    @FXML
    private TableColumn<?, ?> funType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = DatabaseHandler.GetDatabaseConnection();
        fdata = FXCollections.observableArrayList();
        setCellTable();
        LoadFunctionFromDatabase();
    }

    private void setCellTable() {
        funName.setCellValueFactory(new PropertyValueFactory<>("functionName"));
        funOperator.setCellValueFactory(new PropertyValueFactory<>("functionOperator"));
        funType.setCellValueFactory(new PropertyValueFactory<>("functionType"));
    }

    private void LoadFunctionFromDatabase() {

        try {
            ps = con.prepareStatement(GET_FUNCTION_QUERY);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                fdata.add(new Function(rs.getString("fname"), rs.getString("foperator"), rs.getString("ftype")));

            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        funTable.setItems(fdata);
        DatabaseHandler.CloseConnection(con);
    }

    public void exitBtnAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void homeBtnAction(ActionEvent actionEvent) throws Exception {

        homeBtn.getScene().getWindow().hide();
//        hiding view function layout

        GotoHome gotoHome = new GotoHome();
        gotoHome.HomeSection();


//        Stage dashboardStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("../views/Dashboard.fxml"));
//        Scene scene = new Scene(root);
//        dashboardStage.setScene(scene);
//        dashboardStage.show();

    }

    public void addBtnAction(ActionEvent actionEvent) throws Exception {

        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/addFunction.fxml"));
//        Scene scene = new Scene(root);
        dashboardStage.setScene(new Scene(root));
        dashboardStage.show();
    }
}
