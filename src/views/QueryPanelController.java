package views;

import Application.DependencyParserAPI;

import Application.Synonym;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import databaseControl.GetAllSynonyms;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class QueryPanelController {
    @FXML
    private JFXTextField inputQuery;
    @FXML
    private JFXButton executeButton;
    @FXML
    private JFXButton homeButton;
    @FXML
    private JFXButton exitButton;
    @FXML
    private JFXProgressBar progress;


    public void executeAction(ActionEvent actionEvent) {
        progress.setVisible(true);

        String question = inputQuery.getText();


        System.out.println(question);

        // sending question to dependency parser api to genearte world list using stanford api
        DependencyParserAPI.DependencyGeneration(question);

        System.out.println(DependencyParserAPI.DependencyGeneration(question));

    }

    public void exitBtnAction(ActionEvent actionEvent) {
//        Platform.exit();
        exitButton.getScene().getWindow().hide();
    }

    public void homeBtnAction(ActionEvent actionEvent) throws IOException {

        // creating object of logincontroller to call homescreen method due to non static method
//        LoginController loginController = new LoginController();
//        loginController.homeScreen();

        homeButton.getScene().getWindow().hide();

        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.setTitle("Home | Natural Language Interface For Database");
        dashboardStage.show();
        dashboardStage.setResizable(false);

    }
}
