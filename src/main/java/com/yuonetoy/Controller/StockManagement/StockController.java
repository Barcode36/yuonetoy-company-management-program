package com.yuonetoy.Controller.StockManagement;

import com.yuonetoy.DTO.Stock.*;
import com.yuonetoy.Service.StockService;
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
import java.util.HashSet;
import java.util.ResourceBundle;

@Controller
public class StockController implements Initializable {
    @Autowired
    private SpringFxmlLoader fxmlLoader;
    @Autowired
    private StockService stockService;
    @Autowired
    private PredicateCase predicateCase;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initStatus() {
        callCompanyStock();
        callEmployeeStock();
        callSalesAccountStock();
        setProductDTOComboBoxsItems();
    }

    @FXML
    private TableView<CompanyProductStockAtStockViewDTO> companyStock_tableView;
    @FXML
    private TableColumn<CompanyProductStockAtStockViewDTO, String> cs_c1, cs_c2, cs_c3;
    private ObservableList<CompanyProductStockAtStockViewDTO> companyStocks;
    private FilteredList<CompanyProductStockAtStockViewDTO> companyStockFilteredList;

    private void initCompanyStockTableView() {
        cs_c1.setCellValueFactory(new PropertyValueFactory<>("company"));
        cs_c2.setCellValueFactory(new PropertyValueFactory<>("product"));
        cs_c3.setCellValueFactory(new PropertyValueFactory<>("count"));

        companyStocks = companyStock_tableView.getItems();
    }

    private void callCompanyStock() {
        companyStocks.clear();
        companyStocks.addAll(stockService.getCompanyProductStock());
        companyStockFilteredList = new FilteredList<CompanyProductStockAtStockViewDTO>(this.companyStocks, companyStock -> true);
    }

    @FXML
    private TableView<SalesAccountProductStockAtStockViewDTO> salesAccountStock_tableView;
    @FXML
    private TableColumn<SalesAccountProductStockAtStockViewDTO, String> ss_c1, ss_c2, ss_c3, ss_c4;
    private ObservableList<SalesAccountProductStockAtStockViewDTO> salesAccountStocks;
    private FilteredList<SalesAccountProductStockAtStockViewDTO> salesAccountStockFilteredList;
    private void initSalesAccountStockTableView() {
        ss_c1.setCellValueFactory(new PropertyValueFactory<>("salesAccountName"));
        ss_c4.setCellValueFactory(new PropertyValueFactory<>("salesAccountMachine"));
        ss_c2.setCellValueFactory(new PropertyValueFactory<>("product"));
        ss_c3.setCellValueFactory(new PropertyValueFactory<>("count"));

        salesAccountStocks = salesAccountStock_tableView.getItems();
    }

    private void callSalesAccountStock() {
        salesAccountStocks.clear();
        salesAccountStocks.addAll(stockService.getSalesAccountProductStock());
        salesAccountStockFilteredList = new FilteredList<SalesAccountProductStockAtStockViewDTO>(this.salesAccountStocks, salesAccountStock -> true);
    }


    @FXML
    private TableView<EmployeeProductStockAtStockViewDTO> employeeStock_tableView;
    @FXML
    private TableColumn<EmployeeProductStockAtStockViewDTO, String> es_c1, es_c2, es_c3;
    private ObservableList<EmployeeProductStockAtStockViewDTO> employeeStocks;
    private FilteredList<EmployeeProductStockAtStockViewDTO> employeeStockFilteredList;

    private void initEmployeeStockTableView() {
        es_c1.setCellValueFactory(new PropertyValueFactory<>("employee"));
        es_c2.setCellValueFactory(new PropertyValueFactory<>("product"));
        es_c3.setCellValueFactory(new PropertyValueFactory<>("count"));

        employeeStocks = employeeStock_tableView.getItems();
    }

    private void callEmployeeStock() {
        employeeStocks.clear();
        employeeStocks.addAll(stockService.getEmployeeProductStock());
        employeeStockFilteredList = new FilteredList<EmployeeProductStockAtStockViewDTO>(this.employeeStocks, employeeStock -> true);
    }

    @FXML
    private TableView<CompanyMachineStockAtStockViewDTO> companyMachineStock_tableView;
    @FXML
    private TableColumn<CompanyMachineStockAtStockViewDTO, String> mcs_c1, mcs_c2, mcs_c3;
    private ObservableList<CompanyMachineStockAtStockViewDTO> companyMachineStockDTOS;
    private FilteredList<CompanyMachineStockAtStockViewDTO> companyMachineStockDTOFilteredList;

