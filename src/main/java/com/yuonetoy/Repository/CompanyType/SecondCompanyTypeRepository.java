package com.yuonetoy.Repository.CompanyType;

import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import com.yuonetoy.Entitiy.CompanyType.SecondCompanyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SecondCompanyTypeRepository extends JpaRepository<SecondCompanyType, Long> {
    List<SecondCompanyType> findByCompanyType(CompanyType companyType);

    SecondCompanyType findByName(String s);
}
