package com.yuonetoy.Repository.Stock;

import com.yuonetoy.DTO.Stock.CompanyMachineStockAtStockViewDTO;
import com.yuonetoy.DTO.Stock.CompanyProductStockAtStockViewDTO;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyMachineStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyMachineStockRepository extends JpaRepository<CompanyMachineStock, Long> {
    @Query(value = "select cms from Company c join c.companyMachineStocks cms where c.id = :companyId AND cms.machine.id = :machineId")
    CompanyMachineStock findByCompanyIdAndMachineId(@Param("companyId") Long companyId, @Param("machineId") Long machineId);

    @Query(value = "select new com.yuonetoy.DTO.Stock.CompanyMachineStockAtStockViewDTO(cps.id, cps.company.name, cps.machine.name, cps.count) " +
            "from CompanyMachineStock cps " +
            "order by cps.company.name")
    List<CompanyMachineStockAtStockViewDTO> findAllByCompanyMachineStockDTO();
}
