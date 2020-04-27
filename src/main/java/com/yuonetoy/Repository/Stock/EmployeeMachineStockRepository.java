
package com.yuonetoy.Repository.Stock;

import com.yuonetoy.DTO.Stock.EmployeeMachineStockAtStockViewDTO;
import com.yuonetoy.DTO.Stock.EmployeeProductStockAtStockViewDTO;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeMachineStockRepository extends JpaRepository<EmployeeMachineStock, Long> {
    @Query(value = "select ems from Employee e join e.employeeMachineStocks ems where e.id = :employeeId AND ems.machine.id = :machineId")
    EmployeeMachineStock findByEmployeeIdAndMachineId(@Param("employeeId") long employeeId, @Param("machineId") long machineId);

    @Query(value = "select ems from Employee e join e.employeeMachineStocks ems where e.id = :employeeId")
    List<EmployeeMachineStock> findByEmployeeIdOrderByMachine(long employeeId);

    @Query(value = "select new com.yuonetoy.DTO.Stock.EmployeeMachineStockAtStockViewDTO(eps.id, eps.employee.name, eps.machine.name, eps.count) " +
            "from EmployeeMachineStock eps " +
            "order by eps.employee.name")
    List<EmployeeMachineStockAtStockViewDTO> findAllByEmployeeMachineStockDTO();
}
