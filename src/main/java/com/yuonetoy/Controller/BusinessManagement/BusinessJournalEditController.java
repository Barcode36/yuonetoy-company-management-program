package com.yuonetoy.Controller.BusinessManagement;


import com.yuonetoy.Controller.AccountManagement.AddSalesAccountController;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.DTO.BusinessJournalHistoryDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.SalesAccountMachineAtBusinessJournalDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.DTO.Stock.EmployeeMachineStockDTO;
import com.yuonetoy.DTO.Stock.EmployeeProductStockDTO;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Service.BusinessJournalHistoryService;
import com.yuonetoy.Service.MachineManagementService;
import com.yuonetoy.Tool.MakeAlert;
import com.yuonetoy.Tool.SpringFxmlLoader;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.*;
import org.controlsfx.control.spreadsheet.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Controller
public class BusinessJournalEditController implements Initializable {
    public SpringFxmlLoader getFxmlLoader() {
        return fxmlLoader;
    }

    @Autowired
    private SpringFxmlLoader fxmlLoader;

    @Autowired
    private MachineManagementService machineManagementService;

    @Autowired
    private BusinessJournalHistoryService businessJournalHistoryService;

    private Stage primaryStage;

    public void setDialogStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private EmployeeDTO selectedEmployee;


    private SalesAccountDetailDTO newSalesAccount;

    public void initStatus() {
        newSalesAccount = null;
    }

    private GridBase buildGrid() {
        GridBase tempGrid;
        tempGrid = new GridBase(1, 11);
        List<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();

        for (int row = 0; row < tempGrid.getRowCount(); ++row) {
            ObservableList<SpreadsheetCell> currentRow = FXCollections.observableArrayList();
            for (int column = 0; column < tempGrid.getColumnCount(); ++column) {
                currentRow.add(SpreadsheetCellType.STRING.createCell(row, column, 1, 1, ""));
            }
            rows.add(currentRow);
        }
        tempGrid.setRows(rows);
        return tempGrid;
    }

    @FXML
    private SpreadsheetView spreadsheetView;

    @FXML
    private TableView<SalesAccountMachineAtBusinessJournalDTO> machineInfo_tableView;
    private ObservableList<SalesAccountMachineAtBusinessJournalDTO> salesAccountMachineAtBusinessJournalDTOList;
    @FXML
    private TableColumn<SalesAccountMachineAtBusinessJournalDTO, String> m1, m2, m3, m4, m5, m6, m7, m8;

    private void initTableView() {
        salesAccountMachineAtBusinessJournalDTOList = machineInfo_tableView.getItems();
        m1.setCellValueFactory(new PropertyValueFactory<>("machine"));
        m2.setCellValueFactory(new PropertyValueFactory<>("machineCount"));
        m3.setCellValueFactory(new PropertyValueFactory<>("initialQuantity"));
        m4.setCellValueFactory(new PropertyValueFactory<>("fees"));
        m5.setCellValueFactory(new PropertyValueFactory<>("plusValue"));
        m6.setCellValueFactory(new PropertyValueFactory<>("minusValue"));
        m7.setCellValueFactory(new PropertyValueFactory<>("product"));
        m8.setCellValueFactory(new PropertyValueFactory<>("productCount"));
    }

    @FXML
    private ComboBox<SalesAccountDTO> salesAccount_cb;
    private List<SalesAccountDTO> salesAccounts;

    private SalesAccountDTO selectedSalesAccount;

    @FXML
    private ComboBox<String> type_cb;

    @FXML
    private TextField employee_lb;

    private void initCombobox() {
        employeeMachines = employeeMachine_cb.getItems();
        employeeMachine_cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmployeeMachineStockDTO>() {
            @Override
            public void changed(ObservableValue<? extends EmployeeMachineStockDTO> observable, EmployeeMachineStockDTO oldValue, EmployeeMachineStockDTO newValue) {
                if (newValue == null)
                    return;

                if (newValue.getMachine().getIsProductMachine()) {
                    callMachineLinkProduct(newValue.getMachine());
                    employeeProduct_cb.getSelectionModel().select(0);
                } else {
                    employeeProducts.clear();
                    employeeProduct_cb.getSelectionModel().clearSelection();
                }
            }
        });
        employeeProducts = employeeProduct_cb.getItems();

