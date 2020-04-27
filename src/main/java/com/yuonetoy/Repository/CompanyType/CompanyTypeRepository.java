package com.yuonetoy.Repository.CompanyType;

import com.yuonetoy.Entitiy.Company;
import com.yuonetoy.Entitiy.CompanyType.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {
    CompanyType findByName(String s);
}
