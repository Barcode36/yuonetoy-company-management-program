package com.yuonetoy.Repository;

import com.yuonetoy.Entitiy.StockHistory.SendStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SendStockHistoryRepository extends JpaRepository<SendStockHistory, Long> {
    List<SendStockHistory> findAllByDateBetweenOrderByDateDesc(LocalDate firstDate, LocalDate lastDate);
}
