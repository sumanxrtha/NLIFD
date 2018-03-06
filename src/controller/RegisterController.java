package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import databaseControl.DatabaseHandler;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
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

public class RegisterController implements Initializable {

    @FXML
    private JFXButton exitButton;
    @FXML
    private JFXButton registerbtn;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXSpinner sp;

    @FXML
    private JFXTextField fullname, email;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;

    public void exitBtnAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void setLoginButton(ActionEvent actionEvent) throws IOException {
        loginButton.getScene().getWindow().hide();
        gotoLoginPage();
//        Stage dashboardStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
//        Scene scene = new Scene(root);
//        dashboardStage.setScene(scene);
//        dashboardStage.setTitle("User Login");
//        dashboardStage.show();
//        dashboardStage.setResizable(false);
    }

    public void setRegisterBtn(ActionEvent actionEvent) {

        sp.setVisible(true);
        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.seconds(3)); // 5 sec duration for spinner
        pauseTransition.setOnFinished(event -> {
//            DatabaseHandler.GetDatabaseConnection();
            completeRegister(); // verify login
        });
        pauseTransition.play();
    }

    private void completeRegister() {
        String user = username.getText();
        String pass = password.getText();

        if (DatabaseHandler.registerAccount(user, pass)) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Completed");
            alert.setHeaderText("Goto Login secton and enter your information.");
            alert.setContentText(null);
            alert.show();

            registerbtn.getScene().getWindow().hide();
            try {
                sp.setVisible(true);
                gotoLoginPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sp.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Error");
            alert.setHeaderText("Enter correct information");
            alert.setContentText(null);
            alert.show();
//            reseting user and pass field
            username.setText("");
            password.setText("");
            fullname.setText("");
            email.setText("");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sp.setVisible(false);
    }

    public void gotoLoginPage() throws IOException {
        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/Login.fxml"));
        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.setTitle("User Login");
        dashboardStage.show();
        dashboardStage.setResizable(false);
    }
}
