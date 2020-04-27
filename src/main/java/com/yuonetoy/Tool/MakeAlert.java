package com.yuonetoy.Tool;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class MakeAlert {
    public void makeInfoMessage(Stage dialogStage, String title, String headerText, String contentText, boolean setClose){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.get();
        if (button == ButtonType.OK) {
            if (setClose)
            dialogStage.close();
        }
    }

    public void makeErrorMessage(Stage dialogStage, String title, String headerText, String contentText, boolean setClose){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.get();
        if (button == ButtonType.OK) {
            if (setClose)
                dialogStage.close();
        }
    }
}
