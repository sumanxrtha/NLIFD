package views;

import databaseControl.DatabaseHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DisplaySynonymController extends Application {
    @FXML
    private ObservableList<ObservableList> data;

    @FXML
    private TableView listSynonym = new TableView();

    @Override
    public void start(Stage primaryStage) throws Exception {

        listSynonym = new TableView();
        buildData();
//        Scene scene = new Scene(listSynonym);
    }



    public void buildData() {

        data = FXCollections.observableArrayList();

        try {
            Connection conn = DatabaseHandler.GetDatabaseConnection();
            PreparedStatement ps = conn.prepareStatement("Select sname,scolumn,stable from synonym");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String synonymName = rs.getString("sname");
                String synonymColumn = rs.getString("scolumn");
                String synonymTable = rs.getString("stable");

                ObservableList<String> row = FXCollections.observableArrayList();

                for (int i=1; i<=rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
                System.out.println("Data: " +data);
            }
            listSynonym.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
            System.out.println("error on building data from database");
        }

        TableColumn sName = new TableColumn("Names");
        sName.setMinWidth(200);
TableColumn sColumn = new TableColumn("Columns");
        sColumn.setMinWidth(200);
TableColumn sTable = new TableColumn("Tables");
        sTable.setMinWidth(200);

        listSynonym.getColumns().addAll(sName,sColumn,sTable);
        System.out.println("table: " + listSynonym);

    }
}
