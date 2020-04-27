package com.yuonetoy.Controller.EmployeeManagement.Add;

import com.yuonetoy.DTO.Area.EmployeeAreaDTO;
import com.yuonetoy.DTO.Employee.EmployeeDetailDTO;
import com.yuonetoy.Service.EmployeeAreaService;
import com.yuonetoy.Service.EmployeeService;
import com.yuonetoy.Tool.MakeAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Controller
public class AddEmployeeController implements Initializable {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    EmployeeAreaService employeeAreaService;

    private int mode = 0;

    public void setMode(int mode) {
        this.mode = mode;
        if (mode == 0) add_btn.setText("추가");
        else if (mode == 1) add_btn.setText("수정");
    }

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private ObservableList<EmployeeDetailDTO> employees;

    public void setEmployees(ObservableList<EmployeeDetailDTO> employees) {
        this.employees = employees;
    }

    private int selectedIndex;
    private EmployeeDetailDTO selectedEmployeeDetailDTO;

    public void setEmployee(EmployeeDetailDTO employeeDetailDTO) {
        selectedIndex = employees.indexOf(employeeDetailDTO);
        selectedEmployeeDetailDTO = employeeDetailDTO;
    }

    @FXML
    private TextField name_tf, address_tf, ph_tf;
    @FXML
    private DatePicker entryDate_dp;
    @FXML
    private ChoiceBox<EmployeeAreaDTO> area_cb;
    @FXML
    private Button add_btn;

    private ObservableList<EmployeeAreaDTO> employeeAreas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        employeeAreas = area_cb.getItems();
    }

    public void initStatus(){
        callEmployeeArea();

        if (mode == 1)
            printEmployee();
    }

    @FXML
    private void handleAdd() {
        addEmployee();
    }

    public void printEmployee() {
        name_tf.setText(selectedEmployeeDetailDTO.getName());
        address_tf.setText(selectedEmployeeDetailDTO.getAddress());
        ph_tf.setText(selectedEmployeeDetailDTO.getPh());
        entryDate_dp.setValue(selectedEmployeeDetailDTO.getEntryDate());

        area_cb.getItems().forEach(employeeAreaDTO -> {
            if (employeeAreaDTO.getId() == selectedEmployeeDetailDTO.getArea().getId()) {
                area_cb.getSelectionModel().select(employeeAreaDTO);
                return;
            }
        });
    }

    public void addEmployee() {
        final String name = name_tf.getText();
        final String ph = ph_tf.getText();
        final String address = address_tf.getText();
        final LocalDate entryDate = entryDate_dp.getValue();
        final EmployeeAreaDTO area = area_cb.getValue();

        if (mode == 0) {
            EmployeeDetailDTO employeeDetailDTO = employeeService.addEmployee(name, ph, address, entryDate, area);
            employees.add(employeeDetailDTO);

            new MakeAlert().makeInfoMessage(dialogStage, "추가 완료","완료", "추가 완료", true);
        } else if (mode == 1) {
            EmployeeDetailDTO employeeDetailDTO = employeeService.update(selectedEmployeeDetailDTO, name, ph, address , entryDate, area);
            employees.set(selectedIndex, employeeDetailDTO);

            new MakeAlert().makeInfoMessage(dialogStage, "수정 완료","완료", "수정 완료", true);
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private void callEmployeeArea() {
        employeeAreas.addAll(employeeAreaService.readEmployeeArea());
    }
}
