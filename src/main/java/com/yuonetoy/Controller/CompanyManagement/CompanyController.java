package com.yuonetoy.Controller.CompanyManagement;

import com.yuonetoy.DTO.Company.CompanyDetailDTO;
import com.yuonetoy.Service.CompanyService;
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
public class CompanyController implements Initializable {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initStatus() {
        callCompany();
    }

    @FXML
    private TableColumn<CompanyDetailDTO,String> c_c1, c_c2;
    @FXML
    private TableView<CompanyDetailDTO> company_tableView;
    private ObservableList<CompanyDetailDTO> companies;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        c_c1.setCellValueFactory(new PropertyValueFactory<>("name"));
        c_c2.setCellValueFactory(new PropertyValueFactory<>("address"));

        companies = company_tableView.getItems();
    }

    @FXML
    private TextField name_tf, address_tf;

    @Autowired
    CompanyService companyService;

    @FXML
    private void handleAddBtn(){
        final String name = name_tf.getText();
        final String address = address_tf.getText();

        CompanyDetailDTO companyDetailDTO = companyService.addCompany(name, address);

        companies.add(companyDetailDTO);

        name_tf.setText("");
        address_tf.setText("");
    }

    @FXML
    private void handleDelBtn(){
        CompanyDetailDTO selectedCompanyDetailDTO = company_tableView.getSelectionModel().getSelectedItem();

        companyService.deleteCompany(selectedCompanyDetailDTO);
        companies.remove(selectedCompanyDetailDTO);
    }

    private void callCompany() {
        List<CompanyDetailDTO> companyDetailDTOList = companyService.readCompanyDetail();
        this.companies.addAll(companyDetailDTOList);
    }
}
