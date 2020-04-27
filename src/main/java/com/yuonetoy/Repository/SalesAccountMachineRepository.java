package com.yuonetoy.Repository;

import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Model.TotalCoinChangerInfo;
import com.yuonetoy.Model.TotalMachineInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesAccountMachineRepository extends JpaRepository<SalesAccountMachine, Long> {
    @Query("select sa from SalesAccount sa join fetch sa.employee join fetch sa.salesAccountMachines sam left outer join fetch sam.salesAccountProductStock saps where sa.id = :salesAccountId")
    SalesAccount findBySalesAccountId(@Param("salesAccountId") Long id);

    SalesAccountMachine findBySalesAccountIdAndMachineId(Long salesAccountId, Long machineId);

    @Query("Select new com.yuonetoy.Model.TotalMachineInfo(m.name , sum(sams.count), sum(sams.count)) " +
            "from SalesAccountMachine sam " +
            "join sam.salesAccountMachineStock sams " +
            "join sam.machine m " +
            "where m.isCoinChanger = false " +
            "group by m")
    List<TotalMachineInfo> getTotalMachineInfo();

    @Query("Select new com.yuonetoy.Model.TotalMachineInfo(m.name , sum(sams.count), sum(sams.count)) " +
            "from SalesAccountMachine sam " +
            "join sam.salesAccountMachineStock sams " +
            "join sam.machine m " +
            "where sam.salesAccount.id in (:salesAccountId) " +
            "and m.isCoinChanger = false " +
            "group by m")
    List<TotalMachineInfo> getTotalMachineInfo(List<Long> salesAccountId);

    @Query("Select new com.yuonetoy.Model.TotalCoinChangerInfo(m.name , sum(sams.count), sum(saps.count)) " +
            "from SalesAccountMachine sam " +
            "join sam.salesAccountMachineStock sams " +
            "join sam.machine m " +
            "join sam.salesAccountProductStock saps " +
            "where m.isCoinChanger = true " +
            "group by m")
    List<TotalCoinChangerInfo> getTotalCoinChangerInfo();

    @Query("Select new com.yuonetoy.Model.TotalCoinChangerInfo(m.name , sum(sams.count), sum(saps.count)) " +
            "from SalesAccountMachine sam " +
            "join sam.salesAccountMachineStock sams " +
            "join sam.machine m " +
            "join sam.salesAccountProductStock saps " +
            "where sam.salesAccount.id in (:salesAccountId) " +
            "and m.isCoinChanger = true " +
            "group by m")
    List<TotalCoinChangerInfo> getTotalCoinChangerInfo(List<Long> salesAccountId);

    @Query("Select b.fees " +
            "from BusinessJournalHistory b " +
            "where b.salesAccountMachine.id = :salesAccountId " +
            "and not b.fees = 0 " +
            "order by b.businessJournalHistoryList.date desc ")
    List<Double> getFees(Long salesAccountId);

    @Query("Select b.initialQuantity " +
            "from BusinessJournalHistory b " +
            "where b.salesAccountMachine.id = :salesAccountId " +
            "and not b.initialQuantity = 0 " +
            "order by b.businessJournalHistoryList.date desc ")
    List<Integer> getInitialQuantity(Long salesAccountId);
}
