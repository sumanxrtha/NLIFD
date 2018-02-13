package views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import databaseControl.DBOperation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class updateSynonymController {
    @FXML
    private JFXButton updateSynonymButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXTextField synonymColumn;
    @FXML
    private JFXTextField columnName;
    @FXML
    private JFXTextField tableName;

    public void setUpdateAction(ActionEvent actionEvent) {

        /// table = synonym
        // syname == synonymColumnName , col = columnName,  table = tableName
//        if (DBOperation.update()) {

        }









// exit from update synonym panel
    public void exitUpdate(ActionEvent actionEvent) {
        Platform.exit();
    }
}
