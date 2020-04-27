package com.yuonetoy.Controller.SalesManagement;

import com.yuonetoy.DTO.SalesAccountTotalSalesDTO;
import com.yuonetoy.Service.SalesManagementService;
import com.yuonetoy.Tool.PredicateCase;
import com.yuonetoy.Tool.SpringFxmlLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.CheckComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;

@Controller
public class SalesManagementController implements Initializable {
    @Autowired
    SalesManagementService salesManagementService;
    @Autowired
    private PredicateCase predicateCase;

    @Autowired
    private SpringFxmlLoader fxmlLoader;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private RadioButton total_rb, monthMachineSales_rb;

    @FXML
    private DatePicker start_dp, last_dp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(total_rb);
        toggleGroup.getToggles().add(monthMachineSales_rb);

        total_rb.setSelected(true);
        monthMachineSales_rb.setSelected(false);

        initTableView();

        initCheckComboBox();
    }

    public void initStatus() {
        callSalesAccountTotalSales();
        setCheckComboBoxsItems();
    }

    @FXML
    private TableView<SalesAccountTotalSalesDTO> sales_tableView;
    @FXML
    private TableColumn<SalesAccountTotalSalesDTO, String> m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16, m17, machine_c, m18, m19;
    private ObservableList<SalesAccountTotalSalesDTO> salesAccountMachineSalesDTOList;
    private FilteredList<SalesAccountTotalSalesDTO> salesAccountMachineSalesDTOFilteredList;

    private void initTableView() {
        m1.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        m2.setCellValueFactory(new PropertyValueFactory<>("salesAccountArea"));
        m3.setCellValueFactory(new PropertyValueFactory<>("companyType"));
        m4.setCellValueFactory(new PropertyValueFactory<>("secondCompanyType"));
        m5.setCellValueFactory(new PropertyValueFactory<>("employee"));
        m6.setCellValueFactory(new PropertyValueFactory<>("salesAccount"));
        m7.setCellValueFactory(new PropertyValueFactory<>("SalesAccountMachineName"));
        m8.setCellValueFactory(new PropertyValueFactory<>("machineCount"));
        m9.setCellValueFactory(new PropertyValueFactory<>("productCount"));
        m10.setCellValueFactory(new PropertyValueFactory<>("plusValue"));
        m11.setCellValueFactory(new PropertyValueFactory<>("minusValue"));
        m12.setCellValueFactory(new PropertyValueFactory<>("fees"));
        m13.setCellValueFactory(new PropertyValueFactory<>("sales"));
        m14.setCellValueFactory(new PropertyValueFactory<>("feeAmount"));
        m15.setCellValueFactory(new PropertyValueFactory<>("balance"));
        m16.setCellValueFactory(new PropertyValueFactory<>("refundAmount"));
        m17.setCellValueFactory(new PropertyValueFactory<>("date"));
        m18.setCellValueFactory(new PropertyValueFactory<>("state"));
        m19.setCellValueFactory(new PropertyValueFactory<>("withdrawalDate"));

        salesAccountMachineSalesDTOList = sales_tableView.getItems();

        m17.setVisible(false);
    }

    @FXML
    private CheckComboBox<String> employee_cb;
    private ObservableList<String> employees;

    @FXML
    private CheckComboBox<String> area_cb;
    private ObservableList<String> areas;

    @FXML
    private CheckComboBox<String> companyType_cb;
    private ObservableList<String> companyTypes;

    @FXML
    private CheckComboBox<String> secondCompanyType_cb;
    private ObservableList<String> secondCompanyTypes;

    @FXML
    private ComboBox<String> salesAccount_cb;
    private ObservableList<String> salesAccounts;

    @FXML
    private CheckComboBox<String> machine_cb;
    private ObservableList<String> machines;

    private void setCheckComboBoxsItems() {
        salesAccounts.clear();
        areas.clear();
        companyTypes.clear();
        secondCompanyTypes.clear();
        employees.clear();
        machines.clear();

        HashSet<String> salesAccountHashSet = new HashSet<>();
        HashSet<String> salesAccountAreaHashSet = new HashSet<>();
        HashSet<String> companyTypeDTOHashSet = new HashSet<>();
        HashSet<String> secondCompanyTypeDTOHashSet = new HashSet<>();
        HashSet<String> employeeDTOHashSet = new HashSet<>();
        HashSet<String> machineHashSet = new HashSet<>();

        salesAccountMachineSalesDTOList.forEach(salesAccountTotalSalesDTO -> {
            salesAccountHashSet.add(salesAccountTotalSalesDTO.getSalesAccount());
            salesAccountAreaHashSet.add(salesAccountTotalSalesDTO.getSalesAccountArea());
            companyTypeDTOHashSet.add(salesAccountTotalSalesDTO.getCompanyType());
            secondCompanyTypeDTOHashSet.add(salesAccountTotalSalesDTO.getSecondCompanyType());
            employeeDTOHashSet.add(salesAccountTotalSalesDTO.getEmployee());
            machineHashSet.add(salesAccountTotalSalesDTO.getSalesAccountMachineName());
        });

        salesAccounts.addAll(salesAccountHashSet);
        areas.addAll(salesAccountAreaHashSet);
        companyTypes.addAll(companyTypeDTOHashSet);
        if (secondCompanyTypeDTOHashSet.iterator().next() != null)
            secondCompanyTypes.addAll(secondCompanyTypeDTOHashSet);
        employees.addAll(employeeDTOHashSet);
        machines.addAll(machineHashSet);
    }

    private void initCheckComboBox() {
        employees = employee_cb.getItems();
        employee_cb.getCheckModel().getCheckedItems().addListener(listChangeListener);

        areas = area_cb.getItems();
        area_cb.getCheckModel().getCheckedItems().addListener(listChangeListener);

        companyTypes = companyType_cb.getItems();
        companyType_cb.getCheckModel().getCheckedItems().addListener(listChangeListener);

        secondCompanyTypes = secondCompanyType_cb.getItems();
        secondCompanyType_cb.getCheckModel().getCheckedItems().addListener(listChangeListener);

        salesAccounts = salesAccount_cb.getItems();
        salesAccount_cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                salesAccountMachineSalesDTOFilteredList.setPredicate(predicateCase.getSaleAccountTotalSalesFilter(area_cb, "area")
                        .and(predicateCase.getSaleAccountTotalSalesFilter(employee_cb, "employee"))
                        .and(predicateCase.getSaleAccountTotalSalesFilter(companyType_cb, "companyType"))
                        .and(predicateCase.getSaleAccountTotalSalesFilter(secondCompanyType_cb, "secondCompanyType"))
                        .and(predicateCase.getSaleAccountTotalSalesFilter(salesAccount_cb)));
                sales_tableView.setItems(salesAccountMachineSalesDTOFilteredList);
            }
        });

        machines = machine_cb.getItems();
        machine_cb.getCheckModel().getCheckedItems().addListener(listChangeListener);
    }

    ListChangeListener listChangeListener = new ListChangeListener() {
        @Override
        public void onChanged(Change c) {
            salesAccountMachineSalesDTOFilteredList.setPredicate(predicateCase.getSaleAccountTotalSalesFilter(area_cb, "area")
                    .and(predicateCase.getSaleAccountTotalSalesFilter(employee_cb, "employee"))
                    .and(predicateCase.getSaleAccountTotalSalesFilter(companyType_cb, "companyType"))
                    .and(predicateCase.getSaleAccountTotalSalesFilter(secondCompanyType_cb, "secondCompanyType"))
                    .and(predicateCase.getSaleAccountTotalSalesFilter(salesAccount_cb)));
            sales_tableView.setItems(salesAccountMachineSalesDTOFilteredList);
        }
    };

    private void callSalesAccountTotalSales() {
        salesAccountMachineSalesDTOList.clear();

        if (total_rb.isSelected()) {
            machine_c.setVisible(false);
            machine_cb.setVisible(false);
            m17.setVisible(false);
        } else {
            machine_c.setVisible(true);
            machine_cb.setVisible(true);
            m17.setVisible(true);
        }

        salesAccountMachineSalesDTOList.addAll(salesManagementService.getSalesAccountSales(total_rb.isSelected(), start_dp.getValue(), last_dp.getValue()));
        salesAccountMachineSalesDTOFilteredList = salesAccountMachineSalesDTOList.filtered(salesAccountTotalSalesDTO -> true);
    }


    @FXML
    public void handleLookupBtnClick(Event e) throws IOException {
        callSalesAccountTotalSales();
        setCheckComboBoxsItems();
    }

    @FXML
    public void handleMonthLookupBtnClick(Event e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        FXMLLoader loader = fxmlLoader.load("/View/MonthSalesLookupView.fxml");
        AnchorPane page = (AnchorPane) loader.load();

        MonthSalesLookUpController monthSalesLookUpController = loader.getController();
        monthSalesLookUpController.setDialogStage(dialogStage);
        monthSalesLookUpController.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }
}
