package com.agrosatmonitor.api.entity;

import com.agrosatmonitor.api.enums.NivelSaudeVegetacao;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Monitoramento de vegetação (NDVI) — HERDA de MonitoramentoBase.
 * Armazenado em TB_MON_VEGETACAO.
 */
@Entity
@Table(name = "TB_MON_VEGETACAO")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MonitoramentoVegetacao extends MonitoramentoBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_veg")
    @SequenceGenerator(name = "seq_veg", sequenceName = "SQ_MON_VEG", allocationSize = 1)
    @Column(name = "ID_MON_VEG")
    private Long id;
    @Column(name = "NR_NDVI", nullable = false)
    private Double ndvi;

    @Column(name = "TP_NIVEL_SAUDE", nullable = false)
    private Integer nivelSaudeVegetacaoCodigo;

    @Transient
    public NivelSaudeVegetacao getNivelSaudeVegetacao() {
        return nivelSaudeVegetacaoCodigo != null
                ? NivelSaudeVegetacao.fromCodigo(nivelSaudeVegetacaoCodigo)
                : null;
    }

    public void setNivelSaudeVegetacao(NivelSaudeVegetacao nivel) {
        this.nivelSaudeVegetacaoCodigo = nivel.getCodigo();
    }

    @Column(name = "DT_LEITURA", nullable = false)
    private LocalDateTime dataLeitura;

    @Override
    public String descricaoTipo() {
        return String.format("Vegetação: NDVI=%.4f (%s)",
                ndvi, getNivelSaudeVegetacao() != null ? getNivelSaudeVegetacao().name() : "N/A");
    }
}
