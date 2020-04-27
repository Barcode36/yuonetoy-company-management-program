package com.yuonetoy.Controller;

import com.yuonetoy.Controller.AccountManagement.AccountController;
import com.yuonetoy.Controller.BusinessManagement.BusinessJournalEditController;
import com.yuonetoy.Controller.BusinessManagement.BusinessJournalLookupController;
import com.yuonetoy.Controller.CompanyManagement.CompanyController;
import com.yuonetoy.Controller.EmployeeManagement.EmployeeController;
import com.yuonetoy.Controller.EtcManagement.EtcController;
import com.yuonetoy.Controller.ProductManagement.ProductController;
import com.yuonetoy.Controller.SalesManagement.SalesManagementController;
import com.yuonetoy.Controller.StockManagement.StockController;
import com.yuonetoy.Tool.SpringFxmlLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
@Scope("prototype")
public class MainController implements Initializable {
    @Autowired
    private SpringFxmlLoader fxmlLoader;

    private Stage primaryStage;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Button account_management_btn, employee_management_btn, etc_management_btn,
            product_management_btn, stock_management_btn,
            company_management_btn, sales_management_btn, deposit_management_btn;

    @FXML
    private MenuButton business_management_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        account_management_btn.setGraphic(new ImageView("/Images/accountManagement.png"));
        employee_management_btn.setGraphic(new ImageView("/Images/employeeManagement.png"));
        etc_management_btn.setGraphic(new ImageView("/Images/etcManagement.png"));
        product_management_btn.setGraphic(new ImageView("/Images/productManagement.png"));
        business_management_btn.setGraphic(new ImageView("/Images/businessManagement.png"));
        stock_management_btn.setGraphic(new ImageView("/Images/stockManagement.png"));
        company_management_btn.setGraphic(new ImageView("/Images/companyManagement.png"));
        sales_management_btn.setGraphic(new ImageView("/Images/salesManagement.png"));
        deposit_management_btn.setGraphic(new ImageView("/Images/depositManagement.png"));
    }

    private Stage accountStage;

    @FXML
    private void handleAccountBtnClick(ActionEvent e) throws IOException {

        if (accountStage != null && accountStage.isShowing()) {
            accountStage.toFront();
            return;
        }

        accountStage = new Stage(StageStyle.DECORATED);
        accountStage.setTitle("거래처 관리");
        accountStage.setResizable(false);

        FXMLLoader loader = fxmlLoader.load("/View/AccountManagement/AccountView.fxml");
        Parent second = loader.load();
        Scene scene = new Scene(second);

        accountStage.setScene(scene);
        accountStage.show();

        AccountController accountController = loader.getController();
        accountController.setPrimaryStage(accountStage);
        accountController.initStatus();
    }

    private Stage employeeStage;

    @FXML
    private void handleEmployeeBtnClick(ActionEvent e) throws IOException {
        if (employeeStage != null && employeeStage.isShowing()) {
            employeeStage.toFront();
            return;
        }

        employeeStage = new Stage(StageStyle.DECORATED);
        employeeStage.setTitle("사원 관리");
        employeeStage.setResizable(false);

        FXMLLoader loader = fxmlLoader.load("/View/EmployeeManagement/EmployeeView.fxml");
        Parent second = loader.load();
        Scene scene = new Scene(second);

        employeeStage.setScene(scene);
        employeeStage.show();

        EmployeeController employeeController = loader.getController();
        employeeController.setPrimaryStage(employeeStage);
        employeeController.initailStatus();
    }

    private Stage etcStage;

    @FXML
    private void handleEtcBtnClick(ActionEvent e) throws IOException {
        if (etcStage != null && etcStage.isShowing()) {
            etcStage.toFront();
            return;
        }

        etcStage = new Stage(StageStyle.DECORATED);
        etcStage.setTitle("기타 관리");
        etcStage.setResizable(false);

        FXMLLoader loader = fxmlLoader.load("/View/EtcView.fxml");
        Parent second = loader.load();
        Scene scene = new Scene(second);

        etcStage.setScene(scene);
        etcStage.show();

        EtcController etcController = loader.getController();
        etcController.setPrimaryStage(etcStage);
        etcController.initialStatus();
    }

    private Stage productStage;

    @FXML
    private void handleProductBtnClick(ActionEvent e) throws IOException {
        if (productStage != null && productStage.isShowing()) {
            productStage.toFront();
            return;
        }

        productStage = new Stage(StageStyle.DECORATED);
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

    private Stage salesStage;

    @FXML
    private void handleSalesManagementBtnClick(ActionEvent e) throws IOException {
        if (salesStage != null && salesStage.isShowing()) {
            salesStage.toFront();
            return;
        }

        salesStage = new Stage(StageStyle.DECORATED);
        salesStage.setTitle("일일 전표 입력");
        salesStage.setResizable(false);

        FXMLLoader loader = fxmlLoader.load("/View/SalesManagementView.fxml");
        Parent second = loader.load();
        Scene scene = new Scene(second);

        salesStage.setScene(scene);
        salesStage.show();

        SalesManagementController salesManagementController = loader.getController();
        salesManagementController.setPrimaryStage(salesStage);
        salesManagementController.initStatus();
    }

    private Stage companyStage;

    @FXML
    private void handleCompanyBtnClick(ActionEvent e) throws IOException {
        if (companyStage != null && companyStage.isShowing()) {
            companyStage.toFront();
            return;
        }

        companyStage = new Stage(StageStyle.DECORATED);
        companyStage.setTitle("지사 관리");
        companyStage.setResizable(false);

        FXMLLoader loader = fxmlLoader.load("/View/CompanyManagement/CompanyView.fxml");
        Parent second = loader.load();
        Scene scene = new Scene(second);

        companyStage.setScene(scene);
        companyStage.show();

        CompanyController companyController = loader.getController();
        companyController.setPrimaryStage(companyStage);
        companyController.initStatus();
    }

    private Stage stockStage;

    @FXML
    private void handleStockBtnClick(ActionEvent e) throws IOException {
        if (stockStage != null && stockStage.isShowing()) {
            stockStage.toFront();
            return;
        }

        stockStage = new Stage(StageStyle.DECORATED);
        stockStage.setTitle("재고 관리");
        stockStage.setResizable(false);

        FXMLLoader loader = fxmlLoader.load("/View/StockManagement/StockView.fxml");
        Parent second = loader.load();
        Scene scene = new Scene(second);

        stockStage.setScene(scene);
        stockStage.show();

        StockController stockController = loader.getController();
        stockController.setPrimaryStage(stockStage);
        stockController.initStatus();
    }

    private Stage depositStage;

    @FXML
    private void handleDepositManagementBtn(ActionEvent e) throws IOException {
        if (depositStage != null && depositStage.isShowing()) {
            depositStage.toFront();
            return;
        }

        depositStage = new Stage(StageStyle.DECORATED);
        depositStage.setTitle("입금 관리");
        depositStage.setResizable(false);

        FXMLLoader loader = fxmlLoader.load("/View/DepositManagementView.fxml");
        Parent second = loader.load();
        Scene scene = new Scene(second);

        depositStage.setScene(scene);
        depositStage.show();

        DepositManagementController depositManagementController = loader.getController();
        depositManagementController = loader.getController();
        depositManagementController.setPrimaryStage(depositStage);
        depositManagementController.initStatus();
    }


    @FXML
    private void handleBusinessJournalEditBtn() throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        FXMLLoader loader = null;
        AnchorPane page = null;

        loader = fxmlLoader.load("/View/BusinessManagement/BusinessJournalEditView.fxml");
        page = (AnchorPane) loader.load();

        BusinessJournalEditController businessJournalEditController = loader.getController();
        businessJournalEditController = loader.getController();
        businessJournalEditController.setDialogStage(dialogStage);
        businessJournalEditController.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    @FXML
    private void handleBusinessLookupBtn() throws IOException {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);

        FXMLLoader loader = fxmlLoader.load("/View/BusinessManagement/BusinessJournalLookupView.fxml");
        AnchorPane page = (AnchorPane) loader.load();

        BusinessJournalLookupController businessJournalLookupController = loader.getController();
        businessJournalLookupController = loader.getController();
        businessJournalLookupController.setDialogStage(dialogStage);
        businessJournalLookupController.initStatus();

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }


}