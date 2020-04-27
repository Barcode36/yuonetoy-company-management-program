package com.yuonetoy.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private Stage dialogStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleOk() {
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
}
