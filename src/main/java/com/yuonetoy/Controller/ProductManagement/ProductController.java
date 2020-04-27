package com.yuonetoy.Controller.ProductManagement;


import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductDetailDTO;
import com.yuonetoy.Service.ProductService;
import com.yuonetoy.Tool.MakeAlert;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class ProductController implements Initializable {

    @Autowired
    ProductService productService;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private TextField purchase_tf, search_tf, productSearch_tf, purchaseSearch_tf;

    public void initStatus() {
        callProduct();
        callPurchase();

        productSearch_tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null)
                    return;

                productDetailDTOFilteredList.setPredicate(productDetailDTO -> {
                    boolean productSearch = productDetailDTO.getName().contains(productSearch_tf.getText()) || productSearch_tf.getText().equals("");
                    String purchaseAccountName = productDetailDTO.getPurchaseAccount() != null ? productDetailDTO.getPurchaseAccount().getName() : "";
                    boolean purchaseSearch = purchaseAccountName.contains(purchaseSearch_tf.getText()) || purchaseSearch_tf.getText().equals("");

                    return (productSearch && purchaseSearch);
                });

                product_tableView.setItems(productDetailDTOFilteredList);
            }
        });

        purchaseSearch_tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null)
                    return;

                productDetailDTOFilteredList.setPredicate(productDetailDTO -> {
                    boolean productSearch = productDetailDTO.getName().contains(productSearch_tf.getText()) || productSearch_tf.getText().equals("");

                    String purchaseAccountName = productDetailDTO.getPurchaseAccount() != null ? productDetailDTO.getPurchaseAccount().getName() : "";
                    boolean purchaseSearch = purchaseAccountName.contains(purchaseSearch_tf.getText()) || purchaseSearch_tf.getText().equals("");

                    return (productSearch && purchaseSearch);
                });

                product_tableView.setItems(productDetailDTOFilteredList);
            }
        });

        search_tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (oldValue == null || newValue == null)
                    return;
                purchaseAccountDTOFilteredList.setPredicate(purchaseAccountDTO -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String searchText = newValue;
                    if (purchaseAccountDTO.getName().contains(searchText)) {
                        return true;
                    }
                    return false;
                });
                purchase_tableView.setItems(purchaseAccountDTOFilteredList);
            }
        });
    }

    @FXML
    private TableColumn<ProductDetailDTO, String> p_c1, p_c2, p_c3, p_c4;
    @FXML
    private TableView<ProductDetailDTO> product_tableView;
    ObservableList<ProductDetailDTO> products;
    FilteredList<ProductDetailDTO> productDetailDTOFilteredList;

    @FXML
    private TableColumn<PurchaseAccountDTO, String> pa_c1, pa_c2;
    @FXML
    private TableView<PurchaseAccountDTO> purchase_tableView;
    ObservableList<PurchaseAccountDTO> purchaseAccounts;
    FilteredList<PurchaseAccountDTO> purchaseAccountDTOFilteredList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        p_c1.setCellValueFactory(new PropertyValueFactory<>("productType"));
        p_c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        p_c3.setCellValueFactory(new PropertyValueFactory<>("price"));
        p_c4.setCellValueFactory(new PropertyValueFactory<>("purchaseAccount"));

        products = product_tableView.getItems();

        product_tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProductDetailDTO>() {
            @Override
            public void changed(ObservableValue<? extends ProductDetailDTO> observable, ProductDetailDTO oldValue, ProductDetailDTO newValue) {
                name_tf.setText(newValue.getName());
                price_tf.setText(String.valueOf(newValue.getPrice()));
                capsuleToy_checkBox.setSelected(newValue.getCapsuleToy());

                if (newValue.getPurchaseAccount() != null) {
                    purchase_tf.setText(newValue.getPurchaseAccount().getName());
                    purchase_tableView.getSelectionModel().select(newValue.getPurchaseAccount());
                }
                else {
                    purchase_tf.setText("");
                    purchase_tableView.getSelectionModel().clearSelection();
                }
            }
        });

        pa_c1.setCellValueFactory(new PropertyValueFactory<>("name"));
        pa_c2.setCellValueFactory(new PropertyValueFactory<>("address"));

        purchaseAccounts = purchase_tableView.getItems();
        purchase_tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PurchaseAccountDTO>() {
            @Override
            public void changed(ObservableValue<? extends PurchaseAccountDTO> observable, PurchaseAccountDTO oldValue, PurchaseAccountDTO newValue) {
                if (newValue != null)
                purchase_tf.setText(newValue.getName());
            }
        });
    }

    private void callPurchase() {
        purchaseAccounts.addAll(productService.getPurchaseAccount());

        purchaseAccountDTOFilteredList = this.purchaseAccounts.filtered(account -> true);
    }

    private void callProduct() {
        products.addAll(productService.getProductDetailDTO());
        productDetailDTOFilteredList = products.filtered(productDetailDTO -> true);
    }

    @FXML
    private TextField name_tf, price_tf;

    @FXML
    private CheckBox capsuleToy_checkBox;

    @FXML
    private void handleAddBtn() {
        String name = name_tf.getText();
        int price = Integer.parseInt(price_tf.getText());
        boolean isCapsuleToy = capsuleToy_checkBox.isSelected();
        PurchaseAccountDTO purchaseAccountDTO = purchase_tableView.getSelectionModel().getSelectedItem();

        try {
            ProductDetailDTO productDetailDTO = productService.addProductDetailDTO(name, price, isCapsuleToy, purchaseAccountDTO);

            Optional<ProductDetailDTO> optionalProductDetailDTO = products.stream().filter(productDetailDTO1 -> {
                if (productDetailDTO1.getName().equals(name))
                    return true;
                else
                    return false;
            }).findFirst();

            if (optionalProductDetailDTO.isPresent()){
                products.set(products.indexOf(optionalProductDetailDTO.get()), productDetailDTO);
            }else{
                products.add(productDetailDTO);
            }

            new MakeAlert().makeInfoMessage(primaryStage, "알림", "상품 추가", "상품이 추가되었습니다", false);

            name_tf.setText("");
            price_tf.setText("");
            purchase_tf.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            new MakeAlert().makeErrorMessage(primaryStage, "알림", "상품 추가", "상품을 추가할 수 없습니다", false);
        }
    }

    @FXML
    private void handleDelBtn() {
        ProductDetailDTO selectedProduct = product_tableView.getSelectionModel().getSelectedItem();

        boolean successes = false;

        if (selectedProduct.getName().hashCode() == "캡슐 토이".hashCode() || selectedProduct.getName().hashCode() == "시재금".hashCode()) {
            new MakeAlert().makeInfoMessage(primaryStage, "경고", "상품 삭제", "해당 상품은 삭제할 수 없습니다", false);
            return;
        }

        try {
            productService.deleteProduct(selectedProduct);
            successes = true;
        } catch (Exception e) {
            new MakeAlert().makeInfoMessage(primaryStage, "경고", "상품 삭제", "해당 상품은 사용중인 상품입니다", false);
        }

        if (successes) products.remove(selectedProduct);
    }
}
