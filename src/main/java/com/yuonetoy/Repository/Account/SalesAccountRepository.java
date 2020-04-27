package com.yuonetoy.Repository.Account;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Stock.SourceInfoAtStockManagementDTO;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SalesAccountRepository extends JpaRepository<SalesAccount, Long>{
    List<SalesAccount> findAllByEmployeeId(Long employee_id);

    @Query(value = "select new com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO(" +
            "sa.id, sa.name, sa.address, sa.ph, saa.name, c.name, sc.name, e.name, sa.entryDate, sa.isUsing) " +
            "from SalesAccount sa " +
            "join sa.employee e " +
            "join sa.salesAccountArea saa " +
            "join sa.companyType c " +
            "left outer join sa.secondCompanyType sc")
    List<SalesAccountPreviewDTO> findAllSalesAccountPreviewDTO();

    @Query(value = "select new com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO(" +
            "sa.id, sa.name, sa.address, sa.entryDate, e.id, e.name) " +
            "from SalesAccount sa " +
            "join sa.employee e ")
    List<SalesAccountDTO> findAllSalesAccountDTO();

    @Query(value = "select new com.yuonetoy.DTO.Stock.SourceInfoAtStockManagementDTO(" +
            "sa.id, sa.name, sa.address) " +
            "from SalesAccount sa " +
            "where sa.name like :name")
    List<SourceInfoAtStockManagementDTO> findAllSourceInfoStockManagementDTO(@Param("name") String name);

    @Query(value = "select sa from SalesAccount sa left outer join fetch sa.salesAccountMachines sam  left outer join fetch sa.salesAccountRevisionHistories left outer join fetch sam.salesAccountMachineStock left outer join fetch  sam.salesAccountProductStock where sa.id = :salesAccountId")
    SalesAccount findBySalesAccountId(@Param("salesAccountId") Long salesAccountId);

    SalesAccount findByName(String s);
}
