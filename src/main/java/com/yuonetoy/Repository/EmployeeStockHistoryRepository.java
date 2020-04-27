package com.yuonetoy.Repository;

import com.yuonetoy.DTO.StockHistory.EmployeeStockHistoryDTO;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeStockHistoryRepository extends JpaRepository<EmployeeStockHistory, Long> {
    @Query(value = "select sum(esh.sendProductCount) from EmployeeStockHistory esh where esh.employeeProductStock.id = :employeeProductStockId group by esh.employeeProductStock")
    Long getEmployeeProductStock(@Param("employeeProductStockId") Long employeeProductStockId);

    @Query(value = "select sum(esh.sendMachineCount) from EmployeeStockHistory esh where esh.employeeMachineStock.id = :employeeMachineStockId group by esh.employeeMachineStock")
    Long getEmployeeMachineStock(@Param("employeeMachineStockId") Long employeeMachineStockId);

    @Query(value = "select esh " +
            "from EmployeeStockHistory esh " +
            "left outer join esh.businessJournalHistory bjh " +
            "left outer join esh.sendStockHistory ssh " +
            "where not esh.sendMachineCount = 0 " +
            "and esh.employeeMachineStock.employee.id = :employeeId " +
            "and esh.employeeMachineStock.machine.id = :machineId " +
            "And esh.date >= :firstDate " +
            "And esh.date <= :lastDate " +
            "order by esh.date")
    List<EmployeeStockHistory> findByEmployeeMachineStockId(@Param("employeeId") Long employeeId, @Param("machineId") Long machineId, @Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);

     @Query(value = "select esh " +
            "from EmployeeStockHistory esh " +
            "left outer join esh.businessJournalHistory bjh " +
            "left outer join esh.sendStockHistory ssh " +
            "where not esh.sendProductCount = 0 " +
            "and esh.employeeProductStock.employee.id = :employeeId " +
            "and esh.employeeProductStock.product.id = :productId " +
             "And esh.date >= :firstDate " +
             "And esh.date <= :lastDate " +
            "order by esh.date")
    List<EmployeeStockHistory> findByEmployeeProductStockId(@Param("employeeId") Long employeeId, @Param("productId") Long productId, @Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);

    @Query(value = "select sum(esh.sendProductCount) " +
            "from EmployeeStockHistory esh " +
            "where esh.employeeProductStock.employee.id = :employeeId " +
            "and esh.employeeProductStock.product.id = :productId " +
            "And esh.date < :firstDate " +
            "group by esh.employeeProductStock")
    Long beforeEmployeeProductStock(@Param("employeeId") Long employeeId, @Param("productId") Long productId, @Param("firstDate") LocalDate firstDate);

    @Query(value = "select sum(esh.sendMachineCount) " +
            "from EmployeeStockHistory esh " +
            "where esh.employeeMachineStock.employee.id = :employeeId " +
            "and esh.employeeMachineStock.machine.id = :machineId " +
            "And esh.date < :firstDate " +
            "group by esh.employeeMachineStock")
    Long beforeEmployeeMachineStock(@Param("employeeId") Long employeeId, @Param("machineId") Long machineId, @Param("firstDate") LocalDate firstDate);
}