    private void initCompanyStockMachineTableView() {
        mcs_c1.setCellValueFactory(new PropertyValueFactory<>("company"));
        mcs_c2.setCellValueFactory(new PropertyValueFactory<>("machine"));
        mcs_c3.setCellValueFactory(new PropertyValueFactory<>("count"));

        companyMachineStockDTOS = companyMachineStock_tableView.getItems();
    }

    private void callCompanyMachineStock() {
        companyMachineStockDTOS.clear();
        companyMachineStockDTOS.addAll(stockService.getCompanyMachineStock());
        companyMachineStockDTOFilteredList = new FilteredList<CompanyMachineStockAtStockViewDTO>(this.companyMachineStockDTOS, companyStock -> true);
    }

    @FXML
    private TableView<SalesAccountMachineStockAtStockViewDTO> salesAccountMachineStock_tableView;
    @FXML
    private TableColumn<SalesAccountMachineStockAtStockViewDTO, String> mss_c1, mss_c2, mss_c3;
    private ObservableList<SalesAccountMachineStockAtStockViewDTO> salesAccountMachineStockDTOS;
    private FilteredList<SalesAccountMachineStockAtStockViewDTO> salesAccountMachineStockDTOFilteredList;
    private void initSalesAccountMachineStockTableView() {
        mss_c1.setCellValueFactory(new PropertyValueFactory<>("salesAccountName"));
        mss_c2.setCellValueFactory(new PropertyValueFactory<>("salesAccountMachine"));
        mss_c3.setCellValueFactory(new PropertyValueFactory<>("count"));

        salesAccountMachineStockDTOS = salesAccountMachineStock_tableView.getItems();
    }

    private void callSalesAccountMachineStock() {
        salesAccountMachineStockDTOS.clear();
        salesAccountMachineStockDTOS.addAll(stockService.getSalesAccountMachineStock());
        salesAccountMachineStockDTOFilteredList = new FilteredList<SalesAccountMachineStockAtStockViewDTO>(this.salesAccountMachineStockDTOS, salesAccountStock -> true);
    }

    @FXML
    private TableView<EmployeeMachineStockAtStockViewDTO> employeeMachineStock_tableView;
    @FXML
    private TableColumn<EmployeeMachineStockAtStockViewDTO, String> mes_c1, mes_c2, mes_c3;
    private ObservableList<EmployeeMachineStockAtStockViewDTO> employeeMachineStockDTOS;
    private FilteredList<EmployeeMachineStockAtStockViewDTO> employeeMachineStockDTOFilteredList;

    private void initEmployeeMachineStockTableView() {
        mes_c1.setCellValueFactory(new PropertyValueFactory<>("employee"));
        mes_c2.setCellValueFactory(new PropertyValueFactory<>("machine"));
        mes_c3.setCellValueFactory(new PropertyValueFactory<>("count"));

        employeeMachineStockDTOS = employeeMachineStock_tableView.getItems();
    }

    private void callEmployeeMachineStock() {
        employeeMachineStockDTOS.clear();
        employeeMachineStockDTOS.addAll(stockService.getEmployeeMachineStock());
        employeeMachineStockDTOFilteredList = new FilteredList<EmployeeMachineStockAtStockViewDTO>(this.employeeMachineStockDTOS, employeeStock -> true);
    }

    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initEmployeeStockTableView();
        initCompanyStockTableView();
        initSalesAccountStockTableView();

        initCompanyStockMachineTableView();
        initEmployeeMachineStockTableView();
        initSalesAccountMachineStockTableView();

