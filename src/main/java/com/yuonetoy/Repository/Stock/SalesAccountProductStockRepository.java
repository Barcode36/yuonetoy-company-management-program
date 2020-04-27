package com.yuonetoy.Repository.Stock;

import com.yuonetoy.DTO.Stock.SalesAccountProductStockAtStockViewDTO;
import com.yuonetoy.DTO.Stock.SalesAccountProductStockDTO;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesAccountProductStockRepository extends JpaRepository<SalesAccountProductStock, Long> {
    @Query(value = "select sum(bjh.supplyCount) " +
            "from BusinessJournalHistory bjh " +
            "join bjh.salesAccountMachine sam " +
            "where bjh.salesAccountMachine.id = :salesAccountMachineId " +
            "group by bjh.salesAccountMachine")
    Long getTotalProductStock(@Param("salesAccountMachineId") Long salesAccountMachineId);

    @Query(value = "select saps " +
            "from SalesAccountMachine sam " +
            "join sam.salesAccountProductStock saps where  sam.id = :id")
    SalesAccountProductStock findBySalesAccountMachineId(long id);

    @Query(value = "select new com.yuonetoy.DTO.Stock.SalesAccountProductStockAtStockViewDTO(saps.id, saps.salesAccountMachine.salesAccount.name, " +
            "saps.salesAccountMachine.machine.name, saps.product.name, saps.count) " +
            "from SalesAccountProductStock saps " +
            "order by saps.salesAccountMachine.salesAccount.name")
    List<SalesAccountProductStockAtStockViewDTO> findAllBySalesAccountProductStockDTO();
}
