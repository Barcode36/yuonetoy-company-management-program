package com.yuonetoy.Controller.SalesManagement;

import com.yuonetoy.DTO.SalesAccountMonthSalesDTO;
import com.yuonetoy.Service.SalesManagementService;
import com.yuonetoy.Tool.PredicateCase;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.ResourceBundle;

@Controller
public class MonthSalesLookUpController implements Initializable {

    @Autowired
    SalesManagementService salesManagementService;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableView();
        initCheckComboBox();
    }

    public void initStatus() {

    }

    @FXML
    private TableView<SalesAccountMonthSalesDTO> sales_tableView;
    @FXML
    private TableColumn<SalesAccountMonthSalesDTO, String> m1, m2, m3, m4, m17, m18, m19;
    @FXML
    private TableColumn<SalesAccountMonthSalesDTO, Long> m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16;
    private ObservableList<SalesAccountMonthSalesDTO> salesAccountMachineSalesDTOList;
    private FilteredList<SalesAccountMonthSalesDTO> salesAccountMonthSalesDTOFilteredList;

    private void initTableView() {
        m1.setCellValueFactory(new PropertyValueFactory<>("salesAccountArea"));
        m2.setCellValueFactory(new PropertyValueFactory<>("companyType"));
        m3.setCellValueFactory(new PropertyValueFactory<>("secondCompanyType"));
        m4.setCellValueFactory(new PropertyValueFactory<>("salesAccount"));
        m5.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(0)));
        m6.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(1)));
        m7.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(2)));
        m8.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(3)));
        m9.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(4)));
        m10.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(5)));
        m11.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(6)));
        m12.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(7)));
        m13.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(8)));
        m14.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(9)));
        m15.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(10)));
        m16.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getSales(11)));
        m17.setCellValueFactory(new PropertyValueFactory<>("employee"));
        m18.setCellValueFactory(new PropertyValueFactory<>("state"));
        m19.setCellValueFactory(new PropertyValueFactory<>("withdrawalDate"));
        salesAccountMachineSalesDTOList = sales_tableView.getItems();
    }

    @FXML
    private DatePicker start_dp, last_dp;

    private void callSalesAccountMonthSales() {
        salesAccountMachineSalesDTOList.clear();
        salesAccountMachineSalesDTOList.addAll(salesManagementService.getSalesAccountSales(start_dp.getValue(), last_dp.getValue()));
        salesAccountMonthSalesDTOFilteredList = salesAccountMachineSalesDTOList.filtered(salesAccountTotalSalesDTO -> true);
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


    private void setCheckComboBoxsItems() {
        salesAccounts.clear();
        areas.clear();
        companyTypes.clear();
        secondCompanyTypes.clear();
        employees.clear();

        HashSet<String> salesAccountHashSet = new HashSet<>();
        HashSet<String> salesAccountAreaHashSet = new HashSet<>();
        HashSet<String> companyTypeDTOHashSet = new HashSet<>();
        HashSet<String> secondCompanyTypeDTOHashSet = new HashSet<>();
        HashSet<String> employeeDTOHashSet = new HashSet<>();

        salesAccountMachineSalesDTOList.forEach(salesAccountMonthSalesDTO -> {
            salesAccountHashSet.add(salesAccountMonthSalesDTO.getSalesAccount());
            salesAccountAreaHashSet.add(salesAccountMonthSalesDTO.getSalesAccountArea());
            companyTypeDTOHashSet.add(salesAccountMonthSalesDTO.getCompanyType());
            secondCompanyTypeDTOHashSet.add(salesAccountMonthSalesDTO.getSecondCompanyType());
            employeeDTOHashSet.add(salesAccountMonthSalesDTO.getEmployee());
        });

        areas.addAll(salesAccountAreaHashSet);
        companyTypes.addAll(companyTypeDTOHashSet);
        if (secondCompanyTypeDTOHashSet.iterator().next() != null)
            secondCompanyTypes.addAll(secondCompanyTypeDTOHashSet);
        salesAccounts.addAll(salesAccountHashSet);
        employees.addAll(employeeDTOHashSet);
        salesAccounts.addAll(salesAccountHashSet);
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
                salesAccountMonthSalesDTOFilteredList.setPredicate(predicateCase.getSaleAccountMonthSalesFilter(area_cb, "area")
                        .and(predicateCase.getSaleAccountMonthSalesFilter(employee_cb, "employee"))
                        .and(predicateCase.getSaleAccountMonthSalesFilter(companyType_cb, "companyType"))
                        .and(predicateCase.getSaleAccountMonthSalesFilter(secondCompanyType_cb, "secondCompanyType"))
                        .and(predicateCase.getSaleAccountMonthSalesFilter(salesAccount_cb)));
                sales_tableView.setItems(salesAccountMonthSalesDTOFilteredList);
            }
        });
    }

    @Autowired
    private PredicateCase predicateCase;

    ListChangeListener listChangeListener = new ListChangeListener() {
        @Override
        public void onChanged(Change c) {
            salesAccountMonthSalesDTOFilteredList.setPredicate(predicateCase.getSaleAccountMonthSalesFilter(area_cb, "area")
                    .and(predicateCase.getSaleAccountMonthSalesFilter(employee_cb, "employee"))
                    .and(predicateCase.getSaleAccountMonthSalesFilter(companyType_cb, "companyType"))
                    .and(predicateCase.getSaleAccountMonthSalesFilter(secondCompanyType_cb, "secondCompanyType"))
                    .and(predicateCase.getSaleAccountMonthSalesFilter(salesAccount_cb)));
            sales_tableView.setItems(salesAccountMonthSalesDTOFilteredList);
        }
    };

    @FXML
    private void handleLookupBtnClick() {
        callSalesAccountMonthSales();
        setCheckComboBoxsItems();
    }
}
