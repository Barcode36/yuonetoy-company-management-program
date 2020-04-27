package com.yuonetoy.Repository;

import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.Entitiy.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    Machine findByName(String name);

    @Query("select new com.yuonetoy.DTO.MachineDTO(m.id, m.name, m.isCoinChanger, m.isProductMachine, plm.product.name) " +
            "from Machine m " +
            "left outer join ProductLinkedMachine plm on m.id = plm.machine.id " +
            "left outer join plm.product")
    List<MachineDTO> findAllMachineDTO();
}
