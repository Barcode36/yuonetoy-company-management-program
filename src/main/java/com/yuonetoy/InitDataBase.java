package com.yuonetoy;

import com.yuonetoy.Entitiy.*;
import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Account.TaxAccount;
import com.yuonetoy.Entitiy.Area.SalesAccountArea;
import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import com.yuonetoy.Entitiy.CompanyType.SecondCompanyType;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachine;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountMachineStock;
import com.yuonetoy.Entitiy.Stock.SalesAccountStock.SalesAccountProductStock;
import com.yuonetoy.Model.DepositType;
import com.yuonetoy.Repository.*;
import com.yuonetoy.Repository.Account.PurchaseAccountRepository;
import com.yuonetoy.Repository.Account.SalesAccountRepository;
import com.yuonetoy.Repository.Account.TaxAccountRepository;
import com.yuonetoy.Repository.Area.SalesAccountAreaRepository;
import com.yuonetoy.Repository.CompanyType.CompanyTypeRepository;
import com.yuonetoy.Repository.CompanyType.SecondCompanyTypeRepository;
import com.yuonetoy.Repository.Stock.SalesAccountMachineStockRepository;
import com.yuonetoy.Repository.Stock.SalesAccountProductStockRepository;
import com.yuonetoy.Service.BusinessJournalHistoryService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Hashtable;

@Component
public class InitDataBase {
    @Autowired
    private CompanyTypeRepository companyTypeRepository;
    @Autowired
    private SecondCompanyTypeRepository secondCompanyTypeRepository;
    @Autowired
    private SalesAccountAreaRepository salesAccountAreaRepository;
    @Autowired
    private SalesAccountMachineRepository salesAccountMachineRepository;
    @Autowired
    private SalesAccountMachineStockRepository salesAccountMachineStockRepository;
    @Autowired
    private SalesAccountProductStockRepository salesAccountProductStockRepository;
    @Autowired
    private SalesAccountRepository salesAccountRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TaxAccountRepository taxAccountRepository;
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BusinessJournalHistoryListRepository businessJournalHistoryListRepository;
    @Autowired
    private BusinessJournalHistoryRepository businessJournalHistoryRepository;
    @Autowired
    private PurchaseAccountRepository purchaseAccountRepository;


    static HashSet<String> companyTypes = new HashSet<String>();
    static Hashtable<String, String> secondCompanyTypes = new Hashtable<>();
    static HashSet<String> areas = new HashSet<String>();
    static HashSet<String> employees = new HashSet<String>();
    static HashSet<String> taxAccounts = new HashSet<String>();

    static final int companyTypeIndex = 1;
    static final int secondCompanyTypeIndex = 2;
    static final int areaIndex = 3;
    static final int salesAccountIndex = 4;
    static final int entryDateIndex = 5;
    static final int machineCountIndex = 6;
    static final int initialQuantityIndex = 7;
    static final int representativeIndex = 8;
    static final int phIndex = 9;
    static final int addressIndex = 10;
    static final int employeeIndex = 11;
    static final int feeIndex = 14;
    static final int coinChangerMoneyIndex = 16;
    static final int isYuoneToyIndex = 18;
    static final int taxAccountIndex = 19;
    static final int taxBillTypeIndex = 20;
    static final int depositTypeIndex = 21;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ProductLinkedMachineRepository productLinkedMachineRepository;

    public void initDateBase() {
        initCompany();
        initProduct();
        initMachine();
    }

    private void initCompany() {
        Company deagu = companyRepository.findByName("대구 본점");
        if (deagu == null) {
            deagu = new Company();
            deagu.setName("대구 본점");
            deagu.setAddress("대구시");

            companyRepository.save(deagu);
        }

        Company siheung = companyRepository.findByName("시흥 지점");
        if (siheung == null) {
            siheung = new Company();
            siheung.setName("시흥 지점");
            siheung.setAddress("시흥시");

            companyRepository.save(siheung);
        }

        Company deajun = companyRepository.findByName("대전 지점");
        if (deajun == null) {
            deajun = new Company();
            deajun.setName("대전 지점");
            deajun.setAddress("대전시");

            companyRepository.save(deajun);
        }
    }

