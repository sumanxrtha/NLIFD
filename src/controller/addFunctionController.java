package controller;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import databaseControl.DBOperation;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class addFunctionController implements Initializable {

    @FXML
    private JFXTextField fName;
    @FXML
    private JFXTextField fOperator;

    // for radio button section;
    @FXML
    private JFXRadioButton selectType;
    @FXML
    private JFXRadioButton whereType;

    private String typeOption;

    final ToggleGroup group = new ToggleGroup();

    public void setAddFunctionButton(javafx.event.ActionEvent actionEvent) {

// getting input from user for function insertion
        String fNameInput = fName.getText();
        String fOperatorInput = fOperator.getText();

        System.out.println(typeOption);

        // sabai value lai database ko form ma raakney !! ie function table ma raakhey

        final String DB_FUNCTION_TABLE = "function (fname,foperator,ftype)";  //function table in database

        if (DBOperation.insert(DB_FUNCTION_TABLE, fNameInput, fOperatorInput, typeOption)) {
//            addSynonym.getScene().getWindow().isShowing();

//            System.out.println("done")
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Insert completed");
            alert.setContentText(null);
            alert.setTitle("function insertion done");
            alert.show();
            fName.setText("");
            fOperator.setText("");

        } else {
            Alert falert = new Alert(Alert.AlertType.WARNING);
            falert.setHeaderText("Function input have some values");
            falert.setTitle("Empty");
            falert.setContentText(null);
            falert.show();
            fName.setText("");
            fOperator.setText("");
        }

    }

    public void setResetBtn(javafx.event.ActionEvent actionEvent) throws Exception {
        fName.setText("");
        fOperator.setText("");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectType.setToggleGroup(group);
        selectType.setSelected(false);

        whereType.setToggleGroup(group);
        whereType.setSelected(false);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (group.getSelectedToggle() != null) {
                    JFXRadioButton btn = (JFXRadioButton) group.getSelectedToggle();
//                    System.out.println(btn.getText());
                    typeOption = btn.getText();
                }
            }
        });
    }
}
