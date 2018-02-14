package controller;

import Application.DependencyParserAPI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QueryPanelController implements Initializable {
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

    private String question;

    public void executeAction(ActionEvent actionEvent) {
        question = inputQuery.getText();
        if (question.equals("")) {
//            System.out.println("don't empty");
            progress.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("Enter your text or question");
            alert.show();
        } else {
            progress.setVisible(true);
            PauseTransition pt = new PauseTransition();
            pt.setDuration(Duration.seconds(4));
            pt.setOnFinished(event -> executingProcess());
            pt.play();
        }
    }

    private void executingProcess() {
        progress.setVisible(false);

        System.out.println(question);

        // sending question to dependency parser api to genearte world list using stanford api
//        DependencyParserAPI.DependencyGeneration(question);

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progress.setVisible(false);
    }
}
