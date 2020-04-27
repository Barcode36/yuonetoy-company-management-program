package com.yuonetoy.Controller.StockManagement;

import com.yuonetoy.Controller.ProductManagement.ProductController;
import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.SendStockDTO;
import com.yuonetoy.DTO.Stock.CompanyStockDTO;
import com.yuonetoy.DTO.Stock.MakeMachineStockDTO;
import com.yuonetoy.DTO.Stock.PurchaseAccountStockDTO;
import com.yuonetoy.DTO.Stock.StockDTO;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Service.SendStockService;
import com.yuonetoy.Tool.MakeAlert;
import com.yuonetoy.Tool.SpringFxmlLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class SendStockController implements Initializable {
    @Autowired
    private SendStockService sendStockService;

    @Autowired
    private SpringFxmlLoader fxmlLoader;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private int mode = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSourceTableView();
        initTargetTableView();

        initComboBox();
    }

    @FXML
    private TextField sourceName_tf, sourceStock_tf;

    public void initStatus() {
        sendStockDTOHashtable = new Hashtable<>();
    }

    @FXML
    private ComboBox<String> type_cb, stockType_cb;

    @FXML
    private ComboBox<Object> source_cb;
    private ObservableList<Object> sources;

    @FXML
    private ComboBox<Object> target_cb;
    private ObservableList<Object> targets;

    private void initComboBox() {
        type_cb.getItems().add("상품 입고");
        type_cb.getItems().add("상품 출고");
        type_cb.getItems().add("본지 이동");
        type_cb.getItems().add("기계 입고");

        type_cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mode = newValue.intValue();

                sendStockDTOHashtable.clear();
                sourceStockList.clear();
                targetStockList.clear();
                sources.clear();
                targets.clear();

                switch (mode) {
                    case 0:
                        sm2.setCellValueFactory(new PropertyValueFactory<>("purchaseAccount"));
                        sm2.setText("과세 거래처명");

                        tm2.setText("과세 거래처명");
                        tm3.setText("지점명");

                        sm1.setVisible(false);
                        sm2.setVisible(true);
                        sm4.setVisible(true);
                        sm5.setVisible(false);
                        callPurchaseAccountProduct();
                        callCompany(targets);
                        break;
                    case 1:
                        sm2.setCellValueFactory(new PropertyValueFactory<>("company"));
                        sm2.setText("지점명");

                        tm2.setText("지점명");
                        tm3.setText("사원명");

                        sm1.setVisible(true);
                        sm2.setVisible(true);
                        sm4.setVisible(true);
                        sm5.setVisible(true);
                        callCompany(sources);
                        callEmployee();
                        break;
                    case 2:
                        sm2.setCellValueFactory(new PropertyValueFactory<>("company"));
                        sm2.setText("지점명");

                        tm2.setText("지점명");
                        tm3.setText("지점명");

                        sm1.setVisible(true);
                        sm2.setVisible(true);
                        sm4.setVisible(true);
                        sm5.setVisible(true);
                        callCompany(sources);
                        break;
                    case 3:
                        tm3.setText("기계명");

                        sm1.setVisible(false);
                        sm2.setVisible(false);
                        sm4.setVisible(false);
                        sm5.setVisible(false);
                        callMachine();
                        callCompany(targets);
                        break;
                }
            }
        });

        stockType_cb.getItems().add("상품 재고");
        stockType_cb.getItems().add("기계 재고");

        sources = source_cb.getItems();
        source_cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (mode != 0)
                    callCompanyStock();

                if (mode != 2) return;
                targets.clear();
                targets.addAll(sources);
                targets.remove(newValue.intValue());
            }
        });
        targets = target_cb.getItems();
    }

    private void callCompany(List<Object> comboBoxList) {
        comboBoxList.addAll(sendStockService.getCompany());
    }

    private void callEmployee() {
        targets.addAll(sendStockService.getEmployee());
    }

    private void callPurchaseAccountProduct() {
        sourceStockList.clear();
        sourceStockList.addAll(sendStockService.getPurchaseAccountProduct());
    }

    private void callMachine() {
        sourceStockList.clear();
        sourceStockList.addAll(sendStockService.getMachine());
    }

    private void callCompanyStock() {
        sourceStockList.clear();
        CompanyDTO companyDTO = (CompanyDTO) source_cb.getSelectionModel().getSelectedItem();
        sourceStockList.addAll(sendStockService.getCompanyStock(companyDTO));
    }


    @FXML
    private TableView<StockDTO> source_tableView;
    @FXML
    private TableColumn<StockDTO, String> sm1, sm2, sm3, sm4, sm5;
    private ObservableList<StockDTO> sourceStockList;

    @FXML
    private TextField stock_tf;

    private void initSourceTableView() {
        sm1.setCellValueFactory(new PropertyValueFactory<>("stockType"));
        sm3.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        sm4.setCellValueFactory(new PropertyValueFactory<>("price"));
        sm5.setCellValueFactory(new PropertyValueFactory<>("count"));

        sourceStockList = source_tableView.getItems();
        source_tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StockDTO>() {
            @Override
            public void changed(ObservableValue<? extends StockDTO> observable, StockDTO oldValue, StockDTO newValue) {
                switch (mode) {
                    case 0:
                        PurchaseAccountStockDTO purchaseAccountStockDTO = (PurchaseAccountStockDTO) newValue;
                        stock_tf.setText(purchaseAccountStockDTO.getStockName());
                        break;
                    case 1:
                    case 2:
                        CompanyStockDTO companyStockDTO = (CompanyStockDTO) newValue;
                        stock_tf.setText(companyStockDTO.getStockName());
                        break;
                }
            }
        });
    }

    @FXML
    private TextField count_tf;
    @FXML
    private TableView<SendStockDTO> target_tableView;
    @FXML
    private TableColumn<SendStockDTO, String> tm1, tm2, tm3, tm4, tm5, tm6, tm7;
    private ObservableList<SendStockDTO> targetStockList;

    private void initTargetTableView() {
        tm1.setCellValueFactory(new PropertyValueFactory<>("type"));
        tm2.setCellValueFactory(new PropertyValueFactory<>("sourceName"));
        tm3.setCellValueFactory(new PropertyValueFactory<>("targetName"));
        tm4.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        tm5.setCellValueFactory(new PropertyValueFactory<>("stockPrice"));
        tm6.setCellValueFactory(new PropertyValueFactory<>("sendCount"));
        tm7.setCellValueFactory(new PropertyValueFactory<>("date"));
        targetStockList = target_tableView.getItems();
    }

    private Hashtable<Integer, SendStockDTO> sendStockDTOHashtable;

    private void purchaseAccountToCompany() {
        int sendCount = Integer.parseInt(count_tf.getText());
        PurchaseAccountStockDTO purchaseAccountStockDTO = (PurchaseAccountStockDTO) source_tableView.getSelectionModel().getSelectedItem();
        CompanyDTO companyDTO = (CompanyDTO) target_cb.getSelectionModel().getSelectedItem();
        ProductPriceDTO productPriceDTO = purchaseAccountStockDTO.getProduct();
        int stockNameHash = purchaseAccountStockDTO.getStockName().hashCode();
        LocalDate date = date_dp.getValue();

        if (sendStockDTOHashtable.containsKey(stockNameHash)) {
            SendStockDTO temp = sendStockDTOHashtable.get(stockNameHash);
            temp.setSendCount(sendCount);
            targetStockList.setAll(sendStockDTOHashtable.values());
        } else {
            SendStockDTO sendStockDTO = new SendStockDTO();
            sendStockDTO.setType(type_cb.getSelectionModel().getSelectedItem());
            sendStockDTO.setSourcePurchaseAccount(purchaseAccountStockDTO.getPurchaseAccount());
            sendStockDTO.setTargetCompany(companyDTO);
            sendStockDTO.setIsProduct(true);
            sendStockDTO.setProduct(productPriceDTO);
            sendStockDTO.setSendCount(sendCount);
            sendStockDTO.setDate(date);
            sendStockDTO.initStatus();

            sendStockDTOHashtable.put(stockNameHash, sendStockDTO);
            targetStockList.add(sendStockDTO);
        }
    }

    private void CompanyToEmployee() {
        int sendCount = Integer.parseInt(count_tf.getText());
        CompanyStockDTO companyStockDTO = (CompanyStockDTO) source_tableView.getSelectionModel().getSelectedItem();
        EmployeeDTO employeeDTO = (EmployeeDTO) target_cb.getSelectionModel().getSelectedItem();
        ProductPriceDTO productPriceDTO = companyStockDTO.getProduct();
        MachineDTO machineDTO = companyStockDTO.getMachine();
        LocalDate date = date_dp.getValue();

        int stockNameHash = companyStockDTO.getStockName().hashCode();

        if (sendStockDTOHashtable.containsKey(stockNameHash)) {
            SendStockDTO temp = sendStockDTOHashtable.get(stockNameHash);
            temp.setSendCount(sendCount);
            targetStockList.setAll(sendStockDTOHashtable.values());
        } else {
            SendStockDTO sendStockDTO = new SendStockDTO();
            sendStockDTO.setType(type_cb.getSelectionModel().getSelectedItem());
            sendStockDTO.setSourceCompany(companyStockDTO.getCompany());
            sendStockDTO.setTargetEmployee(employeeDTO);

            if (companyStockDTO.isProduct()){
                sendStockDTO.setIsProduct(true);
                sendStockDTO.setProduct(productPriceDTO);
            }else{
                sendStockDTO.setIsProduct(false);
                sendStockDTO.setMachine(machineDTO);
            }

            sendStockDTO.setSendCount(sendCount);
            sendStockDTO.setDate(date);

            sendStockDTO.initStatus();

            sendStockDTOHashtable.put(stockNameHash, sendStockDTO);
            targetStockList.add(sendStockDTO);
        }
    }

    private void companyToCompany() {
        int sendCount = Integer.parseInt(count_tf.getText());
        CompanyStockDTO companyStockDTO = (CompanyStockDTO) source_tableView.getSelectionModel().getSelectedItem();
        CompanyDTO companyDTO = (CompanyDTO) target_cb.getSelectionModel().getSelectedItem();
        ProductPriceDTO productPriceDTO = companyStockDTO.getProduct();
        MachineDTO machineDTO = companyStockDTO.getMachine();
        LocalDate date = date_dp.getValue();

        int stockNameHash = companyStockDTO.getStockName().hashCode();

        if (sendStockDTOHashtable.containsKey(stockNameHash)) {
            SendStockDTO temp = sendStockDTOHashtable.get(stockNameHash);
            temp.setSendCount(sendCount);
            targetStockList.setAll(sendStockDTOHashtable.values());
        } else {
            SendStockDTO sendStockDTO = new SendStockDTO();
            sendStockDTO.setType(type_cb.getSelectionModel().getSelectedItem());
            sendStockDTO.setSourceCompany(companyStockDTO.getCompany());
            sendStockDTO.setTargetCompany(companyDTO);
            sendStockDTO.setSendCount(sendCount);
            sendStockDTO.setDate(date);

            if (companyStockDTO.isProduct()){
                sendStockDTO.setIsProduct(true);
                sendStockDTO.setProduct(productPriceDTO);
            }else{
                sendStockDTO.setIsProduct(false);
                sendStockDTO.setMachine(machineDTO);
            }

            sendStockDTO.initStatus();

            sendStockDTOHashtable.put(stockNameHash, sendStockDTO);
            targetStockList.add(sendStockDTO);
        }
    }

    private void MachineToCompany() {
        int sendCount = Integer.parseInt(count_tf.getText());
        MakeMachineStockDTO makeMachineStockDTO = (MakeMachineStockDTO) source_tableView.getSelectionModel().getSelectedItem();
        CompanyDTO companyDTO = (CompanyDTO) target_cb.getSelectionModel().getSelectedItem();
        MachineDTO machineDTO = makeMachineStockDTO.getMachine();
        LocalDate date = date_dp.getValue();

        int stockNameHash = makeMachineStockDTO.getStockName().hashCode();


        if (sendStockDTOHashtable.containsKey(stockNameHash)) {
            SendStockDTO temp = sendStockDTOHashtable.get(stockNameHash);
            temp.setSendCount(sendCount);
            targetStockList.setAll(sendStockDTOHashtable.values());
        } else {
            SendStockDTO sendStockDTO = new SendStockDTO();
            sendStockDTO.setType(type_cb.getSelectionModel().getSelectedItem());
            sendStockDTO.setTargetCompany(companyDTO);
            sendStockDTO.setIsProduct(false);
            sendStockDTO.setMachine(machineDTO);
            sendStockDTO.setSendCount(sendCount);
            sendStockDTO.setDate(date);
            sendStockDTO.initStatus();

            sendStockDTOHashtable.put(stockNameHash, sendStockDTO);
            targetStockList.add(sendStockDTO);
        }
    }

    @FXML
    private void handlePlusBtn() {
        switch (mode) {
            case 0:
                purchaseAccountToCompany();
                break;
            case 1:
                CompanyToEmployee();
                break;
            case 2:
                companyToCompany();
                break;
            case 3:
                MachineToCompany();
                break;
        }
    }

    @FXML
    private void handleMinusBtn() {
        SendStockDTO sendStockDTO = target_tableView.getSelectionModel().getSelectedItem();
        sendStockDTOHashtable.remove(sendStockDTO);
        targetStockList.remove(sendStockDTO);
    }

    private Stage productStage;

    @FXML
    private void handleProductBtnClick(ActionEvent e) throws IOException {
        if (productStage != null && productStage.isShowing()) {
            productStage.toFront();
            return;
        }

        productStage = new Stage(StageStyle.DECORATED);
        productStage.initModality(Modality.APPLICATION_MODAL);
        productStage.setTitle("일일 전표 입력");
        productStage.setResizable(false);

        FXMLLoader loader = fxmlLoader.load("/View/ProductManagement/ProductView.fxml");
        Parent second = loader.load();
        Scene scene = new Scene(second);

        productStage.setScene(scene);
        productStage.show();

        ProductController productController = loader.getController();
        productController.setPrimaryStage(productStage);
        productController.initStatus();
    }

    @FXML
    private DatePicker date_dp;

    @FXML
    private void handleAddBtnClick() {
        switch (mode) {
            case 0:
                sendStockService.sendStockPAtoC(targetStockList);
                break;
            case 1:
                sendStockService.sendStockCtoE(targetStockList);
                break;
            case 2:
                sendStockService.sendStockCtoC(targetStockList);
                break;
            case 3:
                sendStockService.sendStockMtoC(targetStockList);
        }
        type_cb.getSelectionModel().select(0);
        new MakeAlert().makeInfoMessage(dialogStage, "완료", "완료", "완료", false);
    }

    @FXML
    private void handleCancleBtnClick() {
        dialogStage.close();
    }
}
