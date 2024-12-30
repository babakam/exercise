package com.me.gerimedica.exercise.adapters.out.jpa.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "csv_record")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CSVRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String source;

    @Column(name = "code_list_code")
    private String codeListCode;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "display_value")
    private String displayValue;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "sorting_priority")
    private Integer sortingPriority;
}
