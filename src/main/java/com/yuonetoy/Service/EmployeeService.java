package com.yuonetoy.Service;

import com.yuonetoy.DTO.Area.EmployeeAreaDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDetailDTO;
import com.yuonetoy.DTO.Stock.SourceInfoAtStockManagementDTO;
import com.yuonetoy.Entitiy.Area.EmployeeArea;
import com.yuonetoy.Entitiy.Employee;
import com.yuonetoy.Repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeAreaService employeeAreaService;
    @Autowired
    private ModelMapper modelMapper;

    public EmployeeDetailDTO addEmployee(String name, String ph, String address, LocalDate entryDate, EmployeeAreaDTO employeeAreaDTO) {
        EmployeeArea employeeArea = modelMapper.map(employeeAreaDTO, EmployeeArea.class);

        Employee employee = new Employee();
        employee.setName(name);
        employee.setPh(ph);
        employee.setAddress(address);
        employee.setEntryDate(entryDate);
        employee.setArea(employeeArea);
        employeeRepository.save(employee);


        EmployeeDetailDTO employeeDetailDTO = modelMapper.map(employee, EmployeeDetailDTO.class);

        return employeeDetailDTO;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDetailDTO> readEmployeeDetailDTO() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDetailDTO> employeeDetailDTOList = new ArrayList<>();

        employees.forEach(employee -> {
            EmployeeDetailDTO employeeDetailDTO = modelMapper.map(employee, EmployeeDetailDTO.class);
            //employeeDetailDTO.setArea(employeeAreaService.toEmployeeAreaDTO(employee.getArea()));
            employeeDetailDTOList.add(employeeDetailDTO);
        });

        return employeeDetailDTOList;
    }

    public List<EmployeeDTO> readEmployeeDTO() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();

        employees.forEach(employee -> {
            EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
            employeeDTOList.add(employeeDTO);
        });

        return employeeDTOList;
    }

    public List<SourceInfoAtStockManagementDTO> readEmployeeDTOAtStockManagementView() {
        List<Employee> employees = employeeRepository.findAll();
        List<SourceInfoAtStockManagementDTO> sourceInfoAtStockManagementDTOList = new ArrayList<SourceInfoAtStockManagementDTO>();

        employees.forEach(employee -> {
            SourceInfoAtStockManagementDTO sourceInfoAtStockManagementDTO = new SourceInfoAtStockManagementDTO();
            sourceInfoAtStockManagementDTO.setId(employee.getId());
            sourceInfoAtStockManagementDTO.setName(employee.getName());
            sourceInfoAtStockManagementDTO.setAddress(employee.getAddress());
            sourceInfoAtStockManagementDTOList.add(sourceInfoAtStockManagementDTO);
        });

        return sourceInfoAtStockManagementDTOList;
    }



    public List<String> readEmployeeStringDTO() {
        List<Employee> employees = employeeRepository.findAll();
        List<String> employeeDTOList = new ArrayList<String>();

        employees.forEach(employee -> {
            employeeDTOList.add(employee.getName());
        });

        return employeeDTOList;
    }

    public Employee findEmployee(EmployeeDetailDTO employeeDetailDTO) {
        Employee employee = employeeRepository.findById(employeeDetailDTO.getId()).get();
        return employee;
    }

    public Employee findEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(employeeDTO.getId()).get();
        return employee;
    }

    public void delete(EmployeeDetailDTO employeeDetailDTO) {
        Employee employee = findEmployee(employeeDetailDTO);
        employeeRepository.delete(employee);
    }

    public EmployeeDetailDTO update(EmployeeDetailDTO employeeDetailDTO, String name, String ph, String address, LocalDate entryDate, EmployeeAreaDTO employeeAreaDTO) {
        Employee employee = findEmployee(employeeDetailDTO);

        employeeDetailDTO.setName(name);
        employeeDetailDTO.setPh(ph);
        employeeDetailDTO.setAddress(address);
        employeeDetailDTO.setEntryDate(entryDate);
        employeeDetailDTO.setArea(employeeAreaDTO);

        EmployeeArea employeeArea = employeeAreaService.toEmployeeArea(employeeAreaDTO);

        employee.update(name, ph, address, entryDate, employeeArea);
        employeeRepository.save(employee);

        return employeeDetailDTO;
    }

    public Employee toEmployee(EmployeeDTO employeeDTO){
        return employeeRepository.findById(employeeDTO.getId()).get();
    }

}
