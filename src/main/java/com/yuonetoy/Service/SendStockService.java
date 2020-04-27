package com.yuonetoy.Service;

import com.yuonetoy.DTO.Account.PurchaseAccount.PurchaseAccountDTO;
import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.SendStockDTO;
import com.yuonetoy.DTO.Stock.CompanyStockDTO;
import com.yuonetoy.DTO.Stock.MakeMachineStockDTO;
import com.yuonetoy.DTO.Stock.PurchaseAccountStockDTO;
import com.yuonetoy.Entitiy.*;
import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyMachineStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyProductStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyStockHistory;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeStockHistory;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.StockHistory.SendStockHistory;
import com.yuonetoy.Repository.*;
import com.yuonetoy.Repository.Stock.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SendStockService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EmployeeProductStockRepository employeeProductStockRepository;
    @Autowired
    private EmployeeMachineStockRepository employeeMachineStockRepository;
    @Autowired
    private CompanyProductStockRepository companyProductStockRepository;
    @Autowired
    private CompanyMachineStockRepository companyMachineStockRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyStockHistoryRepository companyStockHistoryRepository;
    @Autowired
    private EmployeeStockHistoryRepository employeeStockHistoryRepository;
    @Autowired
    private CompanyStockService companyStockService;
    @Autowired
    private EmployeeStockService employeeStockService;

    public ProductPriceDTO findCapsuleToy() {
        ProductPriceDTO capsuleToy = modelMapper.map(productRepository.findByNameEquals("캡슐 토이"), ProductPriceDTO.class);
        return capsuleToy;
    }

    @Transactional(readOnly = true)
    public List<CompanyStockDTO> getCompanyStock(CompanyDTO companyDTO) {
        Company company = companyRepository.getCompanyStockByCompanyId(companyDTO.getId());
        List<CompanyStockDTO> companyStockDTOList = new ArrayList<>();

        company.getCompanyProductStocks().forEach(companyProductStock -> {
            ProductPriceDTO productPriceDTO = modelMapper.map(companyProductStock.getProduct(), ProductPriceDTO.class);
            int count = companyProductStock.getCount();

            CompanyStockDTO companyStockDTO = new CompanyStockDTO(companyDTO, true, productPriceDTO, null, count);
            companyStockDTOList.add(companyStockDTO);
        });

        company.getCompanyMachineStocks().forEach(companyMachineStock -> {
            MachineDTO machineDTO = modelMapper.map(companyMachineStock.getMachine(), MachineDTO.class);
            int count = companyMachineStock.getCount();

            CompanyStockDTO companyStockDTO = new CompanyStockDTO(companyDTO, false, null, machineDTO, count);
            companyStockDTOList.add(companyStockDTO);
        });

        return companyStockDTOList;
    }

    @Transactional(readOnly = true)
    public List<PurchaseAccountStockDTO> getPurchaseAccountProduct() {
        List<Product> products = productRepository.findAll();
        List<PurchaseAccountStockDTO> purchaseAccountStockDTOS = new ArrayList<>();

        products.forEach(product -> {
            if (product.getPurchaseAccount() != null) {
                ProductPriceDTO productPriceDTO = modelMapper.map(product, ProductPriceDTO.class);
                PurchaseAccountDTO purchaseAccountDTO = modelMapper.map(product.getPurchaseAccount(), PurchaseAccountDTO.class);
                PurchaseAccountStockDTO purchaseAccountStockDTO = new PurchaseAccountStockDTO(purchaseAccountDTO, productPriceDTO);

                purchaseAccountStockDTOS.add(purchaseAccountStockDTO);
            }
        });
        return purchaseAccountStockDTOS;
    }

    @Autowired
    MachineRepository machineRepository;

    @Transactional(readOnly = true)
    public List<MakeMachineStockDTO> getMachine() {
        List<Machine> machines = machineRepository.findAll();
        List<MakeMachineStockDTO> makeMachineStockDTOList = new ArrayList<>();

        machines.forEach(machine -> {
            MachineDTO machineDTO = modelMapper.map(machine, MachineDTO.class);
            MakeMachineStockDTO makeMachineStockDTO = new MakeMachineStockDTO();
            makeMachineStockDTO.setMachine(machineDTO);
            makeMachineStockDTO.setStockStatus();

            makeMachineStockDTOList.add(makeMachineStockDTO);
        });

        return makeMachineStockDTOList;
    }

    public List<EmployeeDTO> getEmployee() {
        return employeeService.readEmployeeDTO();
    }

    public List<CompanyDTO> getCompany() {
        return companyService.readCompany();
    }

    public void sendStockCtoE(List<SendStockDTO> stockDTOList) {
        stockDTOList.forEach(sendStockDTO -> {
            CompanyDTO sourceCompanyDTO = sendStockDTO.getSourceCompany();
            EmployeeDTO targetEmployeeDTO = sendStockDTO.getTargetEmployee();
            boolean isProduct = sendStockDTO.isProduct();
            ProductPriceDTO sourceProductPriceDTO = null;
            ProductPriceDTO targetProductPriceDTO = null;
            MachineDTO machineDTO = null;

            if (isProduct) {
                sourceProductPriceDTO = sendStockDTO.getProduct();

                if (sourceProductPriceDTO.getCapsuleToy())
                    targetProductPriceDTO = findCapsuleToy();
                else
                    targetProductPriceDTO = sendStockDTO.getProduct();
            } else {
                machineDTO = sendStockDTO.getMachine();
            }

            int sendCount = sendStockDTO.getSendCount();
            LocalDate date = sendStockDTO.getDate();

            Company company = modelMapper.map(sourceCompanyDTO, Company.class);
            Employee employee = modelMapper.map(targetEmployeeDTO, Employee.class);

            SendStockHistory sendStockHistory = new SendStockHistory();
            sendStockHistory.setIsPAtoC(false);
            sendStockHistory.setIsCtoE(true);
            sendStockHistory.setIsCtoC(false);
            sendStockHistory.setIsMtoC(false);
            sendStockHistory.setSourceCompany(company);
            sendStockHistory.setTargetEmployee(employee);
            sendStockHistory.setSendCount(sendCount);
            sendStockHistory.setDate(date);
            sendStockHistoryRepository.save(sendStockHistory);

            if (isProduct) {
                Product sourceProduct = modelMapper.map(sourceProductPriceDTO, Product.class);

                sendStockHistory.setProduct(sourceProduct);
                sendStockHistory.setIsProductStock(true);

                sendStockHistoryRepository.save(sendStockHistory);

                saveCompanyStockHistory(sendStockHistory, company, -sendCount, 0);
                saveEmployeeStockHistory(sendStockHistory, targetProductPriceDTO);
            } else {
                Machine machine = modelMapper.map(machineDTO, Machine.class);
                sendStockHistory.setMachine(machine);
                sendStockHistory.setIsProductStock(false);

                sendStockHistoryRepository.save(sendStockHistory);

                saveCompanyStockHistory(sendStockHistory, company, 0, -sendCount);
                saveEmployeeStockHistory(sendStockHistory, null);
            }

        });
    }

    @Autowired
    private SendStockHistoryRepository sendStockHistoryRepository;

    public void sendStockPAtoC(List<SendStockDTO> sendStockDTOList) {
        sendStockDTOList.forEach(sendStockDTO -> {
            CompanyDTO targetCompanyDTO = sendStockDTO.getTargetCompany();
            ProductPriceDTO productPriceDTO = sendStockDTO.getProduct();
            int sendCount = sendStockDTO.getSendCount();
            LocalDate date = sendStockDTO.getDate();

            Company targetCompany = companyRepository.findById(targetCompanyDTO.getId()).get();
            PurchaseAccount purchaseAccount = modelMapper.map(sendStockDTO.getSourcePurchaseAccount(), PurchaseAccount.class);
            Product product = modelMapper.map(productPriceDTO, Product.class);

            SendStockHistory sendStockHistory = new SendStockHistory();
            sendStockHistory.setIsPAtoC(true);
            sendStockHistory.setIsCtoE(false);
            sendStockHistory.setIsCtoC(false);
            sendStockHistory.setIsMtoC(false);
            sendStockHistory.setIsProductStock(true);
            sendStockHistory.setSourcePurchaseAccount(purchaseAccount);
            sendStockHistory.setTargetCompany(targetCompany);
            sendStockHistory.setProduct(product);
            sendStockHistory.setSendCount(sendCount);
            sendStockHistory.setDate(date);
            sendStockHistoryRepository.save(sendStockHistory);

            saveCompanyStockHistory(sendStockHistory, targetCompany, sendCount, 0);
        });
    }

    public void sendStockCtoC(List<SendStockDTO> sendStockDTOList) {
        sendStockDTOList.forEach(sendStockDTO -> {
            CompanyDTO sourceCompanyDTO = sendStockDTO.getSourceCompany();
            CompanyDTO targetCompanyDTO = sendStockDTO.getTargetCompany();
            boolean isProduct = sendStockDTO.isProduct();
            ProductPriceDTO productPriceDTO = sendStockDTO.getProduct();
            MachineDTO machineDTO = sendStockDTO.getMachine();
            int sendCount = sendStockDTO.getSendCount();
            LocalDate date = sendStockDTO.getDate();

            Company sourceCompany = modelMapper.map(sourceCompanyDTO, Company.class);
            Company targetCompany = companyRepository.findById(targetCompanyDTO.getId()).get();

            SendStockHistory sendStockHistory = new SendStockHistory();
            sendStockHistory.setIsPAtoC(false);
            sendStockHistory.setIsCtoE(false);
            sendStockHistory.setIsCtoC(true);
            sendStockHistory.setIsMtoC(false);
            sendStockHistory.setSourceCompany(sourceCompany);
            sendStockHistory.setTargetCompany(targetCompany);
            sendStockHistory.setSendCount(sendCount);
            sendStockHistory.setDate(date);

            if (isProduct) {
                Product product = modelMapper.map(productPriceDTO, Product.class);
                sendStockHistory.setProduct(product);
                sendStockHistory.setIsProductStock(true);

                sendStockHistoryRepository.save(sendStockHistory);

                saveCompanyStockHistory(sendStockHistory, sourceCompany, -sendCount, 0);
                saveCompanyStockHistory(sendStockHistory, targetCompany, sendCount, 0);
            } else {
                Machine machine = modelMapper.map(machineDTO, Machine.class);
                sendStockHistory.setMachine(machine);
                sendStockHistory.setIsProductStock(false);

                sendStockHistoryRepository.save(sendStockHistory);

                saveCompanyStockHistory(sendStockHistory, sourceCompany, 0, -sendCount);
                saveCompanyStockHistory(sendStockHistory, targetCompany, 0, sendCount);
            }
        });
    }

    public void sendStockMtoC(List<SendStockDTO> sendStockDTOList) {
        sendStockDTOList.forEach(sendStockDTO -> {
            CompanyDTO targetCompanyDTO = sendStockDTO.getTargetCompany();
            MachineDTO machineDTO = sendStockDTO.getMachine();
            int sendCount = sendStockDTO.getSendCount();
            LocalDate date = sendStockDTO.getDate();

            Company targetCompany = companyRepository.findById(targetCompanyDTO.getId()).get();
            Machine machine = modelMapper.map(machineDTO, Machine.class);

            SendStockHistory sendStockHistory = new SendStockHistory();
            sendStockHistory.setIsPAtoC(false);
            sendStockHistory.setIsCtoE(false);
            sendStockHistory.setIsCtoC(false);
            sendStockHistory.setIsMtoC(true);
            sendStockHistory.setIsProductStock(false);
            sendStockHistory.setTargetCompany(targetCompany);
            sendStockHistory.setMachine(machine);
            sendStockHistory.setSendCount(sendCount);
            sendStockHistory.setDate(date);
            sendStockHistoryRepository.save(sendStockHistory);

            saveCompanyStockHistory(sendStockHistory, targetCompany, 0, sendCount);
        });
    }

    private void saveCompanyStockHistory(SendStockHistory sendStockHistory, Company company, int sendProductCount, int sendMachineCount) {
        Product product = sendStockHistory.getProduct();
        Machine machine = sendStockHistory.getMachine();
        LocalDate date = sendStockHistory.getDate();

        CompanyProductStock companyProductStock = null;
        CompanyMachineStock companyMachineStock = null;

        if (sendStockHistory.getIsProductStock()) {
            companyProductStock = companyProductStockRepository.findByCompanyIdAndProductId(company.getId(), product.getId());
            if (companyProductStock == null) {
                companyProductStock = new CompanyProductStock();
                companyProductStock.setId(null);
                companyProductStock.setProduct(product);
                companyProductStock.setCount(0);
                companyProductStock.setCompany(company);

                companyProductStockRepository.save(companyProductStock);
            }
        } else {
            companyMachineStock = companyMachineStockRepository.findByCompanyIdAndMachineId(company.getId(), machine.getId());
            if (companyMachineStock == null) {
                companyMachineStock = new CompanyMachineStock();
                companyMachineStock.setId(null);
                companyMachineStock.setMachine(machine);
                companyMachineStock.setCount(0);
                companyMachineStock.setCompany(company);

                companyMachineStockRepository.save(companyMachineStock);
            }
        }

        CompanyStockHistory companyStockHistory = new CompanyStockHistory();
        companyStockHistory.setSendStockHistory(sendStockHistory);
        companyStockHistory.setCompanyProductStock(companyProductStock);
        companyStockHistory.setCompanyMachineStock(companyMachineStock);
        companyStockHistory.setSendMachineCount(sendMachineCount);
        companyStockHistory.setSendProductCount(sendProductCount);
        companyStockHistory.setDate(date);

        companyStockHistoryRepository.save(companyStockHistory);

        if (sendStockHistory.getIsProductStock()) {
            companyStockService.updateCompanyProductStock(companyProductStock.getId());
        } else {
            companyStockService.updateCompanyMachineStock(companyMachineStock.getId());
        }
    }

    private void saveEmployeeStockHistory(SendStockHistory sendStockHistory, ProductPriceDTO targetProduct) {
        Employee employee = sendStockHistory.getTargetEmployee();
        long productId = targetProduct == null ? 0 : targetProduct.getId();
        Product product = targetProduct == null ? null : modelMapper.map(targetProduct, Product.class);
        Machine machine = sendStockHistory.getMachine();
        int sendProductCount = sendStockHistory.getIsProductStock() ? sendStockHistory.getSendCount() : 0;
        int sendMachineCount = sendStockHistory.getIsProductStock() ? 0 : sendStockHistory.getSendCount();
        LocalDate date = sendStockHistory.getDate();

        EmployeeProductStock employeeProductStock = null;
        EmployeeMachineStock employeeMachineStock = null;

        if (sendStockHistory.getIsProductStock()) {
            employeeProductStock = employeeProductStockRepository.findByEmployeeIdAndProductId(employee.getId(), productId);
            if (employeeProductStock == null) {
                employeeProductStock = new EmployeeProductStock();
                employeeProductStock.setId(null);
                employeeProductStock.setProduct(product);
                employeeProductStock.setCount(0);
                employeeProductStock.setEmployee(employee);

                employeeProductStockRepository.save(employeeProductStock);
            }
        } else {
            employeeMachineStock = employeeMachineStockRepository.findByEmployeeIdAndMachineId(employee.getId(), machine.getId());
            if (employeeMachineStock == null) {
                employeeMachineStock = new EmployeeMachineStock();
                employeeMachineStock.setId(null);
                employeeMachineStock.setMachine(machine);
                employeeMachineStock.setCount(0);
                employeeMachineStock.setEmployee(employee);

                employeeMachineStockRepository.save(employeeMachineStock);
            }
        }

        EmployeeStockHistory employeeStockHistory = new EmployeeStockHistory();
        employeeStockHistory.setIsCompanySendStock(true);
        employeeStockHistory.setSendStockHistory(sendStockHistory);
        employeeStockHistory.setEmployeeProductStock(employeeProductStock);
        employeeStockHistory.setEmployeeMachineStock(employeeMachineStock);
        employeeStockHistory.setSendMachineCount(sendMachineCount);
        employeeStockHistory.setSendProductCount(sendProductCount);
        employeeStockHistory.setDate(date);

        employeeStockHistoryRepository.save(employeeStockHistory);

        if (sendStockHistory.getIsProductStock()) {
            employeeStockService.updateEmployeeProductStock(employeeProductStock.getId());
        } else {
            employeeStockService.updateEmployeeMachineStock(employeeMachineStock.getId());
        }
    }

}
