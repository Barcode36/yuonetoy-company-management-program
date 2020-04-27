package com.yuonetoy.Controller.AccountManagement;


import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.DTO.SalesAccountRevisionHistoryDTO;
import com.yuonetoy.Model.DepositType;
import com.yuonetoy.Model.TaxBillType;
import com.yuonetoy.Service.OpenSalesAccountService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class OpenSalesAccountController implements Initializable {
    @Autowired
    OpenSalesAccountService openSalesAccountService;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private TextField name_tf, representative_tf, address_tf, ph_tf, entryDate_tf,
            employee_tf, area_tf, companyType_tf, secondCompanyType_tf, taxaccount_tf,
            company_tf, taxBill_tf, deposit_tf, using_tf, withdrawalDate_tf;
    @FXML
    private TableView<SalesAccountMachineDetailDTO> machine_tableView;
    @FXML
    private TableColumn<SalesAccountMachineDetailDTO, String> ma_c1, ma_c2, ma_c3, ma_c4, ma_c5, ma_c6;

    @FXML
    private TableView<SalesAccountRevisionHistoryDTO> modifyCount_tableView;
    @FXML
    private TableColumn<SalesAccountRevisionHistoryDTO, String> mc_c1, mc_c2, mc_c3;

    @FXML
    private TableView<SalesAccountRevisionHistoryDTO> modifyFees_tableView;
    @FXML
    private TableColumn<SalesAccountRevisionHistoryDTO, String> mf_c1, mf_c2, mf_c3;

    @FXML
    private TableView<SalesAccountRevisionHistoryDTO> modifyInitialQuantity_tableView;
    @FXML
    private TableColumn<SalesAccountRevisionHistoryDTO, String> mq_c1, mq_c2, mq_c3;


    private ObservableList<SalesAccountMachineDetailDTO> machineStatuses;
    private ObservableList<SalesAccountRevisionHistoryDTO> modifyCountInfos;
    private ObservableList<SalesAccountRevisionHistoryDTO> modifyInitialQuantityInfos;
    private ObservableList<SalesAccountRevisionHistoryDTO> modifyfeesInfos;

    private SalesAccountDetailDTO salesAccountDetailDTO;

    public void setSalesAccount(SalesAccountPreviewDTO salesAccountPreviewDTO) {
        salesAccountDetailDTO = openSalesAccountService.findSalesAccountDetailDTO(salesAccountPreviewDTO);
    }

    public void initStatus() {
        printAccount();
    }

    public void printAccount() {
        using_tf.setText(salesAccountDetailDTO.getIsUsing() ? "입점" : "철수");
        withdrawalDate_tf.setText(salesAccountDetailDTO.getWithdrawalDate() == null ? "" : salesAccountDetailDTO.getWithdrawalDate().toString());
        name_tf.setText(salesAccountDetailDTO.getName());
        representative_tf.setText(salesAccountDetailDTO.getRepresentative());
        address_tf.setText(salesAccountDetailDTO.getAddress());
        ph_tf.setText(salesAccountDetailDTO.getPh());
        entryDate_tf.setText(salesAccountDetailDTO.getEntryDate() == null ? "" : salesAccountDetailDTO.getEntryDate().toString());
        employee_tf.setText(salesAccountDetailDTO.getEmployee().getName());
        area_tf.setText(salesAccountDetailDTO.getSalesAccountArea().getName());
        companyType_tf.setText(salesAccountDetailDTO.getCompanyType().getName());
        if (salesAccountDetailDTO.getSecondCompanyType() != null)
            secondCompanyType_tf.setText(salesAccountDetailDTO.getSecondCompanyType().getName());
        taxaccount_tf.setText(salesAccountDetailDTO.getTaxaccount().getName());
        company_tf.setText(salesAccountDetailDTO.getCompanyType().equals("toy") ? "유원 토이" : "유원 티엔지");

        callModifyinfo();
        callMachines();

        String depositType = new DepositType(salesAccountDetailDTO.getDepositType()).getName();
        String taxbillType = new TaxBillType(salesAccountDetailDTO.getTaxBillType()).getName();

        taxBill_tf.setText(taxbillType);
        deposit_tf.setText(depositType);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ma_c1.setCellValueFactory(new PropertyValueFactory<>("machine"));
        ma_c2.setCellValueFactory(new PropertyValueFactory<>("machineCount"));
        ma_c3.setCellValueFactory(new PropertyValueFactory<>("initialQuantity"));
        ma_c4.setCellValueFactory(new PropertyValueFactory<>("fees"));
        ma_c5.setCellValueFactory(new PropertyValueFactory<>("plusValue"));
        ma_c6.setCellValueFactory(new PropertyValueFactory<>("minusValue"));
        machineStatuses = machine_tableView.getItems();

        mc_c1.setCellValueFactory(new PropertyValueFactory<>("date"));
        mc_c2.setCellValueFactory(new PropertyValueFactory<>("type"));
        mc_c3.setCellValueFactory(new PropertyValueFactory<>("contents"));
        modifyCountInfos = modifyCount_tableView.getItems();

        mf_c1.setCellValueFactory(new PropertyValueFactory<>("date"));
        mf_c2.setCellValueFactory(new PropertyValueFactory<>("type"));
        mf_c3.setCellValueFactory(new PropertyValueFactory<>("contents"));
        modifyfeesInfos = modifyFees_tableView.getItems();

        mq_c1.setCellValueFactory(new PropertyValueFactory<>("date"));
        mq_c2.setCellValueFactory(new PropertyValueFactory<>("type"));
        mq_c3.setCellValueFactory(new PropertyValueFactory<>("contents"));
        modifyInitialQuantityInfos = modifyInitialQuantity_tableView.getItems();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private void callModifyinfo() {
        salesAccountDetailDTO.getSalesAccountRevisionHistories().forEach(salesAccountRevisionHistoryDTO -> {
            if (salesAccountRevisionHistoryDTO.getType().contains("수수료"))
                modifyfeesInfos.add(salesAccountRevisionHistoryDTO);
            else if (salesAccountRevisionHistoryDTO.getType().contains("초기수량"))
                modifyInitialQuantityInfos.add(salesAccountRevisionHistoryDTO);
            else
                modifyCountInfos.add(salesAccountRevisionHistoryDTO);
        });
    }

    private void callMachines() {
        machineStatuses.addAll(salesAccountDetailDTO.getSalesAccountMachines());
    }
}
