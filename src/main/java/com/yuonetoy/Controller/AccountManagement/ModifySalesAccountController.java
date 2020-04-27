package com.yuonetoy.Controller.AccountManagement;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.Model.DepositType;
import com.yuonetoy.Model.TaxBillType;
import com.yuonetoy.Service.ModifySalesAccountService;
import com.yuonetoy.Tool.MakeAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Controller
public class ModifySalesAccountController implements Initializable {

    @Autowired
    ModifySalesAccountService modifySalesAccountService;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private ObservableList<SalesAccountPreviewDTO> salesAccounts;

    public void setSalesAccountList(ObservableList<SalesAccountPreviewDTO> salesAccounts) {
        this.salesAccounts = salesAccounts;
    }

    private SalesAccountDetailDTO salesAccountDetailDTO;
    private TaxAccountDTO selectedTaxAccountDTO = null;
    private int selectedSalesAccountIndex;

    public void setSalesAccount(SalesAccountPreviewDTO salesAccountPreviewDTO) {
        selectedSalesAccountIndex = salesAccounts.indexOf(salesAccountPreviewDTO);
        salesAccountDetailDTO = modifySalesAccountService.findSalesAccountDetailDTO(salesAccountPreviewDTO);
    }

    @FXML
    private TextField name_tf, representative_tf, address_tf, ph_tf, taxAccount_tf;
    @FXML
    private TextField checkTaxaccount_tf;

    public void printAccount() {
        name_tf.setText(salesAccountDetailDTO.getName());
        representative_tf.setText(salesAccountDetailDTO.getRepresentative());
        address_tf.setText(salesAccountDetailDTO.getAddress());
        ph_tf.setText(salesAccountDetailDTO.getPh());
        entryDate_dp.setValue(salesAccountDetailDTO.getEntryDate());

        employees.forEach(employeeDTO -> {
            if (employeeDTO.getId() == salesAccountDetailDTO.getEmployee().getId())
                employee_cb.getSelectionModel().select(employeeDTO);
        });
        salesAreas.forEach(salesAccountAreaDTO -> {
            if (salesAccountAreaDTO.getId() == salesAccountDetailDTO.getSalesAccountArea().getId())
                area_cb.getSelectionModel().select(salesAccountAreaDTO);
        });
        companyTypes.forEach(companyTypeDTO -> {
            if (companyTypeDTO.getId() == salesAccountDetailDTO.getCompanyType().getId()) {
                companyType_cb.getSelectionModel().select(companyTypeDTO);
                callSecondCompanyType();
            }
        });
        secondCompanyTypes.forEach(secondCompanyTypeDTO -> {
            if (secondCompanyTypeDTO.getId() == salesAccountDetailDTO.getSecondCompanyType().getId())
                secondCompanyType_cb.getSelectionModel().select(secondCompanyTypeDTO);
        });

        checkTaxaccount_tf.setText(salesAccountDetailDTO.getTaxaccount().toString());

        if (salesAccountDetailDTO.getCompanyType().equals("toy"))
            yuonetoy_rb.setSelected(true);
        else
            yuonetng_rb.setSelected(true);

        taxBill_cb.setValue(taxBill_cb.getItems().get(salesAccountDetailDTO.getTaxBillType()));
        deposit_cb.setValue(deposit_cb.getItems().get(salesAccountDetailDTO.getDepositType()));
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

    private void initChoiceBoxes() {
        for (int i = 0; i < 4; i++){
            deposit_cb.getItems().add(new DepositType(i));
        }

        for (int i = 0; i < 3; i++){
            taxBill_cb.getItems().add(new TaxBillType(i));
        }

        employees = employee_cb.getItems();
        salesAreas = area_cb.getItems();
        companyTypes = companyType_cb.getItems();
        secondCompanyTypes = secondCompanyType_cb.getItems();

        companyType_cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CompanyTypeDTO>() {
            @Override
            public void changed(ObservableValue<? extends CompanyTypeDTO> observable, CompanyTypeDTO oldValue, CompanyTypeDTO newValue) {
                callSalesAccountArea();
            }
        });
    }

    @FXML
    private ListView<TaxAccountDTO> taxAccount_listView;
    private ObservableList<TaxAccountDTO> taxAccounts;

    private void initListView() {
        taxAccount_listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TaxAccountDTO>() {
            @Override
            public void changed(ObservableValue<? extends TaxAccountDTO> observable, TaxAccountDTO oldValue, TaxAccountDTO newValue) {
                selectedTaxAccountDTO = newValue;
                taxAccount_tf.setText(newValue.getName());
            }
        });
        taxAccounts = taxAccount_listView.getItems();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initChoiceBoxes();
        initRadioButtons();
        initListView();
    }

    @FXML
    void handleSearchTaxAccount() {
        callTaxAccounts();
    }

    @FXML
    private void handleAdd() {
        modifyAccount();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private DatePicker entryDate_dp;

    public void modifyAccount() {
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

        SalesAccountPreviewDTO salesAccountPreviewDTO = modifySalesAccountService.updateSalesAccount(salesAccountDetailDTO, name, representative, address, ph, entryDate,
                taxBillType, depositType, isYuoneToy, salesAccountArea, companyType, secondCompanyType, employee, taxAccount);
        salesAccounts.set(selectedSalesAccountIndex, salesAccountPreviewDTO);

        new MakeAlert().makeInfoMessage(dialogStage, "수정 완료","완료", "수정 완료", true);
    }

    public void initStatus() {
        selectedTaxAccountDTO = salesAccountDetailDTO.getTaxaccount();

        callSalesAccountArea();
        callCompanyType();
        callEmployee();
        callSecondCompanyType();

        printAccount();
    }

    private void callTaxAccounts() {
        taxAccounts.clear();
        taxAccount_listView.getItems().clear();

        final String searchText = taxAccount_tf.getText();

        this.taxAccounts.addAll(modifySalesAccountService.getTaxAccount(searchText));
    }

    private void callEmployee() {
        this.employees.addAll(modifySalesAccountService.getEmployeeList());
    }

    private void callSalesAccountArea() {
        this.salesAreas.addAll(modifySalesAccountService.getSalesAccountAreaList());
    }

    private void callCompanyType() {
        this.companyTypes.addAll(modifySalesAccountService.getCompanyTypeList());
    }

    private void callSecondCompanyType() {
        if (companyType_cb.getSelectionModel().getSelectedIndex() == -1)
            return;

        secondCompanyTypes.clear();
        secondCompanyType_cb.getItems().clear();

        CompanyTypeDTO companyTypeDTO = companyTypes.get(companyType_cb.getSelectionModel().getSelectedIndex());
        this.secondCompanyTypes.addAll(modifySalesAccountService.getSecondCompanyTypeList(companyTypeDTO));
    }
}