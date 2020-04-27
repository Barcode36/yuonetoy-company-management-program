package com.yuonetoy.Repository.Stock;

import com.yuonetoy.DTO.Stock.CompanyProductStockDTO;
import com.yuonetoy.DTO.Stock.EmployeeProductStockAtStockViewDTO;
import com.yuonetoy.DTO.Stock.EmployeeProductStockDTO;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyProductStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeProductStockRepository extends JpaRepository<EmployeeProductStock, Long> {
    @Query(value = "select eps from EmployeeProductStock eps join eps.employee e join eps.product where e.id = :employeeId AND eps.product.id = :productId")
    EmployeeProductStock findByEmployeeIdAndProductId(@Param("employeeId") Long employeeId, @Param("productId") Long productId);

    @Query(value = "select eps.id from EmployeeProductStock eps join eps.employee e join eps.product where e.id = :employeeId AND eps.product.name = '캡슐 토이'")
    Long getEmployeeCapsuleToyStockId(@Param("employeeId") Long employeeId);


    @Query(value = "select new com.yuonetoy.DTO.Stock.EmployeeProductStockAtStockViewDTO(eps.id, eps.employee.name, eps.product.name, eps.count) " +
            "from EmployeeProductStock eps " +
            "order by eps.employee.name")
    List<EmployeeProductStockAtStockViewDTO> findAllByEmployeeProductStockDTO();
}
