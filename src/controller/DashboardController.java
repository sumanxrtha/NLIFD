package controller;

import Application.ChangeSynFunLayout;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private JFXButton exitBtn;

    @FXML
    private JFXButton logoutBtn;
    @FXML
    private Label l;
    @FXML

//    private JFXTogglePane queryGeneration;
//    private JFXButton openButton
    private AnchorPane queryGeneration, addFunctions, viewFunctions, addSynonyms, viewSynonyms;
    ChangeSynFunLayout changeLayout = new ChangeSynFunLayout();

    // creating object of ChangeSynFunLayout Class to class from dashboard
    public void addFunctionsAction(MouseEvent mouseEvent) throws IOException {
        changeLayout.ChangeFun();
    }

    public void viewFunctionsAction(MouseEvent mouseEvent) throws IOException {

        viewFunctions.getScene().getWindow().hide();
        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/DisplayFunction.fxml"));
        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.setTitle("List Functions");
        dashboardStage.show();
        dashboardStage.setResizable(false);

    }

    public void queryGenerationAction(MouseEvent mouseEvent) throws IOException {

        queryGeneration.getScene().getWindow().hide();
        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/QueryPanel.fxml"));
        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.setTitle("Query Panel");
        dashboardStage.show();
        dashboardStage.setResizable(false);

    }

    public void addSynonymsAction(MouseEvent mouseEvent) throws IOException {
        changeLayout.ChangeSyn();
    }


    public void viewSynonymsAction(MouseEvent mouseEvent) throws Exception {

        viewSynonyms.getScene().getWindow().hide();
        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/DisplaySynonym.fxml"));
        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.setTitle("List Synonyms");
        dashboardStage.show();
        dashboardStage.setResizable(false);

    }

    public void exitBtnAction(ActionEvent actionEvent) {

//        Platform.setImplicitExit(true);
        Platform.exit();
    }

    public void logoutBtnAction(ActionEvent actionEvent) throws Exception {

        logoutBtn.getScene().getWindow().hide();         // back to login page
        Scene login = new Scene(FXMLLoader.load(getClass().getResource("../views/Login.fxml")));
        Stage openLogin = new Stage();
        openLogin.setScene(login);
        openLogin.show();
    }
}
