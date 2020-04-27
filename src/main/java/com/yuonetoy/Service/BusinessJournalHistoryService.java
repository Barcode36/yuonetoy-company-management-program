package com.yuonetoy.Service;

import com.yuonetoy.DTO.*;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDetailDTO;
import com.yuonetoy.DTO.StockHistory.SalesAccountStockHistoryDTO;
import com.yuonetoy.Entitiy.*;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeMachineStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeProductStock;
import com.yuonetoy.Entitiy.Stock.EmployeeStock.EmployeeStockHistory;
import com.yuonetoy.Repository.*;
import com.yuonetoy.Repository.Account.SalesAccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class BusinessJournalHistoryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BusinessJournalHistoryRepository businessJournalHistoryRepository;
    @Autowired
    private BusinessJournalHistoryListRepository businessJournalHistoryListRepository;
    @Autowired
    private SalesAccountRevisionHistoryRepository salesAccountRevisionHistoryRepository;
    @Autowired
    private SalesAccountMachineService salesAccountMachineService;
    @Autowired
    private EmployeeStockService employeeStockService;
    @Autowired
    private SalesAccountStockService salesAccountStockService;
    @Autowired
    private SalesAccountMachineSalesService salesAccountMachineSalesService;
    @Autowired
    private EmployeeStockHistoryService employeeStockHistoryService;
    @Autowired
    private SalesAccountRepository salesAccountRepository;

    public List<SalesAccountDTO> readSalesAccount() {
        List<SalesAccountDTO> salesAccountList = salesAccountRepository.findAllSalesAccountDTO();
        return salesAccountList;
    }

    public List<BusinessJournalHistoryDTO> findByBusinessJournalHistoryListId(long id) {
        List<BusinessJournalHistoryDTO> businessJournalHistoryList = businessJournalHistoryRepository.findByBusinessJournalHistoryListId(id);

        return businessJournalHistoryList;
    }

    public List<BusinessJournalHistoryListDTO> findListByAll(LocalDate firstDate, LocalDate lastDate, SalesAccountDTO salesAccountDTO) {
        List<BusinessJournalHistoryListDTO> businessJournalHistoryListDTOList;
        if (salesAccountDTO == null) {
            businessJournalHistoryListDTOList = businessJournalHistoryListRepository.findByAllBusinessJournalHistoryListDTO(firstDate, lastDate);
        } else {
            businessJournalHistoryListDTOList = businessJournalHistoryListRepository.findByAllBusinessJournalHistoryListDTO(firstDate, lastDate, salesAccountDTO.getId());
        }

        return businessJournalHistoryListDTOList;
    }

    private SalesAccount addSalesAccount(SalesAccountDetailDTO salesAccountDetailDTO) {
//        SalesAccountArea salesAccountArea = modelMapper.map(salesAccountDetailDTO.getSalesAccountArea(), SalesAccountArea.class);
//        CompanyType companyType = modelMapper.map(salesAccountDetailDTO.getCompanyType(), CompanyType.class);
//        SecondCompanyType secondCompanyType = salesAccountDetailDTO.getSecondCompanyType() == null ? null : modelMapper.map(salesAccountDetailDTO.getSecondCompanyType(), SecondCompanyType.class);
//        Employee employee = modelMapper.map(salesAccountDetailDTO.getEmployee(), Employee.class);
//        TaxAccount taxAccount = modelMapper.map(salesAccountDetailDTO.getTaxaccount(), TaxAccount.class);
        SalesAccount salesAccount = modelMapper.map(salesAccountDetailDTO, SalesAccount.class);
        salesAccount.setIsUsing(true);
        salesAccount = salesAccountRepository.save(salesAccount);
        return salesAccount;
    }

    //외상매출, 보충, 신규입점, 기계관리
    public SalesAccountDTO saveBusinessJournalHistoryList(List<BusinessJournalHistoryDTO> businessJournalHistoryDTOList, SalesAccountDetailDTO newSalesAccount) {
        //모두 공통된 내용이므로 0번 조회
        String type = businessJournalHistoryDTOList.get(0).getType();
        LocalDate date = businessJournalHistoryDTOList.get(0).getDate();
        SalesAccount salesAccount;

        if (newSalesAccount != null) {
            salesAccount = addSalesAccount(newSalesAccount);
        } else {
            salesAccount = modelMapper.map(businessJournalHistoryDTOList.get(0).getSalesAccount(), SalesAccount.class);
        }

        BusinessJournalHistoryList businessJournalHistoryList = new BusinessJournalHistoryList();
        businessJournalHistoryList.setSalesAccount(salesAccount);
        businessJournalHistoryList.setType(type);
        businessJournalHistoryList.setDate(date);
        businessJournalHistoryList = businessJournalHistoryListRepository.save(businessJournalHistoryList);

        for (BusinessJournalHistoryDTO businessJournalHistoryDTO : businessJournalHistoryDTOList) {

            String smallType = businessJournalHistoryDTO.getSecondType();
            SalesAccountDTO salesAccountDTO = modelMapper.map(salesAccount, SalesAccountDTO.class);
            businessJournalHistoryDTO.setSalesAccount(salesAccountDTO);
            BusinessJournalHistory businessJournalHistory = saveBusinessJournalHistory(businessJournalHistoryList, businessJournalHistoryDTO);

            if (smallType.hashCode() == "신규 입점".hashCode()) {
                long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();

                salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);
                salesAccountStockService.updateSalesAccountMachineStock(salesAccountMachineId);

                employeeStockHistoryService.saveEmployeeStockHistory(businessJournalHistory);

                boolean isProductMachine = businessJournalHistory.getSalesAccountMachine().getMachine().getIsProductMachine();
                boolean isCoinChanger = businessJournalHistory.getSalesAccountMachine().getMachine().getIsCoinChanger();
                if (isProductMachine || isCoinChanger) {
                    long employeeProductStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeProductStock().getId();
                    employeeStockService.updateEmployeeProductStock(employeeProductStockId);
                }

                long employeeMachineStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeMachineStock().getId();
                employeeStockService.updateEmployeeMachineStock(employeeMachineStockId);

                newMachineRevisionHistory(businessJournalHistory, salesAccount, date);
            } else if (smallType.hashCode() == "재입점".hashCode()) {
                Long salesAccountId = businessJournalHistoryList.getSalesAccount().getId();
                salesAccount = salesAccountRepository.findById(salesAccountId).get();
                salesAccount.setIsUsing(true);
                salesAccount.setWithdrawalDate(null);
                salesAccountRepository.save(salesAccount);

                long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();

                salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);
                salesAccountStockService.updateSalesAccountMachineStock(salesAccountMachineId);

                employeeStockHistoryService.saveEmployeeStockHistory(businessJournalHistory);

                boolean isProductMachine = businessJournalHistory.getSalesAccountMachine().getMachine().getIsProductMachine();
                boolean isCoinChanger = businessJournalHistory.getSalesAccountMachine().getMachine().getIsCoinChanger();
                if (isProductMachine || isCoinChanger) {
                    long employeeProductStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeProductStock().getId();
                    employeeStockService.updateEmployeeProductStock(employeeProductStockId);
                }

                long employeeMachineStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeMachineStock().getId();
                employeeStockService.updateEmployeeMachineStock(employeeMachineStockId);

                updateMachineRevisionHistory(businessJournalHistory, salesAccount, date);
            } else if (smallType.hashCode() == "기계 보충".hashCode()) {
                long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();

                salesAccountMachineService.updateMachineInfo(businessJournalHistory);
                salesAccountMachineService.updatePlusMinusValue(businessJournalHistory.getSalesAccountMachine().getId());

                salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);
                salesAccountStockService.updateSalesAccountMachineStock(salesAccountMachineId);

                employeeStockHistoryService.saveEmployeeStockHistory(businessJournalHistory);

                boolean isProductMachine = businessJournalHistory.getSalesAccountMachine().getMachine().getIsProductMachine();
                boolean isCoinChanger = businessJournalHistory.getSalesAccountMachine().getMachine().getIsCoinChanger();
                if (isProductMachine || isCoinChanger) {
                    long employeeProductStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeProductStock().getId();
                    employeeStockService.updateEmployeeProductStock(employeeProductStockId);
                }

                long employeeMachineStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeMachineStock().getId();
                employeeStockService.updateEmployeeMachineStock(employeeMachineStockId);

                updateMachineRevisionHistory(businessJournalHistory, salesAccount, date);
            } else if (smallType.hashCode() == "철수".hashCode()) {
                Long salesAccountId = businessJournalHistoryList.getSalesAccount().getId();
                salesAccount = salesAccountRepository.findById(salesAccountId).get();
                salesAccount.setIsUsing(false);
                salesAccount.setWithdrawalDate(date);
                salesAccountRepository.save(salesAccount);

                long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();

                salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);
                salesAccountStockService.updateSalesAccountMachineStock(salesAccountMachineId);

                employeeStockHistoryService.saveEmployeeStockHistory(businessJournalHistory);

                long employeeMachineStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeMachineStock().getId();
                employeeStockService.updateEmployeeMachineStock(employeeMachineStockId);

                boolean isProductMachine = businessJournalHistory.getSalesAccountMachine().getMachine().getIsProductMachine();
                boolean isCoinChanger = businessJournalHistory.getSalesAccountMachine().getMachine().getIsCoinChanger();
                if (isProductMachine || isCoinChanger) {
                    long employeeProductStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeProductStock().getId();
                    employeeStockService.updateEmployeeProductStock(employeeProductStockId);
                }

                updateMachineRevisionHistory(businessJournalHistory, salesAccount, date);
            } else if (smallType.hashCode() == "기계 관리".hashCode()) {
                long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();
                salesAccountMachineService.updateMachineInfo(businessJournalHistory);

                salesAccountStockService.updateSalesAccountMachineStock(salesAccountMachineId);

                employeeStockHistoryService.saveEmployeeStockHistory(businessJournalHistory);

                long employeeMachineStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeMachineStock().getId();
                employeeStockService.updateEmployeeMachineStock(employeeMachineStockId);

                updateMachineRevisionHistory(businessJournalHistory, salesAccount, date);
            } else if (smallType.hashCode() == "상품 보충".hashCode()) {
                salesAccountMachineService.updatePlusMinusValue(businessJournalHistory.getSalesAccountMachine().getId());

                long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();
                salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);

                boolean isProductMachine = businessJournalHistory.getSalesAccountMachine().getMachine().getIsProductMachine();
                boolean isCoinChanger = businessJournalHistory.getSalesAccountMachine().getMachine().getIsCoinChanger();
                if (isProductMachine || isCoinChanger) {
                    employeeStockHistoryService.saveEmployeeStockHistory(businessJournalHistory);
                    long employeeProductStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeProductStock().getId();
                    employeeStockService.updateEmployeeProductStock(employeeProductStockId);
                }
            } else if (smallType.hashCode() == "외상 매출".hashCode()) {
                long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();
                salesAccountMachineSalesService.saveSalesInfo(businessJournalHistory);
                salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);
            }
        }
        SalesAccountDTO salesAccountDTO = modelMapper.map(salesAccount, SalesAccountDTO.class);
        return salesAccountDTO;
    }

    private BusinessJournalHistory saveBusinessJournalHistory(BusinessJournalHistoryList businessJournalHistoryList, BusinessJournalHistoryDTO businessJournalHistoryDTO) {
        int secondTypeHash = businessJournalHistoryDTO.getSecondType().hashCode();


        // 기계가 처음 설치 된게 아닐때
        BusinessJournalHistory businessJournalHistory = modelMapper.map(businessJournalHistoryDTO, BusinessJournalHistory.class);
        businessJournalHistory.setType(businessJournalHistoryDTO.getSecondType());
        businessJournalHistory.setBusinessJournalHistoryList(businessJournalHistoryList);

        SalesAccountMachine salesAccountMachine = salesAccountMachineService.saveSalesAccountMachine(businessJournalHistoryDTO);
        businessJournalHistory.setSalesAccountMachine(salesAccountMachine);

        businessJournalHistory = businessJournalHistoryRepository.save(businessJournalHistory);

        return businessJournalHistory;
    }

    public void updateBusinessJournalHistory(BusinessJournalHistoryDTO businessJournalHistoryDTO) {
        long businessJournalHistoryId = businessJournalHistoryDTO.getId();
        BusinessJournalHistory businessJournalHistory = businessJournalHistoryRepository.findById(businessJournalHistoryId).get();
        BusinessJournalHistoryList businessJournalHistoryList = businessJournalHistory.getBusinessJournalHistoryList();
        int newMachineCount = businessJournalHistoryDTO.getMachineCount();
        int newInitialQuantity = businessJournalHistoryDTO.getInitialQuantity();
        double newFees = businessJournalHistoryDTO.getFees();
        int newSupplyCount = businessJournalHistoryDTO.getSupplyCount();
        int newPlusValue = businessJournalHistoryDTO.getPlusValue();
        int newMinusValue = businessJournalHistoryDTO.getMinusValue();
        long newSalesValue = businessJournalHistoryDTO.getSalesValue();
        long newRefundValue = businessJournalHistoryDTO.getRefundValue();
        boolean isProductMachine = businessJournalHistory.getSalesAccountMachine().getMachine().getIsProductMachine() || businessJournalHistory.getSalesAccountMachine().getMachine().getIsCoinChanger();

        if (businessJournalHistoryList.getType().hashCode() == "외상 매출".hashCode() && isProductMachine) {
            int productPrice = businessJournalHistory.getProduct().getPrice();
            businessJournalHistory.setSupplyCount((int) -((newSalesValue + newRefundValue) / productPrice));
        } else {
            businessJournalHistory.setSupplyCount(newSupplyCount);
        }

        businessJournalHistory.setMachineCount(newMachineCount);
        businessJournalHistory.setInitialQuantity(newInitialQuantity);
        businessJournalHistory.setFees(newFees);
        businessJournalHistory.setPlusValue(newPlusValue);
        businessJournalHistory.setMinusValue(newMinusValue);
        businessJournalHistory.setSalesValue(newSalesValue);
        businessJournalHistory.setRefundValue(newRefundValue);
        businessJournalHistory = businessJournalHistoryRepository.save(businessJournalHistory);

        List<SalesAccountRevisionHistory> salesAccountRevisionHistories = salesAccountRevisionHistoryRepository.findByBusinessJournalHistoryId(businessJournalHistoryId);

        for (SalesAccountRevisionHistory salesAccountRevisionHistory : salesAccountRevisionHistories) {
            if (salesAccountRevisionHistory.getMachine().getId() == businessJournalHistory.getSalesAccountMachine().getMachine().getId()) {
                if (salesAccountRevisionHistory.getType().contains("수수료")) {
                    salesAccountRevisionHistory.setContents("[" + salesAccountRevisionHistory.getMachine().getName() + "] " + newFees + "%로 " + salesAccountRevisionHistory.getType());

                } else if (salesAccountRevisionHistory.getType().contains("초기수량")) {
                    salesAccountRevisionHistory.setContents("[" + salesAccountRevisionHistory.getMachine().getName() + "] " + newInitialQuantity + "개로 " + salesAccountRevisionHistory.getType());
                } else {
                    salesAccountRevisionHistory.setContents("[" + salesAccountRevisionHistory.getMachine().getName() + "] " + newMachineCount + "대 " + salesAccountRevisionHistory.getType());
                }
                salesAccountRevisionHistoryRepository.save(salesAccountRevisionHistory);
            }
        }

        String type = businessJournalHistory.getType();
        if (type.hashCode() == "신규 입점".hashCode() || type.hashCode() == "재입점".hashCode() || type.hashCode() == "기계 관리".hashCode() || type.hashCode() == "철수".hashCode()) {
            long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();

            salesAccountMachineService.updateMachineInfo(businessJournalHistory);
            salesAccountMachineService.updatePlusMinusValue(businessJournalHistory.getSalesAccountMachine().getId());

            salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);
            salesAccountStockService.updateSalesAccountMachineStock(salesAccountMachineId);

            employeeStockHistoryService.updateEmployeeStockHistory(businessJournalHistory);

            if (isProductMachine) {
                long employeeProductStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeProductStock().getId();
                employeeStockService.updateEmployeeProductStock(employeeProductStockId);
            }

            long employeeMachineStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeMachineStock().getId();
            employeeStockService.updateEmployeeMachineStock(employeeMachineStockId);

        } else if (type.hashCode() == "상품 보충".hashCode()) {
            salesAccountMachineService.updatePlusMinusValue(businessJournalHistory.getSalesAccountMachine().getId());

            long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();
            salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);

            if (isProductMachine) {
                employeeStockHistoryService.updateEmployeeStockHistory(businessJournalHistory);
                long employeeProductStockId = businessJournalHistory.getEmployeeStockHistory().iterator().next().getEmployeeProductStock().getId();
                employeeStockService.updateEmployeeProductStock(employeeProductStockId);
            }
        } else if (type.hashCode() == "외상 매출".hashCode()) {
            long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();
            salesAccountMachineSalesService.updateSalesInfo(businessJournalHistory);
            salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);
        } else if (type.hashCode() == "재고 관리".hashCode()) {
            long salesAccountMachineId = businessJournalHistory.getSalesAccountMachine().getId();
            salesAccountStockService.updateSalesAccountMachineStock(salesAccountMachineId);
            salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);
        }
    }

    public List<Long[]> deleteBusinessJournalHistory(long businessJournalHistoryListId) {
        BusinessJournalHistoryList businessJournalHistoryList = businessJournalHistoryListRepository.findByBusinessJournalHistoryId(businessJournalHistoryListId);
        List<BusinessJournalHistory> businessJournalHistories = businessJournalHistoryList.getBusinessJournalHistory();

        List<Long[]> employeeStockAndSalesAccountStockIdList = new LinkedList<>();

        for (BusinessJournalHistory businessJournalHistory : businessJournalHistories) {

            Long[] employeeStockAndSalesAccountStockId = new Long[3];

            employeeStockAndSalesAccountStockId[0] = businessJournalHistory.getSalesAccountMachine().getId();

            if (businessJournalHistory.getEmployeeStockHistory().size() != 0) {
                EmployeeStockHistory employeeStockHistory = businessJournalHistory.getEmployeeStockHistory().iterator().next();

                EmployeeProductStock employeeProductStock = employeeStockHistory.getEmployeeProductStock();
                if (employeeProductStock != null) {
                    employeeStockAndSalesAccountStockId[1] = employeeProductStock.getId();
                }
                EmployeeMachineStock employeeMachineStock = employeeStockHistory.getEmployeeMachineStock();
                if (employeeMachineStock != null) {
                    employeeStockAndSalesAccountStockId[2] = employeeMachineStock.getId();
                }
            }
            employeeStockAndSalesAccountStockIdList.add(employeeStockAndSalesAccountStockId);

            List<SalesAccountRevisionHistory> salesAccountRevisionHistories = salesAccountRevisionHistoryRepository.findByBusinessJournalHistoryId(businessJournalHistory.getId());
            for (SalesAccountRevisionHistory salesAccountRevisionHistory : salesAccountRevisionHistories) {
                salesAccountRevisionHistoryRepository.delete(salesAccountRevisionHistory);
            }
        }

        LocalDate withdrawalDate = null;
        if (businessJournalHistoryList.getType().hashCode() == "재입점".hashCode()) {
            List<LocalDate> localDateList = businessJournalHistoryRepository.findBySalesAccountWithdrawalDate(businessJournalHistoryList.getSalesAccount().getId(), "철수");
            if (!localDateList.isEmpty())
                withdrawalDate = localDateList.get(0);
        }

        businessJournalHistoryListRepository.delete(businessJournalHistoryList);


        if (businessJournalHistoryList.getType().hashCode() == "철수".hashCode()) {
            SalesAccount salesAccount = businessJournalHistoryList.getSalesAccount();
            salesAccount.setIsUsing(true);
            salesAccount.setWithdrawalDate(null);
            salesAccountRepository.save(salesAccount);

        } else if (businessJournalHistoryList.getType().hashCode() == "신규 입점".hashCode()) {
            SalesAccount salesAccount = businessJournalHistoryList.getSalesAccount();

            salesAccountRepository.delete(salesAccount);
        } else if (businessJournalHistoryList.getType().hashCode() == "기계 관리".hashCode()) {
            for (BusinessJournalHistory businessJournalHistory : businessJournalHistories) {
                salesAccountMachineService.updateMachineInfo(businessJournalHistory);
            }
        } else if (businessJournalHistoryList.getType().hashCode() == "재입점".hashCode()) {
            SalesAccount salesAccount = businessJournalHistoryList.getSalesAccount();
            salesAccount.setIsUsing(false);
            salesAccount.setWithdrawalDate(withdrawalDate);
            salesAccountRepository.save(salesAccount);

            for (BusinessJournalHistory businessJournalHistory : businessJournalHistories) {
                salesAccountMachineService.updateMachineInfo(businessJournalHistory);
            }
        }

        return employeeStockAndSalesAccountStockIdList;
    }

    public void updateDeletedStock(List<Long[]> employeeStockAndSalesAccountStockIdList) {
        // 0번이 salesAccountMachineId, 1번이 updateEmployeeProductStock, 2번 updateEmployeeMachineStock
        for (Long[] longs : employeeStockAndSalesAccountStockIdList) {

            Long salesAccountMachineId = longs[0];

            salesAccountMachineService.updatePlusMinusValue(salesAccountMachineId);

            salesAccountStockService.updateSalesAccountProductStock(salesAccountMachineId);
            salesAccountStockService.updateSalesAccountMachineStock(salesAccountMachineId);

            if (longs[1] != null)
                employeeStockService.updateEmployeeProductStock(longs[1]);
            if (longs[2] != null)
                employeeStockService.updateEmployeeMachineStock(longs[2]);
        }
    }

    public void newMachineRevisionHistory(BusinessJournalHistory businessJournalHistory, SalesAccount salesAccount, LocalDate date) {
        Machine machine = businessJournalHistory.getSalesAccountMachine().getMachine();
        String machineName = businessJournalHistory.getSalesAccountMachine().getMachine().getName();
        int newCountValue = businessJournalHistory.getMachineCount();
        double newFeesValue = businessJournalHistory.getFees();
        int newInitiQuantityCountValue = businessJournalHistory.getInitialQuantity();

        String type = null;
        String contents = null;

        SalesAccountRevisionHistory salesAccountRevisionHistory = null;

        type = "신규 입점";
        contents = "[" + machineName + "] " + newCountValue + "대 " + type;

        //기계 입점 기록 등록
        salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
        salesAccountRevisionHistoryRepository.save(salesAccountRevisionHistory);

        type = "초기수량 등록";
        contents = "[" + machineName + "] " + newInitiQuantityCountValue + "개로 " + type;

        //기계 상품 재고 등록
        if (machine.getIsProductMachine() || machine.getIsCoinChanger()) {
            //기계 입점 기록 등록
            salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
            salesAccountRevisionHistoryRepository.save(salesAccountRevisionHistory);
        }

        type = "수수료 등록";
        contents = "[" + machineName + "] " + newFeesValue + "%로 " + type;

        //기계 입점 기록 등록
        salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
        salesAccountRevisionHistoryRepository.save(salesAccountRevisionHistory);
    }

    private void updateMachineRevisionHistory(BusinessJournalHistory businessJournalHistory, SalesAccount salesAccount, LocalDate date) {
        ArrayList<SalesAccountRevisionHistory> salesAccountRevisionHistories = new ArrayList<SalesAccountRevisionHistory>();
        Machine machine = businessJournalHistory.getSalesAccountMachine().getMachine();
        String machineName = businessJournalHistory.getSalesAccountMachine().getMachine().getName();

        int countValue = businessJournalHistory.getMachineCount();
        double feesValue = businessJournalHistory.getFees();
        int initiQuantityCountValue = businessJournalHistory.getInitialQuantity();

        String type = null;
        String contents = null;

        SalesAccountRevisionHistory salesAccountRevisionHistory = null;

        // 이전 기계 값이 0이면 새로 추가 0이 아니면 변경
        if (businessJournalHistory.getType().hashCode() == "기계 관리".hashCode()) {
            // 기계 대수에 대한 기록 저장
            if (countValue > 0) {
                type = "보충";
                contents = "[" + machineName + "] " + countValue + "대 " + type;

                salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
                salesAccountRevisionHistories.add(salesAccountRevisionHistory);
            } else if (countValue < 0) {
                if (countValue == 0) {
                    type = "철수";
                    contents = "[" + machineName + "] " + countValue + "대 " + type;

                } else {
                    type = "감소";
                    contents = "[" + machineName + "] " + countValue + "대 " + type;

                }
                salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
                salesAccountRevisionHistories.add(salesAccountRevisionHistory);
            }

            if (initiQuantityCountValue != 0) {
                type = "초기수량 변경";
                contents = "[" + machineName + "] " + initiQuantityCountValue + "개로 " + type;

                if (machine.getIsProductMachine() || machine.getIsCoinChanger()) {
                    salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
                    salesAccountRevisionHistories.add(salesAccountRevisionHistory);
                }
            }

            if (feesValue != 0) {
                type = "수수료 변경";
                contents = "[" + machineName + "] " + feesValue + "%로 " + type;

                salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
                salesAccountRevisionHistories.add(salesAccountRevisionHistory);
            }
        } else if (businessJournalHistory.getType().hashCode() == "철수".hashCode()) {
            type = "철수";
            contents = "[" + machineName + "] " + countValue + "대 " + type;

            salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
            salesAccountRevisionHistories.add(salesAccountRevisionHistory);
        } else {
            type = "신규 보충";
            contents = "[" + machineName + "] " + countValue + "대 " + type;

            //기계 입점 기록 등록
            salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
            salesAccountRevisionHistories.add(salesAccountRevisionHistory);

            type = "초기수량 등록";
            contents = "[" + machineName + "] " + initiQuantityCountValue + "개로 " + type;

            //기계 상품 재고 등록
            if (machine.getIsProductMachine() || machine.getIsCoinChanger()) {
                //기계 입점 기록 등록
                salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
                salesAccountRevisionHistories.add(salesAccountRevisionHistory);
            }

            type = "수수료 등록";
            contents = "[" + machineName + "] " + feesValue + "%로 " + type;

            //기계 입점 기록 등록
            salesAccountRevisionHistory = new SalesAccountRevisionHistory(null, salesAccount, machine, date, type, contents, businessJournalHistory);
            salesAccountRevisionHistories.add(salesAccountRevisionHistory);
        }

        //기계 수정 기록 등록
        salesAccountRevisionHistories.forEach(newSalesAccountRevisionHistory -> {
            salesAccountRevisionHistoryRepository.save(newSalesAccountRevisionHistory);
        });
    }


    public List<SalesAccountStockHistoryDTO> getSalesAccountStockMoveHistory(long salesAccountId, LocalDate firstDate, LocalDate lastDate) {
        return businessJournalHistoryRepository.getSalesAccountStockMoveHistory(salesAccountId, firstDate, lastDate);
    }

    public List<Object[]> getBeforeStockCount(long salesAccountId, LocalDate firstDate) {
        return businessJournalHistoryRepository.getBeforeStock(salesAccountId, firstDate);
    }
}