    private void initProduct() {
        Product capsuleToy = productRepository.findByNameEquals("캡슐 토이");
        if (capsuleToy == null) {
            capsuleToy = new Product();
            capsuleToy.setName("캡슐 토이");
            capsuleToy.setIsCapsuleToy(true);
            capsuleToy.setPrice(1000);

            productRepository.save(capsuleToy);
        }

        Product coinChangerMoney = productRepository.findByNameEquals("시재금");
        if (coinChangerMoney == null) {
            coinChangerMoney = new Product();
            coinChangerMoney.setName("시재금");
            coinChangerMoney.setIsCapsuleToy(false);
            coinChangerMoney.setPrice(1);

            productRepository.save(coinChangerMoney);
        }

        Product product1 = productRepository.findByNameEquals("인형");
        if (product1 == null) {
            product1 = new Product();
            product1.setName("인형");
            product1.setIsCapsuleToy(false);
            product1.setPrice(6000);

            productRepository.save(product1);
        }

        Product product2 = productRepository.findByNameEquals("사탕");
        if (product2 == null) {
            product2 = new Product();
            product2.setName("사탕");
            product2.setIsCapsuleToy(false);
            product2.setPrice(500);

            productRepository.save(product2);
        }

        Product product3 = productRepository.findByNameEquals("멘토스");
        if (product3 == null) {
            product3 = new Product();
            product3.setName("멘토스");
            product3.setIsCapsuleToy(false);
            product3.setPrice(500);

            productRepository.save(product3);
        }

        Product product4 = productRepository.findByNameEquals("먹이 캡슐");
        if (product4 == null) {
            product4 = new Product();
            product4.setName("먹이 캡슐");
            product4.setIsCapsuleToy(false);
            product4.setPrice(1000);

            productRepository.save(product4);
        }

        Product product5 = productRepository.findByNameEquals("라바 탱탱볼");
        if (product5 == null) {
            product5 = new Product();
            product5.setName("라바 탱탱볼");
            product5.setIsCapsuleToy(false);
            product5.setPrice(1000);

            productRepository.save(product5);
        }
    }

