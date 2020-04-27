package com.yuonetoy.Service;

import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.Product.ProductDTO;
import com.yuonetoy.DTO.Product.ProductPriceDTO;
import com.yuonetoy.DTO.Stock.CompanyStockDTO;
import com.yuonetoy.DTO.StockHistory.CompanyStockHistoryDTO;
import com.yuonetoy.DTO.StockHistory.SendStockHistoryDTO;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyMachineStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyProductStock;
import com.yuonetoy.Entitiy.Stock.CompanyStock.CompanyStockHistory;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeStockHistory;
import com.yuonetoy.Entitiy.StockHistory.SendStockHistory;
import com.yuonetoy.Repository.CompanyStockHistoryRepository;
import com.yuonetoy.Repository.EmployeeStockHistoryRepository;
import com.yuonetoy.Repository.SendStockHistoryRepository;
import com.yuonetoy.Repository.Stock.CompanyMachineStockRepository;
import com.yuonetoy.Repository.Stock.CompanyProductStockRepository;
import com.yuonetoy.Repository.Stock.EmployeeMachineStockRepository;
import com.yuonetoy.Repository.Stock.EmployeeProductStockRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SendStockHistoryService {
    @Autowired
    private SendStockHistoryRepository sendStockHistoryRepository;
    @Autowired
    private EmployeeStockHistoryRepository employeeStockHistoryRepository;
    @Autowired
    private CompanyStockHistoryRepository companyStockHistoryRepository;
    @Autowired
    private EmployeeStockService employeeStockService;
    @Autowired
    private CompanyStockService companyStockService;

    public void updateSendStockHistory(SendStockHistoryDTO sendStockHistoryDTO) {
        long sendStockHistoryId = sendStockHistoryDTO.getId();
        SendStockHistory sendStockHistory = sendStockHistoryRepository.findById(sendStockHistoryId).get();
        int newSendCount = (int) sendStockHistoryDTO.getSendCount();
        LocalDate newDate = sendStockHistoryDTO.getDate();

        sendStockHistory.setSendCount(newSendCount);
        sendStockHistory.setDate(newDate);
        sendStockHistoryRepository.save(sendStockHistory);

        CompanyStockHistory sourceCompanyStockHistory = sendStockHistory.getCompanyStockHistory().get(0);
        CompanyStockHistory targetCompanyStockHistory = sendStockHistory.getCompanyStockHistory().get(0);
        EmployeeStockHistory employeeStockHistory = null;
        for (EmployeeStockHistory tempEmployeeStockHistory : sendStockHistory.getEmployeeStockHistory()) {
            employeeStockHistory = tempEmployeeStockHistory;
        }

        if (sendStockHistory.getIsCtoE()) {
            if (sendStockHistory.getIsProductStock()) {
                sourceCompanyStockHistory.setSendProductCount(-newSendCount);
                companyStockHistoryRepository.save(sourceCompanyStockHistory);
                companyStockService.updateCompanyProductStock(sourceCompanyStockHistory.getCompanyProductStock().getId());

                employeeStockHistory.setSendProductCount(newSendCount);
                employeeStockHistoryRepository.save(employeeStockHistory);
                employeeStockService.updateEmployeeProductStock(employeeStockHistory.getEmployeeProductStock().getId());
            } else {
                sourceCompanyStockHistory.setSendMachineCount(-newSendCount);
                companyStockHistoryRepository.save(sourceCompanyStockHistory);
                companyStockService.updateCompanyMachineStock(sourceCompanyStockHistory.getCompanyMachineStock().getId());

                employeeStockHistory.setSendMachineCount(newSendCount);
                employeeStockHistoryRepository.save(employeeStockHistory);
                employeeStockService.updateEmployeeMachineStock(employeeStockHistory.getEmployeeMachineStock().getId());
            }
        } else if (sendStockHistory.getIsCtoC()) {
            CompanyStockHistory sourceCompanyStockHistoryC;
            CompanyStockHistory targetCompanyStockHistoryC;

            if (sendStockHistory.getCompanyStockHistory().get(0).getCompanyProductStock().getCompany().getName().hashCode() == sendStockHistory.getSourceCompany().getName().hashCode()) {
                sourceCompanyStockHistoryC = sendStockHistory.getCompanyStockHistory().get(0);
                targetCompanyStockHistoryC = sendStockHistory.getCompanyStockHistory().get(1);
            } else {
                sourceCompanyStockHistoryC = sendStockHistory.getCompanyStockHistory().get(1);
                targetCompanyStockHistoryC = sendStockHistory.getCompanyStockHistory().get(0);
            }

            if (sendStockHistory.getIsProductStock()) {

                sourceCompanyStockHistoryC.setSendProductCount(-newSendCount);
                companyStockHistoryRepository.save(sourceCompanyStockHistoryC);
                companyStockService.updateCompanyProductStock(sourceCompanyStockHistoryC.getCompanyProductStock().getId());

                targetCompanyStockHistoryC.setSendProductCount(newSendCount);
                companyStockHistoryRepository.save(targetCompanyStockHistoryC);
                companyStockService.updateCompanyProductStock(targetCompanyStockHistoryC.getCompanyProductStock().getId());
            } else {

                sourceCompanyStockHistoryC.setSendMachineCount(-newSendCount);
                companyStockHistoryRepository.save(sourceCompanyStockHistoryC);
                companyStockService.updateCompanyMachineStock(sourceCompanyStockHistoryC.getCompanyMachineStock().getId());

                targetCompanyStockHistoryC.setSendMachineCount(newSendCount);
                companyStockHistoryRepository.save(targetCompanyStockHistoryC);
                companyStockService.updateCompanyMachineStock(targetCompanyStockHistoryC.getCompanyMachineStock().getId());
            }
        } else if (sendStockHistory.getIsPAtoC()) {
            targetCompanyStockHistory.setSendProductCount(newSendCount);
            companyStockHistoryRepository.save(targetCompanyStockHistory);

            companyStockService.updateCompanyProductStock(targetCompanyStockHistory.getCompanyProductStock().getId());
        } else if (sendStockHistory.getIsMtoC()) {
            targetCompanyStockHistory.setSendMachineCount(newSendCount);
            companyStockHistoryRepository.save(targetCompanyStockHistory);

            companyStockService.updateCompanyMachineStock(targetCompanyStockHistory.getCompanyMachineStock().getId());
        }
    }

    public Long[] deleteSendStockHistory(SendStockHistoryDTO sendStockHistoryDTO) {
        SendStockHistory sendStockHistory = sendStockHistoryRepository.findById(sendStockHistoryDTO.getId()).get();
        boolean isProduct = sendStockHistory.getIsProductStock();

        Long[] companyStockAndEmployeeStockId = new Long[2];

        if (sendStockHistory.getIsPAtoC()) {
            companyStockAndEmployeeStockId[0] = sendStockHistory.getCompanyStockHistory().get(0).getCompanyProductStock().getId();
        } else if (sendStockHistory.getIsCtoC()) {
            if (isProduct) {
                companyStockAndEmployeeStockId[0] = sendStockHistory.getCompanyStockHistory().get(0).getCompanyProductStock().getId();
                companyStockAndEmployeeStockId[1] = sendStockHistory.getCompanyStockHistory().get(1).getCompanyProductStock().getId();
            } else {
                companyStockAndEmployeeStockId[0] = sendStockHistory.getCompanyStockHistory().get(0).getCompanyMachineStock().getId();
                companyStockAndEmployeeStockId[1] = sendStockHistory.getCompanyStockHistory().get(1).getCompanyMachineStock().getId();
            }
        } else if (sendStockHistory.getIsCtoE()) {
            if (isProduct) {
                companyStockAndEmployeeStockId[0] = sendStockHistory.getCompanyStockHistory().get(0).getCompanyProductStock().getId();
                companyStockAndEmployeeStockId[1] = sendStockHistory.getEmployeeStockHistory().iterator().next().getEmployeeProductStock().getId();
            } else {
                companyStockAndEmployeeStockId[0] = sendStockHistory.getCompanyStockHistory().get(0).getCompanyMachineStock().getId();
                companyStockAndEmployeeStockId[1] = sendStockHistory.getEmployeeStockHistory().iterator().next().getEmployeeMachineStock().getId();
            }
        } else if (sendStockHistory.getIsMtoC()) {
            companyStockAndEmployeeStockId[0] = sendStockHistory.getCompanyStockHistory().get(0).getCompanyMachineStock().getId();
        }

        sendStockHistoryRepository.delete(sendStockHistory);

        return companyStockAndEmployeeStockId;
    }

    public void updateCompanyAndEmployeeStock(boolean isProduct, String type, Long[] companyStockAndEmployeeStockId) {

        if (type.hashCode() == "상품 입고".hashCode()) {
            companyStockService.updateCompanyProductStock(companyStockAndEmployeeStockId[0]);
        } else if (type.hashCode() == "본지 이동".hashCode()) {
            if (isProduct) {
                companyStockService.updateCompanyProductStock(companyStockAndEmployeeStockId[0]);
                companyStockService.updateCompanyProductStock(companyStockAndEmployeeStockId[1]);
            } else {
                companyStockService.updateCompanyMachineStock(companyStockAndEmployeeStockId[0]);
                companyStockService.updateCompanyMachineStock(companyStockAndEmployeeStockId[1]);
            }
        } else if (type.hashCode() == "상품 출고".hashCode()) {
            if (isProduct) {
                companyStockService.updateCompanyProductStock(companyStockAndEmployeeStockId[0]);
                employeeStockService.updateEmployeeProductStock(companyStockAndEmployeeStockId[1]);
            } else {
                companyStockService.updateCompanyMachineStock(companyStockAndEmployeeStockId[0]);
                employeeStockService.updateEmployeeMachineStock(companyStockAndEmployeeStockId[1]);
            }
        } else if (type.hashCode() == "기계 입고".hashCode()) {
            companyStockService.updateCompanyMachineStock(companyStockAndEmployeeStockId[0]);
        }
    }

    public List<SendStockHistoryDTO> getSendStockHistory(LocalDate firstDate, LocalDate lastDate) {
        List<SendStockHistory> sendStockHistoryList = sendStockHistoryRepository.findAllByDateBetweenOrderByDateDesc(firstDate, lastDate);
        List<SendStockHistoryDTO> sendStockHistoryDTOArrayList = new ArrayList<SendStockHistoryDTO>();

        sendStockHistoryList.forEach(sendStockHistory -> {
            SendStockHistoryDTO sendStockHistoryDTO = new SendStockHistoryDTO();
            sendStockHistoryDTO.setId(sendStockHistory.getId());
            sendStockHistoryDTO.setIsProduct(sendStockHistory.getIsProductStock());

            if (sendStockHistory.getIsPAtoC()) {
                sendStockHistoryDTO.setType("상품 입고");
                sendStockHistoryDTO.setSourceName(sendStockHistory.getSourcePurchaseAccount().getName());
                sendStockHistoryDTO.setTargetName(sendStockHistory.getTargetCompany().getName());
            } else if (sendStockHistory.getIsCtoC()) {
                sendStockHistoryDTO.setType("본지 이동");
                sendStockHistoryDTO.setSourceName(sendStockHistory.getSourceCompany().getName());
                sendStockHistoryDTO.setTargetName(sendStockHistory.getTargetCompany().getName());
            } else if (sendStockHistory.getIsCtoE()) {
                sendStockHistoryDTO.setType("상품 출고");
                sendStockHistoryDTO.setSourceName(sendStockHistory.getSourceCompany().getName());
                sendStockHistoryDTO.setTargetName(sendStockHistory.getTargetEmployee().getName());
            } else if (sendStockHistory.getIsMtoC()) {
                sendStockHistoryDTO.setType("기계 입고");
                sendStockHistoryDTO.setTargetName(sendStockHistory.getTargetCompany().getName());
            }

            sendStockHistoryDTO.setSendCount(sendStockHistory.getSendCount());

            if (sendStockHistory.getIsProductStock())
                sendStockHistoryDTO.setStockName(sendStockHistory.getProduct().getName());
            else
                sendStockHistoryDTO.setStockName(sendStockHistory.getMachine().getName());

            sendStockHistoryDTO.setDate(sendStockHistory.getDate());

            sendStockHistoryDTOArrayList.add(sendStockHistoryDTO);
        });

        return sendStockHistoryDTOArrayList;
    }

    public List<CompanyStockHistoryDTO> getCompanyStockMoveHistory(CompanyStockDTO companyStockDTO, LocalDate fristDate, LocalDate lastDate) {
        CompanyDTO companyDTO = companyStockDTO.getCompany();
        ProductPriceDTO productDTO = companyStockDTO.getProduct();
        MachineDTO machineDTO = companyStockDTO.getMachine();

        List<CompanyStockHistory> companyStockHistoryList;

        if (companyStockDTO.isProduct()) {
            long productId = productDTO.getId();
            companyStockHistoryList = companyStockHistoryRepository.findByCompanyProductStockId(companyDTO.getId(), productId, fristDate, lastDate);
        } else {
            long machineId = machineDTO.getId();
            companyStockHistoryList = companyStockHistoryRepository.findByCompanyMachineStockId(companyDTO.getId(), machineId, fristDate, lastDate);
        }

        List<CompanyStockHistoryDTO> companyStockHistoryDTOList = new ArrayList<CompanyStockHistoryDTO>();

        companyStockHistoryList.forEach(companyStockHistory -> {
            SendStockHistory sendStockHistory = companyStockHistory.getSendStockHistory();

            CompanyStockHistoryDTO companyStockHistoryDTO = new CompanyStockHistoryDTO();
            if (sendStockHistory == null){
                    companyStockHistoryDTO.setType("재고 관리");
                    companyStockHistoryDTO.setTargetName("관리자");
            }else {
                if (sendStockHistory.getIsPAtoC()) {
                    companyStockHistoryDTO.setType("상품 입고");
                    companyStockHistoryDTO.setTargetName(sendStockHistory.getSourcePurchaseAccount().getName());
                } else if (sendStockHistory.getIsCtoC()) {
                    companyStockHistoryDTO.setType("본지 이동");
                    companyStockHistoryDTO.setTargetName(sendStockHistory.getTargetCompany().getName());
                } else if (sendStockHistory.getIsCtoE()) {
                    companyStockHistoryDTO.setType("상품 출고");
                    companyStockHistoryDTO.setTargetName(sendStockHistory.getTargetEmployee().getName());
                } else if (sendStockHistory.getIsMtoC()) {
                    companyStockHistoryDTO.setType("기계 입고");
                    companyStockHistoryDTO.setTargetName("관리자");
                }
            }

            if (companyStockDTO.isProduct()) {
                companyStockHistoryDTO.setStockName(companyStockHistory.getCompanyProductStock().getProduct().getName());
                companyStockHistoryDTO.setSendCount(companyStockHistory.getSendProductCount());
                companyStockHistoryDTO.setCompany(companyStockHistory.getCompanyProductStock().getCompany().getName());
            } else {
                companyStockHistoryDTO.setStockName(companyStockHistory.getCompanyMachineStock().getMachine().getName());
                companyStockHistoryDTO.setSendCount(companyStockHistory.getSendMachineCount());
                companyStockHistoryDTO.setCompany(companyStockHistory.getCompanyMachineStock().getCompany().getName());
            }

            companyStockHistoryDTO.setDate(companyStockHistory.getDate());

            companyStockHistoryDTOList.add(companyStockHistoryDTO);
        });
        return companyStockHistoryDTOList;
    }

    public Long getBeforeProductStockCount(long companyId, long productId, LocalDate date) {
        return companyStockHistoryRepository.beforeCompanyProductStock(companyId, productId, date);
    }

    public Long getBeforeMachineStockCount(long companyId, long machineId, LocalDate date) {
        return companyStockHistoryRepository.beforeCompanyMachineStock(companyId, machineId, date);
    }
}