        type_cb.getItems().add("구분");
        type_cb.getItems().add("신규 입점");
        type_cb.getItems().add("철수");
        type_cb.getItems().add("기계 관리");
        type_cb.getItems().add("상품 보충");
        type_cb.getItems().add("외상 매출");
        type_cb.getItems().add("재입점");

        type_cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (selectedSalesAccount == null && (newValue.intValue() == 2 || newValue.intValue() == 3 || newValue.intValue() == 4 || newValue.intValue() == 5 || newValue.intValue() == 6)) {
                    type_cb.getSelectionModel().select(0);
                    return;
                }

                if (newValue.intValue() == 1 || newValue.intValue() == 3 || newValue.intValue() == 6) {
                    machine_hbox.setDisable(false);
                } else {
                    machine_hbox.setDisable(true);
                }

                employeeMachine_cb.getSelectionModel().clearSelection();
                employeeProduct_cb.getSelectionModel().clearSelection();

                if (newValue.intValue() == 0) {
                    spreadsheetView.setEditable(false);
                } else {
                    spreadsheetView.setEditable(true);
                }

                if (newValue.intValue() == 1) {
                    // 초기화
                    machineInfo_tableView.getItems().clear();
                    clearSpreadSheet();
                    handleAddSalesAccountBtnClick();
                } else {
                    for (int i = 1; i < spreadsheetView.getGrid().getRowCount(); i++) {
                        for (int j = 0; j < spreadsheetView.getGrid().getColumnCount(); j++) {
                            if (j == 1 || j == 5)
                                continue;
                            spreadsheetView.getItems().get(i).get(j).setItem(null);
                        }
                    }
                }

                setSpreadsheetViewTypeColumn(newValue.intValue());

