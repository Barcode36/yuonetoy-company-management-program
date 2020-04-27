package com.yuonetoy.Controller.AccountManagement;

import com.yuonetoy.Controller.BusinessManagement.BusinessJournalEditController;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.DTO.Stock.EmployeeMachineStockDTO;
import com.yuonetoy.DTO.Stock.EmployeeProductStockDTO;
import com.yuonetoy.Model.DepositType;
import com.yuonetoy.Model.TaxBillType;
import com.yuonetoy.Service.AddSalesAccountService;
import com.yuonetoy.Service.BusinessJournalHistoryService;
import com.yuonetoy.Tool.MakeAlert;
import com.yuonetoy.Tool.SpringFxmlLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Controller
public class AddSalesAccountController implements Initializable {

    @Autowired
    private AddSalesAccountService addSalesAccountService;

    private BusinessJournalEditController businessJournalEditController;

    public void setBusinessJournalEditController(BusinessJournalEditController businessJournalEditController) {
        this.businessJournalEditController = businessJournalEditController;
    }

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private ObservableList<SalesAccountPreviewDTO> salesAccounts;

    public void setSalesAccountList(ObservableList<SalesAccountPreviewDTO> salesAccounts) {
        this.salesAccounts = salesAccounts;
    }

    private Hashtable<String, SalesAccountMachineDetailDTO> newMachineTable;

    private TaxAccountDTO selectedTaxAccountDTO = null;

    @FXML
    private TextField name_tf, representative_tf, address_tf, ph_tf, count_tf, fees_tf, taxAccount_tf, checkTaxaccount_tf;

    @FXML
    private TableView<SalesAccountMachineDetailDTO> machine_tableView;
    @FXML
    private TableColumn<SalesAccountMachineDetailDTO, String> ma_c1;
    @FXML
    private TableColumn<SalesAccountMachineDetailDTO, Integer> ma_c2, ma_c4;
    @FXML
    private TableColumn<SalesAccountMachineDetailDTO, Double> ma_c3;
    private ObservableList<SalesAccountMachineDetailDTO> machineStatuses;

