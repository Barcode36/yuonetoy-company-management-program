package com.yuonetoy.Controller.AccountManagement;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDetailDTO;
import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountPreviewDTO;
import com.yuonetoy.Service.PurchaseAccountService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class OpenPurchaseAccountController implements Initializable {

    @Autowired
    PurchaseAccountService purchaseAccountService;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private PurchaseAccountDetailDTO purchaseAccountDetailDTO;

    public void setPurchaseAccount(PurchaseAccountPreviewDTO purchaseAccountPreviewDTO) {
        purchaseAccountDetailDTO = purchaseAccountService.findPurchaseAccountDetailDTO(purchaseAccountPreviewDTO);
    }

    @FXML
    private TextField name_tf, representative_tf, address_tf, ph_tf, uptea_tf, jongmok_tf, companyNum_tf, entryDate_tf;

    public void printAccount(){
        name_tf.setText(purchaseAccountDetailDTO.getName());
        representative_tf.setText(purchaseAccountDetailDTO.getRepresentative());
        address_tf.setText(purchaseAccountDetailDTO.getAddress());
        ph_tf.setText(purchaseAccountDetailDTO.getPh());
        uptea_tf.setText(purchaseAccountDetailDTO.getUptea());
        jongmok_tf.setText(purchaseAccountDetailDTO.getJongmok());
        companyNum_tf.setText(purchaseAccountDetailDTO.getCompanyNum());
        entryDate_tf.setText(purchaseAccountDetailDTO.getEntryDate() == null ? "" : purchaseAccountDetailDTO.getEntryDate().toString());
    }

    public void initStatus() {
        printAccount();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
