package com.yuonetoy.Repository;

import com.yuonetoy.DTO.BusinessJournalHistoryListDTO;
import com.yuonetoy.Entitiy.BusinessJournalHistoryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BusinessJournalHistoryListRepository extends JpaRepository<BusinessJournalHistoryList, Long> {

    @Query(value = "select new com.yuonetoy.DTO.BusinessJournalHistoryListDTO(bjhl.id, bjhl.type, sa.name, e.name, bjhl.date) " +
            "from BusinessJournalHistoryList bjhl " +
            "join bjhl.salesAccount sa " +
            "join sa.employee e " +
            "where bjhl.date >= :firstDate " +
            "and bjhl.date <= :lastDate " +
            "and sa.id = :saId " +
            "order by bjhl.date desc")
    List<BusinessJournalHistoryListDTO> findByAllBusinessJournalHistoryListDTO(@Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate, @Param("saId") Long saId);

    @Query(value = "select new com.yuonetoy.DTO.BusinessJournalHistoryListDTO(bjhl.id, bjhl.type, sa.name, e.name, bjhl.date) " +
            "from BusinessJournalHistoryList bjhl " +
            "join bjhl.salesAccount sa " +
            "join sa.employee e " +
            "where bjhl.date >= :firstDate " +
            "and bjhl.date <= :lastDate " +
            "order by bjhl.date desc ")
    List<BusinessJournalHistoryListDTO> findByAllBusinessJournalHistoryListDTO(@Param("firstDate") LocalDate firstDate, @Param("lastDate") LocalDate lastDate);


    @Query(value = "select bjhl " +
            "from BusinessJournalHistoryList bjhl " +
            "join fetch bjhl.businessJournalHistory " +
            "where bjhl.id = :id")
    BusinessJournalHistoryList findByBusinessJournalHistoryId(@Param("id") long id);
}
