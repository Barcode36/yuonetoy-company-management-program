package com.yuonetoy.Controller.BusinessManagement;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.BusinessJournalHistoryDTO;
import com.yuonetoy.DTO.BusinessJournalHistoryListDTO;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.BusinessJournalHistoryList;
import com.yuonetoy.Service.BusinessJournalHistoryService;
import com.yuonetoy.Tool.MakeAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class BusinessJournalLookupController implements Initializable {
    private Stage dialogStage;

    @Autowired
    private BusinessJournalHistoryService businessJournalHistoryService;

    private SalesAccountDTO selectedSalesAccount;

    @FXML
    private ComboBox<SalesAccountDTO> salesAccount_cb;
    private ObservableList<SalesAccountDTO> salesAccounts;


    @FXML
    private TableView<BusinessJournalHistoryListDTO> tableView1;
    @FXML
    private TableColumn<BusinessJournalHistoryListDTO, String> mc1, mc2, mc3, mc4;

    private ObservableList<BusinessJournalHistoryListDTO> businessJournalHistoryListDTOList;

    @FXML
    private TableView<BusinessJournalHistoryDTO> tableView2;
    @FXML
    private TableColumn<BusinessJournalHistoryDTO, String> m1, m5, m11;

    @FXML
    private TableColumn<BusinessJournalHistoryDTO, Double> m4;

    @FXML
    private TableColumn<BusinessJournalHistoryDTO, Integer> m2, m3, m6, m7, m8;

    @FXML
    private TableColumn<BusinessJournalHistoryDTO, Long> m9, m10;

    private ObservableList<BusinessJournalHistoryDTO> businessJournalHistoryDTOList;

    @FXML
    private TableColumn<BusinessJournalHistoryDTO, String> machine_tc, employee_tc, sales_tc;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private DatePicker firstDate_dp, lastDate_dp;

    public void initStatus() {

    }

    private List<SalesAccountDTO> callSalesAccount() {
        return businessJournalHistoryService.readSalesAccount();
    }

    public void callDateNowBusinessJournalHistory() {
        firstDate_dp.setValue(LocalDate.now());
        lastDate_dp.setValue(LocalDate.now());
        businessJournalHistoryListDTOList.addAll(businessJournalHistoryService.findListByAll(firstDate_dp.getValue(), lastDate_dp.getValue(), selectedSalesAccount));
    }

    public void callSalesAccountBusinessJournalHistory(String salesAccountName) {
        YearMonth yearMonth = YearMonth.from(LocalDate.now());
        firstDate_dp.setValue(yearMonth.atDay(1));
        lastDate_dp.setValue(yearMonth.atEndOfMonth());


        Optional<SalesAccountDTO> optionalSalesAccountDTO = salesAccounts.stream().filter(salesAccountDTO -> {
            if (salesAccountDTO.getName().equals(salesAccountName))
                return true;
            else
                return false;
        }).findFirst();

        if (optionalSalesAccountDTO.isPresent()) {
            selectedSalesAccount = optionalSalesAccountDTO.get();
            salesAccount_cb.setValue(selectedSalesAccount);
        }else {
            selectedSalesAccount = null;
        }

        businessJournalHistoryListDTOList.addAll(businessJournalHistoryService.findListByAll(firstDate_dp.getValue(), lastDate_dp.getValue(), selectedSalesAccount));
    }

    @FXML
    private void callBusinessJournalHistory() {
        businessJournalHistoryListDTOList.clear();
        businessJournalHistoryListDTOList.addAll(businessJournalHistoryService.findListByAll(firstDate_dp.getValue(), lastDate_dp.getValue(), selectedSalesAccount));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
    }

    private void initTableView() {
        mc1.setCellValueFactory(new PropertyValueFactory<>("type"));
        mc2.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        mc3.setCellValueFactory(new PropertyValueFactory<>("salesAccountName"));
        mc4.setCellValueFactory(new PropertyValueFactory<>("date"));
        businessJournalHistoryListDTOList = tableView1.getItems();

        tableView1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BusinessJournalHistoryListDTO>() {
            @Override
            public void changed(ObservableValue<? extends BusinessJournalHistoryListDTO> observable, BusinessJournalHistoryListDTO oldValue, BusinessJournalHistoryListDTO newValue) {
                if (newValue == null)
                    return;

                if (newValue.getType().hashCode() == "외상 매출".hashCode()) {
                    machine_tc.setVisible(false);
                    employee_tc.setVisible(false);
                    sales_tc.setVisible(true);
                } else if (newValue.getType().hashCode() == "신규 입점".hashCode()) {
                    machine_tc.setVisible(true);
                    employee_tc.setVisible(true);
                    sales_tc.setVisible(false);
                } else if (newValue.getType().hashCode() == "기계 관리".hashCode()){
                    machine_tc.setVisible(true);
                    employee_tc.setVisible(false);
                    sales_tc.setVisible(false);
                } else if (newValue.getType().hashCode() == "상품 보충".hashCode()) {
                    machine_tc.setVisible(false);
                    employee_tc.setVisible(true);
                    sales_tc.setVisible(false);
                } else if (newValue.getType().hashCode() == "재고 관리".hashCode()) {
                    machine_tc.setVisible(true);
                    employee_tc.setVisible(true);
                    sales_tc.setVisible(false);
                }
                businessJournalHistoryDTOList.clear();
                businessJournalHistoryDTOList.addAll(businessJournalHistoryService.findByBusinessJournalHistoryListId(newValue.getId()));
            }
        });

        m1.setCellValueFactory(new PropertyValueFactory<>("machineName"));
        m2.setCellValueFactory(new PropertyValueFactory<>("machineCount"));
        m3.setCellValueFactory(new PropertyValueFactory<>("initialQuantity"));
        m4.setCellValueFactory(new PropertyValueFactory<>("fees"));
        m5.setCellValueFactory(new PropertyValueFactory<>("productName"));
        m6.setCellValueFactory(new PropertyValueFactory<>("supplyCount"));
        m7.setCellValueFactory(new PropertyValueFactory<>("plusValue"));
        m8.setCellValueFactory(new PropertyValueFactory<>("minusValue"));
        m9.setCellValueFactory(new PropertyValueFactory<>("salesValue"));
        m10.setCellValueFactory(new PropertyValueFactory<>("refundValue"));
        m11.setCellValueFactory(new PropertyValueFactory<>("secondType"));
        businessJournalHistoryDTOList = tableView2.getItems();

        m2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        m2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Integer> event) {
                event.getRowValue().setMachineCount(event.getNewValue());
            }
        });
        m3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        m3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Integer> event) {
                event.getRowValue().setInitialQuantity(event.getNewValue());
            }
        });
        m4.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        m4.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Double> event) {
                event.getRowValue().setFees(event.getNewValue());
            }
        });
        m6.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        m6.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Integer> event) {
                event.getRowValue().setSupplyCount(event.getNewValue());
            }
        });
        m7.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        m7.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Integer> event) {
                event.getRowValue().setPlusValue(event.getNewValue());
            }
        });
        m8.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        m8.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Integer> event) {
                event.getRowValue().setMinusValue(event.getNewValue());
            }
        });
        m9.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        m9.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Long>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Long> event) {
                event.getRowValue().setSalesValue(event.getNewValue());
            }
        });
        m10.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        m10.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Long>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<BusinessJournalHistoryDTO, Long> event) {
                event.getRowValue().setRefundValue(event.getNewValue());
            }
        });

        selectedSalesAccount = null;

        salesAccounts = salesAccount_cb.getItems();
        salesAccounts.addAll(callSalesAccount());

        AutoCompletionBinding<SalesAccountDTO> autoCompletionBinding = TextFields.bindAutoCompletion(salesAccount_cb.getEditor(), salesAccounts);

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
                        selectedSalesAccount = optionalSalesAccountDTO.get();
                        salesAccount_cb.getValue();
                    }else {
                        selectedSalesAccount = null;
                    }

                }
            }
        });
    }

    @FXML
    private void updateBusinessJournal() {
        businessJournalHistoryDTOList.forEach(businessJournalHistoryDTO -> {
            businessJournalHistoryService.updateBusinessJournalHistory(businessJournalHistoryDTO);
        });
        new MakeAlert().makeInfoMessage(dialogStage, "알림", "수정 완료", "수정 완료", false);
    }

    @FXML
    private void deleteBusinessJournal() {
        BusinessJournalHistoryListDTO businessJournalHistoryListDTO = tableView1.getSelectionModel().getSelectedItem();
        List<Long[]> businessJournalHistoryList = businessJournalHistoryService.deleteBusinessJournalHistory(businessJournalHistoryListDTO.getId());
        businessJournalHistoryService.updateDeletedStock(businessJournalHistoryList);

        businessJournalHistoryListDTOList.remove(businessJournalHistoryListDTO);
        businessJournalHistoryDTOList.clear();

        new MakeAlert().makeInfoMessage(dialogStage, "삭제 완료", "삭제 완료", "삭제 완료", false);
    }
}
