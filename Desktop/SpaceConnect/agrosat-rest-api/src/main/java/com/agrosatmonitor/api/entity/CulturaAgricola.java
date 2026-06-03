package com.agrosatmonitor.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_CULTURA_AGRICOLA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CulturaAgricola {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_cultura")
    @SequenceGenerator(name = "sq_cultura", sequenceName = "SQ_CULTURA", allocationSize = 1)
    @Column(name = "ID_CULTURA")
    private Long id;

    @Column(name = "NM_CULTURA", nullable = false, length = 100)
    private String nome;

    @Column(name = "TP_CULTURA", nullable = false, length = 100)
    private String tipo;

    @Column(name = "DS_SAFRA", nullable = false, length = 20)
    private String safra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FAZENDA", nullable = false)
    private Fazenda fazenda;
}
