package com.yuonetoy.Repository;

import com.yuonetoy.Entitiy.Company;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("select c " +
            "from Company c " +
            "left outer join fetch c.companyMachineStocks " +
            "left outer join fetch c.companyProductStocks " +
            "where c.id = :companyID")
    Company getCompanyStockByCompanyId(@Param("companyID") Long id);

    Company findByName(String name);
}
