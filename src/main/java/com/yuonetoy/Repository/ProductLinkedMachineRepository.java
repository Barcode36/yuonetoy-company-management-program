package com.yuonetoy.Repository;

import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.ProductLinkedMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductLinkedMachineRepository extends JpaRepository<ProductLinkedMachine, Long> {
    @Query(value = "select p.id from ProductLinkedMachine plm join plm.machine m join plm.product p where m.id = :machineId ")
    long findByMachine_Id_Return_ProductId(@Param("machineId") Long machineId);

    @Query(value = "select p from ProductLinkedMachine plm join plm.machine m join plm.product p where m.id = :machineId ")
    Product findByMachineIdRetuonProduct(@Param("machineId") Long machineId);

    ProductLinkedMachine findByMachine_Id(Long machineId);
}
