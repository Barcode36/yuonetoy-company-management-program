package com.yuonetoy.Controller.AccountManagement;


import com.sun.javafx.scene.control.skin.LabeledText;
import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountPreviewDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountPreviewDTO;
import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import com.yuonetoy.Model.TotalCoinChangerInfo;
import com.yuonetoy.Model.TotalMachineInfo;
import com.yuonetoy.Service.*;
import com.yuonetoy.Tool.PredicateCase;
import com.yuonetoy.Tool.SpringFxmlLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.CheckComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.net.URL;
import java.util.*;

@Controller
public class AccountController implements Initializable {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SpringFxmlLoader fxmlLoader;

    @FXML
    private Label numsOfAccount_lb, numsOfallAccount_lb;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private int selectionMode;

    private ObservableList<SalesAccountPreviewDTO> salesAccounts;
    private FilteredList<SalesAccountPreviewDTO> salesAccount_filteredData;

    @FXML
    private TableView<SalesAccountPreviewDTO> sales_tableView;
    @FXML
    private TableColumn<SalesAccountPreviewDTO, String> sa_c2, sa_c3, sa_c4, sa_c5, sa_c6, sa_c7, sa_c8, sa_c9, sa_c10;

    private void initSalesAccountTableView() {
        sa_c2.setCellValueFactory(new PropertyValueFactory<>("salesAccountAreaName"));
        sa_c3.setCellValueFactory(new PropertyValueFactory<>("companyTypeName"));
        sa_c4.setCellValueFactory(new PropertyValueFactory<>("secondCompanyTypeName"));
        sa_c5.setCellValueFactory(new PropertyValueFactory<>("name"));
        sa_c6.setCellValueFactory(new PropertyValueFactory<>("address"));
        sa_c7.setCellValueFactory(new PropertyValueFactory<>("ph"));
        sa_c8.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        sa_c9.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        sa_c10.setCellValueFactory(new PropertyValueFactory<>("state"));
        salesAccounts = sales_tableView.getItems();
    }

    private ObservableList<PurchaseAccountPreviewDTO> purchaseAccounts;
    private FilteredList<PurchaseAccountPreviewDTO> purchaseAccount_filterdData;

    @FXML
    private TableView<PurchaseAccountPreviewDTO> purchase_tableView;
    @FXML
    private TableColumn<PurchaseAccount, String> pa_c2, pa_c3, pa_c4, pa_c5;

    private void initPurchaseAccountTableView() {
        pa_c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        pa_c3.setCellValueFactory(new PropertyValueFactory<>("address"));
        pa_c4.setCellValueFactory(new PropertyValueFactory<>("ph"));
        pa_c5.setCellValueFactory(new PropertyValueFactory<>("entryDate"));

        purchaseAccounts = purchase_tableView.getItems();
    }

    private ObservableList<TaxAccountPreviewDTO> taxAccounts;
    private FilteredList<TaxAccountPreviewDTO> taxAccount_filterdData;

    @FXML
    private TableView<TaxAccountPreviewDTO> tax_tableView;
    @FXML
    private TableColumn<TaxAccountPreviewDTO, String> ta_c2, ta_c3, ta_c4, ta_c5;

    private void initTaxAccountTableView() {
        ta_c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        ta_c3.setCellValueFactory(new PropertyValueFactory<>("address"));
        ta_c4.setCellValueFactory(new PropertyValueFactory<>("ph"));
        ta_c5.setCellValueFactory(new PropertyValueFactory<>("entryDate"));
        taxAccounts = tax_tableView.getItems();
    }

    @FXML
    private TableView<TotalCoinChangerInfo> coinChanger_tableView;
    @FXML
    private TableColumn<TotalCoinChangerInfo, String> cc_c1, cc_c2, cc_c3, cc_c4, cc_c5;
    private ObservableList<TotalCoinChangerInfo> coinChangers;

