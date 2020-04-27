package com.yuonetoy;

import com.yuonetoy.Controller.LoginController;
import com.yuonetoy.Tool.SpringFxmlLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApplication extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent parent;
    private LoginController controller;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(MainApplication.class);

        FXMLLoader loader = springContext.getBean(SpringFxmlLoader.class).load("/View/LoginView.fxml");
        parent = loader.load();

        controller = loader.getController();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Yuonetoy");
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.show();

        controller.setParent(parent);
        controller.setPrimaryStage(stage);
    }
}
