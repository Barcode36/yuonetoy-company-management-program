package com.yuonetoy.Repository.Stock;

import com.yuonetoy.DTO.Stock.SalesAccountMachineStockAtStockViewDTO;
import com.yuonetoy.DTO.Stock.SalesAccountProductStockAtStockViewDTO;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountMachineStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesAccountMachineStockRepository extends JpaRepository<SalesAccountMachineStock, Long> {
    @Query(value = "select sum(bjh.machineCount) " +
            "from BusinessJournalHistory bjh " +
            "join bjh.salesAccountMachine sam " +
            "where bjh.salesAccountMachine.id = :salesAccountMachineId " +
            "group by bjh.salesAccountMachine")
    Long getTotalMachineStock(@Param("salesAccountMachineId") Long salesAccountMachineId);

    @Query(value = "select sams " +
            "from SalesAccountMachine sam " +
            "join sam.salesAccountMachineStock sams where sam.id = :id")
    SalesAccountMachineStock findBySalesAccountMachineId(long id);

    @Query(value = "select new com.yuonetoy.DTO.Stock.SalesAccountMachineStockAtStockViewDTO(saps.id, saps.salesAccountMachine.salesAccount.name, " +
            "saps.salesAccountMachine.machine.name, saps.count) " +
            "from SalesAccountMachineStock saps " +
            "order by saps.salesAccountMachine.salesAccount.name")
    List<SalesAccountMachineStockAtStockViewDTO> findAllBySalesAccountMachineStockDTO();
}
