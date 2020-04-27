package com.yuonetoy.Controller.StockManagement;

import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Stock.EmployeeStockDTO;
import com.yuonetoy.DTO.StockHistory.EmployeeStockHistoryDTO;
import com.yuonetoy.Service.EmployeeStockHistoryService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Controller
public class EmployeeStockMoveHistoryController implements Initializable {

    @Autowired
    private EmployeeStockHistoryService employeeStockHistoryService;


    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
        initComboBox();
        initTextFields();
    }

    public void initStatus() {
        callEmployee();

    }

    @FXML
    private TableView<EmployeeStockHistoryDTO> history_tableview;
    @FXML
    private TableColumn<EmployeeStockHistoryDTO, String> m1, m2, m3, m4, m5, m6, m7;
    private ObservableList<EmployeeStockHistoryDTO> employeeStockHistoryDTOS;

    @FXML
    private DatePicker firstDate_dp, lastDate_dp;

    @FXML
    private ComboBox<EmployeeDTO> employee_cb;
    @FXML
    private ListView<EmployeeStockDTO> employeeStock_lv;
    private ObservableList<EmployeeStockDTO> employeeStockDTOList;
    private FilteredList<EmployeeStockDTO> employeeStockDTOFilteredList;

    @FXML
    private TextField search_tf;

    private void initTextFields() {
        search_tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null) return;

                employeeStockDTOFilteredList.setPredicate(companyStockDTO -> {
                    if (companyStockDTO.getStockName().contains(newValue) || newValue.hashCode() == "".hashCode())
                        return true;
                    else
                        return false;
                });

                employeeStock_lv.setItems(employeeStockDTOFilteredList);
            }
        });
    }

    private void initComboBox() {
        employee_cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmployeeDTO>() {
            @Override
            public void changed(ObservableValue<? extends EmployeeDTO> observable, EmployeeDTO oldValue, EmployeeDTO newValue) {
                callEmployeeStock();
            }
        });

        employeeStockDTOList = employeeStock_lv.getItems();
        employeeStockDTOFilteredList = employeeStockDTOList.filtered(employeeStockDTO -> true);
    }

    private void initTableView() {
        m1.setCellValueFactory(new PropertyValueFactory<>("type"));
        m2.setCellValueFactory(new PropertyValueFactory<>("employee"));
        m3.setCellValueFactory(new PropertyValueFactory<>("targetName"));
        m4.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        m5.setCellValueFactory(new PropertyValueFactory<>("sendCount"));
        m6.setCellValueFactory(new PropertyValueFactory<>("stockCount"));
        m7.setCellValueFactory(new PropertyValueFactory<>("date"));

        employeeStockHistoryDTOS = history_tableview.getItems();
    }

    private void callEmployee() {
        employee_cb.getItems().addAll(employeeStockHistoryService.getEmployeeList());
    }

    private void callEmployeeStock() {
        employeeStockDTOList.clear();
        EmployeeDTO employeeDTO = employee_cb.getSelectionModel().getSelectedItem();
        employeeStockDTOList.addAll(employeeStockHistoryService.getEmployeeStock(employeeDTO));
    }

    @FXML
    private void callEmployeeStockHistory() {
        employeeStockHistoryDTOS.clear();
        EmployeeStockDTO employeeStockDTO = employeeStock_lv.getSelectionModel().getSelectedItem();
        EmployeeDTO employeeDTO = employee_cb.getSelectionModel().getSelectedItem();

        LocalDate firstDate = firstDate_dp.getValue();
        LocalDate lastDate = lastDate_dp.getValue();

        Long beforeStockCount;
        if (employeeStockDTO.isProduct()) {
            long productId = employeeStockDTO.getProduct().getId();
            beforeStockCount = employeeStockHistoryService.getBeforeProductStockCount(employeeDTO.getId(), productId, firstDate);
        } else {
            long machineId = employeeStockDTO.getMachine().getId();
            beforeStockCount = employeeStockHistoryService.getBeforeMachineStockCount(employeeDTO.getId(), machineId, firstDate);
        }

        EmployeeStockHistoryDTO employeeStockHistoryDTO = new EmployeeStockHistoryDTO();
        employeeStockHistoryDTO.setType("전일 재고");
        employeeStockHistoryDTO.setStockCount(beforeStockCount == null ? 0 : beforeStockCount);
        employeeStockHistoryDTO.setDate(firstDate_dp.getValue().minusDays(1));
        employeeStockHistoryDTOS.add(employeeStockHistoryDTO);

        employeeStockHistoryDTOS.addAll(employeeStockHistoryService.getEmployeeStockMoveHistory(employeeStockDTO, firstDate, lastDate));

        for (int i = 1; i < employeeStockHistoryDTOS.size(); i++) {
            employeeStockHistoryDTOS.get(i).setStockCount(employeeStockHistoryDTOS.get(i - 1).getStockCount() + employeeStockHistoryDTOS.get(i).getSendCount());
        }
    }

}
