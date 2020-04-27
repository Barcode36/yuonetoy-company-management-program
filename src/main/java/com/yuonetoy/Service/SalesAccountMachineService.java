package com.yuonetoy.Service;

import com.yuonetoy.DTO.BusinessJournalHistoryDTO;
import com.yuonetoy.DTO.SalesAccountMachineDTO;
import com.yuonetoy.DTO.SalesAccountMachineDetailDTO;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Entitiy.Product;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountMachineStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountProductStock;
import com.yuonetoy.Repository.Account.SalesAccountRepository;
import com.yuonetoy.Repository.BusinessJournalHistoryRepository;
import com.yuonetoy.Repository.SalesAccountMachineRepository;
import com.yuonetoy.Repository.Stock.SalesAccountMachineStockRepository;
import com.yuonetoy.Repository.Stock.SalesAccountProductStockRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class SalesAccountMachineService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SalesAccountMachineStockRepository salesAccountMachineStockRepository;
    @Autowired
    private SalesAccountProductStockRepository salesAccountProductStockRepository;
    @Autowired
    private SalesAccountMachineRepository salesAccountMachineRepository;

    @Transactional(readOnly = true)
    public List<SalesAccountMachineDTO> getSalesAccountMachineDTO(Long salesAccount_id) {
        SalesAccount salesAccount = salesAccountMachineRepository.findBySalesAccountId(salesAccount_id);
        List<SalesAccountMachineDTO> salesAccountDTOList = new LinkedList<SalesAccountMachineDTO>();

        salesAccount.getSalesAccountMachines().forEach(salesAccountMachine -> {
            SalesAccountMachineDTO salesAccountMachineDTO = modelMapper.map(salesAccountMachine, SalesAccountMachineDTO.class);
            salesAccountDTOList.add(salesAccountMachineDTO);
        });

        salesAccountDTOList.sort(new Comparator<SalesAccountMachineDTO>() {
            @Override
            public int compare(SalesAccountMachineDTO o1, SalesAccountMachineDTO o2) {
                return o1.getMachine().getName().compareTo(o2.getMachine().getName());
            }
        });

        return salesAccountDTOList;
    }

    @Autowired
    SalesAccountRepository salesAccountRepository;

    @Transactional(readOnly = true)
    public List<SalesAccountMachineDetailDTO> getSalesAccountMachineDetailDTO(Long salesAccount_id) {
        SalesAccount salesAccount = salesAccountRepository.findBySalesAccountId(salesAccount_id);

        List<SalesAccountMachineDetailDTO> salesAccountMachineDetailDTOS = new LinkedList<>();

        if (salesAccount == null || salesAccount.getSalesAccountMachines() == null)
            return null;

        salesAccount.getSalesAccountMachines().forEach(salesAccountMachine -> {
            SalesAccountMachineDetailDTO salesAccountMachineDetailDTO = modelMapper.map(salesAccountMachine, SalesAccountMachineDetailDTO.class);

            if (salesAccountMachine.getMachine().getIsProductMachine()) {
                Product product = salesAccountMachine.getSalesAccountProductStock().getProduct();
                salesAccountMachineDetailDTO.setProduct(product);
            }
            salesAccountMachineDetailDTOS.add(salesAccountMachineDetailDTO);
        });

        salesAccountMachineDetailDTOS.sort(new Comparator<SalesAccountMachineDetailDTO>() {
            @Override
            public int compare(SalesAccountMachineDetailDTO o1, SalesAccountMachineDetailDTO o2) {
                return o1.getMachine().getName().compareTo(o2.getMachine().getName());
            }
        });

        return salesAccountMachineDetailDTOS;
    }

    @Transactional
    public SalesAccountMachine saveSalesAccountMachine(BusinessJournalHistoryDTO businessJournalHistoryDTO) {
        Machine machine = modelMapper.map(businessJournalHistoryDTO.getSalesAccountMachine().getMachine(), Machine.class);
        SalesAccount salesAccount = modelMapper.map(businessJournalHistoryDTO.getSalesAccount(), SalesAccount.class);

        int machineCount = businessJournalHistoryDTO.getMachineCount();
        int initialQuantity = businessJournalHistoryDTO.getInitialQuantity();
        double fees = businessJournalHistoryDTO.getFees();
        int plusValue = businessJournalHistoryDTO.getPlusValue();
        int minusValue = businessJournalHistoryDTO.getMinusValue();
        int productCount = 0;

        SalesAccountMachine salesAccountMachine = salesAccountMachineRepository.findBySalesAccountIdAndMachineId(salesAccount.getId(), machine.getId());

        if (salesAccountMachine != null)
            return salesAccountMachine;

        salesAccountMachine = new SalesAccountMachine();
        //기계 정보 저장
        salesAccountMachine.setSalesAccount(salesAccount);
        salesAccountMachine.setMachine(machine);
        salesAccountMachine.setFees(fees);
        salesAccountMachine.setInitialQuantity(initialQuantity);
        salesAccountMachine.setPlusValue(plusValue);
        salesAccountMachine.setMinusValue(minusValue);

        //기계 재고 저장
        SalesAccountMachineStock salesAccountMachineStock = new SalesAccountMachineStock();
        salesAccountMachineStock.setSalesAccountMachine(salesAccountMachine);
        salesAccountMachineStock.setCount(machineCount);
        salesAccountMachineStock = salesAccountMachineStockRepository.save(salesAccountMachineStock);
        salesAccountMachine.setSalesAccountMachineStock(salesAccountMachineStock);

        if (machine.getIsProductMachine() || machine.getIsCoinChanger()) {
            Product product = modelMapper.map(businessJournalHistoryDTO.getProduct(), Product.class);
            //상품 재고 저장
            SalesAccountProductStock salesAccountProductStock = new SalesAccountProductStock();
            salesAccountProductStock.setSalesAccountMachine(salesAccountMachine);
            salesAccountProductStock.setProduct(product);
            salesAccountProductStock.setCount(productCount);
            salesAccountProductStock = salesAccountProductStockRepository.save(salesAccountProductStock);
            salesAccountMachine.setSalesAccountProductStock(salesAccountProductStock);
        }
        salesAccountMachine = salesAccountMachineRepository.save(salesAccountMachine);

        return salesAccountMachine;
    }

    @Autowired
    private BusinessJournalHistoryRepository businessJournalHistoryRepository;

    public Integer getInitialQuantity(Long salesAccountMachineId) {
        List<Integer> initialQuantityList = salesAccountMachineRepository.getInitialQuantity(salesAccountMachineId);

        return initialQuantityList.size() == 0 ? 0 : initialQuantityList.get(0);
    }

    public Double getFees(Long salesAccountMachineId) {
        List<Double> feesList = salesAccountMachineRepository.getFees(salesAccountMachineId);

        return feesList.size() == 0 ? 0.0 : feesList.get(0);
    }

    public void updateMachineInfo(BusinessJournalHistory businessJournalHistory) {
        SalesAccountMachine salesAccountMachine = businessJournalHistory.getSalesAccountMachine();

        int initialQuantity = getInitialQuantity(salesAccountMachine.getId());
        double fees = getFees(salesAccountMachine.getId());

        salesAccountMachine.setInitialQuantity(initialQuantity);
        salesAccountMachine.setFees(fees);

        salesAccountMachineRepository.save(salesAccountMachine);
    }

    public void updatePlusMinusValue(long salesAccountMachineId) {
        List<Object[]> machineInfo = businessJournalHistoryRepository.findBySalesAccountMachineId(salesAccountMachineId);

        SalesAccountMachine salesAccountMachine = machineInfo.size() == 0 ? null : (SalesAccountMachine) machineInfo.get(0)[0];

        if (salesAccountMachine == null)
            return;

        Integer plusValue = machineInfo.size() == 0 ? 0 : (int) (long) machineInfo.get(0)[1];
        Integer minusValue = machineInfo.size() == 0 ? 0 : (int) (long) machineInfo.get(0)[2];

        salesAccountMachine.setPlusValue(plusValue);
        salesAccountMachine.setMinusValue(minusValue);

        salesAccountMachineRepository.save(salesAccountMachine);
    }
}
