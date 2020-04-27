package com.yuonetoy.Controller.StockManagement;

import com.yuonetoy.DTO.Stock.*;
import com.yuonetoy.Service.StockManagementService;
import com.yuonetoy.Tool.MakeAlert;
import com.yuonetoy.Tool.PredicateCase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class StockManagementController implements Initializable {
    @Autowired
    private StockManagementService stockManagementService;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void initStatus() {
        //initialEmployeeAndCompanyStock();
    }

    @FXML
    private DatePicker date_dp;

    @FXML
    private TableView<SourceInfoAtStockManagementDTO> source_tableView;
    @FXML
    private TableColumn<SourceInfoAtStockManagementDTO, String> s1, s2;
    private ObservableList<SourceInfoAtStockManagementDTO> sourceInfoAtStockManagementDTOList;
    private FilteredList<SourceInfoAtStockManagementDTO> sourceInfoAtStockManagementDTOFilteredList;

    private void initSourceInfoTableView() {
        s1.setCellValueFactory(new PropertyValueFactory<>("name"));
        s2.setCellValueFactory(new PropertyValueFactory<>("address"));

        sourceInfoAtStockManagementDTOList = source_tableView.getItems();
        sourceInfoAtStockManagementDTOFilteredList = sourceInfoAtStockManagementDTOList.filtered(sourceInfoAtStockManagementDTO -> true);
        source_tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SourceInfoAtStockManagementDTO>() {
            @Override
            public void changed(ObservableValue<? extends SourceInfoAtStockManagementDTO> observable, SourceInfoAtStockManagementDTO oldValue, SourceInfoAtStockManagementDTO newValue) {
                switch (type_cb.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        callCompanyStock();
                        break;
                    case 1:
                        callEmployeeStock();
                        break;
                    case 2:
                        callSalesAccountStock();
                        break;
                }
            }
        });
    }

    @FXML
    private TableView<StockAtStockManagementDTO> stock_tableView;
    @FXML
    private TableColumn<StockAtStockManagementDTO, String> st1, st2;
    private ObservableList<StockAtStockManagementDTO> stockAtStockManagementDTOList;
    private FilteredList<StockAtStockManagementDTO> stockAtStockManagementDTOFilteredList;

    private void initStockTableView() {
        st1.setCellValueFactory(new PropertyValueFactory<>("stockType"));
        st2.setCellValueFactory(new PropertyValueFactory<>("stockName"));

        stockAtStockManagementDTOList = stock_tableView.getItems();

        stockAtStockManagementDTOFilteredList = stockAtStockManagementDTOList.filtered(stockAtStockManagementDTO -> true);

        stock_tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<StockAtStockManagementDTO>() {
            @Override
            public void changed(ObservableValue<? extends StockAtStockManagementDTO> observable, StockAtStockManagementDTO oldValue, StockAtStockManagementDTO newValue) {
                if (newValue != null)
                stock_tf.setText(newValue.getStockName());
            }
        });
    }

    @FXML
    private TableView<SourceStockAtStockManagementDTO> sourceStock_tableView;
    @FXML
    private TableColumn<SourceStockAtStockManagementDTO, String> t1, t2, t5;
    @FXML
    private TableColumn<SourceStockAtStockManagementDTO, Integer> t3, t4;
    private ObservableList<SourceStockAtStockManagementDTO> sourceStockAtStockManagementDTOList;
    private FilteredList<SourceStockAtStockManagementDTO> sourceStockAtStockManagementDTOFilteredList;

    private void initSourceStockTableView() {
        t5.setCellValueFactory(new PropertyValueFactory<>("sourceName"));
        t1.setCellValueFactory(new PropertyValueFactory<>("stockType"));
        t2.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        t3.setCellValueFactory(new PropertyValueFactory<>("stockCount"));
        t4.setCellValueFactory(new PropertyValueFactory<>("salesAccountProductStockCount"));
        sourceStockAtStockManagementDTOList = sourceStock_tableView.getItems();

        sourceStockAtStockManagementDTOFilteredList = sourceStockAtStockManagementDTOList.filtered(sourceStockAtStockManagementDTO -> true);

        t3.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        t3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SourceStockAtStockManagementDTO, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SourceStockAtStockManagementDTO, Integer> event) {
                int newValue = event.getNewValue();

                SourceStockAtStockManagementDTO selectedItem = event.getRowValue();
                selectedItem.setStockCount(newValue);
                selectedItem.setChanged(true);
            }
        });

        t4.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        t4.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SourceStockAtStockManagementDTO, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SourceStockAtStockManagementDTO, Integer> event) {
                int newValue = event.getNewValue();

                SourceStockAtStockManagementDTO selectedItem = event.getRowValue();
                selectedItem.setSalesAccountProductStockCount(newValue);
                selectedItem.setChanged(true);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSourceInfoTableView();
        initStockTableView();
        initSourceStockTableView();

        initComboBox();
        initTextFileds();
        callProductAndMachineStock();
    }

    @FXML
    private ComboBox<String> type_cb, stockType_cb;

    @FXML
    private TextField sourceName_cb, stockName_cb, stockName_tf;

    private void initTextFileds() {
        sourceName_cb.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || newValue.equals(""))
                    return;

                sourceInfoAtStockManagementDTOFilteredList.setPredicate(sourceInfoAtStockManagementDTO -> {
                    if (sourceInfoAtStockManagementDTO.getName().contains(newValue))
                        return true;
                    else
                        return false;
                });

                source_tableView.setItems(sourceInfoAtStockManagementDTOFilteredList);
            }
        });

        stockName_cb.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                stockAtStockManagementDTOFilteredList.setPredicate(stockAtStockManagementDTO -> {
                    boolean type = stockAtStockManagementDTO.getStockType().equals(stockType_cb.getValue()) || stockType_cb.getValue().equals("전체");
                    boolean name = stockAtStockManagementDTO.getStockName().contains(stockName_cb.getText());

                    if (type && name)
                        return true;
                    else
                        return false;
                });
                stock_tableView.setItems(stockAtStockManagementDTOFilteredList);
            }
        });

        stockName_tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                sourceStockAtStockManagementDTOFilteredList.setPredicate(sourceStockAtStockManagementDTO -> {
                    boolean name = sourceStockAtStockManagementDTO.getStockName().contains(stockName_tf.getText());
                    if (name)
                        return true;
                    else
                        return false;
                });
                sourceStock_tableView.setItems(sourceStockAtStockManagementDTOFilteredList);
            }
        });
    }

    private void initComboBox() {
        type_cb.getItems().add("지점 재고 관리");
        type_cb.getItems().add("직원 재고 관리");
        type_cb.getItems().add("거래처 재고 관리");

        type_cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                sourceInfoAtStockManagementDTOList.clear();
                sourceStockAtStockManagementDTOList.clear();

                t4.setVisible(false);
                t3.setText("재고 수량");
                stockType_cb.getSelectionModel().select(0);
                stockType_cb.setDisable(false);

                switch (newValue.intValue()) {
                    case 0:
                        callCompany();
                        break;
                    case 1:
                        callEmployee();
                        break;
                    case 2:
                        callSalesAccount();
                        t4.setVisible(true);
                        t3.setText("기계 재고 수량");
                        stockType_cb.getSelectionModel().select(2);
                        stockType_cb.setDisable(true);
                        break;

                }
            }
        });


        stockType_cb.getItems().add("전체");
        stockType_cb.getItems().add("상품 재고");
        stockType_cb.getItems().add("기계 재고");

        stockType_cb.getSelectionModel().select(0);

        stockType_cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                stockAtStockManagementDTOFilteredList.setPredicate(stockAtStockManagementDTO -> {
                    boolean type = stockAtStockManagementDTO.getStockType().equals(stockType_cb.getValue()) || stockType_cb.getValue().equals("전체");
                    boolean name = stockAtStockManagementDTO.getStockName().contains(stockName_cb.getText());

                    if (type && name)
                        return true;
                    else
                        return false;
                });
                stock_tableView.setItems(stockAtStockManagementDTOFilteredList);
            }
        });
    }

    @FXML
    private TextField stock_tf, count_tf;

    private void callCompany() {
        sourceInfoAtStockManagementDTOList.clear();
        sourceInfoAtStockManagementDTOList.addAll(stockManagementService.getCompany());
    }

    private void callEmployee() {
        sourceInfoAtStockManagementDTOList.clear();
        sourceInfoAtStockManagementDTOList.addAll(stockManagementService.getEmployee());
    }

    private void callSalesAccount() {
        sourceInfoAtStockManagementDTOList.clear();
        sourceInfoAtStockManagementDTOList.addAll(stockManagementService.getSalesAccount("%" + sourceName_cb.getText() + "%"));
    }

    private void callProductAndMachineStock() {
        stockAtStockManagementDTOList.clear();
        stockAtStockManagementDTOList.addAll(stockManagementService.getProductAndMachine());
    }

    private void callCompanyStock() {
        Long companyId = source_tableView.getSelectionModel().getSelectedItem().getId();
        sourceStockAtStockManagementDTOList.clear();
        sourceStockAtStockManagementDTOList.addAll(stockManagementService.getCompanyStock(companyId));

        sourceStockAtStockManagementDTOList.forEach(sourceStockAtStockManagementDTO -> {
            sourceStockAtStockManagementDTO.setSourceName(source_tableView.getSelectionModel().getSelectedItem().getName());
        });
    }

    private void callEmployeeStock() {
        Long employeeId = source_tableView.getSelectionModel().getSelectedItem().getId();
        sourceStockAtStockManagementDTOList.clear();
        sourceStockAtStockManagementDTOList.addAll(stockManagementService.getEmployeeStock(employeeId));

        sourceStockAtStockManagementDTOList.forEach(sourceStockAtStockManagementDTO -> {
            sourceStockAtStockManagementDTO.setSourceName(source_tableView.getSelectionModel().getSelectedItem().getName());
        });
    }

    private void callSalesAccountStock() {
        Long salesAccountId = source_tableView.getSelectionModel().getSelectedItem().getId();
        sourceStockAtStockManagementDTOList.clear();
        sourceStockAtStockManagementDTOList.addAll(stockManagementService.getSalesAccountStock(salesAccountId));

        sourceStockAtStockManagementDTOList.forEach(sourceStockAtStockManagementDTO -> {
            sourceStockAtStockManagementDTO.setSourceName(source_tableView.getSelectionModel().getSelectedItem().getName());
        });
    }

    private void initialEmployeeAndCompanyStock() {
        date_dp.setValue(LocalDate.now().minusMonths(1).minusDays(12));
        count_tf.setText("0");

        for (int i = 0; i < 2; i++) {

            type_cb.getSelectionModel().select(i);

            for (SourceInfoAtStockManagementDTO sourceInfoAtStockManagementDTO : sourceInfoAtStockManagementDTOList) {
                source_tableView.getSelectionModel().select(sourceInfoAtStockManagementDTO);

                for (StockAtStockManagementDTO stockAtStockManagementDTO : stockAtStockManagementDTOList) {
                    if (stockAtStockManagementDTO.getStockType().hashCode() == "기계 재고".hashCode()
                            || stockAtStockManagementDTO.getStockName().hashCode() == "캡슐 토이".hashCode()
                            || stockAtStockManagementDTO.getStockName().hashCode() == "인형".hashCode()
                            || stockAtStockManagementDTO.getStockName().hashCode() == "먹이 캡슐".hashCode()
                            || stockAtStockManagementDTO.getStockName().hashCode() == "사탕".hashCode()
                            || stockAtStockManagementDTO.getStockName().hashCode() == "멘토스".hashCode()
                            || stockAtStockManagementDTO.getStockName().hashCode() == "시재금".hashCode()
                            || stockAtStockManagementDTO.getStockName().hashCode() == "라바 탱탱볼".hashCode()) {
                        stock_tableView.getSelectionModel().select(stockAtStockManagementDTO);
                        handleAddBtn();
                    }
                }

                handleSaveStockBtn();
            }
        }
    }

    @FXML
    private void handleAddBtn() {
        StockAtStockManagementDTO selectedStock = stock_tableView.getSelectionModel().getSelectedItem();
        SourceInfoAtStockManagementDTO selectedSource = source_tableView.getSelectionModel().getSelectedItem();

        String stockType = selectedStock.getStockType();
        String stockName = selectedStock.getStockName();
        int stockCount = Integer.parseInt(count_tf.getText());

        int stockNameKey = stockName.hashCode();

        Optional<SourceStockAtStockManagementDTO> optionalSourceStockAtStockManagementDTO = sourceStockAtStockManagementDTOList.stream()
                .filter(sourceStockAtStockManagementDTO1 -> {
                    if (type_cb.getSelectionModel().getSelectedIndex() == 2){
                        return sourceStockAtStockManagementDTO1.getStockName().split(" : ")[0].hashCode() == stockNameKey;
                    }else{
                        return sourceStockAtStockManagementDTO1.getStockName().hashCode() == stockNameKey;
                    }
                })
                .findFirst();

        if (optionalSourceStockAtStockManagementDTO.isPresent()) {
            SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO = optionalSourceStockAtStockManagementDTO.get();
            sourceStockAtStockManagementDTO.setStockCount(stockCount);
            sourceStockAtStockManagementDTO.setChanged(true);
        } else {
            SourceStockAtStockManagementDTO sourceStockAtStockManagementDTO = new SourceStockAtStockManagementDTO();
            sourceStockAtStockManagementDTO.setSourceId(selectedSource.getId());
            sourceStockAtStockManagementDTO.setStockId(selectedStock.getId());
            sourceStockAtStockManagementDTO.setSourceName(selectedSource.getName());
            sourceStockAtStockManagementDTO.setStockCount(stockCount);
            sourceStockAtStockManagementDTO.setStockType(stockType);
            sourceStockAtStockManagementDTO.setStockName(stockName);
            sourceStockAtStockManagementDTO.setStockCount(stockCount);
            sourceStockAtStockManagementDTO.setPreviousStockCount(0);
            sourceStockAtStockManagementDTO.setChanged(true);

            if (type_cb.getSelectionModel().getSelectedIndex() == 2) {
                sourceStockAtStockManagementDTO.setSalesAccountProductStockCount(0);
                sourceStockAtStockManagementDTO.setPreviousSalesAccountProductStockCount(0);
            }

            sourceStockAtStockManagementDTOList.add(sourceStockAtStockManagementDTO);
        }
    }


    @FXML
    private void handleSaveStockBtn() {
        int index = type_cb.getSelectionModel().getSelectedIndex();

        try {
            stockManagementService.saveStock(index, sourceStockAtStockManagementDTOList, date_dp.getValue());
            new MakeAlert().makeInfoMessage(dialogStage, "추가 완료", "완료", "추가 완료", false);

            switch (type_cb.getSelectionModel().getSelectedIndex()) {
                case 0:
                    callCompanyStock();
                    break;
                case 1:
                    callEmployeeStock();
                    break;
                case 2:
                    callSalesAccountStock();
                    break;
            }

        } catch (Exception e) {
            new MakeAlert().makeErrorMessage(dialogStage, "추가 실패", "실패", "추가 실패", false);
            e.printStackTrace();
        }


    }
}
