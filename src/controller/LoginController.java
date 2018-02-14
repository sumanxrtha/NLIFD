package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import databaseControl.DatabaseHandler;
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

public class LoginController implements Initializable {
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXSpinner spinner;

    @FXML
    public void setLoginButton(ActionEvent actionEvent) {

        spinner.setVisible(true);
        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(3)); // 5 sec duration for spinner
        pauseTransition.setOnFinished(event -> {
//            DatabaseHandler.GetDatabaseConnection();
            completeLogin(); // verify login
        });
        pauseTransition.play();
    }

    private void completeLogin() {
        String uname = username.getText();
        String pass = password.getText();

//        boolean status = DatabaseHandler.CheckLoginUser(uname, pass);  //return true if correct
        if (DatabaseHandler.CheckLoginUser(uname, pass)) {  // if true

            loginButton.getScene().getWindow().hide();
            try {
            spinner.setVisible(true);
                Stage dashboardStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("../views/Dashboard.fxml"));
                Scene scene = new Scene(root);
                dashboardStage.setScene(scene);
                dashboardStage.setTitle("Home - NLIFD");
                dashboardStage.show();
                dashboardStage.setResizable(false);
            } catch (IOException e) {
                e.printStackTrace();
            }

//           homeScreen();

        } else {
            spinner.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentication Error");
            alert.setHeaderText("Please, Type correct username & password");

            alert.setContentText("Username " + uname + " is not correct.");
            alert.show();

//            reseting user and pass field
            username.setText("");
            password.setText("");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        spinner.setVisible(false);
    }

//    public void homeScreen() throws IOException {
//
//        loginButton.getScene().getWindow().hide();
//        Stage dashboardStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
//        Scene scene = new Scene(root);
//        dashboardStage.setScene(scene);
//        dashboardStage.setTitle("Home | Natural Language Interface For Database");
//        dashboardStage.show();
//
//    }

}
