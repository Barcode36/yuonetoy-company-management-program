package com.yuonetoy.Service;

import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Stock.EmployeeProductStockDTO;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Repository.ProductLinkedMachineRepository;
import com.yuonetoy.Repository.Stock.EmployeeProductStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductLinkedMachineService {
    @Autowired
    private EmployeeProductStockRepository employeeProductStockRepository;
    @Autowired
    private ProductLinkedMachineRepository productLinkedMachineRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<EmployeeProductStockDTO> callProductLinkedMachine(EmployeeDTO employeeDTO, MachineDTO machineDTO) {
        long productId = productLinkedMachineRepository.findByMachine_Id_Return_ProductId(machineDTO.getId());
        List<EmployeeProductStockDTO> employeeProductStockDTOList = new ArrayList<EmployeeProductStockDTO>();

        EmployeeProductStock employeeProductStock = employeeProductStockRepository.findByEmployeeIdAndProductId(employeeDTO.getId(), productId);

        if (employeeProductStock != null) {
            EmployeeProductStockDTO employeeProductStockDTO = modelMapper.map(employeeProductStock, EmployeeProductStockDTO.class);
            employeeProductStockDTO.setStockName();

            employeeProductStockDTOList.add(employeeProductStockDTO);
        }
        return employeeProductStockDTOList;
    }

}
