package com.yuonetoy.Controller.StockManagement;

import com.yuonetoy.DTO.*;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.StockHistory.SalesAccountStockHistoryDTO;
import com.yuonetoy.Service.BusinessJournalHistoryService;
import com.yuonetoy.Service.MachineManagementService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.IndexedCheckModel;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class SalesAccountStockMoveHistoryController implements Initializable {
    private Stage dialogStage;

    @Autowired
    BusinessJournalHistoryService businessJournalHistoryService;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private ComboBox<SalesAccountDTO> salesAccount_cb;
    private List<SalesAccountDTO> salesAccounts;


    public void initStatus() {
        salesAccounts = callSalesAccount();
        salesAccount_cb.getItems().addAll(salesAccounts);

        AutoCompletionBinding<SalesAccountDTO> autoCompletionBinding = TextFields.bindAutoCompletion(salesAccount_cb.getEditor(), salesAccounts);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
        initComboBox();
    }

    @FXML
    TableView<SalesAccountStockHistoryDTO> history_tableview;
    @FXML
    TableColumn<SalesAccountStockHistoryDTO, String> m1, m2, m3, m4, m5, m6, m7, m8;

    private ObservableList<SalesAccountStockHistoryDTO> businessJournalHistoryDTOList;


    private void initTableView() {
        m1.setCellValueFactory(new PropertyValueFactory<>("type"));
        m2.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        m3.setCellValueFactory(new PropertyValueFactory<>("machineName"));
        m4.setCellValueFactory(new PropertyValueFactory<>("productName"));
        m5.setCellValueFactory(new PropertyValueFactory<>("supplyCount"));
        m6.setCellValueFactory(new PropertyValueFactory<>("salesValue"));
        m7.setCellValueFactory(new PropertyValueFactory<>("stockValue"));
        m8.setCellValueFactory(new PropertyValueFactory<>("date"));
        businessJournalHistoryDTOList = history_tableview.getItems();
    }

    @FXML
    private DatePicker firstDate_dp, lastDate_dp;

    @FXML
    private TextField supplyCount_tf, salesCount_tf;

    @FXML
    private void getSalesAccountStockMoveHistory() {
        businessJournalHistoryDTOList.clear();
        getBeforeStockCount();

        long salesAccountMachineId = salesAccountMachine_lv.getSelectionModel().getSelectedItem().getId();

        LocalDate firstDate = firstDate_dp.getValue();
        LocalDate lastDate = lastDate_dp.getValue();

        businessJournalHistoryDTOList.addAll(businessJournalHistoryService.getSalesAccountStockMoveHistory(salesAccountMachineId, firstDate, lastDate));

        int sumSupplyCount = 0;
        int sumSalesCount = 0;

        for (int i = 1; i < businessJournalHistoryDTOList.size(); i++) {
            businessJournalHistoryDTOList.get(i).setStockValue(businessJournalHistoryDTOList.get(i-1).getStockValue() + businessJournalHistoryDTOList.get(i).getSupplyCount());
            sumSupplyCount += businessJournalHistoryDTOList.get(i).getSupplyCount();
            sumSalesCount += businessJournalHistoryDTOList.get(i).getSalesValue();
        }

        supplyCount_tf.setText(String.valueOf(sumSupplyCount));
        salesCount_tf.setText(String.valueOf(sumSalesCount));
    }

    private void getBeforeStockCount() {
        long salesAccountMachineId = salesAccountMachine_lv.getSelectionModel().getSelectedItem().getId();
        List<Object[]> getBeforeStockAndSales = businessJournalHistoryService.getBeforeStockCount(salesAccountMachineId, firstDate_dp.getValue());

        long beforeStock = getBeforeStockAndSales.get(0)[0] == null ? (long) 0 : (long)getBeforeStockAndSales.get(0)[0];
        long beforeSales =  getBeforeStockAndSales.get(0)[1] == null ? (long) 0 : (long)getBeforeStockAndSales.get(0)[1];

        SalesAccountStockHistoryDTO salesAccountStockHistoryDTO = new SalesAccountStockHistoryDTO();
        salesAccountStockHistoryDTO.setType("전일 재고");
        salesAccountStockHistoryDTO.setStockValue(beforeStock);
        salesAccountStockHistoryDTO.setSalesValue(beforeSales);
        salesAccountStockHistoryDTO.setDate(firstDate_dp.getValue().minusDays(1));

        businessJournalHistoryDTOList.add(salesAccountStockHistoryDTO);
    }

    @FXML
    private ListView<SalesAccountMachineDTO> salesAccountMachine_lv;
    private ObservableList<SalesAccountMachineDTO> salesAccountMachineDTOS;

    private void callSalesAccountMachineDTO(long salesAccountId) {
        salesAccountMachineDTOS.clear();
        salesAccountMachineDTOS.addAll(machineManagementService.getSalesAccountMachineDTO(salesAccountId));
    }

    private void initComboBox() {
        salesAccountMachineDTOS = salesAccountMachine_lv.getItems();

        salesAccount_cb.getEditor().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue && !newValue) {
                    Optional<SalesAccountDTO> optionalSalesAccountDTO = salesAccounts.stream().filter(salesAccountDTO -> {
                        if (salesAccountDTO.getName().equals(salesAccount_cb.getEditor().getText()))
                            return true;
                        else
                            return false;
                    }).findFirst();

                    if (optionalSalesAccountDTO.isPresent()) {
                        callSalesAccountMachineDTO(optionalSalesAccountDTO.get().getId());
                    }
                }
            }
        });
    }

    @Autowired
    MachineManagementService machineManagementService;

    private List<SalesAccountDTO> callSalesAccount() {
        return machineManagementService.getSalesAccountList();
    }
}
