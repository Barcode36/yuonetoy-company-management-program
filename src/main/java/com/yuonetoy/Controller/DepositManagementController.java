package com.yuonetoy.Controller;

import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.DTO.SalesAccountSalesDepoistViewDTO;
import com.yuonetoy.Model.DepositType;
import com.yuonetoy.Model.TaxBillType;
import com.yuonetoy.Service.DepositManagementService;
import com.yuonetoy.Tool.MakeAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.LongStringConverter;
import org.controlsfx.control.CheckComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class DepositManagementController implements Initializable {
    @Autowired
    private DepositManagementService depositManagementService;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCheckComboBox();
        initTableView();
    }

    public void initStatus() {
        setCheckComboBoxsItems();
    }

    @FXML
    private TextField totalSales_tf, totalDeposit_tf, totalFees_tf, totalBalance_tf, salesAccount_tf, taxAccount_tf;

    @FXML
    private DatePicker first_dp, last_dp;

    @FXML
    private CheckComboBox<TaxBillType> taxBillType_cb;
    private ObservableList<TaxBillType> taxBillTypes;
    @FXML
    private CheckComboBox<DepositType> depositType_cb;
    private ObservableList<DepositType> depositTypes;
    @FXML
    private CheckComboBox<CompanyTypeDTO> companyType_cb;
    private ObservableList<CompanyTypeDTO> companyTypeDTOS;
    @FXML
    private CheckComboBox<SecondCompanyTypeDTO> secondCompanyType_cb;
    private ObservableList<SecondCompanyTypeDTO> secondCompanyTypeDTOS;
    @FXML
    private CheckComboBox<EmployeeDTO> employee_cb;
    private ObservableList<EmployeeDTO> employeeDTOS;

    private void callTaxBillTypes() {
        taxBillTypes.clear();
        for (int i = 0; i < 3; i++) {
            taxBillTypes.add(new TaxBillType(i));
        }
    }

    private void callDepositTypes() {
        depositTypes.clear();
        for (int i = 0; i < 4; i++) {
            depositTypes.add(new DepositType(i));
        }
    }

    private void callCompanyTypes() {
        companyTypeDTOS.clear();
        companyTypeDTOS.addAll(depositManagementService.getCompanyType());
    }

    private void callSecondCompanyTypes() {
        secondCompanyTypeDTOS.clear();
        secondCompanyTypeDTOS.addAll(depositManagementService.getSecondCompanyType());
    }

    private void callEmployee() {
        employeeDTOS.clear();
        employeeDTOS.addAll(depositManagementService.getEmployee());
    }

    private void initCheckComboBox() {
        taxBillTypes = taxBillType_cb.getItems();
        depositTypes = depositType_cb.getItems();
        companyTypeDTOS = companyType_cb.getItems();
        secondCompanyTypeDTOS = secondCompanyType_cb.getItems();
        employeeDTOS = employee_cb.getItems();
    }

    private void setCheckComboBoxsItems() {
        callTaxBillTypes();
        callDepositTypes();
        callCompanyTypes();
        callSecondCompanyTypes();
        callEmployee();
    }

    @FXML
    private TableView<SalesAccountSalesDepoistViewDTO> sales_tableView;
    @FXML
    private TableColumn<SalesAccountSalesDepoistViewDTO, String> m1, m2, m3, m4, m5, m6, m7, m8, m9, m11, m12, m13;
    @FXML
    private TableColumn<SalesAccountSalesDepoistViewDTO, Long> m14, m16;
    @FXML
    private TableColumn<SalesAccountSalesDepoistViewDTO, LocalDate> m15;
    private ObservableList<SalesAccountSalesDepoistViewDTO> salesAccountSalesDepoistViewDTOList;

    private SalesAccountSalesDepoistViewDTO selectedRow;

    private void initTableView() {
        sales_tableView.setEditable(true);
        salesAccountSalesDepoistViewDTOList = sales_tableView.getItems();

        m1.setCellValueFactory(new PropertyValueFactory<>("confirmationOfPayment"));
        m2.setCellValueFactory(new PropertyValueFactory<>("date"));
        m3.setCellValueFactory(new PropertyValueFactory<>("taxType"));
        m4.setCellValueFactory(new PropertyValueFactory<>("depositType"));
        m5.setCellValueFactory(new PropertyValueFactory<>("companyType"));
        m6.setCellValueFactory(new PropertyValueFactory<>("secondCompanyType"));
        m7.setCellValueFactory(new PropertyValueFactory<>("employee"));
        m8.setCellValueFactory(new PropertyValueFactory<>("taxAccount"));
        m9.setCellValueFactory(new PropertyValueFactory<>("salesAccount"));
        m11.setCellValueFactory(new PropertyValueFactory<>("sales"));
        m12.setCellValueFactory(new PropertyValueFactory<>("feesAmount"));
        m13.setCellValueFactory(new PropertyValueFactory<>("balance"));
        m14.setCellValueFactory(new PropertyValueFactory<>("depositAmount"));
        m15.setCellValueFactory(new PropertyValueFactory<>("depositDate"));
        m16.setCellValueFactory(new PropertyValueFactory<>("remainingAmount"));

        m14.setEditable(true);
        m14.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        m14.setOnEditStart(new EventHandler<TableColumn.CellEditEvent<SalesAccountSalesDepoistViewDTO, Long>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SalesAccountSalesDepoistViewDTO, Long> event) {
                selectedRow = event.getRowValue();
                selectedRow.setDepositAmount(selectedRow.getBalance());
            }
        });
        m14.setOnEditCancel(new EventHandler<TableColumn.CellEditEvent<SalesAccountSalesDepoistViewDTO, Long>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SalesAccountSalesDepoistViewDTO, Long> event) {
                selectedRow.setDepositAmount(0);
                selectedRow.setConfirmationOfPayment(null);
                selectedRow.setDepositDate(null);
                selectedRow.setRemainingAmount(selectedRow.getBalance());
            }
        });

        m14.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SalesAccountSalesDepoistViewDTO, Long>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SalesAccountSalesDepoistViewDTO, Long> event) {
                Long newValue = event.getNewValue();
                selectedRow.setDepositAmount(newValue);
                selectedRow.setRemainingAmount(selectedRow.getBalance() - selectedRow.getDepositAmount());
                LocalDate collectionDate = selectedRow.getDate();

                // YearMonth 날짜를 다루는 클래스
                LocalDate depositDate = YearMonth.from(collectionDate.plusMonths(1)).atEndOfMonth();
                selectedRow.setDepositDate(depositDate);

                if (newValue == selectedRow.getBalance()) {
                    selectedRow.setConfirmationOfPayment("입금 완료");
                }

                countValue();
                selectedRow = null;
            }
        });
    }

    private void countValue() {
        long totalSalesValue = 0;
        long totalFeesAmountValue = 0;
        long totalBalanceValue = 0;
        long totalDepositValue = 0;

        for (SalesAccountSalesDepoistViewDTO salesAccountSalesDepoistViewDTO : salesAccountSalesDepoistViewDTOList) {
            totalSalesValue += salesAccountSalesDepoistViewDTO.getSales();
            totalFeesAmountValue += salesAccountSalesDepoistViewDTO.getFeesAmount();
            totalBalanceValue += salesAccountSalesDepoistViewDTO.getBalance();
            totalDepositValue += salesAccountSalesDepoistViewDTO.getDepositAmount();
        }

        totalSales_tf.setText(String.valueOf(totalSalesValue));
        totalFees_tf.setText(String.valueOf(totalFeesAmountValue));
        totalBalance_tf.setText(String.valueOf(totalBalanceValue));
        totalDeposit_tf.setText(String.valueOf(totalDepositValue));
    }

    private void callSalesAccountSales() {
        List<Integer> taxBillTypeId = new LinkedList<>();

        taxBillType_cb.getCheckModel().getCheckedItems().forEach(taxBillType -> {
            taxBillTypeId.add(taxBillType.getId());
        });

        List<Integer> depositTypeId = new LinkedList<>();
                    depositType_cb.getCheckModel().getCheckedItems().forEach(depositType -> {
                depositTypeId.add(depositType.getId());
            });

        List<Long> companyTypeId = new LinkedList<>();
                    companyType_cb.getCheckModel().getCheckedItems().forEach(companyTypeDTO -> {
                companyTypeId.add(companyTypeDTO.getId());
            });

        List<Long> secondCompanyTypeId = new LinkedList<>();
                    secondCompanyType_cb.getCheckModel().getCheckedItems().forEach(secondCompanyTypeDTO -> {
                secondCompanyTypeId.add(secondCompanyTypeDTO.getId());
            });

        List<Long> employeeId = new LinkedList<>();
                    employee_cb.getCheckModel().getCheckedItems().forEach(employeeDTO -> {
                employeeId.add(employeeDTO.getId());
            });

        salesAccountSalesDepoistViewDTOList.clear();
        List<SalesAccountSalesDepoistViewDTO> getSalesAccountSalesDepoistViewDTOList = depositManagementService.getSalesInfo(taxBillTypeId, depositTypeId, companyTypeId,
                secondCompanyTypeId, employeeId, salesAccount_tf.getText(), taxAccount_tf.getText(),
                first_dp.getValue(), last_dp.getValue());
        salesAccountSalesDepoistViewDTOList.addAll(getSalesAccountSalesDepoistViewDTOList);
    }

    @FXML
    public void handleSaveBtnClick(Event e) throws IOException {
        try{
            salesAccountSalesDepoistViewDTOList.forEach(salesAccountSalesDepoistViewDTO -> {
                if (salesAccountSalesDepoistViewDTO.getConfirmationOfPayment() != null)
                    depositManagementService.updateSalesInfo(salesAccountSalesDepoistViewDTO);
            });

            new MakeAlert().makeInfoMessage(primaryStage, "저장 완료", "완료", "저장 완료", false);
        }catch (Exception e1){
            e1.printStackTrace();
            new MakeAlert().makeErrorMessage(primaryStage, "저장 실패", "실패", "저장 실패", false);
        }
    }

    @FXML
    public void handleLookUpBtnClick(Event e) throws IOException {
        callSalesAccountSales();
        countValue();
    }

    @FXML
    public void handleAllDepositBtnClick(Event e) throws IOException {
        salesAccountSalesDepoistViewDTOList.forEach(salesAccountSalesDepoistViewDTO -> {
            salesAccountSalesDepoistViewDTO.setConfirmationOfPayment("입금 완료");
            salesAccountSalesDepoistViewDTO.setDepositAmount(salesAccountSalesDepoistViewDTO.getBalance());
            LocalDate depositDate = YearMonth.from(salesAccountSalesDepoistViewDTO.getDate().plusMonths(1)).atEndOfMonth();
            salesAccountSalesDepoistViewDTO.setDepositDate(depositDate);
        });
        countValue();
    }
}
