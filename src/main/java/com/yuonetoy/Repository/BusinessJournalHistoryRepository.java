package com.yuonetoy.Repository;

import com.yuonetoy.DTO.BusinessJournalHistoryDTO;
import com.yuonetoy.DTO.StockHistory.SalesAccountStockHistoryDTO;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BusinessJournalHistoryRepository extends JpaRepository<BusinessJournalHistory, Long> {

    @Query("select new com.yuonetoy.DTO.BusinessJournalHistoryDTO(bjh.id, bjh.type, bjh.salesAccountMachine.machine.name, bjh.product.name, " +
            "bjh.machineCount, bjh.initialQuantity, bjh.fees, " +
            "bjh.supplyCount, bjh.plusValue, bjh.minusValue, " +
            "bjh.salesValue, bjh.refundValue) " +
            "from BusinessJournalHistory bjh " +
            "left outer join bjh.product " +
            "where bjh.businessJournalHistoryList.id = :bjhlId")
    List<BusinessJournalHistoryDTO> findByBusinessJournalHistoryListId(@Param("bjhlId") long id);

    @Query("select bjh.salesAccountMachine, sum(bjh.plusValue), sum(bjh.minusValue) " +
            "from BusinessJournalHistory bjh " +
            "where bjh.salesAccountMachine.id = :salesAccountMachineId " +
            "group by bjh.salesAccountMachine")
    List<Object[]> findBySalesAccountMachineId(@Param("salesAccountMachineId") long salesAccountMachineId);

    @Query("select sum(bjh.fees) " +
            "from BusinessJournalHistory bjh " +
            "where bjh.salesAccountMachine.id = :salesAccountMachineId And bjh.businessJournalHistoryList.date <= :date " +
            "group by bjh.salesAccountMachine")
    Double getFees(@Param("salesAccountMachineId") long salesAccountMachineId, @Param("date") LocalDate date);

    @Query("select new com.yuonetoy.DTO.StockHistory.SalesAccountStockHistoryDTO(" +
            "bjh.type, sa.employee.name, sam.machine.name, p.name, bjh.supplyCount, bjh.salesValue, bjhl.date ) " +
            "from BusinessJournalHistory bjh " +
            "join bjh.businessJournalHistoryList bjhl " +
            "join bjh.salesAccountMachine sam " +
            "join bjh.product p " +
            "join bjhl.salesAccount sa " +
            "where not(bjh.supplyCount = 0 And bjh.salesValue = 0) " +
            "And sam.id = :salesAccountMachineId " +
            "And bjh.businessJournalHistoryList.date >= :firstDate " +
            "And bjh.businessJournalHistoryList.date <= :lastDate ")
    List<SalesAccountStockHistoryDTO> getSalesAccountStockMoveHistory(@Param("salesAccountMachineId") long salesAccountMachineId, @Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);

    @Query("select sum(bjh.supplyCount), sum(bjh.salesValue)" +
            "from BusinessJournalHistory bjh " +
            "join bjh.businessJournalHistoryList bjhl " +
            "join bjh.salesAccountMachine sam " +
            "where sam.id = :salesAccountMachineId " +
            "And bjh.businessJournalHistoryList.date < :date ")
    List<Object[]> getBeforeStock(@Param("salesAccountMachineId") long salesAccountMachineId, @Param("date") LocalDate date);

    @Query("select bjhl.date " +
            "from BusinessJournalHistoryList bjhl " +
            "join bjhl.salesAccount sa " +
            "where sa.id = :salesAccountId " +
            "and bjhl.type like :type " +
            "order by bjhl.date desc")
    List<LocalDate> findBySalesAccountWithdrawalDate(@Param("salesAccountId") long salesAccountId, @Param("type") String type);
}
