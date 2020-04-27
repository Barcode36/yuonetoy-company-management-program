package com.yuonetoy.Repository.Account;

import com.yuonetoy.Entitiy.Account.PurchaseAccount;
import com.yuonetoy.Entitiy.Account.SalesAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseAccountRepository extends JpaRepository<PurchaseAccount, Long> {
    PurchaseAccount findByName(String s);
}
