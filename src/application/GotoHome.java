package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GotoHome {

    public void HomeSection() throws Exception {
        Stage dashboardStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/Dashboard.fxml"));
        Scene scene = new Scene(root);
        dashboardStage.setScene(scene);
        dashboardStage.setTitle("Home | Natural Language Interface For Database");
        dashboardStage.show();
        dashboardStage.setResizable(false);
    }

}
