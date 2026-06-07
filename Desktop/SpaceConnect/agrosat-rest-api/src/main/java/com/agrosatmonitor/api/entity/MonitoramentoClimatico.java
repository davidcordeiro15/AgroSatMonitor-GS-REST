package com.agrosatmonitor.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Monitoramento climático — HERDA de MonitoramentoBase.
 * Armazenado em TB_MON_CLIMATICO.
 */
@Entity
@Table(name = "TB_MON_CLIMATICO")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MonitoramentoClimatico extends MonitoramentoBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cli")
    @SequenceGenerator(name = "seq_cli", sequenceName = "SQ_MON_CLI", allocationSize = 1)
    @Column(name = "ID_MON_CLI")
    private Long id;

    @Column(name = "NR_TEMPERATURA", nullable = false)
    private Double temperatura;

    @Column(name = "NR_UMIDADE", nullable = false)
    private Double umidade;

    @Column(name = "NR_PRECIPITACAO", nullable = false)
    private Double precipitacao;

    @Column(name = "NR_VEL_VENTO", nullable = false)
    private Double velocidadeVento;

    @Column(name = "DT_LEITURA", nullable = false)
    private LocalDateTime dataLeitura;

    @Override
    public String descricaoTipo() {
        return String.format("Climático: %.1f°C, %.0f%% umidade", temperatura, umidade);
    }
}
