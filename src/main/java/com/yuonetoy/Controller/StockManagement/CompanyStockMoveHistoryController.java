package com.yuonetoy.Controller.StockManagement;

import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Stock.CompanyStockDTO;
import com.yuonetoy.DTO.StockHistory.CompanyStockHistoryDTO;
import com.yuonetoy.Service.CompanyService;
import com.yuonetoy.Service.SendStockHistoryService;
import com.yuonetoy.Service.SendStockService;
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
public class CompanyStockMoveHistoryController implements Initializable {

    @Autowired
    private SendStockHistoryService sendStockHistoryService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private SendStockService sendStockService;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTextFields();
        initComboBox();
        initTableView();
        initDatePicker();
    }

    public void initStatus() {
        callCompany();
    }

    @FXML
    private TableView<CompanyStockHistoryDTO> history_tableview;

    @FXML
    private TableColumn<CompanyStockHistoryDTO, String> m1, m2, m3, m4, m5, m6, m7;

    private ObservableList<CompanyStockHistoryDTO> companyStockHistoryDTOS;

    @FXML
    private DatePicker firstDate_dp, lastDate_dp;


    private void initDatePicker() {

    }

    private void initComboBox() {
        company_cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CompanyDTO>() {
            @Override
            public void changed(ObservableValue<? extends CompanyDTO> observable, CompanyDTO oldValue, CompanyDTO newValue) {
                callCompanyStock();
            }
        });

        companyStockDTOS = companyStock_lv.getItems();
    }

    private void initTableView() {
        m1.setCellValueFactory(new PropertyValueFactory<>("type"));
        m2.setCellValueFactory(new PropertyValueFactory<>("company"));
        m3.setCellValueFactory(new PropertyValueFactory<>("targetName"));
        m4.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        m5.setCellValueFactory(new PropertyValueFactory<>("sendCount"));
        m6.setCellValueFactory(new PropertyValueFactory<>("stockCount"));
        m7.setCellValueFactory(new PropertyValueFactory<>("date"));

        companyStockHistoryDTOS = history_tableview.getItems();
    }

    @FXML
    private ComboBox<CompanyDTO> company_cb;

    @FXML
    private ListView<CompanyStockDTO> companyStock_lv;
    private ObservableList<CompanyStockDTO> companyStockDTOS;
    private FilteredList<CompanyStockDTO> companyStockDTOFilteredList;

    @FXML
    private TextField search_tf;

    private void initTextFields() {
        search_tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null) return;

                companyStockDTOFilteredList.setPredicate(companyStockDTO -> {
                    if (companyStockDTO.getStockName().contains(newValue) || newValue.hashCode() == "".hashCode())
                        return true;
                    else
                        return false;
                });

                companyStock_lv.setItems(companyStockDTOFilteredList);
            }
        });
    }

    private void callCompanyStock() {
        companyStockDTOS.clear();
        companyStockDTOS.addAll(sendStockService.getCompanyStock(company_cb.getSelectionModel().getSelectedItem()));
        companyStockDTOFilteredList = companyStockDTOS.filtered(companyStockDTO -> true);
    }

    private void callCompany() {
        company_cb.getItems().addAll(companyService.readCompany());
    }


    @FXML
    private void callCompanyStockHistory() {
        companyStockHistoryDTOS.clear();
        CompanyStockDTO companyStockDTO = companyStock_lv.getSelectionModel().getSelectedItem();
        CompanyDTO companyDTO = company_cb.getSelectionModel().getSelectedItem();

        LocalDate firstDate = firstDate_dp.getValue();
        LocalDate lastDate = lastDate_dp.getValue();

        Long beforeStockCount;
        if (companyStockDTO.isProduct()) {
            long productId = companyStockDTO.getProduct().getId();
            beforeStockCount = sendStockHistoryService.getBeforeProductStockCount(companyDTO.getId(), productId, firstDate);
        } else {
            long machineId = companyStockDTO.getMachine().getId();
            beforeStockCount = sendStockHistoryService.getBeforeMachineStockCount(companyDTO.getId(), machineId, firstDate);
        }

        CompanyStockHistoryDTO companyStockHistoryDTO = new CompanyStockHistoryDTO();
        companyStockHistoryDTO.setType("전일 재고");
        companyStockHistoryDTO.setStockCount(beforeStockCount == null ? 0 : beforeStockCount);
        companyStockHistoryDTO.setDate(firstDate_dp.getValue().minusDays(1));
        companyStockHistoryDTOS.add(companyStockHistoryDTO);

        companyStockHistoryDTOS.addAll(sendStockHistoryService.getCompanyStockMoveHistory(companyStockDTO, firstDate, lastDate));

        for (int i = 1; i < companyStockHistoryDTOS.size(); i++) {
            companyStockHistoryDTOS.get(i).setStockCount(companyStockHistoryDTOS.get(i - 1).getStockCount() + companyStockHistoryDTOS.get(i).getSendCount());
        }
    }
}
