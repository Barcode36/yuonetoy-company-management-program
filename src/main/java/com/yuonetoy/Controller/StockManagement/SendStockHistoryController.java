package com.yuonetoy.Controller.StockManagement;

import com.yuonetoy.DTO.Product.ProductDTO;
import com.yuonetoy.DTO.StockHistory.SendStockHistory;
import com.yuonetoy.DTO.StockHistory.SendStockHistoryDTO;
import com.yuonetoy.Service.SendStockHistoryService;
import com.yuonetoy.Tool.MakeAlert;
import com.yuonetoy.Tool.PredicateCase;
import com.yuonetoy.Tool.SpringFxmlLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LongStringConverter;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class SendStockHistoryController implements Initializable {
    @Autowired
    private SendStockHistoryService sendStockHistoryService;


    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initComboBoxes();
        initTableView();
        initDatePicker();
    }

    public void initStatus() {

    }

    @FXML
    private TableView<SendStockHistoryDTO> history_tableview;

    @FXML
    private TableColumn<SendStockHistoryDTO, String> m1, m2, m3, m4;
    @FXML
    private TableColumn<SendStockHistoryDTO, Long> m5;
    @FXML
    private TableColumn<SendStockHistoryDTO, LocalDate> m6;

    private ObservableList<SendStockHistoryDTO> sendStockHistories;
    private FilteredList<SendStockHistoryDTO> sendStockHistoryFilteredList;


    @FXML
    private DatePicker firstDate_dp, lastDate_dp;
    private void initDatePicker() {
        lastDate_dp.setValue(LocalDate.now());
    }

    @FXML
    private ComboBox<String> type_cb, source_cb, target_cb, stock_cb;
    private ObservableList<String> types, sources, targets, stocks;

    private void initComboBoxes() {
        types = type_cb.getItems();
        sources = source_cb.getItems();
        targets = target_cb.getItems();
        stocks = stock_cb.getItems();

        type_cb.getSelectionModel().selectedItemProperty().addListener(changeListener);
        source_cb.getSelectionModel().selectedItemProperty().addListener(changeListener);
        target_cb.getSelectionModel().selectedItemProperty().addListener(changeListener);
        stock_cb.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    private void setComboBoxsItems() {
        types.clear();
        sources.clear();
        targets.clear();
        stocks.clear();

        HashSet<String> typesHashSet = new HashSet<>();
        HashSet<String> sourceHashSet = new HashSet<>();
        HashSet<String> targetHashSet = new HashSet<>();
        HashSet<String> stockHashSet = new HashSet<>();

        sendStockHistories.forEach(sendStockHistoryDTO -> {
            typesHashSet.add(sendStockHistoryDTO.getType());
            sourceHashSet.add(sendStockHistoryDTO.getSourceName());
            targetHashSet.add(sendStockHistoryDTO.getTargetName());
            stockHashSet.add(sendStockHistoryDTO.getStockName());
        });

        types.add("전체");
        types.addAll(typesHashSet);
        sources.addAll(sourceHashSet);
        targets.addAll(targetHashSet);
        stocks.addAll(stockHashSet);
    }

    private void initTableView() {
        m1.setCellValueFactory(new PropertyValueFactory<>("type"));
        m2.setCellValueFactory(new PropertyValueFactory<>("sourceName"));
        m3.setCellValueFactory(new PropertyValueFactory<>("targetName"));
        m4.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        m5.setCellValueFactory(new PropertyValueFactory<>("sendCount"));
        m6.setCellValueFactory(new PropertyValueFactory<>("date"));

        m5.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        m5.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SendStockHistoryDTO, Long>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SendStockHistoryDTO, Long> event) {
                long newValue = event.getNewValue();
                SendStockHistoryDTO row = event.getRowValue();
                row.setSendCount(newValue);
                updateSendStockHistory(row);
            }
        });
        TextFieldTableCell<DatePicker, LocalDate> a = new TextFieldTableCell<>();

        m6.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        m6.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SendStockHistoryDTO, LocalDate>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SendStockHistoryDTO, LocalDate> event) {
                LocalDate newValue = event.getNewValue();
                SendStockHistoryDTO row = event.getRowValue();
                row.setDate(newValue);
                updateSendStockHistory(row);
            }
        });

        sendStockHistories = history_tableview.getItems();
    }

    @FXML
    private void deleteSendStockHistory() {
        SendStockHistoryDTO sendStockHistoryDTO = history_tableview.getSelectionModel().getSelectedItem();
        boolean isProduct = sendStockHistoryDTO.isProduct();
        Long[] companyStockAndEmployeeStockId = sendStockHistoryService.deleteSendStockHistory(sendStockHistoryDTO);
        sendStockHistoryService.updateCompanyAndEmployeeStock(isProduct, sendStockHistoryDTO.getType(), companyStockAndEmployeeStockId);

        sendStockHistories.remove(sendStockHistoryDTO);
        new MakeAlert().makeInfoMessage(dialogStage, "삭제 완료", "완료", "완료", false);
    }

    private void updateSendStockHistory(SendStockHistoryDTO sendStockHistoryDTO) {
        sendStockHistoryService.updateSendStockHistory(sendStockHistoryDTO);
        new MakeAlert().makeInfoMessage(dialogStage, "수정 완료", "완료", "완료", false);
    }

    @FXML
    private void callHistory() {
        sendStockHistories.clear();
        sendStockHistories.addAll(sendStockHistoryService.getSendStockHistory(firstDate_dp.getValue(), lastDate_dp.getValue()));
        sendStockHistoryFilteredList = new FilteredList<>(this.sendStockHistories, sendStockHistory -> true);

        setComboBoxsItems();
    }

    @Autowired
    private PredicateCase predicateCase;

    private ChangeListener changeListener = new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {

            sendStockHistoryFilteredList.setPredicate(
                    predicateCase.getSendStockHistoryDTOFilter(type_cb,"type")
                            .and(predicateCase.getSendStockHistoryDTOFilter(source_cb, "source"))
                            .and(predicateCase.getSendStockHistoryDTOFilter(target_cb, "target"))
                            .and(predicateCase.getSendStockHistoryDTOFilter(stock_cb, "stock"))
            );
            history_tableview.setItems(sendStockHistoryFilteredList);
        }
    };
}
