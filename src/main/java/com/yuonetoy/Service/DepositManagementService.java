package com.yuonetoy.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yuonetoy.DTO.Company.CompanyDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.SalesAccountSalesDepoistViewDTO;
import com.yuonetoy.Entitiy.Account.QSalesAccount;
import com.yuonetoy.Entitiy.Account.QTaxAccount;
import com.yuonetoy.Entitiy.BusinessJournalHistory;
import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import com.yuonetoy.Entitiy.CompanyType.QCompanyType;
import com.yuonetoy.Entitiy.CompanyType.QSecondCompanyType;
import com.yuonetoy.Entitiy.QEmployee;
import com.yuonetoy.Entitiy.SalesAccountMachine.QSalesAccountMachine;
import com.yuonetoy.Entitiy.SalesAccountMachine.QSalesAccountMachineSales;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachineSales;
import com.yuonetoy.Repository.BusinessJournalHistoryRepository;
import com.yuonetoy.Repository.SalesAccountMachineSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

@Service
public class DepositManagementService {
    @Autowired
    private SalesAccountMachineSalesRepository salesAccountMachineSalesRepository;
    @Autowired
    private CompanyTypeService companyTypeService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EntityManager entityManager;

    public List<CompanyTypeDTO> getCompanyType() {
        return companyTypeService.readCompanyType();
    }

    public List<SecondCompanyTypeDTO> getSecondCompanyType() {
        return companyTypeService.readSecondCompanyType();
    }

    public List<EmployeeDTO> getEmployee() {
        return employeeService.readEmployeeDTO();
    }


    public List<SalesAccountSalesDepoistViewDTO> getSalesInfo(List<Integer> taxBillTypeId, List<Integer> depositTypeId, List<Long> companyTypeId,
                                                              List<Long> secondCompanyTypeId, List<Long> employeeId, String salesAccountName,
                                                              String taxAccountName, LocalDate firstDate, LocalDate lastDate) {

        QSalesAccountMachineSales sams = new QSalesAccountMachineSales("sams");
        QSalesAccountMachine sam = new QSalesAccountMachine("sam");
        QSalesAccount sa = new QSalesAccount("sa");
        QTaxAccount ta = new QTaxAccount("ta");
        QEmployee e = new QEmployee("e");
        QCompanyType c = new QCompanyType("c");
        QSecondCompanyType sc = new QSecondCompanyType("sc");

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(sams.date.goe(firstDate))
                .and(sams.date.loe(lastDate));

        if (!taxBillTypeId.isEmpty()) {
            builder.and(sa.taxBillType.in(taxBillTypeId));
        }
        if (!depositTypeId.isEmpty()) {
            builder.and(sa.depositType.in(depositTypeId));
        }
        if (!employeeId.isEmpty()) {
            builder.and(e.id.in(employeeId));
        }

        if (!companyTypeId.isEmpty()) {
            builder.and(sa.companyType.id.in(companyTypeId));
        }

        if (!secondCompanyTypeId.isEmpty()) {
            builder.and(sa.secondCompanyType.id.in(secondCompanyTypeId));
        }

        if (!salesAccountName.equals("")) {
            builder.and(sa.name.like("%" + taxAccountName + "%"));
        }

        if (!taxAccountName.equals("")) {
            builder.and(ta.name.like("%" + taxAccountName + "%"));
        }

        JPAQuery<SalesAccountSalesDepoistViewDTO> query = new JPAQuery(entityManager);
        query.select(Projections.constructor(SalesAccountSalesDepoistViewDTO.class,
                sams.id, sams.confirmationOfPayment, sams.date,
                sa.taxBillType, sa.depositType, c.name,
                sc.name, e.name, ta.name,
                sa.name, sams.sales.sum(), sams.balance.sum(),
                sams.depositAmount.sum(), sams.depositDate))
                .from(sams)
                .innerJoin(sams.salesAccountMachine, sam)
                .innerJoin(sam.salesAccount, sa)
                .innerJoin(sa.taxaccount, ta)
                .innerJoin(sa.employee, e)
                .innerJoin(sa.companyType, c)
                .leftJoin(sa.secondCompanyType, sc)
                .where(builder)
                .groupBy(sa.id)
                .orderBy(sa.name.asc());

        List<SalesAccountSalesDepoistViewDTO> salesAccountSalesDepoistViewDTOList = query.fetch();

        return salesAccountSalesDepoistViewDTOList;
    }

    public void updateSalesInfo(SalesAccountSalesDepoistViewDTO salesAccountSalesDepoistViewDTO) {
        Long salesAccountMachineSaleId = salesAccountSalesDepoistViewDTO.getId();
        Boolean confirmationOfPayment = salesAccountSalesDepoistViewDTO.getConfirmationOfPayment().equals(null) ? false : true;
        LocalDate depositDate = salesAccountSalesDepoistViewDTO.getDepositDate();
        Long depositAmount = salesAccountSalesDepoistViewDTO.getDepositAmount();

        SalesAccountMachineSales salesAccountMachineSales = salesAccountMachineSalesRepository.findById(salesAccountMachineSaleId).get();
        salesAccountMachineSales.setConfirmationOfPayment(confirmationOfPayment);
        salesAccountMachineSales.setDepositAmount(depositAmount);
        salesAccountMachineSales.setDepositDate(depositDate);
        salesAccountMachineSalesRepository.save(salesAccountMachineSales);
    }
}
