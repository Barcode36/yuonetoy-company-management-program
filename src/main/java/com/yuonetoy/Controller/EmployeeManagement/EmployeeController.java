package com.yuonetoy.Controller.EmployeeManagement;

import com.yuonetoy.Controller.EmployeeManagement.Add.AddEmployeeController;
import com.yuonetoy.DTO.Employee.EmployeeDetailDTO;
import com.yuonetoy.Service.EmployeeService;
import com.yuonetoy.Tool.SpringFxmlLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class EmployeeController implements Initializable {
    @Autowired
    private SpringFxmlLoader fxmlLoader;
    @Autowired
    private EmployeeService employeeService;

    private Stage primaryStage;

    @FXML
    private TableView<EmployeeDetailDTO> employee_tableView;
    @FXML
    private TableColumn<EmployeeDetailDTO, String> em_c1, em_c2, em_c3, em_c4, em_c5;
    private ObservableList<EmployeeDetailDTO> employees;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initailStatus(){
        callEmployee();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        em_c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        em_c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        em_c3.setCellValueFactory(new PropertyValueFactory<>("area"));
        em_c4.setCellValueFactory(new PropertyValueFactory<>("ph"));
        em_c5.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        employees = employee_tableView.getItems();
    }

    @FXML
    private void handleAddBtnClick(ActionEvent e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);
        dialogStage.setTitle("component/Signup");

        FXMLLoader loader = fxmlLoader.load("/View/EmployeeManagement/AddEmployeeView.fxml");
        AnchorPane page = (AnchorPane) loader.load();

        AddEmployeeController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setEmployees(employees);
        controller.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    @FXML
    private void handleModifyBtnClick(ActionEvent e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);
        dialogStage.setTitle("component/Signup");

        FXMLLoader loader = fxmlLoader.load("/View/EmployeeManagement/AddEmployeeView.fxml");
        AnchorPane page = (AnchorPane) loader.load();

        AddEmployeeController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setMode(1);
        controller.setEmployees(employees);
        controller.setEmployee(employee_tableView.getSelectionModel().getSelectedItem());
        controller.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    @FXML
    private void handleDeleteBtnClick(ActionEvent e) {
        final EmployeeDetailDTO employeeDetailDTO =employee_tableView.getSelectionModel().getSelectedItem();
        employeeService.delete(employeeDetailDTO);
        employees.remove(employeeDetailDTO);
    }

    private void callEmployee() {
        employees.addAll(employeeService.readEmployeeDetailDTO());
    }
}