    private void initMachineStatuesTableView() {
        ma_c1.setCellValueFactory(new PropertyValueFactory<>("machine"));
        ma_c2.setCellValueFactory(new PropertyValueFactory<>("machineCount"));
        ma_c3.setCellValueFactory(new PropertyValueFactory<>("fees"));
        ma_c4.setCellValueFactory(new PropertyValueFactory<>("initialQuantity"));
        machineStatuses = machine_tableView.getItems();


        ma_c2.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ma_c2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SalesAccountMachineDetailDTO, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SalesAccountMachineDetailDTO, Integer> event) {
                int newValue = event.getNewValue();
                SalesAccountMachineDetailDTO newMachine = machineStatuses.get(event.getTablePosition().getRow());
                newMachine.setMachineCount(newValue);

                String name = newMachine.getMachine().getName();
                newMachineTable.get(name).setMachineCount(newValue);
            }
        });

        ma_c3.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        ma_c3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SalesAccountMachineDetailDTO, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SalesAccountMachineDetailDTO, Double> event) {
                Double newValue = event.getNewValue();
                SalesAccountMachineDetailDTO newMachine = machineStatuses.get(event.getTablePosition().getRow());
                newMachine.setFees(newValue);

                String name = newMachine.getMachine().getName();
                newMachineTable.get(name).setFees(newValue);
            }
        });

        ma_c4.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ma_c4.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SalesAccountMachineDetailDTO, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SalesAccountMachineDetailDTO, Integer> event) {
                int newValue = event.getNewValue();
                SalesAccountMachineDetailDTO newMachine = machineStatuses.get(event.getTablePosition().getRow());
                newMachine.setInitialQuantity(newValue);

                String name = newMachine.getMachine().getName();
                newMachineTable.get(name).setInitialQuantity(newValue);
            }
        });
    }


    ToggleGroup toggleGroup;
    @FXML
    private RadioButton yuonetoy_rb, yuonetng_rb;

    private void initRadioButtons() {
        toggleGroup = new ToggleGroup();
        yuonetng_rb.setToggleGroup(toggleGroup);
        yuonetoy_rb.setToggleGroup(toggleGroup);
    }

    @FXML
    private ChoiceBox<EmployeeDTO> employee_cb;
    @FXML
    private ChoiceBox<SalesAccountAreaDTO> area_cb;
    @FXML
    private ChoiceBox<CompanyTypeDTO> companyType_cb;
    @FXML
    private ChoiceBox<SecondCompanyTypeDTO> secondCompanyType_cb;
    @FXML
    private ChoiceBox<TaxBillType> taxBill_cb;
    @FXML
    private ChoiceBox<DepositType> deposit_cb;

    private ObservableList<EmployeeDTO> employees;
    private ObservableList<SalesAccountAreaDTO> salesAreas;
    private ObservableList<CompanyTypeDTO> companyTypes;
    private ObservableList<SecondCompanyTypeDTO> secondCompanyTypes;

    @FXML
    private ChoiceBox<EmployeeMachineStockDTO> machine_cb;
    private ObservableList<EmployeeMachineStockDTO> machines;

    @FXML
    private ChoiceBox<EmployeeProductStockDTO> product_cb;
    private ObservableList<EmployeeProductStockDTO> products;

    private void initChoiceBoxes() {
        for (int i = 0; i < 4; i++) {
            deposit_cb.getItems().add(new DepositType(i));
        }

        for (int i = 0; i < 3; i++) {
            taxBill_cb.getItems().add(new TaxBillType(i));
        }

        employees = employee_cb.getItems();
        salesAreas = area_cb.getItems();
        companyTypes = companyType_cb.getItems();
        secondCompanyTypes = secondCompanyType_cb.getItems();
        machines = machine_cb.getItems();
        products = product_cb.getItems();

        companyType_cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CompanyTypeDTO>() {
            @Override
            public void changed(ObservableValue<? extends CompanyTypeDTO> observable, CompanyTypeDTO oldValue, CompanyTypeDTO newValue) {
                callSecondCompanyType();
            }
        });

        employee_cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmployeeDTO>() {
            @Override
            public void changed(ObservableValue<? extends EmployeeDTO> observable, EmployeeDTO oldValue, EmployeeDTO newValue) {
                machines.clear();
                callMachine(newValue.getId());
            }
        });

        machine_cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmployeeMachineStockDTO>() {
            @Override
            public void changed(ObservableValue<? extends EmployeeMachineStockDTO> observable, EmployeeMachineStockDTO oldValue, EmployeeMachineStockDTO newValue) {
                products.clear();
                callMachineLinkProduct(newValue.getMachine());
                product_cb.getSelectionModel().select(0);
            }
        });
    }

    private void callMachineLinkProduct(MachineDTO machineDTO) {
        if (!machineDTO.getIsProductMachine())
            return;

        EmployeeDTO employee = employee_cb.getSelectionModel().getSelectedItem();
        products.addAll(addSalesAccountService.callProductLinkedMachine(employee, machineDTO));
    }

    @FXML
    private DatePicker entryDate_dp;

    @FXML
    private ListView<TaxAccountDTO> taxAccount_listView;
    private ObservableList<TaxAccountDTO> taxAccounts;

    private void initListView() {
        taxAccount_listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TaxAccountDTO>() {
            @Override
            public void changed(ObservableValue<? extends TaxAccountDTO> observable, TaxAccountDTO oldValue, TaxAccountDTO newValue) {
                selectedTaxAccountDTO = newValue;
                checkTaxaccount_tf.setText(newValue.getName());
            }
        });
        taxAccounts = taxAccount_listView.getItems();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initChoiceBoxes();
        initRadioButtons();
        initMachineStatuesTableView();
        initListView();
    }

    @FXML
    void handleSearchTaxAccount() {
        callTaxAccounts();
    }

    @FXML
    private void handleAdd() {
        addAccount();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private TextField productCount_tf;

    @FXML
    private void handlePlusBtnClick() {
        final MachineDTO machine = machine_cb.getSelectionModel().getSelectedItem().getMachine();
        final String name = machine.getName();
        final int machineCount = count_tf.getText().equals("") ? 0 : Integer.parseInt(count_tf.getText());
        final double fees = fees_tf.getText().equals("") ? 0 : Double.parseDouble(fees_tf.getText());

        ProductPriceDTO productPriceDTO = null;
        if (machine.getIsProductMachine())
            productPriceDTO = product_cb.getSelectionModel().getSelectedItem().getProduct();

        final int productCount = productCount_tf.getText().equals("") ? 0 : Integer.parseInt(productCount_tf.getText());
        LocalDate localDate = entryDate_dp.getValue();

        EmployeeDTO employeeDTO = employee_cb.getSelectionModel().getSelectedItem();

        SalesAccountMachineDetailDTO salesAccountMachineDetailDTO;
        if (newMachineTable.containsKey(name)) {
            salesAccountMachineDetailDTO = newMachineTable.get(name);
            salesAccountMachineDetailDTO.setMachineCount(machineCount);
            salesAccountMachineDetailDTO.setFees(fees);
            salesAccountMachineDetailDTO.setInitialQuantity(productCount);
        } else {
            salesAccountMachineDetailDTO = new SalesAccountMachineDetailDTO(null, null, machine, machineCount, fees, productCount, 0, 0, localDate, productPriceDTO, employeeDTO);
        }

        newMachineTable.put(name, salesAccountMachineDetailDTO);
        machineStatuses.setAll(newMachineTable.values());

        count_tf.setText("");
        fees_tf.setText("");
        productCount_tf.setText("");
    }

    @FXML
    private void handleMinusBtnClick() {
        SalesAccountMachineDetailDTO selectedItem = machine_tableView.getSelectionModel().getSelectedItem();
        String name = selectedItem.getMachine().getName();

        newMachineTable.remove(name);

        machineStatuses.remove(selectedItem);
    }

    public void addAccount() {
        final String name = name_tf.getText();
        final String representative = representative_tf.getText();
        final String address = address_tf.getText();
        final String ph = ph_tf.getText();
        final LocalDate entryDate = entryDate_dp.getValue();
        final EmployeeDTO employee = employee_cb.getSelectionModel().getSelectedItem();
        final SalesAccountAreaDTO salesAccountArea = area_cb.getSelectionModel().getSelectedItem();
        final CompanyTypeDTO companyType = companyType_cb.getSelectionModel().getSelectedItem();
        final SecondCompanyTypeDTO secondCompanyType = secondCompanyType_cb.getSelectionModel().getSelectedItem();
        final TaxAccountDTO taxAccount = selectedTaxAccountDTO;
        final int taxBillType = taxBill_cb.getSelectionModel().getSelectedItem().getId();
        final int depositType = deposit_cb.getSelectionModel().getSelectedItem().getId();
        final boolean isYuoneToy = toggleGroup.getSelectedToggle().toString().contains("토이") ? true : false; //0은 토이 1은 tng

        SalesAccountDetailDTO salesAccountDetailDTO = new SalesAccountDetailDTO();
        salesAccountDetailDTO.setName(name);
        salesAccountDetailDTO.setRepresentative(representative);
        salesAccountDetailDTO.setAddress(address);
        salesAccountDetailDTO.setPh(ph);
        salesAccountDetailDTO.setEntryDate(entryDate);
        salesAccountDetailDTO.setTaxBillType(taxBillType);
        salesAccountDetailDTO.setDepositType(depositType);
        salesAccountDetailDTO.setIsYuoneToy(isYuoneToy);
        salesAccountDetailDTO.setSalesAccountArea(salesAccountArea);
        salesAccountDetailDTO.setCompanyType(companyType);
        salesAccountDetailDTO.setSecondCompanyType(secondCompanyType);
        salesAccountDetailDTO.setEmployee(employee);
        salesAccountDetailDTO.setTaxaccount(taxAccount);

        businessJournalEditController.setNewSaleAccount(salesAccountDetailDTO, newMachineTable);
        dialogStage.close();
    }


    public void initStatus() {
        newMachineTable = new Hashtable<>();
        callSalesAccountArea();
        callCompanyType();
        callEmployee();
        callSecondCompanyType();

        entryDate_dp.getChronology().dateNow();
        deposit_cb.getSelectionModel().select(0);
        taxBill_cb.getSelectionModel().select(0);
        employee_cb.getSelectionModel().select(0);
        area_cb.getSelectionModel().select(0);
        companyType_cb.getSelectionModel().select(0);
        secondCompanyType_cb.getSelectionModel().select(0);
    }

    private void callTaxAccounts() {
        taxAccounts.clear();
        taxAccount_listView.getItems().clear();

        final String searchText = taxAccount_tf.getText();

        taxAccounts.addAll(addSalesAccountService.getTaxAccount(searchText));
    }

    private void callEmployee() {
        employees.addAll(addSalesAccountService.getEmployeeList());
    }

    private void callSalesAccountArea() {
        salesAreas.addAll(addSalesAccountService.getSalesAccountAreaList());
    }

    private void callCompanyType() {
        companyTypes.addAll(addSalesAccountService.getCompanyTypeList());
    }

    private void callSecondCompanyType() {
        if (companyType_cb.getSelectionModel().getSelectedIndex() == -1)
            return;

        secondCompanyTypes.clear();

        CompanyTypeDTO companyTypeDTO = companyType_cb.getSelectionModel().getSelectedItem();
        secondCompanyTypes.addAll(addSalesAccountService.getSecondCompanyTypeList(companyTypeDTO));
    }

    private void callMachine(long employee_id) {
        machines.clear();
        machines.addAll(addSalesAccountService.getMachineList(employee_id));
    }

    @FXML
    private void handleAddBtnClick() {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(this.dialogStage);
        dialogStage.setResizable(false);

        FXMLLoader loader = null;
        AnchorPane page = null;

        dialogStage.setTitle("과세 거래처 추가");
        try {
            loader = businessJournalEditController.getFxmlLoader().load("/View/AccountManagement/AddTaxAccountView.fxml");
            page = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddTaxAccountController addTaxAccountController = loader.getController();
        addTaxAccountController.setDialogStage(dialogStage);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }
}