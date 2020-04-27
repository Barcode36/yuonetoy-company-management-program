package com.yuonetoy.Controller.EtcManagement;

import com.yuonetoy.DTO.Area.EmployeeAreaDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.MachineDTO;

import com.yuonetoy.DTO.Product.ProductDTO;
import com.yuonetoy.DTO.Product.ProductDetailDTO;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.ProductLinkedMachine;
import com.yuonetoy.Service.*;

import com.yuonetoy.Tool.MakeAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class EtcController implements Initializable {
    //
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Autowired
    private EmployeeAreaService employeeAreaService;
    @Autowired
    private SalesAccountAreaService salesAccountAreaService;
    @Autowired
    private CompanyTypeService companyTypeService;
    @Autowired
    private MachineService machineService;

    @Autowired
    private ProductService productService;


    @FXML
    private TextField employeeArea_tf, salesArea_tf, companyType_tf, secondCompanyType_tf, machine_tf;
    @FXML
    private ListView<EmployeeAreaDTO> employeeAreas_listView;
    @FXML
    private ListView<SalesAccountAreaDTO> salesAreas_listView;
    @FXML
    private ListView<CompanyTypeDTO> companyType_listView;
    @FXML
    private ListView<SecondCompanyTypeDTO> secondCompanyType_listView;
    @FXML
    private TableView<MachineDTO> machines_tableView;
    @FXML
    private TableColumn<MachineDTO, String> c1, c2, c3;

    @FXML
    private ListView<ProductDTO> product_lv;
    private ObservableList<ProductDTO> productDTOS;
    private FilteredList<ProductDTO> productDTOFilteredList;

    private ObservableList<EmployeeAreaDTO> employeeAreas;
    private ObservableList<SalesAccountAreaDTO> salesAccountAreas;
    private ObservableList<CompanyTypeDTO> companyTypes;
    private ObservableList<SecondCompanyTypeDTO> secondCompanyTypes;
    private ObservableList<MachineDTO> machines;


    @FXML
    private ComboBox<String> machineType_cb;

    @FXML
    private TextField product_tf, productSearch_tf;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        machineType_cb.getItems().add(0, "상품 기계 (예 : 자판기)");
        machineType_cb.getItems().add(1, "무상품 기계 (예 : 라이더)");
        machineType_cb.getItems().add(2, "동전 교환기");

        machineType_cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                product_lv.getSelectionModel().clearSelection();
                product_tf.setText("");
                productSearch_tf.setText("");

                switch (newValue.intValue()) {
                    case 0:
                        product_lv.setDisable(false);
                        product_tf.setDisable(false);
                        productSearch_tf.setDisable(false);
                        break;
                    case 1:
                    case 2:
                        product_lv.setDisable(true);
                        product_tf.setDisable(true);
                        productSearch_tf.setDisable(true);
                        break;
                }
            }
        });

        productSearch_tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null)
                    return;

                productDTOFilteredList.setPredicate(productDTO -> {
                    if (productDTO.getName().contains(newValue) || newValue.equals(""))
                        return true;
                    else
                        return false;
                });

                product_lv.setItems(productDTOFilteredList);
            }
        });

        employeeAreas_listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {// && (event.getTarget() instanceof LabeledText)
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !event.getTarget().toString().contains("null")) {
                    updateEmployeeArea();
                }
            }
        });
        employeeAreas = employeeAreas_listView.getItems();

        salesAreas_listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {// && (event.getTarget() instanceof LabeledText)
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !event.getTarget().toString().contains("null")) {
                    updateSalesArea();
                }
            }
        });
        salesAccountAreas = salesAreas_listView.getItems();

        companyType_listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !event.getTarget().toString().contains("null")) {
                    updateCompanyType();
                } else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1 && !event.getTarget().toString().contains("null")) {
                    callSmallBusinessType();
                }
            }
        });
        companyTypes = companyType_listView.getItems();

        secondCompanyType_listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {// && (event.getTarget() instanceof LabeledText)
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2 && !event.getTarget().toString().contains("null")) {
                    updateSecondCompanyType();
                }
            }
        });
        secondCompanyTypes = secondCompanyType_listView.getItems();

        machines = machines_tableView.getItems();
        c1.setCellValueFactory(new PropertyValueFactory<>("machineType"));
        c2.setCellValueFactory(new PropertyValueFactory<>("machineName"));
        c3.setCellValueFactory(new PropertyValueFactory<>("product"));

        machines_tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MachineDTO>() {
            @Override
            public void changed(ObservableValue<? extends MachineDTO> observable, MachineDTO oldValue, MachineDTO newValue) {
                if (newValue.getIsProductMachine()) {
                    if (newValue.getIsCoinChanger())
                        machineType_cb.getSelectionModel().select(2);
                    else
                        machineType_cb.getSelectionModel().select(0);
                } else {
                    machineType_cb.getSelectionModel().select(1);
                }

                machine_tf.setText(newValue.getName());

                if (newValue.getIsProductMachine()) {
                    Optional<ProductDTO> optionalProductDTO = productDTOS.stream().filter(productDTO -> {
                        if (productDTO.getName().equals(newValue.getProduct()))
                            return true;
                        else
                            return false;
                    }).findFirst();

                    if (optionalProductDTO.isPresent()) {
                        product_lv.getSelectionModel().select(optionalProductDTO.get());
                        product_tf.setText(optionalProductDTO.get().getName());
                    }
                }
            }
        });

        productDTOS = product_lv.getItems();
        product_lv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductDTO>() {
            @Override
            public void changed(ObservableValue<? extends ProductDTO> observable, ProductDTO oldValue, ProductDTO newValue) {
                if (newValue != null)
                    product_tf.setText(newValue.getName());
            }
        });

    }

    public void initialStatus() {
        callEmployeeArea();
        callSalesArea();
        callCompanyType();
        callSmallBusinessType();
        callMachine();
        callProduct();
    }

    private void callEmployeeArea() {
        employeeAreas.addAll(employeeAreaService.readEmployeeArea());
    }

    private void callSalesArea() {
        salesAccountAreas.addAll(salesAccountAreaService.readSalesAccountArea());
    }

    private void callMachine() {
        machines.addAll(machineService.readMachine());
    }

    private void callCompanyType() {
        companyTypes.addAll(companyTypeService.readCompanyType());
        companyType_listView.getSelectionModel().select(0);
    }

    private void callProduct() {
        productDTOS.addAll(productService.getProductDTO());
        productDTOFilteredList = productDTOS.filtered(productDTO -> true);
    }

    @FXML
    private void callSmallBusinessType() {
        CompanyTypeDTO companyTypeDTO = companyType_listView.getSelectionModel().getSelectedItem();

        if (companyTypeDTO == null)
            return;

        secondCompanyTypes.clear();
        secondCompanyType_listView.getItems().clear();

        secondCompanyTypes.addAll(companyTypeService.readSecondCompanyType(companyTypeDTO));
    }

    public void addEmployeeArea() {
        final String name = employeeArea_tf.getText();

        EmployeeAreaDTO employeeAreaDTO = employeeAreaService.addEmployeeArea(name);
        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "추가 완료", false);

        employeeAreas.add(employeeAreaDTO);

        employeeArea_tf.setText("");
    }

    public void addSalesArea() {
        final String name = salesArea_tf.getText();

        SalesAccountAreaDTO salesAccountAreaDTO = salesAccountAreaService.addSalesAccountArea(name);
        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "추가 완료", false);

        salesAccountAreas.add(salesAccountAreaDTO);

        salesArea_tf.setText("");
    }

    public void addCompanyType() {
        final String name = companyType_tf.getText();

        CompanyTypeDTO companyTypeDTO = companyTypeService.addCompanyType(name);
        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "추가 완료", false);

        companyTypes.add(companyTypeDTO);

        companyType_tf.setText("");
    }

    public void addSecondCompanyType() {
        CompanyTypeDTO companyType = companyType_listView.getSelectionModel().getSelectedItem();

        if (companyType == null)
            return;

        final String name = secondCompanyType_tf.getText();

        SecondCompanyTypeDTO secondCompanyTypeDTO = companyTypeService.addSecondCompanyType(name, companyType);
        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "추가 완료", false);

        secondCompanyTypes.add(secondCompanyTypeDTO);

        secondCompanyType_tf.setText("");
    }

    public void addMachine() {
        final String name = machine_tf.getText();
        final Boolean isCoinChanger = machineType_cb.getSelectionModel().isSelected(2);
        final Boolean isProductMachine = machineType_cb.getSelectionModel().isSelected(0);
        ProductDTO productDTO = product_lv.getSelectionModel().getSelectedItem();

        try {
            MachineDTO machineDTO = machineService.addMachine(name, isCoinChanger, isProductMachine, productDTO);
            new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "추가 완료", false);

            Optional<MachineDTO> optionalMachineDTO = machines.stream().filter(machineDTO1 -> {
                if (machineDTO1.getName().equals(name))
                    return true;
                else
                    return false;
            }).findFirst();

            if (optionalMachineDTO.isPresent()) {
                machines.set(machines.indexOf(optionalMachineDTO.get()), machineDTO);
            } else {
                machines.add(machineDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new MakeAlert().makeErrorMessage(primaryStage, "알림", "실패", "추가 실패", false);
        }

        machine_tf.setText("");
        product_lv.getSelectionModel().clearSelection();
        product_tf.setText("");
        productSearch_tf.setText("");
    }

    public void delEmployeeArea() {
        final EmployeeAreaDTO employeeAreaDTO = employeeAreas_listView.getSelectionModel().getSelectedItem();
        employeeAreaService.deleteEmployeeArea(employeeAreaDTO);
        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "삭제 완료", false);

        employeeAreas.remove(employeeAreaDTO);
    }

    public void delSalesAccountArea() {
        final SalesAccountAreaDTO salesAccountAreaDTO = salesAreas_listView.getSelectionModel().getSelectedItem();
        salesAccountAreaService.deleteSalesAccountArea(salesAccountAreaDTO);
        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "삭제 완료", false);

        salesAccountAreas.remove(salesAccountAreaDTO);
    }

    public void delCompanyType() {
        final CompanyTypeDTO companyTypeDTO = companyType_listView.getSelectionModel().getSelectedItem();
        companyTypeService.deleteCompanyType(companyTypeDTO);
        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "삭제 완료", false);

        companyTypes.remove(companyTypeDTO);
    }

    public void delSecondCompanyType() {
        final SecondCompanyTypeDTO secondCompanyTypeDTO = secondCompanyType_listView.getSelectionModel().getSelectedItem();
        companyTypeService.deleteSecondCompanyType(secondCompanyTypeDTO);
        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "삭제 완료", false);

        secondCompanyTypes.remove(secondCompanyTypeDTO);
    }

    public void delMachine() {
        final MachineDTO machineDTO = machines_tableView.getSelectionModel().getSelectedItem();
        machineService.deleteMachine(machineDTO);
        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "삭제 완료", false);

        machines.remove(machineDTO);
    }

    public void updateEmployeeArea() {
        int index = employeeAreas_listView.getSelectionModel().getSelectedIndex();
        EmployeeAreaDTO employeeAreaDTO = employeeAreas_listView.getSelectionModel().getSelectedItem();

        String name = inputDialog();
        if (name == null) return;

        employeeAreaDTO = employeeAreaService.update(employeeAreaDTO, name);
        employeeAreas.set(index, employeeAreaDTO);

        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "수정 완료", false);
    }

    public void updateSalesArea() {
        final int index = salesAreas_listView.getSelectionModel().getSelectedIndex();
        SalesAccountAreaDTO salesAccountAreaDTO = salesAreas_listView.getSelectionModel().getSelectedItem();

        String name = inputDialog();
        if (name == null) return;

        salesAccountAreaDTO = salesAccountAreaService.update(salesAccountAreaDTO, name);
        salesAccountAreas.set(index, salesAccountAreaDTO);

        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "수정 완료", false);
    }

    public void updateCompanyType() {
        final int index = companyType_listView.getSelectionModel().getSelectedIndex();
        CompanyTypeDTO companyTypeDTO = companyType_listView.getSelectionModel().getSelectedItem();

        String name = inputDialog();
        if (name == null) return;

        companyTypeDTO = companyTypeService.updateCompanyType(companyTypeDTO, name);
        companyTypes.set(index, companyTypeDTO);

        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "수정 완료", false);
    }

    public void updateSecondCompanyType() {
        final int index = secondCompanyType_listView.getSelectionModel().getSelectedIndex();
        SecondCompanyTypeDTO secondCompanyTypeDTO = secondCompanyType_listView.getSelectionModel().getSelectedItem();

        String name = inputDialog();
        if (name == null) return;

        secondCompanyTypeDTO = companyTypeService.updateSecondCompanyType(secondCompanyTypeDTO, name);
        secondCompanyTypes.set(index, secondCompanyTypeDTO);

        new MakeAlert().makeInfoMessage(primaryStage, "알림", "완료", "수정 완료", false);
    }

    public String inputDialog() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("수정");
        dialog.setHeaderText("내용을 수정합니다");
        dialog.setContentText("수정할 내용을 입력해주세요 : ");

        //result.ifPresent(name -> System.out.println("Your name: " + name));
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }
}
