package com.yuonetoy.Entitiy;

import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Tool.LocalDatePersistenceConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BusinessJournalHistoryList")
public class BusinessJournalHistoryList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BJHL_ID")
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SA_FK", referencedColumnName = "SA_ID")
    private SalesAccount salesAccount;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name = "DATE")
    private LocalDate date;

    @OneToMany(mappedBy = "businessJournalHistoryList", fetch = FetchType.LAZY)
    List<BusinessJournalHistory> businessJournalHistory;
}