    private void initMachine() {
        Machine capsuleMachine1 = machineRepository.findByName("1단 자판기");
        if (capsuleMachine1 == null) {
            capsuleMachine1 = new Machine();
            capsuleMachine1.setName("1단 자판기");
            capsuleMachine1.setIsCoinChanger(false);
            capsuleMachine1.setIsProductMachine(true);
            machineRepository.save(capsuleMachine1);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("캡슐 토이");
            productLinkedMachine.setMachine(capsuleMachine1);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine capsuleMachine2 = machineRepository.findByName("3단 자판기");
        if (capsuleMachine2 == null) {
            capsuleMachine2 = new Machine();
            capsuleMachine2.setName("2단 자판기");
            capsuleMachine2.setIsCoinChanger(false);
            capsuleMachine2.setIsProductMachine(true);
            machineRepository.save(capsuleMachine2);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("캡슐 토이");
            productLinkedMachine.setMachine(capsuleMachine2);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine capsuleMachine3 = machineRepository.findByName("3단 자판기");
        if (capsuleMachine3 == null) {
            capsuleMachine3 = new Machine();
            capsuleMachine3.setName("3단 자판기");
            capsuleMachine3.setIsCoinChanger(false);
            capsuleMachine3.setIsProductMachine(true);
            machineRepository.save(capsuleMachine3);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("캡슐 토이");
            productLinkedMachine.setMachine(capsuleMachine3);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine capsuleMachineMulti = machineRepository.findByName("멀티 자판기");
        if (capsuleMachineMulti == null) {
            capsuleMachineMulti = new Machine();
            capsuleMachineMulti.setName("멀티 자판기");
            capsuleMachineMulti.setIsCoinChanger(false);
            capsuleMachineMulti.setIsProductMachine(true);
            machineRepository.save(capsuleMachineMulti);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("캡슐 토이");
            productLinkedMachine.setMachine(capsuleMachineMulti);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine machine1 = machineRepository.findByName("인형 뽑기");
        if (machine1 == null) {
            machine1 = new Machine();
            machine1.setName("인형 뽑기");
            machine1.setIsCoinChanger(false);
            machine1.setIsProductMachine(true);
            machineRepository.save(machine1);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("인형");
            productLinkedMachine.setMachine(machine1);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine machine2 = machineRepository.findByName("라바 건 게임기");
        if (machine2 == null) {
            machine2 = new Machine();
            machine2.setName("라바 건 게임기");
            machine2.setIsCoinChanger(false);
            machine2.setIsProductMachine(true);
            machineRepository.save(machine2);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("라바 탱탱볼");
            productLinkedMachine.setMachine(machine2);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine machine3 = machineRepository.findByName("다이노코어 라이더");
        if (machine3 == null) {
            machine3 = new Machine();
            machine3.setName("다이노코어 라이더");
            machine3.setIsCoinChanger(false);
            machine3.setIsProductMachine(false);
            machineRepository.save(machine3);
        }

        Machine machine4 = machineRepository.findByName("콩순이 라이더");
        if (machine4 == null) {
            machine4 = new Machine();
            machine4.setName("콩순이 라이더");
            machine4.setIsCoinChanger(false);
            machine4.setIsProductMachine(false);
            machineRepository.save(machine4);
        }

        Machine machine5 = machineRepository.findByName("곰탱이 자판기");
        if (machine5 == null) {
            machine5 = new Machine();
            machine5.setName("곰탱이 자판기");
            machine5.setIsCoinChanger(false);
            machine5.setIsProductMachine(true);
            machineRepository.save(machine5);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("캡슐 토이");
            productLinkedMachine.setMachine(machine5);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine machine6 = machineRepository.findByName("사탕 자판기");
        if (machine6 == null) {
            machine6 = new Machine();
            machine6.setName("사탕 자판기");
            machine6.setIsCoinChanger(false);
            machine6.setIsProductMachine(true);
            machineRepository.save(machine6);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("사탕");
            productLinkedMachine.setMachine(machine6);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine machine7 = machineRepository.findByName("멘토스 자판기");
        if (machine7 == null) {
            machine7 = new Machine();
            machine7.setName("곰탱이 자판기");
            machine7.setIsCoinChanger(false);
            machine7.setIsProductMachine(true);
            machineRepository.save(machine7);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("멘토스");
            productLinkedMachine.setMachine(machine7);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine machine8 = machineRepository.findByName("토이랜드 게임기");
        if (machine8 == null) {
            machine8 = new Machine();
            machine8.setName("토이랜드 게임기");
            machine8.setIsCoinChanger(false);
            machine8.setIsProductMachine(false);
            machineRepository.save(machine8);
        }

        Machine machine9 = machineRepository.findByName("핀볼 게임기");
        if (machine9 == null) {
            machine9 = new Machine();
            machine9.setName("핀볼 게임기");
            machine9.setIsCoinChanger(false);
            machine9.setIsProductMachine(true);
            machineRepository.save(machine9);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("캡슐 토이");
            productLinkedMachine.setMachine(machine9);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine machine10 = machineRepository.findByName("손금 게임기");
        if (machine10 == null) {
            machine10 = new Machine();
            machine10.setName("손금 게임기");
            machine10.setIsCoinChanger(false);
            machine10.setIsProductMachine(false);
            machineRepository.save(machine10);
        }

        Machine machine11 = machineRepository.findByName("두더지 게임기");
        if (machine11 == null) {
            machine11 = new Machine();
            machine11.setName("두더지 게임기");
            machine11.setIsCoinChanger(false);
            machine11.setIsProductMachine(false);
            machineRepository.save(machine11);
        }

        Machine machine12 = machineRepository.findByName("먹이 자판기");
        if (machine12 == null) {
            machine12 = new Machine();
            machine12.setName("먹이 자판기");
            machine12.setIsCoinChanger(false);
            machine12.setIsProductMachine(false);
            machineRepository.save(machine12);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product capsuleToy = productRepository.findByNameEquals("먹이 캡슐");
            productLinkedMachine.setMachine(machine12);
            productLinkedMachine.setProduct(capsuleToy);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine coinChanger = machineRepository.findByName("동전 교환기");
        if (coinChanger == null) {
            coinChanger = new Machine();
            coinChanger.setName("동전 교환기");
            coinChanger.setIsCoinChanger(true);
            coinChanger.setIsProductMachine(false);
            machineRepository.save(coinChanger);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product coinChangerMoney = productRepository.findByNameEquals("시재금");
            productLinkedMachine.setMachine(coinChanger);
            productLinkedMachine.setProduct(coinChangerMoney);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine coinChanger2 = machineRepository.findByName("동전,지폐 교환기");
        if (coinChanger2 == null) {
            coinChanger2 = new Machine();
            coinChanger2.setName("동전,지폐 교환기");
            coinChanger2.setIsCoinChanger(true);
            coinChanger2.setIsProductMachine(false);
            machineRepository.save(coinChanger2);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product coinChangerMoney = productRepository.findByNameEquals("시재금");
            productLinkedMachine.setMachine(coinChanger2);
            productLinkedMachine.setProduct(coinChangerMoney);

            productLinkedMachineRepository.save(productLinkedMachine);
        }

        Machine coinChanger3 = machineRepository.findByName("지폐 교환기");
        if (coinChanger3 == null) {
            coinChanger3 = new Machine();
            coinChanger3.setName("지폐 교환기");
            coinChanger3.setIsCoinChanger(true);
            coinChanger3.setIsProductMachine(false);
            machineRepository.save(coinChanger3);

            ProductLinkedMachine productLinkedMachine = new ProductLinkedMachine();

            Product coinChangerMoney = productRepository.findByNameEquals("시재금");
            productLinkedMachine.setMachine(coinChanger3);
            productLinkedMachine.setProduct(coinChangerMoney);

            productLinkedMachineRepository.save(productLinkedMachine);
        }
    }

    public void initDataBase() {
        initDateBase();

        Workbook workbook = getWorkbook("src/main/resources/backup/accountBackup.xlsx", "xlsx");
        Sheet sheet = workbook.getSheetAt(0);
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveETC(sheet);
        saveSalesAccount(sheet);

        workbook = getWorkbook("src/main/resources/backup/productBackup.xlsx", "xlsx");
        sheet = workbook.getSheetAt(0);
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveProduct(sheet);
    }

    private void saveProduct(Sheet sheet) {
        final int rowValue = sheet.getLastRowNum();

        PurchaseAccount purchaseAccount = purchaseAccountRepository.findByName("지정 안됨");
        if (purchaseAccount == null)
            purchaseAccount = savePurchaseAccount();

        for (int i = 1; i < rowValue; i++) {
            Product product = productRepository.findByNameEquals(getCell(sheet, i, 0).getStringCellValue());
            if (product == null) {
                product = new Product();
                product.setName(getCell(sheet, i, 0).getStringCellValue());
                product.setPrice((int) getCell(sheet, i, 1).getNumericCellValue());
                product.setPurchaseAccount(purchaseAccount);
                product.setCapsuleToy(true);
                productRepository.save(product);
            }
        }
    }

    private void saveSalesAccount(Sheet sheet) {
        final int rowValue = sheet.getLastRowNum();

        for (int i = 1; i < rowValue; i++) {

            String salesAccountName = getCell(sheet, i, salesAccountIndex).getStringCellValue();
            SalesAccount salesAccount = salesAccountRepository.findByName(salesAccountName);

            if (salesAccount == null) {
                salesAccount = new SalesAccount();
                salesAccount.setName(salesAccountName);
                salesAccount.setIsUsing(true);

                CompanyType companyType = companyTypeRepository.findByName(getCell(sheet, i, companyTypeIndex).getStringCellValue());
                salesAccount.setCompanyType(companyType);

                if (!getCell(sheet, i, companyTypeIndex).getStringCellValue().equals(getCell(sheet, i, secondCompanyTypeIndex).getStringCellValue())) {
                    SecondCompanyType secondCompanyType = secondCompanyTypeRepository.findByName(getCell(sheet, i, secondCompanyTypeIndex).getStringCellValue());
                    salesAccount.setSecondCompanyType(secondCompanyType);
                }
                SalesAccountArea salesAccountArea = salesAccountAreaRepository.findByName(getCell(sheet, i, areaIndex).getStringCellValue());
                salesAccount.setSalesAccountArea(salesAccountArea);

                Employee employee = employeeRepository.findByName(getCell(sheet, i, employeeIndex).getStringCellValue());
                salesAccount.setEmployee(employee);

                TaxAccount taxAccount = taxAccountRepository.findByName(getCell(sheet, i, taxAccountIndex).getStringCellValue());
                salesAccount.setTaxaccount(taxAccount);

//                String date = getCell(sheet, i, entryDateIndex).getLocalDateTimeCellValue().toLocalDate();
//
//                try {
//                    if (!date.equals("") && !date.contains("-")) {
//                        StringBuilder stringBuilder = new StringBuilder(date);
//                        stringBuilder.insert(4, '-');
//                        stringBuilder.insert(7, '-');
//
//
//                        if (stringBuilder.length() == 8)
//                            stringBuilder.insert(5, '0');
//
//                        date = stringBuilder.toString();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//              }

                LocalDate entryDate = LocalDate.now().minusMonths(1).minusDays(12);
                salesAccount.setEntryDate(entryDate);

                String address = getCell(sheet, i, addressIndex).getStringCellValue();
                salesAccount.setAddress(address);

                int depositTypeInt;
                String depositType = getCell(sheet, i, depositTypeIndex).getStringCellValue();
                if (depositType.equals("입금")) {
                    depositTypeInt = 0;
                } else if (depositType.equals("현금수금")) {
                    depositTypeInt = 2;
                } else {
                    depositTypeInt = 1;
                }
                salesAccount.setDepositType(depositTypeInt);

                String ph = getCell(sheet, i, phIndex).getStringCellValue();
                salesAccount.setPh(ph);

                String representative = getCell(sheet, i, representativeIndex).getStringCellValue();
                salesAccount.setRepresentative(representative);

                int taxBillTypeInt;
                String taxBillType = getCell(sheet, i, taxBillTypeIndex).getStringCellValue();
                if (taxBillType.contains("수수료")) {
                    taxBillTypeInt = 2;
                } else {
                    if (taxBillType.contains("역발행")) {
                        taxBillTypeInt = 1;
                    } else {
                        taxBillTypeInt = 0;
                    }
                }
                salesAccount.setTaxBillType(taxBillTypeInt);

                Boolean isYuonetoy = getCell(sheet, i, isYuoneToyIndex).getStringCellValue().contains("토이") ? true : false;
                salesAccount.setIsYuoneToy(isYuonetoy);

                salesAccount = salesAccountRepository.save(salesAccount);

                SalesAccountMachine salesAccountMachine = new SalesAccountMachine();
                salesAccountMachine.setSalesAccount(salesAccount);
                salesAccountMachine.setFees(getCell(sheet, i, feeIndex).getNumericCellValue());
                salesAccountMachine.setInitialQuantity((int) getCell(sheet, i, initialQuantityIndex).getNumericCellValue());
                salesAccountMachine.setPlusValue(0);
                salesAccountMachine.setMinusValue(0);

                Machine machine = machineRepository.findByName("3단 자판기");
                salesAccountMachine.setMachine(machine);
                salesAccountMachine = salesAccountMachineRepository.save(salesAccountMachine);

                SalesAccountMachineStock salesAccountMachineStock = new SalesAccountMachineStock();
                salesAccountMachineStock.setSalesAccountMachine(salesAccountMachine);
                salesAccountMachineStock.setCount((int) getCell(sheet, i, machineCountIndex).getNumericCellValue());
                salesAccountMachineStockRepository.save(salesAccountMachineStock);

                SalesAccountProductStock salesAccountProductStock = new SalesAccountProductStock();
                salesAccountProductStock.setSalesAccountMachine(salesAccountMachine);
                salesAccountProductStock.setCount(0);

                Product product = productRepository.findByNameEquals("캡슐 토이");
                salesAccountProductStock.setProduct(product);
                salesAccountProductStockRepository.save(salesAccountProductStock);

                BusinessJournalHistoryList businessJournalHistoryList = new BusinessJournalHistoryList();
                businessJournalHistoryList.setType("신규 입점");
                businessJournalHistoryList.setDate(entryDate);
                businessJournalHistoryList.setSalesAccount(salesAccount);
                businessJournalHistoryList = businessJournalHistoryListRepository.save(businessJournalHistoryList);

                BusinessJournalHistory businessJournalHistory = new BusinessJournalHistory();
                businessJournalHistory.setSupplyCount(0);
                businessJournalHistory.setMachineCount((int) getCell(sheet, i, machineCountIndex).getNumericCellValue());
                businessJournalHistory.setFees(getCell(sheet, i, feeIndex).getNumericCellValue());
                businessJournalHistory.setInitialQuantity((int) getCell(sheet, i, initialQuantityIndex).getNumericCellValue());
                businessJournalHistory.setPlusValue(0);
                businessJournalHistory.setType("신규 입점");
                businessJournalHistory.setMinusValue(0);
                businessJournalHistory.setProduct(product);
                businessJournalHistory.setSalesAccountMachine(salesAccountMachine);
                businessJournalHistory.setRefundValue((long) 0);
                businessJournalHistory.setSalesValue((long) 0);
                businessJournalHistory.setBusinessJournalHistoryList(businessJournalHistoryList);
                businessJournalHistoryRepository.save(businessJournalHistory);

                businessJournalHistoryService.newMachineRevisionHistory(businessJournalHistory, salesAccount, entryDate);
            }
        }
    }

    @Autowired
    BusinessJournalHistoryService businessJournalHistoryService;

    private void saveETC(Sheet sheet) {
        final int rowValue = sheet.getLastRowNum();

        for (int i = 1; i < rowValue; i++) {
            companyTypes.add(getCell(sheet, i, companyTypeIndex).getStringCellValue());
            secondCompanyTypes.put(getCell(sheet, i, secondCompanyTypeIndex).getStringCellValue(), getCell(sheet, i, companyTypeIndex).getStringCellValue());
            areas.add(getCell(sheet, i, areaIndex).getStringCellValue());
            employees.add(getCell(sheet, i, employeeIndex).getStringCellValue());
            taxAccounts.add(getCell(sheet, i, taxAccountIndex).getStringCellValue());
        }

        saveCompanyType(companyTypes);
        saveSecondCompanyType(secondCompanyTypes);
        saveAreaType(areas);
        saveEmployee(employees);
        saveTaxAccount(taxAccounts);
    }

    public Workbook getWorkbook(String filename, String version) {

        try {
            FileInputStream stream = new FileInputStream(filename);
            //표준 xls 버젼
            if ("xls".equals(version)) {
                return new HSSFWorkbook(stream);
                //확장 xlsx 버젼
            } else if ("xlsx".equals(version)) {
                return new XSSFWorkbook(stream);
            }
            throw new NoClassDefFoundError();
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cell getCell(Row row, int cellnum) {
        Cell cell = row.getCell(cellnum);
        if (cell == null) {
            cell = row.createCell(cellnum);
        }
        return cell;
    }

    public Cell getCell(Sheet sheet, int rownum, int cellnum) {
        Row row = getRow(sheet, rownum);
        return getCell(row, cellnum);
    }

    public Row getRow(Sheet sheet, int rownum) {
        Row row = sheet.getRow(rownum);
        if (row == null) {
            row = sheet.createRow(rownum);
        }
        return row;
    }

    public void saveCompanyType(HashSet<String> companyTypes) {
        for (String companyTypeName : companyTypes) {
            if (companyTypeName.equals("") || companyTypeName == null)
                continue;
            CompanyType newCompanyType = companyTypeRepository.findByName(companyTypeName);
            if (newCompanyType == null) {
                newCompanyType = new CompanyType();
                newCompanyType.setName(companyTypeName);
                companyTypeRepository.save(newCompanyType);
            }
        }
    }

    public void saveSecondCompanyType(Hashtable<String, String> secondCompanyTypes) {
        for (String secondCompanyTypeName : secondCompanyTypes.keySet()) {
            if (secondCompanyTypeName.equals("") || secondCompanyTypeName == null)
                continue;
            SecondCompanyType newSecondCompanyType = secondCompanyTypeRepository.findByName(secondCompanyTypeName);
            if (newSecondCompanyType == null) {
                if (secondCompanyTypes.get(secondCompanyTypeName).equals(secondCompanyTypeName))
                    continue;

                CompanyType companyType = companyTypeRepository.findByName(secondCompanyTypes.get(secondCompanyTypeName));
                newSecondCompanyType = new SecondCompanyType();
                newSecondCompanyType.setName(secondCompanyTypeName);
                newSecondCompanyType.setCompanyType(companyType);
                secondCompanyTypeRepository.save(newSecondCompanyType);
            }
        }
    }

    public void saveAreaType(HashSet<String> Areas) {
        for (String areaName : Areas) {
            if (areaName.equals("") || areaName == null)
                continue;
            SalesAccountArea newArea = salesAccountAreaRepository.findByName(areaName);
            if (newArea == null) {
                newArea = new SalesAccountArea();
                newArea.setName(areaName);
                salesAccountAreaRepository.save(newArea);
            }
        }
    }

    public void saveEmployee(HashSet<String> employees) {
        for (String employeeName : employees) {
            if (employeeName.equals("") || employeeName == null)
                continue;
            Employee newEmployee = employeeRepository.findByName(employeeName);
            if (newEmployee == null) {
                newEmployee = new Employee();
                newEmployee.setName(employeeName);
                newEmployee.setEntryDate(LocalDate.now().minusMonths(1).minusDays(12));
                employeeRepository.save(newEmployee);
            }
        }
    }

    public void saveTaxAccount(HashSet<String> taxAccounts) {
        for (String taxAccountName : taxAccounts) {
            if (taxAccountName.equals("") || taxAccountName == null)
                continue;
            TaxAccount newTaxAccount = taxAccountRepository.findByName(taxAccountName);
            if (newTaxAccount == null) {
                newTaxAccount = new TaxAccount();
                newTaxAccount.setName(taxAccountName);
                newTaxAccount.setEntryDate(LocalDate.now().minusMonths(1).minusDays(12));
                taxAccountRepository.save(newTaxAccount);
            }
        }
    }

    public PurchaseAccount savePurchaseAccount() {
        PurchaseAccount newPurchaseAccount = new PurchaseAccount();
        newPurchaseAccount.setName("지정 안됨");
        newPurchaseAccount = purchaseAccountRepository.save(newPurchaseAccount);

        return newPurchaseAccount;
    }
}
