package com.agrosatmonitor.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Classe abstrata base para todos os registros de monitoramento.
 * Demonstra ABSTRAÇÃO e HERANÇA em POO.
 */
@MappedSuperclass
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public abstract class MonitoramentoBase {

    @Id
    private Long id;

    @Column(name = "NR_LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "NR_LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "DT_CRIACAO", nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FAZENDA", nullable = false)
    private Fazenda fazenda;

    @PrePersist
    protected void prePersist() {
        if (dataCriacao == null) dataCriacao = LocalDateTime.now();
    }

    /**
     * Método abstrato — demonstra POLIMORFISMO.
     * Cada subclasse descreve o tipo de monitoramento de forma diferente.
     */
    public abstract String descricaoTipo();
}
