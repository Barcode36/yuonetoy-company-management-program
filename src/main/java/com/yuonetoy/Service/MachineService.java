package com.yuonetoy.Service;

import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductDTO;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.ProductLinkedMachine;
import com.yuonetoy.Repository.MachineRepository;
import com.yuonetoy.Repository.ProductLinkedMachineRepository;
import com.yuonetoy.Repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {

    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private ProductLinkedMachineRepository productLinkedMachineRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public void addProductLinkedMachine(Machine machine, Product product) {
        ProductLinkedMachine productLinkedMachine = productLinkedMachineRepository.findByMachine_Id(machine.getId());
        if (productLinkedMachine == null){
            productLinkedMachine = new ProductLinkedMachine();
        }
        productLinkedMachine.setMachine(machine);
        productLinkedMachine.setProduct(product);
        productLinkedMachineRepository.save(productLinkedMachine);
    }

    public MachineDTO addMachine(String name, Boolean isCoinchanger, Boolean isProductMachine, ProductDTO productDTO) {

        Machine machine = machineRepository.findByName(name);
        if (machine == null)
            machine = new Machine();

        machine.setName(name);
        machine.setIsCoinChanger(isCoinchanger);

        if (isCoinchanger)
            machine.setIsProductMachine(true);
        else {
            machine.setIsProductMachine(isProductMachine);
        }

        machineRepository.save(machine);

        if (isProductMachine) {
            Product product;
            if (isCoinchanger){
                product = productRepository.findByNameEquals("시재금");
            }else{
                product = modelMapper.map(productDTO, Product.class);
            }

            addProductLinkedMachine(machine, product);
        }

        MachineDTO machineDTO = new MachineDTO();
        machineDTO.setId(machine.getId());
        machineDTO.setName(machine.getName());
        machineDTO.setIsCoinChanger(machine.getIsCoinChanger());
        machineDTO.setIsProductMachine(machine.getIsProductMachine());

        if (productDTO != null)
            machineDTO.setProduct(productDTO.getName());
        else
            machineDTO.setProduct("");

        machineDTO.initStatus();

        return machineDTO;
    }

    public void deleteMachine(MachineDTO machineDTO) {
        Machine machine = findMachine(machineDTO);
        machineRepository.delete(machine);
    }

    public List<MachineDTO> readMachine() {
        List<MachineDTO> machines = machineRepository.findAllMachineDTO();

        return machines;
    }

    public Machine findMachine(MachineDTO machineDTO) {
        return machineRepository.findById(machineDTO.getId()).get();
    }

    public MachineDTO update(MachineDTO machineDTO, String name) {
        Machine machine = findMachine(machineDTO);

        machineDTO.setName(name);

        machine.update(name);
        machineRepository.save(machine);

        return machineDTO;
    }
}
