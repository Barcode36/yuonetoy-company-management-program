package com.yuonetoy.Repository;

import com.yuonetoy.Entitiy.SalesAccountRevisionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesAccountRevisionHistoryRepository extends JpaRepository<SalesAccountRevisionHistory, Long> {
    List<SalesAccountRevisionHistory> findByBusinessJournalHistoryId(Long id);
}
