package com.yuonetoy.Tool;

import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountDTO;
import com.yuonetoy.DTO.Account.SalesAccount.SalesAccountPreviewDTO;
import com.yuonetoy.DTO.Area.SalesAccountAreaDTO;
import com.yuonetoy.DTO.CompanyType.CompanyTypeDTO;
import com.yuonetoy.DTO.CompanyType.SecondCompanyTypeDTO;
import com.yuonetoy.DTO.Employee.EmployeeDTO;
import com.yuonetoy.DTO.MachineDTO;
import com.yuonetoy.DTO.SalesAccountMonthSalesDTO;
import com.yuonetoy.DTO.SalesAccountTotalSalesDTO;
import com.yuonetoy.DTO.Stock.*;
import com.yuonetoy.DTO.StockHistory.SendStockHistoryDTO;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Area.SalesAccountArea;
import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import com.yuonetoy.Entitiy.CompanyType.SecondCompanyType;
import com.yuonetoy.Entitiy.SalesAccountMachine.SalesAccountMachineSales;
import javafx.scene.control.ComboBox;
import org.controlsfx.control.CheckComboBox;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Predicate;

@Configuration
public class PredicateCase {

    public Predicate<SalesAccountTotalSalesDTO> getSaleAccountTotalSalesFilter(CheckComboBox checkComboBox, String type) {
        Predicate<SalesAccountTotalSalesDTO> filter = salesAccountTotalSalesDTO -> {
            if (checkComboBox.getCheckModel().getCheckedIndices().size() == 0)
                return true;

            boolean Check = false;
            for (Object object : checkComboBox.getCheckModel().getCheckedItems()) {
                String targetString = (String) object;
                switch (type) {
                    case "area":
                        Check |= targetString.hashCode() == (salesAccountTotalSalesDTO.getSalesAccountArea().hashCode());
                        break;
                    case "employee":
                        Check |= targetString.hashCode() == (salesAccountTotalSalesDTO.getEmployee().hashCode());
                        break;
                    case "companyType":
                        Check |= targetString.hashCode() == (salesAccountTotalSalesDTO.getCompanyType().hashCode());
                        break;
                    case "secondCompanyType":
                        Check |= targetString.hashCode() == (salesAccountTotalSalesDTO.getSecondCompanyType().hashCode());
                        break;
                    case "salesAccountMachine":
                        Check |= targetString.hashCode() == (salesAccountTotalSalesDTO.getSalesAccountMachineName().hashCode());
                        break;
                }
            }
            return Check;
        };

        return filter;
    }

