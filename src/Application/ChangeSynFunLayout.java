package Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeSynFunLayout {

    public void ChangeFun() throws IOException {
        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/addFunction.fxml"));
        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.setTitle("Add Functions");
        dashboardStage.show();
        dashboardStage.setResizable(false);
    }

    public void ChangeSyn() throws IOException {

        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/addSynonym.fxml"));
        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.setTitle("Add Synonym");
        dashboardStage.show();
        dashboardStage.setResizable(false);
    }
}
