package com.yuonetoy.Entitiy;

import com.yuonetoy.Entitiy.Account.SalesAccount;
import com.yuonetoy.Entitiy.Machine;
import com.yuonetoy.Tool.LocalDatePersistenceConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SalesAccount_RevisionHistory")
public class SalesAccountRevisionHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SA_RH_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SA_FK", referencedColumnName = "SA_ID")
    private SalesAccount salesAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "M_FK", referencedColumnName = "M_ID")
    private Machine machine;

    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "CONTENTS")
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BJH_FK", referencedColumnName = "BJH_ID")
    private BusinessJournalHistory businessJournalHistory;
}