        initCheckComboBox();
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() == 0){
                    callCompanyStock();
                    callEmployeeStock();
                    callSalesAccountStock();
                    setProductDTOComboBoxsItems();
                    stock_cb.getSelectionModel().selectedItemProperty().addListener(productStockChangeListener);
                    company_cb.getCheckModel().getCheckedItems().addListener(companyProductStockListChangeListener);
                    companyStock_cb.getSelectionModel().selectedItemProperty().addListener(companyProductStockChangeListener);
                    employee_cb.getCheckModel().getCheckedItems().addListener(employeeProductStockListChangeListener);
                    employeeStock_cb.getSelectionModel().selectedItemProperty().addListener(employeeProductStockChangeListener);
                    salesAccount_cb.getSelectionModel().selectedItemProperty().addListener(salesAccountProductStockChangeListener);
                    salesAccountStock_cb.getSelectionModel().selectedItemProperty().addListener(salesAccountProductStockChangeListener);
                }else{
                    callCompanyMachineStock();
                    callEmployeeMachineStock();
                    callSalesAccountMachineStock();
                    setMachineDTOComboBoxsItems();
                    stock_cb.getSelectionModel().selectedItemProperty().addListener(machineStockChangeListener);
                    company_cb.getCheckModel().getCheckedItems().addListener(companyMachineStockListChangeListener);
                    companyStock_cb.getSelectionModel().selectedItemProperty().addListener(companyMachineStockChangeListener);
                    employee_cb.getCheckModel().getCheckedItems().addListener(employeeMachineStockListChangeListener);
                    employeeStock_cb.getSelectionModel().selectedItemProperty().addListener(employeeMachineStockChangeListener);
                    salesAccount_cb.getSelectionModel().selectedItemProperty().addListener(salesAccountMachineStockChangeListener);
                    salesAccountStock_cb.getSelectionModel().selectedItemProperty().addListener(salesAccountMachineStockChangeListener);
                }
            }
        });
    }

    @FXML
    private ComboBox<String> stock_cb;
    private ObservableList<String> stockList;

    @FXML
    private CheckComboBox<String> employee_cb;
    private ObservableList<String> employees;

    @FXML
    private CheckComboBox<String> company_cb;
    private ObservableList<String> companyes;

    @FXML
    private ComboBox<String> salesAccount_cb;
    private ObservableList<String> salesAccounts;

    @FXML
    private ComboBox<String> companyStock_cb;
    private ObservableList<String> companyStockList;

    @FXML
    private ComboBox<String> employeeStock_cb;
    private ObservableList<String> employeeStockList;

    @FXML
    private ComboBox<String> salesAccountStock_cb;
    private ObservableList<String> salesAccountStockList;

    private void setProductDTOComboBoxsItems() {
        salesAccounts.clear();
        companyes.clear();
        employees.clear();


        HashSet<String> salesAccountDTOHashSet = new HashSet<>();
        HashSet<String> companyDTOHashSet = new HashSet<>();
        HashSet<String> employeeDTOHashSet = new HashSet<>();

        companyStocks.forEach(companyProductStockAtStockViewDTO -> {
            companyDTOHashSet.add(companyProductStockAtStockViewDTO.getCompany());
        });

        employeeStocks.forEach(employeeProductStockAtStockViewDTO -> {
            employeeDTOHashSet.add(employeeProductStockAtStockViewDTO.getEmployee());
        });

        salesAccountStocks.forEach(salesAccountProductStockAtStockViewDTO -> {
            salesAccountDTOHashSet.add(salesAccountProductStockAtStockViewDTO.getSalesAccountName());
        });
        salesAccounts.addAll(salesAccountDTOHashSet);
        companyes.addAll(companyDTOHashSet);
        employees.addAll(employeeDTOHashSet);


        salesAccountStockList.clear();
        companyStockList.clear();
        employeeStockList.clear();
        stockList.clear();

        HashSet<String> stockListHashSet = new HashSet<>();
        HashSet<String> salesAccountStockDTOHashSet = new HashSet<>();
        HashSet<String> companyStockDTOHashSet = new HashSet<>();
        HashSet<String> employeeStockDTOHashSet = new HashSet<>();

        companyStocks.forEach(companyProductStockAtStockViewDTO -> {
            companyStockDTOHashSet.add(companyProductStockAtStockViewDTO.getProduct());
        });

        employeeStocks.forEach(employeeProductStockAtStockViewDTO -> {
            employeeStockDTOHashSet.add(employeeProductStockAtStockViewDTO.getProduct());
        });

        salesAccountStocks.forEach(salesAccountProductStockAtStockViewDTO -> {
            salesAccountStockDTOHashSet.add(salesAccountProductStockAtStockViewDTO.getProduct());
        });

        stockListHashSet.addAll(companyStockDTOHashSet);
        stockListHashSet.addAll(employeeStockDTOHashSet);
        stockListHashSet.addAll(salesAccountStockDTOHashSet);
        stockList.addAll(stockListHashSet);
        salesAccountStockList.addAll(salesAccountStockDTOHashSet);
        companyStockList.addAll(companyStockDTOHashSet);
        employeeStockList.addAll(employeeStockDTOHashSet);

    }

    private void setMachineDTOComboBoxsItems() {
        salesAccounts.clear();
        companyes.clear();
        employees.clear();

        HashSet<String> salesAccountDTOHashSet = new HashSet<>();
        HashSet<String> companyDTOHashSet = new HashSet<>();
        HashSet<String> employeeDTOHashSet = new HashSet<>();

        companyMachineStockDTOS.forEach(companyMachineStockAtStockViewDTO -> {
            companyDTOHashSet.add(companyMachineStockAtStockViewDTO.getCompany());
        });

        employeeMachineStockDTOS.forEach(employeeMachineStockAtStockViewDTO -> {
            employeeDTOHashSet.add(employeeMachineStockAtStockViewDTO.getEmployee());
        });

        salesAccountMachineStockDTOS.forEach(salesAccountMachineStockAtStockViewDTO -> {
            salesAccountDTOHashSet.add(salesAccountMachineStockAtStockViewDTO.getSalesAccountName());
        });

        salesAccounts.addAll(salesAccountDTOHashSet);
        companyes.addAll(companyDTOHashSet);
        employees.addAll(employeeDTOHashSet);

        salesAccountStockList.clear();
        companyStockList.clear();
        employeeStockList.clear();
        stockList.clear();

        HashSet<String> stockListHashSet = new HashSet<>();
        HashSet<String> salesAccountStockDTOHashSet = new HashSet<>();
        HashSet<String> companyStockDTOHashSet = new HashSet<>();
        HashSet<String> employeeStockDTOHashSet = new HashSet<>();

        companyMachineStockDTOS.forEach(companyMachineStockAtStockViewDTO -> {
            companyStockDTOHashSet.add(companyMachineStockAtStockViewDTO.getMachine());
        });

        employeeMachineStockDTOS.forEach(employeeMachineStockAtStockViewDTO -> {
            employeeStockDTOHashSet.add(employeeMachineStockAtStockViewDTO.getMachine());
        });

        salesAccountMachineStockDTOS.forEach(salesAccountMachineStockAtStockViewDTO -> {
            salesAccountStockDTOHashSet.add(salesAccountMachineStockAtStockViewDTO.getSalesAccountMachine());
        });

        stockListHashSet.addAll(companyStockDTOHashSet);
        stockListHashSet.addAll(employeeStockDTOHashSet);
        stockListHashSet.addAll(salesAccountStockDTOHashSet);
        stockList.addAll(stockListHashSet);

        salesAccountStockList.addAll(salesAccountStockDTOHashSet);
        companyStockList.addAll(companyStockDTOHashSet);
        employeeStockList.addAll(employeeStockDTOHashSet);
    }

    private void initCheckComboBox() {
        companyes = company_cb.getItems();
        company_cb.getCheckModel().getCheckedItems().addListener(companyProductStockListChangeListener);

        companyStockList = companyStock_cb.getItems();
        companyStock_cb.getSelectionModel().selectedItemProperty().addListener(companyProductStockChangeListener);

        employees = employee_cb.getItems();
        employee_cb.getCheckModel().getCheckedItems().addListener(employeeProductStockListChangeListener);

        employeeStockList = employeeStock_cb.getItems();
        employeeStock_cb.getSelectionModel().selectedItemProperty().addListener(employeeProductStockChangeListener);

        salesAccounts = salesAccount_cb.getItems();

        salesAccount_cb.getSelectionModel().selectedItemProperty().addListener(salesAccountProductStockChangeListener);

        salesAccountStockList = salesAccountStock_cb.getItems();
        salesAccountStock_cb.getSelectionModel().selectedItemProperty().addListener(salesAccountProductStockChangeListener);

        stockList = stock_cb.getItems();
        stock_cb.getSelectionModel().selectedItemProperty().addListener(productStockChangeListener);
    }

    private ListChangeListener employeeProductStockListChangeListener = new ListChangeListener() {
        @Override
        public void onChanged(Change c) {
            employeeStockFilteredList.setPredicate(
                    predicateCase.getEmployeeProductStockAtStockViewDTOEmployeeFilter(employee_cb)
                            .and(predicateCase.getEmployeeProductStockAtStockViewDTOEmployeeStockFilter(employeeStock_cb)));
            employeeStock_tableView.setItems(employeeStockFilteredList);
        }
    };

    private ChangeListener employeeProductStockChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            employeeStockFilteredList.setPredicate(
                    predicateCase.getEmployeeProductStockAtStockViewDTOEmployeeFilter(employee_cb)
                            .and(predicateCase.getEmployeeProductStockAtStockViewDTOEmployeeStockFilter(employeeStock_cb)));
            employeeStock_tableView.setItems(employeeStockFilteredList);
        }
    };

    private ListChangeListener employeeMachineStockListChangeListener = new ListChangeListener() {
        @Override
        public void onChanged(Change c) {
            employeeMachineStockDTOFilteredList.setPredicate(
                    predicateCase.getEmployeeMachineStockAtStockViewDTOEmployeeFilter(employee_cb)
                            .and(predicateCase.getEmployeeMachineStockAtStockViewDTOEmployeeStockFilter(employeeStock_cb)));
            employeeMachineStock_tableView.setItems(employeeMachineStockDTOFilteredList);
        }
    };

    private ChangeListener employeeMachineStockChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            employeeMachineStockDTOFilteredList.setPredicate(
                    predicateCase.getEmployeeMachineStockAtStockViewDTOEmployeeFilter(employee_cb)
                            .and(predicateCase.getEmployeeMachineStockAtStockViewDTOEmployeeStockFilter(employeeStock_cb)));
            employeeMachineStock_tableView.setItems(employeeMachineStockDTOFilteredList);
        }
    };


    private ListChangeListener companyProductStockListChangeListener = new ListChangeListener() {
        @Override
        public void onChanged(Change c) {
            companyStockFilteredList.setPredicate(
                    predicateCase.getCompanyProductStockAtStockViewDTOCompanyFilter(company_cb)
                    .and(predicateCase.getCompanyProductStockAtStockViewDTOCompanyStockFilter(companyStock_cb)));
            companyStock_tableView.setItems(companyStockFilteredList);
        }
    };

    private ChangeListener companyProductStockChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            companyStockFilteredList.setPredicate(
                    predicateCase.getCompanyProductStockAtStockViewDTOCompanyFilter(company_cb)
                            .and(predicateCase.getCompanyProductStockAtStockViewDTOCompanyStockFilter(companyStock_cb)));
            companyStock_tableView.setItems(companyStockFilteredList);
        }
    };

    private ListChangeListener companyMachineStockListChangeListener = new ListChangeListener() {
        @Override
        public void onChanged(Change c) {
            companyMachineStockDTOFilteredList.setPredicate(
                    predicateCase.getCompanyMachineStockAtStockViewDTOCompanyFilter(company_cb)
                            .and(predicateCase.getCompanyMachineStockAtStockViewDTOCompanyStockFilter(companyStock_cb)));
            companyMachineStock_tableView.setItems(companyMachineStockDTOFilteredList);
        }
    };

    private ChangeListener companyMachineStockChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            companyMachineStockDTOFilteredList.setPredicate(
                    predicateCase.getCompanyMachineStockAtStockViewDTOCompanyFilter(company_cb)
                            .and(predicateCase.getCompanyMachineStockAtStockViewDTOCompanyStockFilter(companyStock_cb)));
            companyMachineStock_tableView.setItems(companyMachineStockDTOFilteredList);
        }
    };

    private ChangeListener salesAccountProductStockChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            salesAccountStockFilteredList.setPredicate(
                    predicateCase.getSalesAccountProductStockAtStockViewDTOSalesAccountFilter(salesAccount_cb)
                            .and(predicateCase.getSalesAccountProductStockAtStockViewDTOSalesAccountStockFilter(salesAccountStock_cb)));
            salesAccountStock_tableView.setItems(salesAccountStockFilteredList);
        }
    };

    private ChangeListener salesAccountMachineStockChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            salesAccountMachineStockDTOFilteredList.setPredicate(
                    predicateCase.getSalesAccountMachineStockAtStockViewDTOSalesAccountFilter(salesAccount_cb)
                            .and(predicateCase.getSalesAccountMachineStockAtStockViewDTOSalesAccountStockFilter(salesAccountStock_cb)));
            salesAccountMachineStock_tableView.setItems(salesAccountMachineStockDTOFilteredList);
        }
    };

    private ChangeListener productStockChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            salesAccountStockFilteredList.setPredicate(
                    predicateCase.getSalesAccountProductStockAtStockViewDTOSalesAccountStockFilter(stock_cb));
            salesAccountStock_tableView.setItems(salesAccountStockFilteredList);

            employeeStockFilteredList.setPredicate(
                    predicateCase.getEmployeeProductStockAtStockViewDTOEmployeeStockFilter(stock_cb));
            employeeStock_tableView.setItems(employeeStockFilteredList);

            companyStockFilteredList.setPredicate(
                    predicateCase.getCompanyProductStockAtStockViewDTOCompanyStockFilter(stock_cb));
            companyStock_tableView.setItems(companyStockFilteredList);
        }
    };

    private ChangeListener machineStockChangeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            salesAccountMachineStockDTOFilteredList.setPredicate(
                    predicateCase.getSalesAccountMachineStockAtStockViewDTOSalesAccountStockFilter(stock_cb));
            salesAccountMachineStock_tableView.setItems(salesAccountMachineStockDTOFilteredList);

            employeeMachineStockDTOFilteredList.setPredicate(
                    predicateCase.getEmployeeMachineStockAtStockViewDTOEmployeeStockFilter(stock_cb));
            employeeMachineStock_tableView.setItems(employeeMachineStockDTOFilteredList);

            companyMachineStockDTOFilteredList.setPredicate(
                    predicateCase.getCompanyMachineStockAtStockViewDTOCompanyStockFilter(stock_cb));
            companyMachineStock_tableView.setItems(companyMachineStockDTOFilteredList);
        }
    };

    @FXML
    public void handleCtoCBtnClick(Event e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        dialogStage.setTitle("상품 일지 작성");
        FXMLLoader loader = fxmlLoader.load("/View/StockManagement/SendStockView.fxml");

        AnchorPane page = (AnchorPane) loader.load();

        SendStockController sendStockController = loader.getController();
        sendStockController.setDialogStage(dialogStage);
        sendStockController.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }


    @FXML
    public void handleOpenBtnClick(Event e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        dialogStage.setTitle("재고 이동 조회");
        FXMLLoader loader = fxmlLoader.load("/View/StockManagement/StockHistoryView.fxml");

        AnchorPane page = (AnchorPane) loader.load();

        SendStockHistoryController sendStockHistoryController = loader.getController();
        sendStockHistoryController.setDialogStage(dialogStage);
        sendStockHistoryController.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    @FXML
    private void handleCompanyStockMoveBtnClick() throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        FXMLLoader loader = null;
        AnchorPane page = null;

        loader = fxmlLoader.load("/View/StockManagement/CompanyStockMoveHistoryView.fxml");
        page = (AnchorPane) loader.load();

        CompanyStockMoveHistoryController companyStockMoveHistoryController = loader.getController();
        companyStockMoveHistoryController = loader.getController();
        companyStockMoveHistoryController.setDialogStage(dialogStage);
        companyStockMoveHistoryController.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    @FXML
    public void handleSalesAccountStockMoveBtnClick(Event e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        dialogStage.setTitle("상품 일지 작성");
        FXMLLoader loader = fxmlLoader.load("/View/StockManagement/SalesAccountStockMoveHistoryView.fxml");

        AnchorPane page = (AnchorPane) loader.load();

        SalesAccountStockMoveHistoryController salesAccountStockMoveHistoryController = loader.getController();
        salesAccountStockMoveHistoryController.setDialogStage(dialogStage);
        salesAccountStockMoveHistoryController.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    @FXML
    public void handleEmployeeStockMoveBtnClick(Event e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        dialogStage.setTitle("직원 재고 이동 조회");
        FXMLLoader loader = fxmlLoader.load("/View/StockManagement/EmployeeStockMoveHistoryView.fxml");

        AnchorPane page = (AnchorPane) loader.load();

        EmployeeStockMoveHistoryController employeeStockMoveHistoryController = loader.getController();
        employeeStockMoveHistoryController.setDialogStage(dialogStage);
        employeeStockMoveHistoryController.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    @FXML
    public void handleStockManagementBtnClick(Event e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        dialogStage.setTitle("재고 관리");
        FXMLLoader loader = fxmlLoader.load("/View/StockManagementView.fxml");

        AnchorPane page = (AnchorPane) loader.load();

        StockManagementController stockManagementController = loader.getController();
        stockManagementController.setDialogStage(dialogStage);
        stockManagementController.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }
}
