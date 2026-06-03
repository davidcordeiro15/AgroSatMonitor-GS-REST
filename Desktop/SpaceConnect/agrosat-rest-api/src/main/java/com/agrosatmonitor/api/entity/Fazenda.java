package com.agrosatmonitor.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_FAZENDA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Fazenda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_fazenda")
    @SequenceGenerator(name = "sq_fazenda", sequenceName = "SQ_FAZENDA", allocationSize = 1)
    @Column(name = "ID_FAZENDA")
    private Long id;

    @Column(name = "NM_FAZENDA", nullable = false, length = 200)
    private String nome;

    @Column(name = "NR_LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "NR_LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "NR_AREA_HECTARES")
    private Double areaHectares;

    @Column(name = "NM_CIDADE", nullable = false, length = 100)
    private String cidade;

    @Column(name = "SG_ESTADO", nullable = false, length = 2)
    private String estado;

    @Column(name = "DT_CADASTRO", nullable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "fazenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CulturaAgricola> culturas = new ArrayList<>();

    @OneToMany(mappedBy = "fazenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MonitoramentoClimatico> monitoramentosClimaticos = new ArrayList<>();

    @OneToMany(mappedBy = "fazenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MonitoramentoVegetacao> monitoramentosVegetacao = new ArrayList<>();

    @OneToMany(mappedBy = "fazenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AlertaAgricola> alertas = new ArrayList<>();

    @OneToMany(mappedBy = "fazenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<HistoricoConsulta> historicosConsulta = new ArrayList<>();

    @PrePersist
    protected void prePersist() {
        if (dataCadastro == null) dataCadastro = LocalDateTime.now();
    }
}
