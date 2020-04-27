package com.yuonetoy.Repository.Stock;

import com.yuonetoy.DTO.Stock.CompanyProductStockAtStockViewDTO;
import com.yuonetoy.DTO.Stock.CompanyProductStockDTO;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyProductStockRepository extends JpaRepository<CompanyProductStock, Long>  {
    @Query(value = "select cps from Company c join c.companyProductStocks cps where c.id = :companyId AND cps.product.id = :productId")
    CompanyProductStock findByCompanyIdAndProductId(@Param("companyId") Long companyId, @Param("productId") Long productId);

    @Query(value = "select new com.yuonetoy.DTO.Stock.CompanyProductStockAtStockViewDTO(cps.id, cps.company.name, cps.product.name, cps.count) " +
            "from CompanyProductStock cps " +
            "order by cps.company.name")
    List<CompanyProductStockAtStockViewDTO> findAllByCompanyProductStockDTO();
}
