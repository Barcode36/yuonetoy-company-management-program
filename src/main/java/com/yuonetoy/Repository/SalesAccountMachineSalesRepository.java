package com.yuonetoy.Repository;

import com.yuonetoy.DTO.SalesAccountSalesDepoistViewDTO;
import com.yuonetoy.DTO.SalesAccountTotalSalesDTO;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachineSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SalesAccountMachineSalesRepository extends JpaRepository<SalesAccountMachineSales, Long>, QuerydslPredicateExecutor<SalesAccountMachineSales> {
    // 기계명 상품명 기계 수 없음
    @Query("SELECT new com.yuonetoy.DTO.SalesAccountTotalSalesDTO(" +
            "sa.entryDate, sa.salesAccountArea.name, sa.companyType.name, " +
            "sa.secondCompanyType.name, sa.employee.name, sa.name," +
            "sam.machine.name, sam.salesAccountMachineStock.count, sam.salesAccountProductStock.count, sam.plusValue, sam.minusValue, sam.fees," +
            "sum(sams.sales), sum(sams.balance), sum(sams.refundAmount), sams.date, sa.isUsing, sa.withdrawalDate) " +
            "from SalesAccountMachineSales sams join sams.salesAccountMachine sam join sam.salesAccount sa left outer join sa.secondCompanyType " +
            "group by sa.name order by sa.name")
    List<SalesAccountTotalSalesDTO> findAllSalesAccountTotalSales();

    // 기계명 상품명 기계 수 없음
    @Query("SELECT new com.yuonetoy.DTO.SalesAccountTotalSalesDTO(" +
            "sa.entryDate, sa.salesAccountArea.name, sa.companyType.name, " +
            "sa.secondCompanyType.name, sa.employee.name, sa.name," +
            "sam.machine.name, sam.salesAccountMachineStock.count, sam.salesAccountProductStock.count, sam.plusValue, sam.minusValue, sam.fees," +
            "sum(sams.sales),sum(sams.balance), sum(sams.refundAmount), sams.date, sa.isUsing, sa.withdrawalDate) " +
            "from SalesAccountMachineSales sams join sams.salesAccountMachine sam join sam.salesAccount sa left outer join sa.secondCompanyType " +
            "where sams.date >= :startDate " +
            "AND sams.date <= :lastDate " +
            "group by sa, sams.date order by sa.name, sams.date desc")
    List<SalesAccountTotalSalesDTO> findAllSalesAccountMonthTotalSales(@Param("startDate") LocalDate startDate, @Param("lastDate") LocalDate lastDate);

    @Query("SELECT new com.yuonetoy.DTO.SalesAccountTotalSalesDTO(" +
            "sa.entryDate, sa.salesAccountArea.name, sa.companyType.name, " +
            "sa.secondCompanyType.name, sa.employee.name, sa.name," +
            "sam.machine.name, sam.salesAccountMachineStock.count, sam.salesAccountProductStock.count, sam.plusValue, sam.minusValue, sam.fees," +
            "sum(sams.sales),sum(sams.balance), sum(sams.refundAmount), sams.date, sa.isUsing, sa.withdrawalDate) " +
            "from SalesAccountMachineSales sams join sams.salesAccountMachine sam  join sam.salesAccount sa left outer join sa.secondCompanyType " +
            "where sams.date >= :startDate " +
            "And sams.date <= :lastDate " +
            "group by sa, sam " +
            "order by sa.name, sam.machine.name")
    List<SalesAccountTotalSalesDTO> findAllSalesAccountMachineSales(@Param("startDate") LocalDate startDate,
                                                                    @Param("lastDate") LocalDate lastDate);
}
