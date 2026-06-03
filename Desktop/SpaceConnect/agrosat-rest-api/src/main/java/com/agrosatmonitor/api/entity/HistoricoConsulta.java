package com.agrosatmonitor.api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_HISTORICO_CONSULTA")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistoricoConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_historico")
    @SequenceGenerator(name = "sq_historico", sequenceName = "SQ_HISTORICO", allocationSize = 1)
    @Column(name = "ID_HISTORICO")
    private Long id;

    @Column(name = "DS_ENDPOINT", length = 300)
    private String endpointConsultado;

    @Column(name = "DT_CONSULTA", nullable = false)
    private LocalDateTime dataConsulta;

    @Column(name = "NR_TEMPO_RESP_MS", nullable = false)
    private Long tempoRespostaMs;

    @Column(name = "FL_SUCESSO", nullable = false)
    private Integer sucesso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FAZENDA", nullable = false)
    private Fazenda fazenda;

    public boolean isSucesso() { return sucesso != null && sucesso == 1; }

    public void setSucessoBool(boolean val) { this.sucesso = val ? 1 : 0; }

    @PrePersist
    protected void prePersist() {
        if (dataConsulta == null) dataConsulta = LocalDateTime.now();
        if (sucesso == null) sucesso = 1;
    }
}
