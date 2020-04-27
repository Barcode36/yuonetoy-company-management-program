package com.yuonetoy.Controller.AccountManagement;

import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountDetailDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountPreviewDTO;
import com.yuonetoy.Service.TaxAccountService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class OpenTaxAccountController implements Initializable {

    @Autowired
    TaxAccountService taxAccountService;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private TaxAccountDetailDTO taxAccountDetailDTO;

    public void setTaxAccount(TaxAccountPreviewDTO taxAccountPreviewDTO) {
        taxAccountDetailDTO = taxAccountService.findTaxAccountDetailDTO(taxAccountPreviewDTO);
    }

    public void initStatus() {
        printAccount();
    }

    @FXML
    private TextField name_tf, representative_tf, address_tf, ph_tf, uptea_tf,
            jongmok_tf, companyNum_tf, entryDate_tf,
            managerName_tf, managerEmail_tf, managerPh_tf;

    public void printAccount() {
        name_tf.setText(taxAccountDetailDTO.getName());
        representative_tf.setText(taxAccountDetailDTO.getRepresentative());
        address_tf.setText(taxAccountDetailDTO.getAddress());
        ph_tf.setText(taxAccountDetailDTO.getPh());
        uptea_tf.setText(taxAccountDetailDTO.getUptea());
        jongmok_tf.setText(taxAccountDetailDTO.getJongmok());
        companyNum_tf.setText(taxAccountDetailDTO.getCompanyNum());
        entryDate_tf.setText(taxAccountDetailDTO.getEntryDate() == null ? "" : taxAccountDetailDTO.getEntryDate().toString());
        managerName_tf.setText(taxAccountDetailDTO.getManager_name());
        managerEmail_tf.setText(taxAccountDetailDTO.getManager_email());
        managerPh_tf.setText(taxAccountDetailDTO.getManager_ph());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}
