package com.yuonetoy.Controller.AccountManagement;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDetailDTO;
import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountPreviewDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountPreviewDTO;
import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import com.yuonetoy.Service.PurchaseAccountService;
import com.yuonetoy.Tool.MakeAlert;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class AddPurchaseAccountController implements Initializable {

    @Autowired
    PurchaseAccountService purchaseAccountService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initStatus() {
        if (mode == 1)
            printAccount();
    }

    private int mode = 0;

    @FXML
    private Button add_btn;

    public void setMode(int mode) {
        this.mode = mode;
        if (mode == 0) add_btn.setText("추가");
        else if (mode == 1) add_btn.setText("수정");
    }

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private ObservableList<PurchaseAccountPreviewDTO> purchaseAccounts;

    public void setPurchaseAccountList(ObservableList<PurchaseAccountPreviewDTO> purchaseAccounts) {
        this.purchaseAccounts = purchaseAccounts;
    }

    private PurchaseAccountDetailDTO purchaseAccountDetailDTO;
    private int selectedPurchaseAccountIndex;

    public void setPurchaseAccount(PurchaseAccountPreviewDTO purchaseAccountPreviewDTO) {
        selectedPurchaseAccountIndex = purchaseAccounts.indexOf(purchaseAccountPreviewDTO);
        purchaseAccountDetailDTO = purchaseAccountService.findPurchaseAccountDetailDTO(purchaseAccountPreviewDTO);
    }

    @FXML
    private TextField name_tf, representative_tf, address_tf, ph_tf, uptea_tf, jongmok_tf, companyNum_tf;
    @FXML
    private DatePicker entryDate_dp;

    public void printAccount() {
        name_tf.setText(purchaseAccountDetailDTO.getName());
        representative_tf.setText(purchaseAccountDetailDTO.getRepresentative());
        address_tf.setText(purchaseAccountDetailDTO.getAddress());
        ph_tf.setText(purchaseAccountDetailDTO.getPh());
        uptea_tf.setText(purchaseAccountDetailDTO.getUptea());
        jongmok_tf.setText(purchaseAccountDetailDTO.getJongmok());
        companyNum_tf.setText(purchaseAccountDetailDTO.getCompanyNum());
        entryDate_dp.setValue(purchaseAccountDetailDTO.getEntryDate());
    }

    @FXML
    private void handleAdd() {
        if (mode == 0)
            addAccount();
        else if (mode == 1)
            modifyAccount();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void addAccount() {
        final String name = name_tf.getText();
        final String representative = representative_tf.getText();
        final String address = address_tf.getText();
        final LocalDate entryDate = entryDate_dp.getValue();
        final String ph = ph_tf.getText();
        final String uptea = uptea_tf.getText();
        final String jongmok = jongmok_tf.getText();
        final String companyNum = companyNum_tf.getText();

        PurchaseAccountPreviewDTO purchaseAccountPreviewDTO = purchaseAccountService.addPurchaseAccount(name, representative, address, ph, entryDate, uptea, jongmok, companyNum);
        purchaseAccounts.add(purchaseAccountPreviewDTO);

        new MakeAlert().makeInfoMessage(dialogStage, "추가 완료", "완료", "추가 완료", true);
    }

    public void modifyAccount() {
        final String name = name_tf.getText();
        final String representative = representative_tf.getText();
        final String address = address_tf.getText();
        final LocalDate entryDate = entryDate_dp.getValue();
        final String ph = ph_tf.getText();
        final String uptea = uptea_tf.getText();
        final String jongmok = jongmok_tf.getText();
        final String companyNum = companyNum_tf.getText();

        PurchaseAccountPreviewDTO purchaseAccountPreviewDTO = purchaseAccountService.updatePurchaseAccount(purchaseAccountDetailDTO, name, representative, address, ph, entryDate, uptea, jongmok, companyNum);
        purchaseAccounts.set(selectedPurchaseAccountIndex, purchaseAccountPreviewDTO);

        new MakeAlert().makeInfoMessage(dialogStage, "수정 완료", "완료", "수정 완료", true);
    }
}
