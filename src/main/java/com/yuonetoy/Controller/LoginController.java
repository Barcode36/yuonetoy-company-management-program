package com.yuonetoy.Controller;

import com.yuonetoy.InitDataBase;
import com.yuonetoy.Tool.SpringFxmlLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginController implements Initializable {
    private Stage primaryStage;

    public LoginController() {
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Parent parent;

    public void setParent(Parent parent) {
        this.parent = parent;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Autowired
    private SpringFxmlLoader fxmlLoader;

    @FXML
    private TextField id_tf;

    @Autowired
    InitDataBase initDataBase;

    @FXML
    private void handleLogin(ActionEvent e) throws IOException {
        primaryStage.close();
        FXMLLoader loader = fxmlLoader.load("/View/MainView.fxml");
        Parent page = (AnchorPane) loader.load();

        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });

        if (id_tf.getText().equals("BackUp")) {
            initDataBase.initDataBase();
        }

        MainController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);
    }

    @FXML
    private void handleOpenSignupDialog(ActionEvent e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);
        dialogStage.setTitle("component/Signup");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SignUpView.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        SignUpController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }
}