    public Predicate<SalesAccountTotalSalesDTO> getSaleAccountTotalSalesFilter(ComboBox<String> comboBox) {
        Predicate<SalesAccountTotalSalesDTO> filter = salesAccountTotalSalesDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (salesAccountTotalSalesDTO.getSalesAccount().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<SalesAccountMonthSalesDTO> getSaleAccountMonthSalesFilter(CheckComboBox checkComboBox, String type) {
        Predicate<SalesAccountMonthSalesDTO> filter = salesAccountMonthSalesDTO -> {
            if (checkComboBox.getCheckModel().getCheckedIndices().size() == 0)
                return true;

            boolean Check = false;
            for (Object object : checkComboBox.getCheckModel().getCheckedItems()) {
                String targetString = (String) object;

                switch (type) {
                    case "area":
                        Check |= targetString.hashCode() == (salesAccountMonthSalesDTO.getSalesAccountArea().hashCode());
                        break;
                    case "employee":
                        Check |= targetString.hashCode() == (salesAccountMonthSalesDTO.getEmployee().hashCode());
                        break;
                    case "companyType":
                        Check |= targetString.hashCode() == (salesAccountMonthSalesDTO.getCompanyType().hashCode());
                        break;
                    case "secondCompanyType":
                        Check |= targetString.hashCode() == (salesAccountMonthSalesDTO.getSecondCompanyType().hashCode());
                        break;
                }
            }
            return Check;
        };

        return filter;
    }

    public Predicate<SalesAccountMonthSalesDTO> getSaleAccountMonthSalesFilter(ComboBox<String> comboBox) {
        Predicate<SalesAccountMonthSalesDTO> filter = salesAccountMonthSalesDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (salesAccountMonthSalesDTO.getSalesAccount().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<SalesAccountMachineStockAtStockViewDTO> getSalesAccountMachineStockAtStockViewDTOSalesAccountFilter(ComboBox<String> comboBox) {
        Predicate<SalesAccountMachineStockAtStockViewDTO> filter = SalesAccountMachineStockAtStockViewDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (SalesAccountMachineStockAtStockViewDTO.getSalesAccountName().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<SalesAccountMachineStockAtStockViewDTO> getSalesAccountMachineStockAtStockViewDTOSalesAccountStockFilter(ComboBox<String> comboBox) {
        Predicate<SalesAccountMachineStockAtStockViewDTO> filter = SalesAccountMachineStockAtStockViewDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (SalesAccountMachineStockAtStockViewDTO.getSalesAccountMachine().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<SalesAccountProductStockAtStockViewDTO> getSalesAccountProductStockAtStockViewDTOSalesAccountFilter(ComboBox<String> comboBox) {
        Predicate<SalesAccountProductStockAtStockViewDTO> filter = SalesAccountProductStockAtStockViewDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (SalesAccountProductStockAtStockViewDTO.getSalesAccountName().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<SalesAccountProductStockAtStockViewDTO> getSalesAccountProductStockAtStockViewDTOSalesAccountStockFilter(ComboBox<String> comboBox) {
        Predicate<SalesAccountProductStockAtStockViewDTO> filter = SalesAccountProductStockAtStockViewDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (SalesAccountProductStockAtStockViewDTO.getProduct().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<EmployeeMachineStockAtStockViewDTO> getEmployeeMachineStockAtStockViewDTOEmployeeFilter(CheckComboBox checkComboBox) {
        Predicate<EmployeeMachineStockAtStockViewDTO> filter = EmployeeMachineStockAtStockViewDTO -> {
            if (checkComboBox.getCheckModel().getCheckedIndices().size() == 0)
                return true;

            boolean Check = false;
            for (Object object : checkComboBox.getCheckModel().getCheckedItems()) {
                String targetString = (String) object;

                Check |= targetString.hashCode() == (EmployeeMachineStockAtStockViewDTO.getEmployee().hashCode());
            }
            return Check;
        };

        return filter;
    }

    public Predicate<EmployeeMachineStockAtStockViewDTO> getEmployeeMachineStockAtStockViewDTOEmployeeStockFilter(ComboBox<String> comboBox) {
        Predicate<EmployeeMachineStockAtStockViewDTO> filter = EmployeeMachineStockAtStockViewDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (EmployeeMachineStockAtStockViewDTO.getMachine().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<EmployeeProductStockAtStockViewDTO> getEmployeeProductStockAtStockViewDTOEmployeeFilter(CheckComboBox checkComboBox) {
        Predicate<EmployeeProductStockAtStockViewDTO> filter = EmployeeProductStockAtStockViewDTO -> {
            if (checkComboBox.getCheckModel().getCheckedIndices().size() == 0)
                return true;

            boolean Check = false;
            for (Object object : checkComboBox.getCheckModel().getCheckedItems()) {
                String targetString = (String) object;

                Check |= targetString.hashCode() == (EmployeeProductStockAtStockViewDTO.getEmployee().hashCode());
            }
            return Check;
        };

        return filter;
    }

    public Predicate<EmployeeProductStockAtStockViewDTO> getEmployeeProductStockAtStockViewDTOEmployeeStockFilter(ComboBox<String> comboBox) {
        Predicate<EmployeeProductStockAtStockViewDTO> filter = EmployeeProductStockAtStockViewDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (EmployeeProductStockAtStockViewDTO.getProduct().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<CompanyMachineStockAtStockViewDTO> getCompanyMachineStockAtStockViewDTOCompanyFilter(CheckComboBox checkComboBox) {
        Predicate<CompanyMachineStockAtStockViewDTO> filter = companyMachineStockAtStockViewDTO -> {
            if (checkComboBox.getCheckModel().getCheckedIndices().size() == 0)
                return true;

            boolean Check = false;
            for (Object object : checkComboBox.getCheckModel().getCheckedItems()) {
                String targetString = (String) object;

                Check |= targetString.hashCode() == (companyMachineStockAtStockViewDTO.getCompany().hashCode());
            }
            return Check;
        };

        return filter;
    }

    public Predicate<CompanyMachineStockAtStockViewDTO> getCompanyMachineStockAtStockViewDTOCompanyStockFilter(ComboBox<String> comboBox) {
        Predicate<CompanyMachineStockAtStockViewDTO> filter = companyMachineStockAtStockViewDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (companyMachineStockAtStockViewDTO.getMachine().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<CompanyProductStockAtStockViewDTO> getCompanyProductStockAtStockViewDTOCompanyFilter(CheckComboBox checkComboBox) {
        Predicate<CompanyProductStockAtStockViewDTO> filter = companyProductStockAtStockViewDTO -> {
            if (checkComboBox.getCheckModel().getCheckedIndices().size() == 0)
                return true;

            boolean Check = false;
            for (Object object : checkComboBox.getCheckModel().getCheckedItems()) {
                String targetString = (String) object;

                Check |= targetString.hashCode() == (companyProductStockAtStockViewDTO.getCompany().hashCode());
            }
            return Check;
        };

        return filter;
    }

    public Predicate<CompanyProductStockAtStockViewDTO> getCompanyProductStockAtStockViewDTOCompanyStockFilter(ComboBox<String> comboBox) {
        Predicate<CompanyProductStockAtStockViewDTO> filter = companyProductStockAtStockViewDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (companyProductStockAtStockViewDTO.getProduct().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<SalesAccountPreviewDTO> getSalesAccountPreviewDTOFilter(CheckComboBox<String> checkComboBox, String type) {
        Predicate<SalesAccountPreviewDTO> filter = salesAccountPreviewDTO -> {
            if (checkComboBox.getCheckModel().getCheckedIndices().size() == 0)
                return true;

            boolean check = false;
            for (Object object : checkComboBox.getCheckModel().getCheckedItems()) {
                String targetString = (String) object;

                switch (type) {
                    case "area":
                        check |= targetString.hashCode() == (salesAccountPreviewDTO.getSalesAccountAreaName().hashCode());
                        break;
                    case "employee":
                        check |= targetString.hashCode() == (salesAccountPreviewDTO.getEmployeeName().hashCode());
                        break;
                    case "companyType":
                        check |= targetString.hashCode() == (salesAccountPreviewDTO.getCompanyTypeName().hashCode());
                        break;
                    case "secondCompanyType":
                        if (salesAccountPreviewDTO.getSecondCompanyTypeName() == null)
                            check = false;
                        else
                            check |= targetString.hashCode() == (salesAccountPreviewDTO.getSecondCompanyTypeName().hashCode());
                        break;
                }
            }
            return check;
        };

        return filter;
    }

    public Predicate<SalesAccountPreviewDTO> getSalesAccountPreviewDTOFilter(ComboBox<String> comboBox) {
        Predicate<SalesAccountPreviewDTO> filter = SalesAccountPreviewDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("")) {
                Check = true;
            } else {
                Check |= (SalesAccountPreviewDTO.getName().contains(targetString));
            }
            return Check;
        };

        return filter;
    }

    public Predicate<SendStockHistoryDTO> getSendStockHistoryDTOFilter(ComboBox<String> comboBox, String type) {
        Predicate<SendStockHistoryDTO> filter = sendStockHistoryDTO -> {
            boolean Check = false;
            String targetString = comboBox.getSelectionModel().getSelectedItem();

            if (targetString == null || targetString.equals("전체") || targetString.equals("")) {
                Check = true;
            } else {
                switch (type) {
                    case "type":
                        Check |= targetString.hashCode() == (sendStockHistoryDTO.getType().hashCode());
                        break;
                    case "source":
                        Check |= sendStockHistoryDTO.getSourceName().contains(targetString);
                        break;
                    case "target":
                        Check |= sendStockHistoryDTO.getTargetName().contains(targetString);
                        break;
                    case "stock":
                        Check |= sendStockHistoryDTO.getStockName().contains(targetString);
                        break;
                }
            }
            return Check;
        };

        return filter;
    }
}