                showSpreadsheetViewColumns(newValue.intValue());
            }
        });

        salesAccounts = salesAccount_cb.getItems();
        salesAccounts.addAll(callSalesAccount());
        AutoCompletionBinding<SalesAccountDTO> autoCompletionBinding = TextFields.bindAutoCompletion(salesAccount_cb.getEditor(), salesAccounts);

        salesAccount_cb.getEditor().focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (oldValue && !newValue) {
                    Optional<SalesAccountDTO> optionalSalesAccountDTO = salesAccounts.stream().filter(salesAccountDTO -> {
                        if (salesAccountDTO.getName().equals(salesAccount_cb.getEditor().getText()))
                            return true;
                        else
                            return false;
                    }).findFirst();

                    if (optionalSalesAccountDTO.isPresent()) {
                        selectedSalesAccount = optionalSalesAccountDTO.get();
                        salesAccount_cb.getValue();
                        employee_lb.setText(selectedSalesAccount.getEmployee().getName());
                        selectedEmployee = selectedSalesAccount.getEmployee();

                        setSpreadsheetViewToSalesAccountMachine(callSalesAccountMachineDetailDTO(selectedSalesAccount.getId()));
                    }
                }
            }
        });
    }

    private void setSpreadsheetViewTypeColumn(int type) {
        for (int i = 1; i < spreadsheetView.getGrid().getRowCount(); i++) {
            spreadsheetView.getGrid().getRows().get(i).get(typeCol).setEditable(true);
            spreadsheetView.getGrid().getRows().get(i).get(typeCol).setItem(type == 0 ? null : type_cb.getSelectionModel().getSelectedItem());

            for (int j = 0; j < spreadsheetView.getGrid().getColumnCount(); j++) {
                spreadsheetView.getGrid().getRows().get(i).get(j).setEditable(false);
            }

            SalesAccountMachineDetailDTO salesAccountMachineDetailDTO = (SalesAccountMachineDetailDTO) spreadsheetView.getGrid().getRows().get(i).get(machineCol).getItem();

            switch (type) {
                case 0:
                case 2:
                    for (int j = 0; j < spreadsheetView.getGrid().getColumnCount(); j++) {
                        spreadsheetView.getGrid().getRows().get(i).get(j).setEditable(false);
                    }
                    break;
                case 1:
                case 3:
                case 6:
                    spreadsheetView.getGrid().getRows().get(i).get(machineSupplyCol).setEditable(true);
                    spreadsheetView.getGrid().getRows().get(i).get(initialQuantityCol).setEditable(true);
                    spreadsheetView.getGrid().getRows().get(i).get(feesCol).setEditable(true);
                    break;
                case 4:
                    if (salesAccountMachineDetailDTO.getMachine().getIsProductMachine()) {
                        spreadsheetView.getGrid().getRows().get(i).get(productSupplyCol).setEditable(true);
                        if (!salesAccountMachineDetailDTO.getMachine().getIsCoinChanger()) {
                            spreadsheetView.getGrid().getRows().get(i).get(plusValueCol).setEditable(true);
                            spreadsheetView.getGrid().getRows().get(i).get(minusValueCol).setEditable(true);
                        }
                    }
                    break;
                case 5:
                    if (!salesAccountMachineDetailDTO.getMachine().getIsCoinChanger()) {
                        spreadsheetView.getGrid().getRows().get(i).get(salesValueCol).setEditable(true);
                        spreadsheetView.getGrid().getRows().get(i).get(refundValueCol).setEditable(true);
                    }
                    break;
            }
        }
    }

    private void showSpreadsheetViewColumns(int type) {
        for (SpreadsheetColumn spreadsheetColumn : spreadsheetView.getColumns()) {
            spreadsheetView.showColumn(spreadsheetColumn);
        }

        switch (type) {
            case 3:
            case 6: //기계 관리
                callMachine(selectedEmployee.getId());
                for (int i = productCol; i <= refundValueCol; i++) {
                    spreadsheetView.hideColumn(spreadsheetView.getColumns().get(i));
                }
                break;
            case 4: //상품 보충
                for (int i = machineSupplyCol; i <= feesCol; i++)
                    spreadsheetView.hideColumn(spreadsheetView.getColumns().get(i));
                for (int i = salesValueCol; i <= refundValueCol; i++)
                    spreadsheetView.hideColumn(spreadsheetView.getColumns().get(i));
                break;
            case 5: //외상 매출
                for (int i = machineSupplyCol; i <= minusValueCol; i++)
                    spreadsheetView.hideColumn(spreadsheetView.getColumns().get(i));
                break;
        }
    }

    private ArrayList<String> titleColumns;

    private final int typeCol = 0;
    private final int machineCol = 1;
    private final int machineSupplyCol = 2;
    private final int initialQuantityCol = 3;
    private final int feesCol = 4;
    private final int productCol = 5;
    private final int productSupplyCol = 6;
    private final int plusValueCol = 7;
    private final int minusValueCol = 8;
    private final int salesValueCol = 9;
    private final int refundValueCol = 10;

    public void initSpreadSheet() {
        titleColumns = new ArrayList<>();

        titleColumns.add("구분");
        titleColumns.add("기계명");
        titleColumns.add("기계 보충 수량");
        titleColumns.add("세팅 변경");
        titleColumns.add("수수료 변경");
        titleColumns.add("상품명");
        titleColumns.add("보충 수량");
        titleColumns.add("+500");
        titleColumns.add("-500");
        titleColumns.add("수금액");
        titleColumns.add("환불금");

        spreadsheetView.setGrid(buildGrid());

        ObservableList<SpreadsheetCell> firstRow = spreadsheetView.getGrid().getRows().get(0);

        for (int i = 0; i < firstRow.size(); i++) {
            firstRow.get(i).setItem(titleColumns.get(i));
            firstRow.get(i).setEditable(false);
        }


        ((SpreadsheetCellEditor.IntegerEditor) spreadsheetView.getEditor(SpreadsheetCellType.INTEGER).get()).getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue == null || newValue.equals(""))
                    return;

                int selectedType = type_cb.getSelectionModel().getSelectedIndex();
                int selectedColumn = spreadsheetView.getSelectionModel().getFocusedCell().getColumn();

                if (selectedType == 5 && selectedColumn == 2) {
                    sumSales(newValue);
                }
            }
        });


    }

    private void sumSales(String newSalesValue) {
        int result = 0;
        for (int i = 1; i < spreadsheetView.getGrid().getRowCount(); i++) {
            if (i == spreadsheetView.getSelectionModel().getFocusedCell().getRow())
                result += Integer.parseInt(newSalesValue);
            else
                result += Integer.parseInt(spreadsheetView.getGrid().getRows().get(i).get(salesValueCol).getText().equals("") ? "0" : spreadsheetView.getGrid().getRows().get(i).get(salesValueCol).getText());
        }
        sales_lb.setText(String.valueOf(result));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSpreadSheet();
        initCombobox();
        initTableView();
    }

    private List<SalesAccountDTO> callSalesAccount() {
        return machineManagementService.getSalesAccountList();
    }

    private void clearSpreadSheet() {
        spreadsheetView.getGrid().getRows().remove(1, spreadsheetView.getGrid().getRowCount());
    }

    private List<SalesAccountMachineDetailDTO> callSalesAccountMachineDetailDTO(long salesAccountId) {
        clearSpreadSheet();
        salesAccountMachineAtBusinessJournalDTOList.clear();

        List<SalesAccountMachineDetailDTO> salesAccountMachineDetailDTOList = machineManagementService.getSalesAccountMachineDetailDTO(salesAccountId);
        // 쿼리 조회를 줄이기 위해 직원을 따로 주입

        if (salesAccountMachineDetailDTOList == null) return null;

        salesAccountMachineDetailDTOList.forEach(salesAccountMachineDetailDTO -> {
            salesAccountMachineDetailDTO.setEmployee(selectedEmployee);
            salesAccountMachineAtBusinessJournalDTOList.add(new SalesAccountMachineAtBusinessJournalDTO(salesAccountMachineDetailDTO));
        });
        return salesAccountMachineDetailDTOList;
    }

    private void setSpreadsheetViewToSalesAccountMachine(List<SalesAccountMachineDetailDTO> salesAccountMachine) {
        if (salesAccountMachine == null) {
            return;
        }

        Grid tempGrid = spreadsheetView.getGrid();
        tempGrid.getRows().remove(1, tempGrid.getRowCount());

        salesAccountMachine.forEach(salesAccountMachineDetailDTO -> {
            ProductPriceDTO productDTO = salesAccountMachineDetailDTO.getProduct();

            SpreadsheetCellType.ObjectType machine = new SpreadsheetCellType.ObjectType();
            SpreadsheetCellType.ObjectType product = new SpreadsheetCellType.ObjectType();

            ObservableList<SpreadsheetCell> currentRow = FXCollections.observableArrayList();

            int coulum = 0;
            int row = tempGrid.getRowCount();

            currentRow.add(SpreadsheetCellType.STRING.createCell(row, coulum++, 1, 1, ""));
            currentRow.add(machine.createCell(row, coulum++, 1, 1, salesAccountMachineDetailDTO));
            currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
            currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
            currentRow.add(SpreadsheetCellType.DOUBLE.createCell(row, coulum++, 1, 1, null));
            currentRow.add(product.createCell(row, coulum++, 1, 1, productDTO));
            currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
            currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
            currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
            currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
            currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
            currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
            spreadsheetView.getGrid().getRows().add(currentRow);
        });

        for (int i = 1; i < spreadsheetView.getGrid().getRowCount(); i++) {
            for (int j = 0; j < spreadsheetView.getGrid().getColumnCount(); j++)
                spreadsheetView.getGrid().getRows().get(i).get(j).setEditable(false);
        }
    }

    @FXML
    private TextField sales_lb;

    @FXML
    private DatePicker date_dp;

    private void setStockCount() {
        if (type_cb.getSelectionModel().getSelectedIndex() == 5) {
            for (int i = 1; i < spreadsheetView.getGrid().getRowCount(); i++) {
                ObservableList<SpreadsheetCell> currentRow = spreadsheetView.getGrid().getRows().get(i);

                SalesAccountMachineDetailDTO salesAccountMachineDetailDTO = (SalesAccountMachineDetailDTO) currentRow.get(machineCol).getItem();
                boolean isProductMachine = salesAccountMachineDetailDTO.getMachine().getIsProductMachine();

                if (isProductMachine) {
                    long salesValue = currentRow.get(salesValueCol).getItem() == null ? 0 : Long.valueOf((int) currentRow.get(salesValueCol).getItem());
                    long refundValue = currentRow.get(refundValueCol).getItem() == null ? 0 : Long.valueOf((int) currentRow.get(refundValueCol).getItem());

                    int salesAccountStock = (int) ((salesValue + refundValue));

                    currentRow.get(productSupplyCol).setEditable(true);
                    currentRow.get(productSupplyCol).setItem(-salesAccountStock);
                    currentRow.get(productSupplyCol).setEditable(false);
                }
            }
        }
    }

    @FXML
    private void saveBusinessJournalHistory() {
        if (type_cb.getSelectionModel().getSelectedIndex() == 0 || selectedSalesAccount == null || date_dp.getEditor().getText().isEmpty()) {
            new MakeAlert().makeErrorMessage(primaryStage, "알림", "추가 실패", "모든 내용을 입력하세요", false);
            return;
        }

        List<BusinessJournalHistoryDTO> businessJournalHistoryDTOList = new LinkedList<>();

        // 재고 수정
        setStockCount();

        if (type_cb.getSelectionModel().getSelectedIndex() == 2) {
            withdrawal();
        }

        for (int i = 1; i < spreadsheetView.getGrid().getRowCount(); i++) {
            ObservableList<SpreadsheetCell> currentRow = spreadsheetView.getGrid().getRows().get(i);
            BusinessJournalHistoryDTO businessJournalHistoryDTO = new BusinessJournalHistoryDTO();


            SalesAccountMachineDetailDTO salesAccountMachineDetailDTO = (SalesAccountMachineDetailDTO) currentRow.get(machineCol).getItem();
            ProductPriceDTO productPriceDTO = currentRow.get(productCol).getItem() == null ? null : (ProductPriceDTO) currentRow.get(productCol).getItem();
            int machineCount = currentRow.get(machineSupplyCol).getItem() == null ? 0 : (Integer) currentRow.get(machineSupplyCol).getItem();
            int initialQuantity = currentRow.get(initialQuantityCol).getItem() == null ? 0 : (Integer) currentRow.get(initialQuantityCol).getItem();
            double fees = currentRow.get(feesCol).getItem() == null ? 0 : (double) currentRow.get(feesCol).getItem();
            int supplyCount = currentRow.get(productSupplyCol).getItem() == null ? 0 : (Integer) currentRow.get(productSupplyCol).getItem();
            int plusValue = currentRow.get(plusValueCol).getItem() == null ? 0 : (Integer) currentRow.get(plusValueCol).getItem();
            int minusValue = currentRow.get(minusValueCol).getItem() == null ? 0 : (Integer) currentRow.get(minusValueCol).getItem();
            long salesValue = currentRow.get(salesValueCol).getItem() == null ? 0 : Long.valueOf((int) currentRow.get(salesValueCol).getItem());
            long refundValue = currentRow.get(refundValueCol).getItem() == null ? 0 : Long.valueOf((int) currentRow.get(refundValueCol).getItem());

            if (machineCount == 0 && initialQuantity == 0 && fees == 0 &&
                    supplyCount == 0 && plusValue == 0 && minusValue == 0 &&
                    salesValue == 0 && refundValue == 0 && currentRow.get(typeCol).getText().hashCode() != "철수".hashCode())
                continue;

            businessJournalHistoryDTO.setType(type_cb.getSelectionModel().getSelectedItem());
            businessJournalHistoryDTO.setEmployeeName(employee_lb.getText());
            businessJournalHistoryDTO.setDate(date_dp.getValue());
            businessJournalHistoryDTO.setSalesAccount(selectedSalesAccount);

            if (currentRow.get(typeCol).getText().hashCode() == "철수".hashCode()) {
                businessJournalHistoryDTO.setSecondType(currentRow.get(typeCol).getText());
                businessJournalHistoryDTO.setSalesAccountMachine(salesAccountMachineDetailDTO);
                businessJournalHistoryDTO.setMachineCount(-salesAccountMachineDetailDTO.getMachineCount());
                businessJournalHistoryDTO.setInitialQuantity(salesAccountMachineDetailDTO.getInitialQuantity());
                businessJournalHistoryDTO.setFees(salesAccountMachineDetailDTO.getFees());
                businessJournalHistoryDTO.setProduct(productPriceDTO);
                businessJournalHistoryDTO.setSupplyCount(-salesAccountMachineDetailDTO.getProductCount());
                businessJournalHistoryDTO.setPlusValue(-salesAccountMachineDetailDTO.getPlusValue());
                businessJournalHistoryDTO.setMinusValue(-salesAccountMachineDetailDTO.getMinusValue());
                businessJournalHistoryDTO.setSalesValue((long) 0);
                businessJournalHistoryDTO.setRefundValue((long) 0);
            } else {
                businessJournalHistoryDTO.setSecondType(currentRow.get(typeCol).getText());
                businessJournalHistoryDTO.setSalesAccountMachine(salesAccountMachineDetailDTO);
                businessJournalHistoryDTO.setMachineCount(machineCount);
                businessJournalHistoryDTO.setInitialQuantity(initialQuantity);
                businessJournalHistoryDTO.setFees(fees);
                businessJournalHistoryDTO.setProduct(productPriceDTO);
                businessJournalHistoryDTO.setSupplyCount(supplyCount);
                businessJournalHistoryDTO.setPlusValue(plusValue);
                businessJournalHistoryDTO.setMinusValue(minusValue);
                businessJournalHistoryDTO.setSalesValue(salesValue);
                businessJournalHistoryDTO.setRefundValue(refundValue);
            }
            businessJournalHistoryDTOList.add(businessJournalHistoryDTO);
        }

        try {
            SalesAccountDTO salesAccountDTO = businessJournalHistoryService.saveBusinessJournalHistoryList(businessJournalHistoryDTOList, newSalesAccount);

            if (type_cb.getSelectionModel().getSelectedIndex() == 1) {
                salesAccounts.add(salesAccountDTO);
            }

            newSalesAccount = null;
            selectedSalesAccount = salesAccountDTO;

            type_cb.getSelectionModel().select(0);

            setSpreadsheetViewToSalesAccountMachine(callSalesAccountMachineDetailDTO(salesAccountDTO.getId()));

            new MakeAlert().makeInfoMessage(primaryStage, "추가 완료", "완료", "추가 완료", false);

        } catch (Exception e) {
            e.printStackTrace();
            new MakeAlert().makeErrorMessage(primaryStage, "추가 실패", "실패", "추가 실패 \n" + e, false);
        }
    }

    @FXML
    private TextField machineCount_tf, initialQuantity_tf, fees_tf;

    @FXML
    private void handleMachinePlusBtn() {
        Optional<SalesAccountMachineAtBusinessJournalDTO> optionalSalesAccountMachineAtBusinessJournalDTO = machineInfo_tableView.getItems().stream().filter(salesAccountMachineAtBusinessJournalDTO -> {
            if (salesAccountMachineAtBusinessJournalDTO.getMachine().hashCode() == employeeMachine_cb.getSelectionModel().getSelectedItem().getMachine().getName().hashCode())
                return true;
            else
                return false;
        }).findFirst();

        if (optionalSalesAccountMachineAtBusinessJournalDTO.isPresent())
            new MakeAlert().makeErrorMessage(primaryStage, "추가 실패", "실패", "이미 설치된 기계 입니다", false);
        else
            addMachine(null);
    }

    private void addMachine(SalesAccountMachineDetailDTO salesAccountMachineDetailDTO) {
        Grid tempGrid = spreadsheetView.getGrid();

        SpreadsheetCellType.ObjectType machine = new SpreadsheetCellType.ObjectType();
        SpreadsheetCellType.ObjectType product = new SpreadsheetCellType.ObjectType();

        ObservableList<SpreadsheetCell> currentRow = FXCollections.observableArrayList();

        int coulum = 0;
        int row = tempGrid.getRowCount();

        ProductPriceDTO productPriceDTO = null;
        int machineSupplyCount;
        int initialQuantity;
        double fees;

        String type;
        if (type_cb.getSelectionModel().getSelectedIndex() != 2)
            type = type_cb.getSelectionModel().getSelectedItem();
        else
            type = "기계 보충";

        //신규입점일때
        if (salesAccountMachineDetailDTO != null) {
            productPriceDTO = salesAccountMachineDetailDTO.getProduct();
            machineSupplyCount = salesAccountMachineDetailDTO.getMachineCount();
            initialQuantity = salesAccountMachineDetailDTO.getInitialQuantity();
            fees = salesAccountMachineDetailDTO.getFees();
        } else {
            // 신규 보충일때
            machineSupplyCount = machineCount_tf.getText().equals("") ? 0 : Integer.parseInt(machineCount_tf.getText());
            initialQuantity = initialQuantity_tf.getText().equals("") ? 0 : Integer.parseInt(initialQuantity_tf.getText());
            fees = fees_tf.getText().equals("") ? 0.0 : Double.parseDouble(fees_tf.getText());

            MachineDTO machineDTO = employeeMachine_cb.getSelectionModel().getSelectedItem().getMachine();

            if (machineDTO.getIsProductMachine())
                productPriceDTO = employeeProduct_cb.getSelectionModel().getSelectedItem().getProduct();

            salesAccountMachineDetailDTO = new SalesAccountMachineDetailDTO();
            salesAccountMachineDetailDTO.setMachine(machineDTO);
        }

        currentRow.add(SpreadsheetCellType.STRING.createCell(row, coulum++, 1, 1, type));
        currentRow.add(machine.createCell(row, coulum++, 1, 1, salesAccountMachineDetailDTO));
        currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, machineSupplyCount));
        currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, initialQuantity));
        currentRow.add(SpreadsheetCellType.DOUBLE.createCell(row, coulum++, 1, 1, fees));
        currentRow.add(product.createCell(row, coulum++, 1, 1, productPriceDTO));
        currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
        currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
        currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
        currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));
        currentRow.add(SpreadsheetCellType.INTEGER.createCell(row, coulum++, 1, 1, null));

        spreadsheetView.getGrid().getRows().add(currentRow);
    }

    @FXML
    private HBox machine_hbox;

    @FXML
    private void minusMachine() {
        int selectedRow = spreadsheetView.getSelectionModel().getFocusedCell().getRow();
        SpreadsheetCell typeCell = spreadsheetView.getGrid().getRows().get(selectedRow).get(0);

        if (typeCell.getText().hashCode() == "기계 관리".hashCode()) {
            spreadsheetView.getGrid().getRows().get(selectedRow).get(typeCol).setEditable(true);
            spreadsheetView.getGrid().getRows().get(selectedRow).get(typeCol).setItem("철수");
            spreadsheetView.getGrid().getRows().get(selectedRow).get(typeCol).setEditable(false);

            for (int i = 2; i < spreadsheetView.getGrid().getColumnCount(); i++) {
                if (i == 5) continue;
                spreadsheetView.getGrid().getRows().get(selectedRow).get(i).setItem(null);
                spreadsheetView.getGrid().getRows().get(selectedRow).get(i).setEditable(false);
            }
        } else if (typeCell.getText().hashCode() == "철수".hashCode()) {
            spreadsheetView.getGrid().getRows().get(selectedRow).get(typeCol).setEditable(true);
            spreadsheetView.getGrid().getRows().get(selectedRow).get(typeCol).setItem(type_cb.getValue());
            spreadsheetView.getGrid().getRows().get(selectedRow).get(typeCol).setEditable(false);

            for (int i = 2; i < spreadsheetView.getGrid().getColumnCount(); i++) {
                if (i == 5) continue;
                spreadsheetView.getGrid().getRows().get(selectedRow).get(i).setItem(null);
                spreadsheetView.getGrid().getRows().get(selectedRow).get(i).setEditable(true);
            }
        } else {
            spreadsheetView.getGrid().getRows().remove(selectedRow);
        }

    }

    private void withdrawal() {
        for (int i = 0; i < spreadsheetView.getGrid().getRowCount(); i++) {
            for (int j = machineSupplyCol; j < spreadsheetView.getGrid().getColumnCount(); j++) {
                if (j == productCol) continue;
                spreadsheetView.getGrid().getRows().get(i).get(j).setItem(null);
            }
        }
    }

    @FXML
    private ComboBox<EmployeeMachineStockDTO> employeeMachine_cb;
    private ObservableList<EmployeeMachineStockDTO> employeeMachines;

    private void callMachine(long employee_id) {
        employeeMachines.clear();
        employeeMachines.addAll(machineManagementService.getEmployeeMachineStockList(employee_id));
    }

    @FXML
    private ComboBox<EmployeeProductStockDTO> employeeProduct_cb;
    private ObservableList<EmployeeProductStockDTO> employeeProducts;

    private void callMachineLinkProduct(MachineDTO machineDTO) {
        employeeProducts.clear();
        employeeProducts.addAll(machineManagementService.callProductLinkedMachine(selectedEmployee, machineDTO));
    }

    @FXML
    public void handleAddSalesAccountBtnClick() {
        Stage dialogStage = new Stage(StageStyle.DECORATED);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(primaryStage);
        dialogStage.setResizable(false);
        dialogStage.setTitle("매출 거래처 추가");

        try {
            FXMLLoader loader = fxmlLoader.load("/View/AccountManagement/AddSalesAccountView.fxml");
            AnchorPane page = (AnchorPane) loader.load();

            AddSalesAccountController addSalesAccountController = loader.getController();
            addSalesAccountController.setDialogStage(dialogStage);
            addSalesAccountController.initStatus();
            addSalesAccountController.setBusinessJournalEditController(this);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNewSaleAccount(SalesAccountDetailDTO salesAccountDetailDTO, Hashtable<String, SalesAccountMachineDetailDTO> salesAccountMachineDetailDTOHashtable) {
        newSalesAccount = salesAccountDetailDTO;

        SalesAccountDTO salesAccountDTO = new SalesAccountDTO();
        salesAccountDTO.setName(salesAccountDetailDTO.getName());
        salesAccountDTO.setAddress(salesAccountDetailDTO.getAddress());
        salesAccountDTO.setEmployee(salesAccountDetailDTO.getEmployee());
        salesAccountDTO.setEntryDate(salesAccountDetailDTO.getEntryDate());

        salesAccount_cb.getItems().add(salesAccountDTO);
        salesAccount_cb.getSelectionModel().select(salesAccountDTO);
        selectedSalesAccount = salesAccountDTO;

        date_dp.setValue(salesAccountDTO.getEntryDate());
        for (SalesAccountMachineDetailDTO salesAccountMachineDetailDTO : salesAccountMachineDetailDTOHashtable.values()) {
            addMachine(salesAccountMachineDetailDTO);
        }
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
        businessJournalLookupController.callDateNowBusinessJournalHistory();

        dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (selectedSalesAccount != null)
                    setSpreadsheetViewToSalesAccountMachine(callSalesAccountMachineDetailDTO(selectedSalesAccount.getId()));
            }
        });

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    @FXML
    private void handleBusinessLookupSalesAccountBtn() throws IOException {
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
        businessJournalLookupController.callSalesAccountBusinessJournalHistory(selectedSalesAccount.getName());

        dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if (selectedSalesAccount != null)
                    setSpreadsheetViewToSalesAccountMachine(callSalesAccountMachineDetailDTO(selectedSalesAccount.getId()));
            }
        });

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.show();
    }
}
