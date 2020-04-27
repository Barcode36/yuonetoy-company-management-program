package com.yuonetoy.Model;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;

import java.util.List;

public class CheckedLists {
    List<EmployeeDTO> checkedEmployeeList;
    List<SalesAccountAreaDTO> checkedAreaList;
    List<CompanyTypeDTO> checkedCompanyTypeList;
    List<SecondCompanyTypeDTO> checkedSecondCompanyTypeList;
    List<SalesAccountDTO> checkedSalesAccountList;
    List<MachineDTO> checkedMachineList;

    public CheckedLists(List<EmployeeDTO> checkedEmployeeList, List<SalesAccountAreaDTO> checkedAreaList, List<CompanyTypeDTO> checkedCompanyTypeList, List<SecondCompanyTypeDTO> checkedSecondCompanyTypeList, List<SalesAccountDTO> checkedSalesAccountList, List<MachineDTO> checkedMachineList) {
        this.checkedEmployeeList = checkedEmployeeList;
        this.checkedAreaList = checkedAreaList;
        this.checkedCompanyTypeList = checkedCompanyTypeList;
        this.checkedSecondCompanyTypeList = checkedSecondCompanyTypeList;
        this.checkedSalesAccountList = checkedSalesAccountList;
        this.checkedMachineList = checkedMachineList;
    }

    public List<EmployeeDTO> getCheckedEmployeeList() {
        return checkedEmployeeList;
    }

    public void setCheckedEmployeeList(List<EmployeeDTO> checkedEmployeeList) {
        this.checkedEmployeeList = checkedEmployeeList;
    }

    public List<SalesAccountAreaDTO> getCheckedAreaList() {
        return checkedAreaList;
    }

    public void setCheckedAreaList(List<SalesAccountAreaDTO> checkedAreaList) {
        this.checkedAreaList = checkedAreaList;
    }

    public List<CompanyTypeDTO> getCheckedCompanyTypeList() {
        return checkedCompanyTypeList;
    }

    public void setCheckedCompanyTypeList(List<CompanyTypeDTO> checkedCompanyTypeList) {
        this.checkedCompanyTypeList = checkedCompanyTypeList;
    }

    public List<SecondCompanyTypeDTO> getCheckedSecondCompanyTypeList() {
        return checkedSecondCompanyTypeList;
    }

    public void setCheckedSecondCompanyTypeList(List<SecondCompanyTypeDTO> checkedSecondCompanyTypeList) {
        this.checkedSecondCompanyTypeList = checkedSecondCompanyTypeList;
    }

    public List<SalesAccountDTO> getCheckedSalesAccountList() {
        return checkedSalesAccountList;
    }

    public void setCheckedSalesAccountList(List<SalesAccountDTO> checkedSalesAccountList) {
        this.checkedSalesAccountList = checkedSalesAccountList;
    }

    public List<MachineDTO> getCheckedMachineList() {
        return checkedMachineList;
    }

    public void setCheckedMachineList(List<MachineDTO> checkedMachineList) {
        this.checkedMachineList = checkedMachineList;
    }
}