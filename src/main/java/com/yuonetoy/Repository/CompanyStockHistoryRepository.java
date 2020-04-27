package com.yuonetoy.Repository;

import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyStockHistory;
import com.yuonetoy.Entitiy.StockHistory.SendStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CompanyStockHistoryRepository extends JpaRepository<CompanyStockHistory, Long> {
    @Query(value = "select sum(esh.sendProductCount) from CompanyStockHistory esh where esh.companyProductStock.id = :companyProductStockId group by esh.companyProductStock")
    Long getCompanyProductStock(@Param("companyProductStockId") Long companyProductStockId);

    @Query(value = "select sum(esh.sendMachineCount) from CompanyStockHistory esh where esh.companyMachineStock.id = :companyMachineStockId group by esh.companyMachineStock")
    Long getCompanyMachineStock(@Param("companyMachineStockId") Long companyMachineStockId);


    @Query(value = "select esh " +
            "from CompanyStockHistory esh " +
            "left outer join esh.companyMachineStock " +
            "left outer join esh.companyProductStock " +
            "where not esh.sendProductCount = 0 " +
            "and esh.companyProductStock.company.id = :companyId " +
            "and esh.companyProductStock.product.id = :productId " +
            "and esh.date >= :firstDate " +
            "and esh.date <= :lastDate " +
            "order by esh.date")
    List<CompanyStockHistory> findByCompanyProductStockId(@Param("companyId") Long companyId, @Param("productId") Long productId, @Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);

    @Query(value = "select esh " +
            "from CompanyStockHistory esh " +
            "left outer join esh.companyMachineStock " +
            "left outer join esh.companyProductStock " +
            "where not esh.sendMachineCount = 0 " +
            "and esh.companyMachineStock.company.id = :companyId " +
            "and esh.companyMachineStock.machine.id = :machineId " +
            "and esh.date >= :firstDate " +
            "and esh.date <= :lastDate " +
            "order by esh.date")
    List<CompanyStockHistory> findByCompanyMachineStockId(@Param("companyId") Long companyId, @Param("machineId") Long machineId, @Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);

    @Query(value = "select sum(esh.sendProductCount) " +
            "from CompanyStockHistory esh " +
            "where esh.companyMachineStock.company.id = :companyId " +
            "and esh.companyProductStock.product.id = :productId " +
            "And esh.date < :date " +
            "group by esh.companyProductStock")
    Long beforeCompanyProductStock(@Param("companyId") Long companyId, @Param("productId") Long productId, @Param("date") LocalDate date);

    @Query(value = "select sum(esh.sendMachineCount) " +
            "from CompanyStockHistory esh " +
            "where esh.companyMachineStock.company.id = :companyId " +
            "and esh.companyMachineStock.machine.id = :machineId " +
            "And esh.date < :date " +
            "group by esh.companyMachineStock")
    Long beforeCompanyMachineStock(@Param("companyId") Long companyId, @Param("machineId") Long machineId, @Param("date") LocalDate date);
}
