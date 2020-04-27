package com.yuonetoy.Tool;

import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SpringFxmlLoader {

    @Autowired
    private ApplicationContext springContext ;

    public FXMLLoader load(String resourceName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resourceName));
        loader.setControllerFactory(springContext::getBean);
        return loader;
    }
}