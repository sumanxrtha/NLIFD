package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTogglePane;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
//    private JFXTogglePane queryGeneration;
//    private JFXButton openButton
    private AnchorPane queryGeneration, addFunctions, viewFunctions, addSynonyms, viewSynonyms;


    public void addFunctionsAction(MouseEvent mouseEvent) throws IOException {
        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/addFunction.fxml"));
        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.setTitle("Add Functions");
        dashboardStage.show();
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
        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/addSynonym.fxml"));
        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.setTitle("Add Synonym");
        dashboardStage.show();
    }


    public void viewSynonymsAction(MouseEvent mouseEvent) throws IOException {

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