    private void initCoinChangerTableView() {
        cc_c1.setCellValueFactory(new PropertyValueFactory<>("name"));
        cc_c2.setCellValueFactory(new PropertyValueFactory<>("count"));
        cc_c3.setCellValueFactory(new PropertyValueFactory<>("initialQuantity"));
        cc_c4.setCellValueFactory(new PropertyValueFactory<>("totalCount"));
        cc_c5.setCellValueFactory(new PropertyValueFactory<>("totalInitialQuantity"));
        coinChangers = coinChanger_tableView.getItems();
    }

    @FXML
    private TableView<TotalMachineInfo> machine_tableView;
    @FXML
    private TableColumn<TotalMachineInfo, String> mc_c1, mc_c2, mc_c3;
    private ObservableList<TotalMachineInfo> machines;

    private void initMachineTableView() {
        mc_c1.setCellValueFactory(new PropertyValueFactory<>("name"));
        mc_c2.setCellValueFactory(new PropertyValueFactory<>("count"));
        mc_c3.setCellValueFactory(new PropertyValueFactory<>("totalCount"));
        machines = machine_tableView.getItems();
    }

    @FXML
    private TextField purchaseName_tf, taxName_tf;

    private void initTextFields() {
        purchaseName_tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                purchaseAccount_filterdData.setPredicate(purchaseAccountPreviewDTO -> {
                    if (purchaseAccountPreviewDTO.getName().contains(newValue))
                        return true;
                    else
                        return false;
                });
                purchase_tableView.setItems(purchaseAccount_filterdData);
            }
        });

        taxName_tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                taxAccount_filterdData.setPredicate(taxAccountPreviewDTO -> {
                    if (taxAccountPreviewDTO.getName().contains(newValue))
                        return true;
                    else
                        return false;
                });
                tax_tableView.setItems(taxAccount_filterdData);
            }
        });
    }

    @FXML
    private CheckComboBox<String> area_cb, companyTypes_cb, secondCompanyTypes_cb, employee_cb;
    private ObservableList<String> areas, companyTypes, secondCompanyTypes, employees, salesAccountNames;

    @FXML
    private ComboBox<String> salesAccountName_cb;


    private void initComboBoxes() {
        areas = area_cb.getItems();
        companyTypes = companyTypes_cb.getItems();
        secondCompanyTypes = secondCompanyTypes_cb.getItems();
        employees = employee_cb.getItems();
        salesAccountNames = salesAccountName_cb.getItems();

        salesAccountName_cb.getSelectionModel().selectedItemProperty().addListener(changeListener);
        area_cb.getCheckModel().getCheckedItems().addListener(listChangeListener);
        companyTypes_cb.getCheckModel().getCheckedItems().addListener(listChangeListener);
        secondCompanyTypes_cb.getCheckModel().getCheckedItems().addListener(listChangeListener);
        employee_cb.getCheckModel().getCheckedItems().addListener(listChangeListener);
    }

    private void setChoiceBoxsItems() {
        areas.clear();
        companyTypes.clear();
        secondCompanyTypes.clear();
        employees.clear();
        salesAccountNames.clear();

        HashSet<String> salesAccountNameHashSet = new HashSet<>();
        HashSet<String> salesAccountAreaDTOHashSet = new HashSet<>();
        HashSet<String> companyTypeDTOHashSet = new HashSet<>();
        HashSet<String> secondCompanyTypeDTOHashSet = new HashSet<>();
        HashSet<String> employeeDTOHashSet = new HashSet<>();

        salesAccounts.forEach(salesAccountPreviewDTO -> {
            salesAccountNameHashSet.add(salesAccountPreviewDTO.getName());
            salesAccountAreaDTOHashSet.add(salesAccountPreviewDTO.getSalesAccountAreaName());
            companyTypeDTOHashSet.add(salesAccountPreviewDTO.getCompanyTypeName());
            secondCompanyTypeDTOHashSet.add(salesAccountPreviewDTO.getSecondCompanyTypeName());
            employeeDTOHashSet.add(salesAccountPreviewDTO.getEmployeeName());
        });

        salesAccountNames.addAll(salesAccountNameHashSet);
        areas.addAll(salesAccountAreaDTOHashSet);
        companyTypes.addAll(companyTypeDTOHashSet);

        secondCompanyTypeDTOHashSet.remove(null);
        secondCompanyTypes.addAll(secondCompanyTypeDTOHashSet);
        employees.addAll(employeeDTOHashSet);
    }

    @FXML
    private Label sorted_lb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSalesAccountTableView();
        initPurchaseAccountTableView();
        initTaxAccountTableView();
        initCoinChangerTableView();
        initMachineTableView();

        initComboBoxes();
        initTextFields();

        initTabPane();
        initTitledPane();

        sordLabelString = new StringBuilder();
    }

    public void initStatus() {
        selectionMode = 0;
        callAccounts();
    }

    @FXML
    private TabPane tabPane;

    public void initTabPane() {
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                salesAccount_filteredData.getSource().clear();
                Stage stage = (Stage) titledPane.getScene().getWindow();

                switch (newValue.intValue()){
                    case 0:
                        selectionMode = 0;
                        stage.setHeight(823);
                        titledPane.expandedProperty().setValue(true);
                        break;
                    case 1:
                        selectionMode = 1;
                        stage.setHeight(848 - 310);
                        break;
                    case 2:
                        selectionMode = 2;
                        stage.setHeight(848 - 310);
                        break;
                }

                salesAccountName_cb.setValue("");
                purchaseName_tf.setText("");
                taxName_tf.setText("");

                callAccounts();
            }
        });
    }

    @FXML
    private TitledPane titledPane;

    public void initTitledPane() {
        titledPane.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Stage stage = (Stage) titledPane.getScene().getWindow();
                if (newValue) {
                    stage.setHeight(823);
                } else {
                    stage.setHeight(tabPane.getHeight() - titledPane.getHeight() + 98);
                }
            }
        });
    }

    @FXML
    public void handleAddBtnClick(Event e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        FXMLLoader loader = null;
        AnchorPane page = null;

        switch (selectionMode) {
            case 0:
                dialogStage.setTitle("매출 거래처 추가");
                loader = fxmlLoader.load("/View/AccountManagement/AddSalesAccountView.fxml");
                page = (AnchorPane) loader.load();

                AddSalesAccountController addSalesAccountController = loader.getController();
                addSalesAccountController.setDialogStage(dialogStage);
                addSalesAccountController.setSalesAccountList(salesAccounts);
                addSalesAccountController.initStatus();
                break;
            case 1:
                dialogStage.setTitle("매입 거래처 추가");
                loader = fxmlLoader.load("/View/AccountManagement/AddPurchaseAccountView.fxml");
                page = (AnchorPane) loader.load();

                AddPurchaseAccountController addPurchaseAccountController = loader.getController();
                addPurchaseAccountController.setDialogStage(dialogStage);
                addPurchaseAccountController.setPurchaseAccountList(purchaseAccounts);
                break;

            case 2:
                dialogStage.setTitle("과세 거래처 추가");
                loader = fxmlLoader.load("/View/AccountManagement/AddTaxAccountView.fxml");
                page = (AnchorPane) loader.load();

                AddTaxAccountController addTaxAccountController = loader.getController();
                addTaxAccountController.setDialogStage(dialogStage);
                addTaxAccountController.setTaxAccountList(taxAccounts);
                break;
        }

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    @FXML
    public void handleModifyBtnClick(Event e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        FXMLLoader loader = null;
        AnchorPane page = null;

        switch (selectionMode) {
            case 0:
                dialogStage.setTitle("매출 거래처 수정");
                loader = fxmlLoader.load("/View/AccountManagement/ModifySalesAccountView.fxml");
                page = (AnchorPane) loader.load();

                ModifySalesAccountController modifySalesAccountController = loader.getController();
                modifySalesAccountController.setDialogStage(dialogStage);
                modifySalesAccountController.setSalesAccountList(salesAccounts);
                modifySalesAccountController.setSalesAccount(sales_tableView.getSelectionModel().getSelectedItem());
                modifySalesAccountController.initStatus();
                break;

            case 1:
                dialogStage.setTitle("매입 거래처 수정");
                loader = fxmlLoader.load("/View/AccountManagement/AddPurchaseAccountView.fxml");
                page = (AnchorPane) loader.load();

                AddPurchaseAccountController addPurchaseAccountController = loader.getController();
                addPurchaseAccountController.setDialogStage(dialogStage);
                addPurchaseAccountController.setMode(1);
                addPurchaseAccountController.setPurchaseAccountList(purchaseAccounts);
                addPurchaseAccountController.setPurchaseAccount(purchase_tableView.getSelectionModel().getSelectedItem());
                addPurchaseAccountController.initStatus();
                break;

            case 2:
                dialogStage.setTitle("과세 거래처 수정");
                loader = fxmlLoader.load("/View/AccountManagement/AddTaxAccountView.fxml");
                page = (AnchorPane) loader.load();

                AddTaxAccountController addTaxAccountController = loader.getController();
                addTaxAccountController.setDialogStage(dialogStage);
                addTaxAccountController.setMode(1);
                addTaxAccountController.setTaxAccountList(taxAccounts);
                addTaxAccountController.setTaxAccount(tax_tableView.getSelectionModel().getSelectedItem());
                addTaxAccountController.initStatus();
                break;
        }

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    @FXML
    public void handleDeleteBtnClick(Event e) {

    }

    @FXML
    public void handleOpenBtnClick(Event e) throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        FXMLLoader loader = null;
        AnchorPane page = null;

        switch (selectionMode) {
            case 0:
                dialogStage.setTitle("매출 거래처 추가");
                loader = fxmlLoader.load("/View/AccountManagement/OpenSalesAccountView.fxml");
                page = (AnchorPane) loader.load();

                OpenSalesAccountController openSalesAccountController = loader.getController();
                openSalesAccountController.setDialogStage(dialogStage);
                openSalesAccountController.setSalesAccount(sales_tableView.getSelectionModel().getSelectedItem());
                openSalesAccountController.initStatus();
                break;
            case 1:
                dialogStage.setTitle("매입 거래처 추가");
                loader = fxmlLoader.load("/View/AccountManagement/OpenPurchaseAccountView.fxml");
                page = (AnchorPane) loader.load();

                OpenPurchaseAccountController openPurchaseAccountController = loader.getController();
                openPurchaseAccountController.setDialogStage(dialogStage);
                openPurchaseAccountController.setPurchaseAccount(purchase_tableView.getSelectionModel().getSelectedItem());
                openPurchaseAccountController.initStatus();
                break;

            case 2:
                dialogStage.setTitle("과세 거래처 추가");
                loader = fxmlLoader.load("/View/AccountManagement/OpenTaxAccountView.fxml");
                page = (AnchorPane) loader.load();

                OpenTaxAccountController openTaxAccountController = loader.getController();
                openTaxAccountController.setDialogStage(dialogStage);
                openTaxAccountController.setTaxAccount(tax_tableView.getSelectionModel().getSelectedItem());
                openTaxAccountController.initStatus();

                break;
        }

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    private void callAccounts() {
        if (selectionMode == 0) {
            salesAccounts.clear();

            salesAccounts.addAll(accountService.readSalesAccount());
            salesAccount_filteredData = salesAccounts.filtered(salesAccount -> true);

            setChoiceBoxsItems();
            initMahcineInformation();
        } else if (selectionMode == 1) {
            purchaseAccounts.clear();

            purchaseAccounts.addAll(accountService.readPurchaseAccount());
            purchaseAccount_filterdData = purchaseAccounts.filtered(purchaseAccount -> true);
        } else if (selectionMode == 2) {
            taxAccounts.clear();

            taxAccounts.addAll(accountService.readTaxAccount());
            taxAccount_filterdData = taxAccounts.filtered(taxAccount -> true);
        }
    }

    Hashtable<Long, TotalMachineInfo> totalMachineInfoHashtable = new Hashtable<>();
    Hashtable<Long, TotalCoinChangerInfo> totalCoinChangerInfoHashtable = new Hashtable<>();

    private void initMahcineInformation() {
        setMachineCount();
    }

    private void setMachineCount() {
        machines.clear();
        coinChangers.clear();

        numsOfallAccount_lb.setText(salesAccounts.size() + "개");
        numsOfAccount_lb.setText(salesAccounts.size() + "개");

        accountService.getTotalMachine().forEach(totalMachineInfo -> {
            long machineId = totalMachineInfo.getName().hashCode();
            totalMachineInfoHashtable.put(machineId, totalMachineInfo);
        });

        machines.addAll(totalMachineInfoHashtable.values());

        accountService.getTotalCoinChanger().forEach(totalCoinChangerInfo -> {
            long machineId = totalCoinChangerInfo.getName().hashCode();
            totalCoinChangerInfoHashtable.put(machineId, totalCoinChangerInfo);
        });
        coinChangers.addAll(totalCoinChangerInfoHashtable.values());
    }

    private void getMachineCount() {
        if (salesAccount_filteredData.size() == salesAccounts.size()){
            machines.forEach(totalMachineInfo -> {
                totalMachineInfo.setCount(totalMachineInfo.getTotalCount());
            });

            coinChangers.forEach(totalCoinChangerInfo -> {
                totalCoinChangerInfo.setCount(totalCoinChangerInfo.getTotalCount());
                totalCoinChangerInfo.setInitialQuantity(totalCoinChangerInfo.getTotalInitialQuantity());
            });

            machine_tableView.refresh();
            coinChanger_tableView.refresh();

            return;
        }

        for (TotalMachineInfo totalMachineInfo : machines) {
            totalMachineInfo.setCount(0);
        }

        for (TotalCoinChangerInfo totalCoinChangerInfo : coinChangers) {
            totalCoinChangerInfo.setCount(0);
            totalCoinChangerInfo.setInitialQuantity(0);
        }

        numsOfAccount_lb.setText(salesAccount_filteredData.size() + "개");

        List<Long> salesAccountId = new ArrayList<>();
        salesAccount_filteredData.forEach(salesAccountPreviewDTO -> {
            salesAccountId.add(salesAccountPreviewDTO.getId());
        });

        accountService.getTotalMachine(salesAccountId).forEach(totalMachineInfo -> {
            long machineId = totalMachineInfo.getName().hashCode();
            totalMachineInfoHashtable.get(machineId).setCount(totalMachineInfo.getCount());
        });

        accountService.getTotalCoinChanger(salesAccountId).forEach(totalCoinChangerInfo -> {
            long coinChangerId = totalCoinChangerInfo.getName().hashCode();
            TotalCoinChangerInfo selectedCoinChangerInfo = totalCoinChangerInfoHashtable.get(coinChangerId);
            selectedCoinChangerInfo.setCount(totalCoinChangerInfo.getCount());
            selectedCoinChangerInfo.setInitialQuantity(totalCoinChangerInfo.getInitialQuantity());
        });

        machine_tableView.refresh();
        coinChanger_tableView.refresh();
    }

    @Autowired
    private PredicateCase predicateCase;

    private ChangeListener changeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {

            salesAccount_filteredData.setPredicate(
                    predicateCase.getSalesAccountPreviewDTOFilter(salesAccountName_cb)
                            .and(predicateCase.getSalesAccountPreviewDTOFilter(employee_cb, "employee"))
                            .and(predicateCase.getSalesAccountPreviewDTOFilter(companyTypes_cb, "companyType"))
                            .and(predicateCase.getSalesAccountPreviewDTOFilter(secondCompanyTypes_cb, "secondCompanyType"))
                            .and(predicateCase.getSalesAccountPreviewDTOFilter(area_cb, "salesAccountArea"))
            );
            sales_tableView.setItems(salesAccount_filteredData);

            numsOfAccount_lb.setText(salesAccount_filteredData.size() + "개");

            sordLabelString.delete(0, sordLabelString.length());
            sordLabelString.append("[ 지역구분 : ")
                    .append(area_cb.getCheckModel().getCheckedIndices().size() == 0 ? "전체" : area_cb.getCheckModel().getCheckedItems())
                    .append(" ]     [ 업장구분 : ")
                    .append(companyTypes_cb.getCheckModel().getCheckedIndices().size() == 0 ? "전체" : companyTypes_cb.getCheckModel().getCheckedItems())
                    .append(" ]     [ 소분류 : ")
                    .append(secondCompanyTypes_cb.getCheckModel().getCheckedIndices().size() == 0 ? "전체" : secondCompanyTypes_cb.getCheckModel().getCheckedItems())
                    .append(" ]     [ 상호명 : \"")
                    .append(salesAccountName_cb.getEditor().getText().equals("") ? "전체\"" : (salesAccountName_cb.getEditor().getText() + "\"을 포함 "))
                    .append(" ]     [ 담당자 : ")
                    .append(employee_cb.getCheckModel().getCheckedIndices().size() == 0 ? "전체" : employee_cb.getCheckModel().getCheckedItems())
                    .append(" ]");

            sorted_lb.setText(sordLabelString.toString());

            getMachineCount();
        }
    };

    private StringBuilder sordLabelString;

    private ListChangeListener listChangeListener = new ListChangeListener() {
        @Override
        public void onChanged(Change c) {

            salesAccount_filteredData.setPredicate(
                    predicateCase.getSalesAccountPreviewDTOFilter(salesAccountName_cb)
                            .and(predicateCase.getSalesAccountPreviewDTOFilter(employee_cb, "employee"))
                            .and(predicateCase.getSalesAccountPreviewDTOFilter(companyTypes_cb, "companyType"))
                            .and(predicateCase.getSalesAccountPreviewDTOFilter(secondCompanyTypes_cb, "secondCompanyType"))
                            .and(predicateCase.getSalesAccountPreviewDTOFilter(area_cb, "salesAccountArea"))
            );
            sales_tableView.setItems(salesAccount_filteredData);
            numsOfAccount_lb.setText(salesAccount_filteredData.size() + "개");

            sordLabelString.delete(0, sordLabelString.length());
            sordLabelString.append("[ 지역구분 : ")
                    .append(area_cb.getCheckModel().getCheckedIndices().size() == 0 ? "전체" : area_cb.getCheckModel().getCheckedItems())
                    .append(" ]     [ 업장구분 : ")
                    .append(companyTypes_cb.getCheckModel().getCheckedIndices().size() == 0 ? "전체" : companyTypes_cb.getCheckModel().getCheckedItems())
                    .append(" ]     [ 소분류 : ")
                    .append(secondCompanyTypes_cb.getCheckModel().getCheckedIndices().size() == 0 ? "전체" : secondCompanyTypes_cb.getCheckModel().getCheckedItems())
                    .append(" ]     [ 상호명 : ")
                    .append(salesAccountName_cb.getEditor().getText().equals("") ? "전체" : ("\"" + salesAccountName_cb.getEditor().getText() + "\"을 포함 "))
                    .append(" ]     [ 담당자 : ")
                    .append(employee_cb.getCheckModel().getCheckedIndices().size() == 0 ? "전체" : employee_cb.getCheckModel().getCheckedItems())
                    .append(" ]");

            sorted_lb.setText(sordLabelString.toString());

            getMachineCount();
        }
    };
}

