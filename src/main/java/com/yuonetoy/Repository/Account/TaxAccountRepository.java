package com.yuonetoy.Repository.Account;

import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Account.TaxAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxAccountRepository extends JpaRepository<TaxAccount, Long> {
    List<TaxAccount> findByNameContaining(String searchText);

    TaxAccount findByName(String s);
}
