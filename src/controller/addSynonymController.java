package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DBOperation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class addSynonymController {

    @FXML
    private JFXTextField synonymColumn;
    @FXML
    private JFXTextField tableName;
    @FXML
    private JFXTextField columnName;

    @FXML
    private JFXButton addSynonym;

    @FXML
    private JFXButton resetButton;


    public void setResetButton(javafx.event.ActionEvent actionEvent) {

        synonymColumn.setText("");
        columnName.setText("");
        tableName.setText("");
    }

    public void setAddSynonymButton(javafx.event.ActionEvent actionEvent) {


//        addSynonym.getScene().getWindow().show();

        String syName = synonymColumn.getText();
        String syTable = tableName.getText();
        String syColumn = columnName.getText();

        final String SYNONYM_DB_TABLE = "synonym (syname, scolumn,stable) ";

        // sabai value lai synonym database ko form ma raakney !!
//        String getAll = "'"+sColumnName+ " '";

        if (DBOperation.insert(SYNONYM_DB_TABLE, syName, syColumn, syTable)) {

//            System.out.println("done")
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Insert completed");
            alert.setContentText(null);
            alert.setTitle("database insertion done");
            alert.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("All field are required");
            alert.setTitle("Empty");
            alert.setContentText(null);
            alert.show();
            synonymColumn.setText("");
            tableName.setText("");
            columnName.setText("");
        }

    }
}
