package com.yuonetoy.Controller.AccountManagement;

import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountDetailDTO;
import com.yuonetoy.DTO.Account.TaxAccount.TaxAccountPreviewDTO;
import com.yuonetoy.Service.TaxAccountService;
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
import java.util.ResourceBundle;

@Controller
public class AddTaxAccountController implements Initializable {

    @Autowired
    TaxAccountService taxAccountService;

    private int mode = 0;

    public void setMode(int mode) {
        this.mode = mode;
        if (mode == 0) add_btn.setText("추가");
        else if (mode == 1) add_btn.setText("수정");
    }

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private ObservableList<TaxAccountPreviewDTO> taxAccounts;

    public void setTaxAccountList(ObservableList<TaxAccountPreviewDTO> taxAccounts) {
        this.taxAccounts = taxAccounts;
    }

    private TaxAccountDetailDTO taxAccountDetailDTO;
    private int selectedTaxAccountIndex;

    public void setTaxAccount(TaxAccountPreviewDTO taxAccountPreviewDTO) {
        selectedTaxAccountIndex = taxAccounts.indexOf(taxAccountPreviewDTO);
        taxAccountDetailDTO = taxAccountService.findTaxAccountDetailDTO(taxAccountPreviewDTO);
    }

    @FXML
    private TextField name_tf, representative_tf, address_tf, ph_tf,
            uptea_tf, jongmok_tf, companyNum_tf,
            managerName_tf, managerEmail_tf, managerPh_tf;
    @FXML
    private DatePicker entryDate_dp;
    @FXML
    private Button add_btn;

    public void printAccount() {
        name_tf.setText(taxAccountDetailDTO.getName());
        representative_tf.setText(taxAccountDetailDTO.getRepresentative());
        address_tf.setText(taxAccountDetailDTO.getAddress());
        ph_tf.setText(taxAccountDetailDTO.getPh());
        uptea_tf.setText(taxAccountDetailDTO.getUptea());
        jongmok_tf.setText(taxAccountDetailDTO.getJongmok());
        companyNum_tf.setText(taxAccountDetailDTO.getCompanyNum());
        entryDate_dp.setValue(taxAccountDetailDTO.getEntryDate());
        managerName_tf.setText(taxAccountDetailDTO.getManager_name());
        managerEmail_tf.setText(taxAccountDetailDTO.getManager_email());
        managerPh_tf.setText(taxAccountDetailDTO.getManager_ph());
    }

    public void initStatus(){
        if (mode == 1){
            printAccount();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        final String managerName = managerName_tf.getText();
        final String managerEmail = managerEmail_tf.getText();
        final String managerPh = managerPh_tf.getText();

        try{
            TaxAccountPreviewDTO taxAccountPreviewDTO = taxAccountService.addTaxAccount(name, representative, address, ph, entryDate, uptea, jongmok, companyNum, managerName, managerEmail, managerPh);
            if (taxAccounts != null)
                taxAccounts.add(taxAccountPreviewDTO);

            new MakeAlert().makeInfoMessage(dialogStage, "추가 완료","완료", "추가 완료", true);
        }catch (Exception e){
            new MakeAlert().makeErrorMessage(dialogStage, "추가 실패","실패", "추가 실패" + e.toString(), true);
            e.printStackTrace();
        }

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
        final String managerName = managerName_tf.getText();
        final String managerEmail = managerEmail_tf.getText();
        final String managerPh = managerPh_tf.getText();

        try {
            TaxAccountPreviewDTO taxAccountPreviewDTO = taxAccountService.updateTaxAccount(taxAccountDetailDTO, name, representative, address, ph, entryDate, uptea, jongmok, companyNum, managerName, managerEmail, managerPh);
            taxAccounts.set(selectedTaxAccountIndex, taxAccountPreviewDTO);

            new MakeAlert().makeInfoMessage(dialogStage, "수정 완료","완료", "수정 완료", true);
        }catch (Exception e){
            new MakeAlert().makeErrorMessage(dialogStage, "추가 실패","실패", "추가 실패" + e.toString(), true);
            e.printStackTrace();
        }
    }
}
