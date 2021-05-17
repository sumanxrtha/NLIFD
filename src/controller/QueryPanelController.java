package controller;

import application.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import database.QueryParser;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Duration;
import testdemo.DivideList;
import testdemo.QueryGeneratorFlow;
import testdemo.SqlLibrary;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import static testdemo.DemoTestRun.getData;
import static testdemo.DemoTestRun.runTask;

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

    @FXML
    private TextArea outputQuery;

    @FXML
    private TableView displayOutput;

    @FXML
    private TextArea outputData;

    private String question;


    boolean OK = false;


    public void executeAction(ActionEvent actionEvent) {
        question = inputQuery.getText(); // getting input from users
//
//        if (OK)
//            outputQuery.clear();


        if (question.equals("")) {
//            System.out.println("don't empty");
            progress.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("Enter your text or question");
            alert.show();
        }

        outputQuery.clear();
        outputData.clear();
            progress.setVisible(true);
            PauseTransition pt = new PauseTransition();
            pt.setDuration(Duration.seconds(3));
            pt.setOnFinished(event -> executingProcess());
            pt.play();
    }

    private void executingProcess() {
        progress.setVisible(false);

        OK = true;
        System.out.println("=========== YOUR QUESTION ============");
        System.out.println(question);
        System.out.println("=====================================");
        runTask(question);

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
        Parent root = FXMLLoader.load(getClass().getResource("../views/Dashboard.fxml"));
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

    public  void runTask(String question) {

        Collection coll = DependencyParserAPI.DependencyGeneration(question);

        List lists = (List) coll;
        System.out.println("");
        System.out.println("********************Actual dependeincies.**********************");
        for (int i = 0; i < lists.size(); i++) {
            System.out.println(lists.get(i));
        }


        System.out.println("");
        System.out.println("*********Contents of the filtered list of dependencies ********");
        ArrayList<String> list = HelperClass.WordListFilter(coll);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        System.out.println("*************************************************************");
        System.out.println("");

        testdemo.DivideList.PopulateList(list);

        System.out.println("");
        System.out.println("*********Contents of the selectList list(Probable select)********");
        for (int i = 0; i < testdemo.DivideList.selectList.size(); i++) {
            System.out.println(testdemo.DivideList.selectList.get(i));
        }
        System.out.println("*************************************************************");
        System.out.println("");

        System.out.println("");
        System.out.println("*********Contents of the whereList list(Probable where)********");
        for (int i = 0; i < testdemo.DivideList.whereList.size(); i++) {
            System.out.println(DivideList.whereList.get(i));
        }
        System.out.println("*************************************************************");
        System.out.println("");

        QueryGeneratorFlow.GetAll();

        // this code is for displaying resultant output in table form
        String output = QueryGeneratorFlow.GenerateSelect() + "\n";
        output += QueryGeneratorFlow.GenerateFrom() + "\n";
        output += QueryGeneratorFlow.GenerateWhere();

        outputQuery.setText(output);

        String[] heading = new String[QueryGeneratorFlow.select.size()];
        QueryGeneratorFlow.select.toArray(heading);

        ArrayList<ArrayList<String>> test = SqlLibrary.GetQueryResult(output, QueryGeneratorFlow.select);
        Object[][] result = new Object[test.size()][];

        for (int k = 0; k < test.size(); k++) {
            ArrayList<String> temp = test.get(k);
            result[k] = new Object[test.size()];
            for (int l = 0; l < temp.size(); l++) {
                result[k][l] = temp.get(l);
                System.out.println(temp.get(l));
                outputData.setText(temp.get(l));
            }
        }


    }
}
